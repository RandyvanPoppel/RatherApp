package dao.jpa;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import dao.blueprint.IChoiceDAO;
import dao.jpa.config.JPA;
import models.Choice;

import java.util.List;

@JPA
@Stateless
@Default
public class ChoiceDAOJPA implements IChoiceDAO {
    @PersistenceContext(unitName = "localhost")
    private EntityManager em;

    @Override
    public Choice addChoice(Choice choice) {
        TypedQuery<Choice> query = em.createNamedQuery("choice.getByDescription", Choice.class);
        query.setParameter("description", choice.getDescription());
        List<Choice> results = query.getResultList();
        if (results.size() > 0)
        {
            return results.get(0);
        }
        em.persist(choice);
        return choice;
    }

    @Override
    public Choice getById(long choiceId) {
        return em.find(Choice.class, choiceId);
    }
}
