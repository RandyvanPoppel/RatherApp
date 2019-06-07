package dao.jpa.config;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class DAOJPA {
    @PersistenceContext(unitName = "localhost")
    protected EntityManager em;

    public void setEntityManager(EntityManager entityManager) { this.em = entityManager; }
}
