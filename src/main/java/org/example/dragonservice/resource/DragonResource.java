package org.example.dragonservice.resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.dragonservice.model.*;
import org.example.dragonservice.service.DragonService;



import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Path("/dragons")
public class DragonResource {
    private DragonService dragonService = new DragonService();

    // GET /dragons
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response getDragons(
            @HeaderParam("Authorization") String authHeader,
            @QueryParam("page") @DefaultValue("1") int page,
            @QueryParam("size") @DefaultValue("10") int size,
            @QueryParam("sort") String sort,
            @QueryParam("filter") String filter) {

        if (!isAuthorized(authHeader)) {
            return unauthorizedResponse();
        }

        List<Dragon> dragons = dragonService.getDragons(page, size, sort, filter);
        DragonList dragonList = new DragonList();
        dragonList.setDragons(dragons);

        return Response.ok(dragonList).build();
    }

    // POST /dragons
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response addDragon(
            @HeaderParam("Authorization") String authHeader,
            Dragon dragon) {

        if (!isAuthorized(authHeader)) {
            return unauthorizedResponse();
        }

        List<ErrorResponse.ErrorDetail> validationErrors = validateDragon(dragon);
        if (!validationErrors.isEmpty()) {
            return badRequestResponse(validationErrors);
        }

        try {
            dragonService.addDragon(dragon);
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return serverErrorResponse();
        }
    }

    // GET /dragons/group-by-name
    @GET
    @Path("/group-by-name")
    @Produces(MediaType.APPLICATION_XML)
    public Response groupByName(@HeaderParam("Authorization") String authHeader) {

        if (!isAuthorized(authHeader)) {
            return unauthorizedResponse();
        }

        List<DragonGroupByName> groupData = dragonService.groupByName();

        GenericEntity<List<DragonGroupByName>> entity = new GenericEntity<List<DragonGroupByName>>(groupData) {};
        return Response.ok(entity).build();
    }

    // GET /dragons/count-speaking
    @GET
    @Path("/count-speaking")
    @Produces(MediaType.APPLICATION_XML)
    public Response countSpeaking(@HeaderParam("Authorization") String authHeader) {

        if (!isAuthorized(authHeader)) {
            return unauthorizedResponse();
        }

        int count = dragonService.countSpeaking();

        DragonCount dragonCount = new DragonCount();
        dragonCount.setCount(count);

        return Response.ok(dragonCount).build();
    }

    // GET /dragons/search-by-name
    @GET
    @Path("/search-by-name")
    @Produces(MediaType.APPLICATION_XML)
    public Response searchByName(
            @HeaderParam("Authorization") String authHeader,
            @QueryParam("prefix") String prefix) {

        if (!isAuthorized(authHeader)) {
            return unauthorizedResponse();
        }

        if (prefix == null || prefix.trim().isEmpty()) {
            ErrorResponse.ErrorDetail detail = new ErrorResponse.ErrorDetail();
            detail.setField("prefix");
            detail.setIssue("Field 'prefix' is required and must be at least 1 character long.");

            return badRequestResponse(List.of(detail));
        }

        List<Dragon> dragons = dragonService.searchByName(prefix);
        if (dragons.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        DragonList dragonList = new DragonList();
        dragonList.setDragons(dragons);

        return Response.ok(dragonList).build();
    }

    // GET /dragons/{id}
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getDragonById(
            @HeaderParam("Authorization") String authHeader,
            @PathParam("id") Integer id) {

        if (!isAuthorized(authHeader)) {
            return unauthorizedResponse();
        }

        if (id == null || id <= 0) {
            ErrorResponse.ErrorDetail detail = new ErrorResponse.ErrorDetail();
            detail.setField("id");
            detail.setIssue("Field 'id' is required and must be at least 1 character long.");

            return badRequestResponse(List.of(detail));
        }

        Dragon dragon = dragonService.getDragonById(id);
        if (dragon == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(dragon).build();
    }

    // PUT /dragons/{id}
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_XML)
    public Response updateDragon(
            @HeaderParam("Authorization") String authHeader,
            @PathParam("id") Integer id,
            Dragon dragon) {

        if (!isAuthorized(authHeader)) {
            return unauthorizedResponse();
        }

        if (id == null || id <= 0) {
            ErrorResponse.ErrorDetail detail = new ErrorResponse.ErrorDetail();
            detail.setField("id");
            detail.setIssue("Field 'id' is required and must be at least 1 character long.");

            return badRequestResponse(List.of(detail));
        }

        List<ErrorResponse.ErrorDetail> validationErrors = validateDragon(dragon);
        if (!validationErrors.isEmpty()) {
            return badRequestResponse(validationErrors);
        }

        try {
            boolean updated = dragonService.updateDragon(id, dragon);
            if (!updated) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok().build();
        } catch (Exception e) {
            return serverErrorResponse();
        }
    }

    // DELETE /dragons/{id}
    @DELETE
    @Path("/{id}")
    public Response deleteDragon(
            @HeaderParam("Authorization") String authHeader,
            @PathParam("id") Integer id) {

        if (!isAuthorized(authHeader)) {
            return unauthorizedResponse();
        }

        if (id == null || id <= 0) {
            ErrorResponse.ErrorDetail detail = new ErrorResponse.ErrorDetail();
            detail.setField("id");
            detail.setIssue("Field 'id' is required and must be at least 1 character long.");

            return badRequestResponse(List.of(detail));
        }

        try {
            boolean deleted = dragonService.deleteDragon(id);
            if (!deleted) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (Exception e) {
            return serverErrorResponse();
        }
    }

    // Вспомогательные методы

    private boolean isAuthorized(String authHeader) {
        // Простая проверка авторизации для демонстрации
        return authHeader != null && authHeader.startsWith("Bearer ");
    }

    private Response unauthorizedResponse() {
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    private Response badRequestResponse(List<ErrorResponse.ErrorDetail> details) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(400);
        errorResponse.setMessage("Invalid data");
        errorResponse.setDetails(details);
        return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
    }

    private Response serverErrorResponse() {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    private List<ErrorResponse.ErrorDetail> validateDragon(Dragon dragon) {
        List<ErrorResponse.ErrorDetail> errors = new ArrayList<>();

        if (dragon.getName() == null || dragon.getName().trim().isEmpty()) {
            ErrorResponse.ErrorDetail detail = new ErrorResponse.ErrorDetail();
            detail.setField("name");
            detail.setIssue("Field 'name' is required and must be at least 1 character long.");
            errors.add(detail);
        }

        if (dragon.getWingspan() == null || dragon.getWingspan() <= 0) {
            ErrorResponse.ErrorDetail detail = new ErrorResponse.ErrorDetail();
            detail.setField("wingspan");
            detail.setIssue("Field 'wingspan' must be a positive number.");
            errors.add(detail);
        }

        // Дополнительные проверки можно добавить здесь

        return errors;
    }
}