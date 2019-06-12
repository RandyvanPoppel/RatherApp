package service;

import dao.blueprint.IComparisonDAO;
import models.Choice;
import models.Comparison;
import models.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import services.ComparisonService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.MockitoAnnotations.initMocks;

public class ComparisonServiceTest {

    @InjectMocks
    private ComparisonService comparisonService;

    @Mock
    private IComparisonDAO comparisonDAO;

    private User user1;
    private Choice choice1;
    private List<String> choiceStrings;
    private List<Choice> choices;

    @Before
    public void setUp() {
        initMocks(this);

        choiceStrings = new ArrayList<>();
        choiceStrings.add("Choice1");
        choiceStrings.add("Choice2");

        choices = new ArrayList<>();
        for (String choiceString : choiceStrings) {
            Choice choice = new Choice(choiceString);
            choices.add(choice);
        }

        user1 = new User(1, "TestUser1");
    }

    @Test
    public void addComparison() {
        Comparison comparison = new Comparison(user1, choices);
        Mockito.when(comparisonDAO.addComparison(comparison)).thenReturn(comparison);
        Assert.assertEquals(comparison, comparisonDAO.addComparison(comparison));
    }

    @Test
    public void getLatestComparisons() {
        List<Comparison> comparisons = new ArrayList<>();
        Mockito.when(comparisonDAO.getLatestComparisons(100000)).thenReturn(comparisons);
        Assert.assertEquals(comparisons, comparisonService.getLatestComparisons(100000));
    }

    @Test
    public void getById() {
        Comparison comparison = new Comparison(user1, choices);
        Mockito.when(comparisonDAO.getById(comparison.getId())).thenReturn(comparison);
        Assert.assertEquals(comparison, comparisonDAO.getById(comparison.getId()));
    }
}
