package controllers;

import models.Comparison;
import services.ComparisonService;

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
}
