package dao.jpa;

import dao.blueprint.IVoteDAO;
import dao.jpa.config.JPA;
import models.Vote;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@JPA
@Stateless
@Default
public class VoteDAOJPA implements IVoteDAO {
    @PersistenceContext(unitName = "localhost")
    private EntityManager em;

    @Override
    public Vote addVote(Vote vote) {
        em.persist(vote);
        return vote;
    }
}
