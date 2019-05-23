package services;

import dao.blueprint.IChoiceDAO;
import dao.blueprint.IComparisonDAO;
import dao.blueprint.IUserDAO;
import models.Choice;
import models.Comparison;
import models.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ComparisonService {
    @Inject
    private IUserDAO userDAO;

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
        // TODO: [AUTH] Get user from Authentication system
        User postingUser = userDAO.getById(1);

        return comparisonDAO.addComparison(new Comparison(postingUser, choices));
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
