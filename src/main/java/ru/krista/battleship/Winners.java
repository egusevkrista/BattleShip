package ru.krista.battleship;

import ru.krista.battleship.entities.GameManager;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/winners")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Winners {

    @Inject
    GameManager manager;

    @Path("get")
    @GET
    public Response getWinners() {
        return Response.ok().entity(manager.getWinners()).build();
    }
}
