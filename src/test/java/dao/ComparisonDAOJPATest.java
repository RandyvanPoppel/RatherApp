package dao;

import dao.jpa.ChoiceDAOJPA;
import dao.jpa.ComparisonDAOJPA;
import dao.jpa.UserDAOJPA;
import models.Choice;
import models.Comparison;
import models.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import util.DatabaseCleaner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.MockitoAnnotations.initMocks;

public class ComparisonDAOJPATest {

    @InjectMocks
    private UserDAOJPA userDAOJPA;

    @InjectMocks
    private ChoiceDAOJPA choiceDAOJPA;

    @InjectMocks
    private ComparisonDAOJPA comparisonDAOJPA;

    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("localhost_test");
    private EntityManager entityManager;
    private EntityTransaction transaction;

    private User user;
    private Choice choice1;
    private Choice choice2;
    private List<Choice> choices;

    @Before
    public void setUp() {
        initMocks(this);

        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();

        userDAOJPA = new UserDAOJPA();
        userDAOJPA.setEntityManager(entityManager);

        choiceDAOJPA = new ChoiceDAOJPA();
        choiceDAOJPA.setEntityManager(entityManager);

        comparisonDAOJPA = new ComparisonDAOJPA();
        comparisonDAOJPA.setEntityManager(entityManager);

        user = new User(1, "TestUser");
        choice1 = new Choice("Choice1");
        choice2 = new Choice("Choice2");

        choices = new ArrayList<>();

        transaction.begin();
        user = userDAOJPA.addUser(user);
        choices.add(choiceDAOJPA.addChoice(choice1));
        choices.add(choiceDAOJPA.addChoice(choice2));
        transaction.commit();
    }

    @After
    public void tearDown() throws Exception {
        new DatabaseCleaner(entityManager).clean();
        this.entityManager.clear();
        this.entityManager.close();
    }

    @Test
    public void addComparison()
    {
        Comparison comparison = new Comparison(user, choices);

        transaction.begin();
        Comparison testComparison = comparisonDAOJPA.addComparison(comparison);
        transaction.commit();

        Assert.assertEquals(testComparison.getUser(), user);
        List<Choice> testChoices = testComparison.getChoices();
        Assert.assertEquals(testChoices.get(0), choice1);
        Assert.assertEquals(testChoices.get(1), choice2);
    }
}
