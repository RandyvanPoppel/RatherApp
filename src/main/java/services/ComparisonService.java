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

    public Comparison addComparison(User user, List<String> choiceStrings) {
        List<Choice> choices = new ArrayList<>();
        for (String choiceString : choiceStrings) {
            Choice choice = new Choice(choiceString);
            choices.add(choiceDAO.addChoice(choice));
        }
        return comparisonDAO.addComparison(new Comparison(user, choices));
    }

    public List<Comparison> getLatestComparisons(long unixTimeStamp)
    {
        return comparisonDAO.getLatestComparisons(unixTimeStamp);
    }

    public Comparison getById(long comparisonId)
    {
        return comparisonDAO.getById(comparisonId);
    }

    public void setUserDAO(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setChoiceDAO(IChoiceDAO choiceDAO) {
        this.choiceDAO = choiceDAO;
    }

    public void setComparisonDAO(IComparisonDAO comparisonDAO) {
        this.comparisonDAO = comparisonDAO;
    }
}
