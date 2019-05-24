package controllers;

import models.Comparison;
import models.Vote;
import services.ComparisonService;
import services.VoteService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Stateless
@Path("comparison")
public class ComparisonController {

    @Inject
    private ComparisonService comparisonService;

    @Inject
    VoteService voteService;

    @POST
    @Path("add")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Comparison addComparison(@QueryParam("choices") final List<String> choices) {
        return comparisonService.addComparison(choices);
    }

    @GET
    @Path("getLatest")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Comparison> getLatestComparisons(@QueryParam("unixTimeStamp") final long unixTimeStamp)
    {
        List<Comparison> comparisons = comparisonService.getLatestComparisons(unixTimeStamp);
        return comparisons;
    }

    @POST
    @Path("vote")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Vote vote(@QueryParam("comparisonId") final long comparisonId,
                     @QueryParam("choiceId") final long choiceId) {
        return voteService.vote(comparisonId, choiceId);
    }
}
