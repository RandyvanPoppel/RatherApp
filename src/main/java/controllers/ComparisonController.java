package controllers;

import models.Comparison;
import models.Vote;
import models.hateoas.RequestMethod;
import services.ComparisonService;
import services.VoteService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
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
    public Comparison addComparison(@QueryParam("choiceDescriptions") final List<String> choices, @Context UriInfo uriInfo) {
        Comparison comparison = comparisonService.addComparison(choices);
        String uri = uriInfo.getBaseUriBuilder()
                .path(ComparisonController.class)
                .path("add")
                .build()
                .toString();
        comparison.addLink(uri, "self", RequestMethod.POST, new String[]{"choiceDescriptions"});
        return comparison;
    }

    @GET
    @Path("getLatest")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Comparison> getLatestComparisons(@QueryParam("unixTimeStamp") final long unixTimeStamp) {
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
