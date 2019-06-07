package dao.jpa;

import dao.blueprint.IComparisonDAO;
import dao.jpa.config.DAOJPA;
import dao.jpa.config.JPA;
import models.Comparison;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.TypedQuery;
import java.util.List;

@JPA
@Stateless
@Default
public class ComparisonDAOJPA extends DAOJPA implements IComparisonDAO {
    private static final int AMOUTOFRESULTS = 12;

    @Override
    public Comparison addComparison(Comparison comparison) {
        em.persist(comparison);
        return comparison;
    }

    @Override
    public Comparison updateComparison(Comparison comparison) {
        em.merge(comparison);
        return comparison;
    }

    @Override
    public List<Comparison> getLatestComparisons(long unixTimeStamp) {
        TypedQuery<Comparison> query = em.createNamedQuery("comparison.getLatest", Comparison.class);
        query.setParameter("unixTimeStamp", unixTimeStamp);
        return query.setMaxResults(AMOUTOFRESULTS).getResultList();
    }

    @Override
    public Comparison getById(long comparisonId) {
        return em.find(Comparison.class, comparisonId);
    }
}
