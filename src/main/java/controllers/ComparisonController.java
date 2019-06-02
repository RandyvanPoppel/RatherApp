package controllers;

import controllers.hateoas.HATEOAS;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import models.Comparison;
import models.User;
import models.Vote;
import models.hateoas.RequestMethod;
import services.ComparisonService;
import services.UserService;
import services.VoteService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Stateless
@Path("comparison")
public class ComparisonController {

    @Inject
    private UserService userService;

    @Inject
    private ComparisonService comparisonService;

    @Inject
    VoteService voteService;

    @POST
    @Path("add")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Comparison addComparison(@QueryParam("choiceDescriptions") final List<String> choices,
                                    @Context UriInfo uriInfo,
                                    ContainerRequestContext requestContext) {
        User user = findOrCreateAuthenticatedUser(requestContext.getHeaderString("authUser"));
        Comparison comparison = comparisonService.addComparison(user.getId(), choices);
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
                     @Context UriInfo uriInfo,
                     ContainerRequestContext requestContext) {
        User user = findOrCreateAuthenticatedUser(requestContext.getHeaderString("authUser"));
        Vote vote = voteService.vote(user.getId(), comparisonId, choiceId);
        vote.addLink(HATEOAS.createLink(ComparisonController.class, uriInfo, "self", "vote", RequestMethod.POST, new String[]{"Authorization: tokenString"}, new String[]{"comparisonId", "choiceId"}));
        vote.addLink(HATEOAS.createLink(ComparisonController.class, uriInfo, "add", "add", RequestMethod.POST, new String[]{"Authorization: tokenString"}, new String[]{"choiceDescriptions"}));
        vote.addLink(HATEOAS.createLink(ComparisonController.class, uriInfo, "getLatest", "getLatest", RequestMethod.GET, new String[]{"Authorization: tokenString"}, new String[]{"unixTimeStamp"}));
        return vote;
    }

    private User findOrCreateAuthenticatedUser(String resultobject) {
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(resultobject);
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        long id = jsonObject.get("id").getAsLong();
        String username = jsonObject.get("username").getAsString();

        User user = userService.getById(id);
        if (user != null)
        {
            if (!user.getUsername().equals(username))
            {
                user.setUsername(username);
                userService.updateUser(user);
            }
        }
        else {
            user = userService.addUser(id, username);
        }
        return user;
    }
}
