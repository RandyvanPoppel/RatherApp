package dao;

import dao.jpa.ComparisonDAOJPA;
import org.junit.After;
import org.junit.Before;
import org.mockito.InjectMocks;
import util.DatabaseCleaner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.mockito.MockitoAnnotations.initMocks;

public class ComparisonDAOJPATest {

    @InjectMocks
    private ComparisonDAOJPA comparisonDAOJPA;

    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("localhost_test");
    private EntityManager entityManager;
    private EntityTransaction transaction;

    @Before
    public void setUp() {
        initMocks(this);

        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();

        comparisonDAOJPA = new ComparisonDAOJPA();
        comparisonDAOJPA.setEntityManager(entityManager);
    }

    @After
    public void tearDown() throws Exception {
        new DatabaseCleaner(entityManager).clean();
        this.entityManager.clear();
        this.entityManager.close();
    }
}
