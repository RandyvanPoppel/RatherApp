package controllers.filters;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class SecurityFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (!requestContext.getUriInfo().getPath().contains("ping"))
        {
            Response unauthorizedResponse = Response.status(Response.Status.UNAUTHORIZED).build();
            if (requestContext.getHeaders().get("Authorization") != null) {
                String tokenString = requestContext.getHeaders().get("Authorization").get(0);
                tokenString = tokenString.split(" ")[1];
                HttpResponse<JsonNode> body = null;
                try {
                    body = Unirest.get("http://localhost:8080/AuthenticationApp-1.0/rest/jsonwebtoken/validate").queryString("tokenString", tokenString).asJson();
                } catch (UnirestException e) {
                    e.printStackTrace();
                }
                if (body.getBody() != null) {
                    String resultobject = body.getBody().toString();
                    requestContext.getHeaders().add("authUser", resultobject);
                } else {
                    requestContext.abortWith(unauthorizedResponse);
                }
            }
        }
    }
}
