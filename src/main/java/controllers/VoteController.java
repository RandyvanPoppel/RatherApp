package controllers;

import models.Vote;
import services.VoteService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("vote")
public class VoteController {

    @Inject
    VoteService voteService;

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Vote vote(@QueryParam("comparisonid") final String comparisonId,
                     @QueryParam("choiceid") final String choiceId) {
        System.out.println(comparisonId);
        System.out.println(choiceId);
        System.out.println("REQUEST RECEIVED");
        return voteService.vote(Long.parseLong(comparisonId), Long.parseLong(choiceId));
    }
}
