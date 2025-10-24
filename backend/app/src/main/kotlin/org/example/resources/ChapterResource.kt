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
import org.example.model.Chapter
import org.example.service.ChapterService

@Path("/chapters")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
open class ChapterResource(
    private val service: ChapterService,
) {
    @GET
    open fun getAll(
        @QueryParam("page") @DefaultValue("0") page: Int,
        @QueryParam("size") @DefaultValue("20") size: Int,
    ) = service.findAll(page, size)

    @POST
    open fun create(
        @Valid chapter: Chapter,
    ): Response {
        val saved = service.create(chapter)
        return Response.status(Response.Status.CREATED).entity(saved).build()
    }

    @GET
    @Path("/{id}")
    open fun getById(
        @PathParam("id") id: Long,
    ) = service.findById(id)

    @PUT
    @Path("/{id}")
    open fun update(
        @PathParam("id") id: Long,
        @Valid chapter: Chapter,
    ) = service.update(id, chapter)

    @DELETE
    @Path("/{id}")
    open fun delete(
        @PathParam("id") id: Long,
    ): Response {
        service.delete(id)
        return Response.status(Response.Status.NO_CONTENT).build()
    }
}
