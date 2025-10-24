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
import org.example.model.Page
import org.example.model.SpaceMarine
import org.example.model.requests.SpaceMarineCreateRequest
import org.example.model.requests.SpaceMarineUpdateRequest
import org.example.service.SpaceMarineService

@Path("/space-marines")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
open class SpaceMarineResource {
    @Inject
    private lateinit var service: SpaceMarineService

    @GET
    open fun getAll(
        @QueryParam("page") @DefaultValue("0") page: Int,
        @QueryParam("size") @DefaultValue("20") size: Int,
    ): Page<SpaceMarine> {
        require(page >= 0) { "page must be >= 0" }
        require(size in 1..100) { "size must be between 1 and 100" }
        return service.findAll(page, size)
    }

    @POST
    open fun create(
        @Valid entity: SpaceMarineCreateRequest,
    ): Response {
        val saved = service.create(entity)
        return Response.status(Response.Status.CREATED).entity(saved).build()
    }

    @GET
    @Path("/{id}")
    open fun getById(
        @PathParam("id") id: Int,
    ): SpaceMarine = service.findById(id)

    @PUT
    @Path("/{id}")
    open fun update(
        @PathParam("id") id: Int,
        @Valid update: SpaceMarineUpdateRequest,
    ): SpaceMarine = service.update(id, update)

    @DELETE
    @Path("/{id}")
    open fun delete(
        @PathParam("id") id: Int,
    ): Response {
        service.delete(id)
        return Response.status(Response.Status.NO_CONTENT).build()
    }
}
