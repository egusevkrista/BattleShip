package ru.krista.battleship;

import ru.krista.battleship.entities.*;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;


@Path("/ships")
public class Ships {

    @Inject
    GameManager manager;

    @Path("set")
    @PUT
    @Produces("text/plain")
    public Response placeShips(@QueryParam("x") int x, @QueryParam("y") int y,
                               @QueryParam("size") int size, @QueryParam("direction") ShipDirection direction) {
        int res = manager.getPlayer().getField().placeShip(x, y, size, direction);
        if (res != 0) {
            return Response.status(400).entity("Ship cannot be placed!").build();
        }
        return Response.ok().entity("Ship placed!").build();
    }

    @Path("fire")
    @PUT
    @Produces("text/plain")
    public Response fireShip(@QueryParam("x") int x, @QueryParam("y") int y) {
        manager.getPlayer().fire(x, y);
        return Response.ok().entity("Fired").build();
    }
}