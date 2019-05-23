package services;

import dao.blueprint.IUserDAO;
import models.User;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Singleton
@Startup
public class StartUpService {
    @Inject
    IUserDAO userDAO;

    public StartUpService() {}

    @PostConstruct
    private void initStart() {
        userDAO.addUser(new User(1));
        userDAO.addUser(new User(2));
    }
}
