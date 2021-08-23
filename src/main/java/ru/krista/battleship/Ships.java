package ru.krista.battleship;

import ru.krista.battleship.entities.*;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Обработка запросов, связанных с установкой кораблей и стрельбы.
 */
@Path("/ships")
public class Ships {

    /**
     * Менеджер игры.
     */
    @Inject
    GameManager manager;

    /**
     * PUT - запрос для установки корабля по заданным параметрам.
     *
     * @param x         координата x.
     * @param y         координата y.
     * @param size      размер корабля.
     * @param direction направление корабля.
     */
    @Path("set")
    @PUT
    @Produces("text/plain")

    public Response placeShips(@QueryParam("x") int x, @QueryParam("y") int y,
                               @QueryParam("size") int size, @QueryParam("direction") ShipDirection direction) {
        Ship newShip = new Ship(x, y, size, direction);
        int res = manager.getPlayer().getField().placeShip(newShip);
        if (res != 0) {
            return Response.status(400).entity("Ship cannot be placed!").build();
        }
        return Response.ok().entity("Ship placed!").build();
    }

    /**
     * PUT - запрос для стрельбы по боту по заданным параметрам.
     *
     * @param x координата x.
     * @param y координата y.
     */
    @Path("fire")
    @PUT
    @Produces("text/plain")

    public Response fireShip(@QueryParam("x") int x, @QueryParam("y") int y) {
        manager.getPlayer().fire(x, y);
        return Response.ok().entity("Fired").build();
    }
}