package dao.jpa;

import dao.blueprint.IVoteDAO;
import dao.jpa.config.DAOJPA;
import dao.jpa.config.JPA;
import models.Vote;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;

@JPA
@Stateless
@Default
public class VoteDAOJPA extends DAOJPA implements IVoteDAO {

    @Override
    public Vote addVote(Vote vote) {
        em.persist(vote);
        return vote;
    }
}
