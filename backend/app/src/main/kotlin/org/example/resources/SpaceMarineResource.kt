package org.example.resources

import jakarta.enterprise.context.RequestScoped
import jakarta.inject.Inject
import jakarta.validation.Valid
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.DELETE
import jakarta.ws.rs.DefaultValue
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.PUT
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.QueryParam
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.example.exceptions.NotFoundException
import org.example.model.Page
import org.example.model.SpaceMarine
import org.example.model.requests.SpaceMarineCreateRequest
import org.example.model.requests.SpaceMarineUpdateRequest
import org.example.service.ChapterService
import org.example.service.CoordinatesService
import org.example.service.SpaceMarineService
import java.util.logging.Logger

@Path("/space-marines")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
open class SpaceMarineResource {
    @Inject
    private lateinit var spaceMarineService: SpaceMarineService

    @Inject
    private lateinit var coordinatesService: CoordinatesService

    @Inject
    private lateinit var chapterService: ChapterService

    companion object {
        private val logger = Logger.getLogger(SpaceMarineResource::class.java.name)
    }

    @GET
    open fun getAll(
        @QueryParam("page") @DefaultValue("0") page: Int,
        @QueryParam("size") @DefaultValue("20") size: Int,
    ): Page<SpaceMarine> {
        logger.info("getAll called with page=$page, size=$size")
        require(page >= 0) { "page must be >= 0" }
        require(size in 1..100) { "size must be between 1 and 100" }
        return spaceMarineService.findAll(page, size)
    }

    @POST
    fun create(
        @Valid spaceMarine: SpaceMarineCreateRequest,
    ): SpaceMarine {
        logger.info("Received create request: $spaceMarine")
        logger.info(
            "Name: ${spaceMarine.name}, " +
                "CoordinatesId: ${spaceMarine.coordinatesId}, " +
                "Weapon: ${spaceMarine.weaponType}",
        )

        // Validate existence of referenced entities
        validateCoordinatesAndChapter(
            coordinatesId = spaceMarine.coordinatesId,
            chapterId = spaceMarine.chapterId,
        )

        return spaceMarineService.create(spaceMarine)
    }

    @GET
    @Path("/{id}")
    open fun getById(
        @PathParam("id") id: Int,
    ): SpaceMarine = spaceMarineService.findById(id)

    @PUT
    @Path("/{id}")
    open fun update(
        @PathParam("id") id: Int,
        @Valid update: SpaceMarineUpdateRequest,
    ): SpaceMarine {
        // Validate existence of referenced entities only if provided in update
        validateCoordinatesAndChapter(
            coordinatesId = update.coordinatesId,
            chapterId = update.chapterId,
        )
        return spaceMarineService.update(id, update)
    }

    @DELETE
    @Path("/{id}")
    open fun delete(
        @PathParam("id") id: Int,
    ): Response {
        spaceMarineService.delete(id)
        return Response.status(Response.Status.NO_CONTENT).build()
    }

    private fun validateCoordinatesAndChapter(
        coordinatesId: Long?,
        chapterId: Long?,
    ) {
        coordinatesId?.let {
            try {
                coordinatesService.findById(it)
            } catch (e: NotFoundException) {
                throw NotFoundException("Coordinates with ID $it not found")
            }
        }
        chapterId?.let {
            try {
                chapterService.findById(it)
            } catch (e: NotFoundException) {
                throw NotFoundException("Chapter with ID $it not found")
            }
        }
    }

    // 1. Сумма значений health
    @GET
    @Path("/health/sum")
    fun calculateHealthSum(): Long {
        logger.info("Handling health sum request")
        return spaceMarineService.sumHealth()
    }

    // 2. Среднее значение health
    @GET
    @Path("/health/average")
    fun calculateHealthAverage(): Double {
        logger.info("Handling health average request")
        return spaceMarineService.averageHealth()
    }
}
