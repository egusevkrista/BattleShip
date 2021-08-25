package ru.krista.battleship;

import ru.krista.battleship.entities.GameManager;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * Обработка запросов, связанных с именем игрока и созданием игры.
 */
@Path("/player")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class PlayerData {


    /**
     * Менеджер игры
     */
    @Inject
    GameManager manager;
    /**
     * POST - запрос для задания имени игрока.
     * После задания имени менеджер начинает игру.
     *
     * @param name Имя игрока
     * @return Возвращает ответ с сущностью "Created" и кодом 200.
     * @see GameManager#startGame()
     */
    @Path("set")
    @POST
    public Response create(@QueryParam("name") String name) {
        manager.getPlayer().setName(name);
        manager.startGame();
        return Response.ok().entity("Created player: " + name).build();
    }

    /**
     * GET - запрос для получения имени игрока.
     *
     * @return Возвращает ответ с именем игрока и кодом 200.
     */
    @Path("get")
    @GET
    public Response getName() {
        return Response.ok().entity(manager.getPlayer().getName()).build();
    }

}