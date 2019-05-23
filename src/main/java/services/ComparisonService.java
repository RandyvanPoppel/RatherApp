package services;

import dao.blueprint.IChoiceDAO;
import dao.blueprint.IComparisonDAO;
import models.Choice;
import models.Comparison;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ComparisonService {
    @Inject
    private IChoiceDAO choiceDAO;

    @Inject
    private IComparisonDAO comparisonDAO;

    public ComparisonService() {}

    public Comparison addComparison(List<String> choiceStrings) {
        List<Choice> choices = new ArrayList<>();
        for (String choiceString : choiceStrings) {
            Choice choice = new Choice(choiceString);
            choices.add(choiceDAO.addChoice(choice));
        }
        return comparisonDAO.addComparison(new Comparison(choices));
    }

    public List<Comparison> getLatestComparisons(long unixTimeStamp)
    {
        return comparisonDAO.getLatestComparisons(unixTimeStamp);
    }

    public Comparison getById(long comparisonId)
    {
        return comparisonDAO.getById(comparisonId);
    }
}
