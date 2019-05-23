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
    public Vote vote(@QueryParam("comparisonId") final long comparisonId,
                     @QueryParam("choiceId") final long choiceId) {
        return voteService.vote(comparisonId, choiceId);
    }
}
