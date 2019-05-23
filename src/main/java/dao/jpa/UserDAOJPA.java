package dao.jpa;

import dao.blueprint.IUserDAO;
import dao.jpa.config.JPA;
import models.User;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@JPA
@Stateless
@Default
public class UserDAOJPA implements IUserDAO {
    @PersistenceContext(unitName = "localhost")
    private EntityManager em;

    @Override
    public User addUser(User user) {
        em.persist(user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        em.merge(user);
        return user;
    }

    @Override
    public User getById(long userId) {
        return em.find(User.class, userId);
    }
}
