package ru.krista.battleship;

import ru.krista.battleship.entities.Field;
import ru.krista.battleship.entities.Opponent;
import ru.krista.battleship.entities.Player;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/player")
@Produces(MediaType.APPLICATION_JSON)
public class PlayerData {

    @Inject
    Player newplayer;

    @Inject
    Opponent op;

    @Path("set/{name}")
    @POST
    public Response create(@PathParam("name") String name) {
        newplayer.setName(name);
        newplayer.getField().startGame();
        op.getField().startGame();
        op.getField().fullShipsRandom();

        return Response.ok().entity("Created").build();
    }

    @Path("get")
    @GET
    public Response getName() {
        return Response.ok().entity(newplayer.getName()).build();
    }

}