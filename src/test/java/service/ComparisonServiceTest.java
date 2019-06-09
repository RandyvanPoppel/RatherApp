package service;

import dao.jpa.ChoiceDAOJPA;
import dao.jpa.ComparisonDAOJPA;
import dao.jpa.UserDAOJPA;
import dao.jpa.VoteDAOJPA;
import models.Choice;
import models.Comparison;
import models.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import services.ComparisonService;
import services.UserService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.MockitoAnnotations.initMocks;

public class ComparisonServiceTest {

    @InjectMocks
    private ComparisonService comparisonService;

    @Mock
    private UserDAOJPA userDAOJPA;

    @Mock
    private ChoiceDAOJPA choiceDAOJPA;

    @Mock
    private ComparisonDAOJPA comparisonDAOJPA;

    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("localhost_test");
    private EntityManager entityManager;
    private EntityTransaction transaction;

    private User user1;
    private Choice choice1;
    private List<String> choiceStrings;

    @Before
    public void setUp() {
        initMocks(this);

        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();

        userDAOJPA = new UserDAOJPA();
        userDAOJPA.setEntityManager(entityManager);
        comparisonService.setUserDAO(userDAOJPA);

        choiceDAOJPA = new ChoiceDAOJPA();
        choiceDAOJPA.setEntityManager(entityManager);
        comparisonService.setChoiceDAO(choiceDAOJPA);

        comparisonDAOJPA = new ComparisonDAOJPA();
        comparisonDAOJPA.setEntityManager(entityManager);
        comparisonService.setComparisonDAO(comparisonDAOJPA);

        choiceStrings = new ArrayList<>();
        choiceStrings.add("Choice1");
        choiceStrings.add("Choice2");

        user1 = new User(1, "TestUser1");
        choice1 = new Choice("Choice1");

        transaction.begin();
        user1 = userDAOJPA.addUser(user1);
        choice1 = choiceDAOJPA.addChoice(choice1);
        transaction.commit();
    }

    @Test
    public void addComparisonAndGetById()
    {
        Comparison testComparison1 = comparisonService.addComparison(user1, choiceStrings);

        transaction.begin();
        Comparison testComparison2 = comparisonService.getById(testComparison1.getId());
        transaction.commit();

        Assert.assertEquals(testComparison1, testComparison2);
    }
}
