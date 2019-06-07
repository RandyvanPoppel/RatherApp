package models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ComparisonTest {
    private Comparison comparison;
    private User user1;
    private User user2;
    private Choice choice1;
    private Choice choice2;
    private Choice choice3;
    private List<Choice> choices;

    @Before
    public void setUp() {
        user1 = new User(1, "TestUser1");
        user1 = new User(2, "TestUser2");

        choice1 = new Choice("Choice1");
        choice2 = new Choice("Choice2");
        choice3 = new Choice("Choice3");

        choices = new ArrayList<>();
        choices.add(choice1);
        choices.add(choice2);

        comparison = new Comparison(user1, choices);
    }

    @Test
    public void getId()
    {
        Assert.assertNotNull(comparison.getId());
    }

    @Test
    public void getChoices()
    {
        Assert.assertEquals(comparison.getChoices(), choices);
    }

    @Test
    public void getUser()
    {
        Assert.assertEquals(comparison.getUser(), user1);
    }

    @Test
    public void addVoteWithExistingChoice()
    {
        Vote vote1 = new Vote(user2, choice1);
        comparison.addOrUpdateVote(vote1);
        Assert.assertEquals(comparison.getVotes().get(0).getUser(), vote1.getUser());
        Assert.assertEquals(comparison.getVotes().get(0).getChoice(), vote1.getChoice());
    }

    @Test
    public void addVoteWithNonExistingChoice()
    {
        Vote vote1 = new Vote(user2, choice3);
        comparison.addOrUpdateVote(vote1);
        Assert.assertEquals(comparison.getVotes().size(), 0);
    }

    @Test
    public void updateVoteWithExistingChoice()
    {
        Vote vote1 = new Vote(user2, choice1);
        comparison.addOrUpdateVote(vote1);
        Assert.assertEquals(comparison.getVotes().get(0).getUser(), vote1.getUser());
        Assert.assertEquals(comparison.getVotes().get(0).getChoice(), vote1.getChoice());

        Vote vote2 = new Vote(user2, choice2);
        comparison.addOrUpdateVote(vote2);
        Assert.assertEquals(comparison.getVotes().size(), 1);
        Assert.assertEquals(comparison.getVotes().get(0).getUser(), vote2.getUser());
        Assert.assertEquals(comparison.getVotes().get(0).getChoice(), vote2.getChoice());
    }

    @Test
    public void updateVoteWithNonExistingChoice()
    {
        Vote vote1 = new Vote(user2, choice1);
        comparison.addOrUpdateVote(vote1);
        Assert.assertEquals(comparison.getVotes().size(), 1);
        Assert.assertEquals(comparison.getVotes().get(0).getUser(), vote1.getUser());
        Assert.assertEquals(comparison.getVotes().get(0).getChoice(), vote1.getChoice());

        Vote vote2 = new Vote(user2, choice3);
        comparison.addOrUpdateVote(vote2);
        Assert.assertEquals(comparison.getVotes().size(), 1);
        Assert.assertEquals(comparison.getVotes().get(0).getUser(), vote1.getUser());
        Assert.assertEquals(comparison.getVotes().get(0).getChoice(), vote1.getChoice());
    }
}
