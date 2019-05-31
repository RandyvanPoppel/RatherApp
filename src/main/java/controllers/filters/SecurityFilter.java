package controllers.filters;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import dao.blueprint.IUserDAO;
import dao.jpa.UserDAOJPA;
import models.User;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class SecurityFilter implements ContainerRequestFilter {

    private UserDAOJPA userDAO;

//    @Inject
//    private IUserDAO userDAO;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
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
                JsonParser parser = new JsonParser();
                JsonElement jsonElement = parser.parse(resultobject);
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                String username = jsonObject.get("username").getAsString();
                // TODO: Fix userDAO injection so Users can be found/created in the DB
//                User user = userDAO.getByUsername(username);
//                if (user == null)
//                {
//                    user = new User(username);
//                    userDAO.addUser(user);
//                }
//                requestContext.getHeaders().add("userId", String.valueOf(user.getId()));
            } else {
                requestContext.abortWith(unauthorizedResponse);
            }
        } else {
            requestContext.abortWith(unauthorizedResponse);
        }
    }
}
