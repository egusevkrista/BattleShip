package ru.krista.battleship;

import ru.krista.battleship.entities.*;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Обработка запросов, связанных с установкой кораблей и стрельбы.
 */
@Path("/ships")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
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
     * @return Возвращает ответ в виде установленного корабля(JSON - объект) и кода 200, если корабль успешно установлен.
     * В ином случае возвращает сущность "Ship cannot be placed!" и код 400.
     */
    @Path("setWithParams")
    @PUT
    public Response placeShip(@QueryParam("x") int x, @QueryParam("y") int y,
                              @QueryParam("size") int size, @QueryParam("direction") ShipDirection direction) {
        Ship newShip = new Ship(x, y, size, direction);
        int res = manager.getPlayer().getField().placeShip(newShip);
        if (res != 0) {
            return Response.status(400).entity(1).build();
        }
        return Response.ok().entity(0).build();
    }

    /**
     * PUT - запрос для установки корабля.
     * В запрос передается JSON - объект, который переводится в экземпляр класса Ship
     *
     * @param newShip экземпляр класса корабль.
     * @return Возвращает ответ в виде установленного корабля(JSON - объект) и кода 200, если корабль успешно установлен.
     * В ином случае возвращает сущность "Ship cannot be placed!" и код 400.
     */
    @Path("set")
    @PUT
    public Response placeShip(Ship newShip) {
        int res = manager.getPlayer().getField().placeShip(newShip);
        if (res != 0) {
            return Response.status(400).entity(1).build();
        }
        return Response.ok().entity(0).build();
    }

    /**
     * PUT - запрос для стрельбы по боту по заданным параметрам.
     *
     * @param x координата x.
     * @param y координата y.
     * @return Возвращает ответ с сущностью "Fired" и код 200.
     */
    @Path("fire")
    @PUT
    public Response fireShip(@QueryParam("x") int x, @QueryParam("y") int y) {
        manager.getPlayer().fire(x, y);
        return Response.ok().entity(0).build();
    }

    @Path("get")
    @GET
    public Response getShips() {
        return Response.ok().entity(manager.getPlayer().getField().getShips()).build();
    }
}