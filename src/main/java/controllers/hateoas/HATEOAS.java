package controllers.hateoas;

import models.hateoas.Link;
import models.hateoas.RequestMethod;

import javax.ws.rs.core.UriInfo;

public class HATEOAS {
    public static Link createLink(Class currentClass, UriInfo uriInfo, String rel, String pathAfterBaseUri, RequestMethod method, String[] headerParams, String[] queryParams) {
        String uri = uriInfo.getBaseUriBuilder()
                .path(currentClass)
                .path(pathAfterBaseUri)
                .build()
                .toString();
        return new Link(uri, rel, method, headerParams, queryParams);
    }
}
