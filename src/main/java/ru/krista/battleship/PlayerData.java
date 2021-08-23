package ru.krista.battleship;

import ru.krista.battleship.entities.GameManager;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/player")
@Produces(MediaType.APPLICATION_JSON)
public class PlayerData {

    @Inject
    GameManager manager;

    @Path("set/{name}")
    @POST
    public Response create(@PathParam("name") String name) {
        manager.getPlayer().setName(name);
        manager.startGame();
        return Response.ok().entity("Created").build();
    }

    @Path("get")
    @GET
    public Response getName() {
        return Response.ok().entity(manager.getPlayer().getName()).build();
    }

}