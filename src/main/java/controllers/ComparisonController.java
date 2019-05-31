package controllers;

import controllers.hateoas.HATEOAS;
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
    public Comparison addComparison(@QueryParam("choiceDescriptions") final List<String> choices,
                                    @Context UriInfo uriInfo) {
        Comparison comparison = comparisonService.addComparison(choices);
        comparison.addLink(HATEOAS.createLink(ComparisonController.class, uriInfo, "self", "add", RequestMethod.POST, new String[]{"Authorization: tokenString"}, new String[]{"choiceDescriptions"}));
        comparison.addLink(HATEOAS.createLink(ComparisonController.class, uriInfo, "getLatest", "getLatest", RequestMethod.GET, new String[]{"Authorization: tokenString"}, new String[]{"unixTimeStamp"}));
        comparison.addLink(HATEOAS.createLink(ComparisonController.class, uriInfo, "vote", "vote", RequestMethod.POST, new String[]{"Authorization: tokenString"}, new String[]{"comparisonId", "choiceId"}));
        return comparison;
    }

    @GET
    @Path("getLatest")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Comparison> getLatestComparisons(@QueryParam("unixTimeStamp") final long unixTimeStamp,
                                                 @Context UriInfo uriInfo) {
        List<Comparison> comparisons = comparisonService.getLatestComparisons(unixTimeStamp);
        for (Comparison comparison : comparisons) {
            comparison.addLink(HATEOAS.createLink(ComparisonController.class, uriInfo, "self", "getLatest", RequestMethod.GET, new String[]{"Authorization: tokenString"}, new String[]{"unixTimeStamp"}));
            comparison.addLink(HATEOAS.createLink(ComparisonController.class, uriInfo, "add", "add", RequestMethod.POST, new String[]{"Authorization: tokenString"}, new String[]{"choiceDescriptions"}));
            comparison.addLink(HATEOAS.createLink(ComparisonController.class, uriInfo, "vote", "vote", RequestMethod.POST, new String[]{"Authorization: tokenString"}, new String[]{"comparisonId", "choiceId"}));
        }
        return comparisons;
    }

    @POST
    @Path("vote")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Vote vote(@QueryParam("comparisonId") final long comparisonId,
                     @QueryParam("choiceId") final long choiceId,
                     @Context UriInfo uriInfo) {
        Vote vote = voteService.vote(comparisonId, choiceId);
        vote.addLink(HATEOAS.createLink(ComparisonController.class, uriInfo, "self", "vote", RequestMethod.POST, new String[]{"Authorization: tokenString"}, new String[]{"comparisonId", "choiceId"}));
        vote.addLink(HATEOAS.createLink(ComparisonController.class, uriInfo, "add", "add", RequestMethod.POST, new String[]{"Authorization: tokenString"}, new String[]{"choiceDescriptions"}));
        vote.addLink(HATEOAS.createLink(ComparisonController.class, uriInfo, "getLatest", "getLatest", RequestMethod.GET, new String[]{"Authorization: tokenString"}, new String[]{"unixTimeStamp"}));
        return vote;
    }
}
