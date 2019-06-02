package services;

import dao.blueprint.IUserDAO;
import models.User;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class UserService {
    @Inject
    private IUserDAO userDAO;

    public UserService() {
    }

    public User addUser(long userId, String username) {
        return userDAO.addUser(new User(userId, username));
    }

    public User updateUser(User user) {
        return userDAO.updateUser(user);
    }

    public User getById(long userId)
    {
        return userDAO.getById(userId);
    }

    public User getByUsername(String username)
    {
        return userDAO.getByUsername(username);
    }
}
