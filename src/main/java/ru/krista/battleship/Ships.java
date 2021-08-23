package ru.krista.battleship;

import ru.krista.battleship.entities.Opponent;
import ru.krista.battleship.entities.Player;
import ru.krista.battleship.entities.ShipDirection;
import ru.krista.battleship.entities.Winner;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;


@Path("/ships")
public class Ships {
    @Inject
    Player player;

    @Inject
    Winner winner;

    @Inject
    Opponent op;

    @Path("/set")
    @PUT
    @Produces("text/plain")
    public Response placeShips(@QueryParam("x") int x, @QueryParam("y") int y,
                               @QueryParam("size") int size, @QueryParam("direction") ShipDirection direction) {
        int res = player.getField().placeShip(x, y, size, direction);
        if (res != 0) {
            return Response.status(400).entity("Ship cannot be placed!").build();
        }
        return Response.ok().entity("Ship placed!").build();
    }

    @Path("fire")
    @PUT
    @Produces("text/plain")
    public Response fireShip(@QueryParam("x") int x, @QueryParam("y") int y) {
        player.fire(x, y);
        System.out.println(player.getField().getHP() + ":" + op.getField().getHP());
        return Response.ok().entity("Fired").build();
    }
}