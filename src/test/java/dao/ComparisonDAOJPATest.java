package dao;

import dao.jpa.ChoiceDAOJPA;
import dao.jpa.ComparisonDAOJPA;
import dao.jpa.UserDAOJPA;
import dao.jpa.VoteDAOJPA;
import models.Choice;
import models.Comparison;
import models.User;
import models.Vote;
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

    @InjectMocks
    private VoteDAOJPA voteDAOJPA;

    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("localhost_test");
    private EntityManager entityManager;
    private EntityTransaction transaction;

    private User user1;
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

        voteDAOJPA = new VoteDAOJPA();
        voteDAOJPA.setEntityManager(entityManager);

        user1 = new User(1, "TestUser1");
        choice1 = new Choice("Choice1");
        choice2 = new Choice("Choice2");

        choices = new ArrayList<>();

        transaction.begin();
        user1 = userDAOJPA.addUser(user1);
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
        Comparison comparison = new Comparison(user1, choices);

        transaction.begin();
        Comparison testComparison1 = comparisonDAOJPA.addComparison(comparison);
        transaction.commit();

        Assert.assertEquals(testComparison1.getUser(), user1);
        List<Choice> testChoices = testComparison1.getChoices();
        Assert.assertEquals(testChoices.get(0), choice1);
        Assert.assertEquals(testChoices.get(1), choice2);
    }

    @Test
    public void updateComparison()
    {
        Comparison comparison = new Comparison(user1, choices);

        transaction.begin();
        Comparison testComparison1 = comparisonDAOJPA.addComparison(comparison);
        transaction.commit();

        Assert.assertTrue(testComparison1.getVotes().isEmpty());

        Vote vote = new Vote(user1, choice1);

        transaction.begin();
        Vote testVote = voteDAOJPA.addVote(vote);
        transaction.commit();

        testComparison1.addOrUpdateVote(testVote);

        transaction.begin();
        Comparison testComparison2 = comparisonDAOJPA.updateComparison(testComparison1);
        transaction.commit();

        Assert.assertEquals(testComparison2.getVotes().size(), 1);
    }

    @Test
    public void getComparisonById()
    {
        Comparison comparison = new Comparison(user1, choices);

        transaction.begin();
        Comparison testComparison1 = comparisonDAOJPA.addComparison(comparison);
        transaction.commit();

        long id = testComparison1.getId();

        transaction.begin();
        Comparison testComparison2 = comparisonDAOJPA.getById(id);
        transaction.commit();

        Assert.assertEquals(testComparison1, testComparison2);
    }
}
