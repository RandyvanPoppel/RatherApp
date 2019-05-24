package services;

import dao.blueprint.IChoiceDAO;
import dao.blueprint.IComparisonDAO;
import dao.blueprint.IUserDAO;
import models.Choice;
import models.Comparison;
import models.User;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.ArrayList;

@Singleton
@Startup
public class StartUpService {
    @Inject
    IUserDAO userDAO;

    @Inject
    IChoiceDAO choiceDAO;

    @Inject
    IComparisonDAO comparisonDAO;

    public StartUpService() {}

    @PostConstruct
    private void initStart() {
        User poster = new User(1);
        userDAO.addUser(poster);

        User rater = new User(2);
        userDAO.addUser(rater);

        ArrayList<Choice> choices1 = new ArrayList<>();
        choices1.add(choiceDAO.addChoice(new Choice("Choice A")));
        choices1.add(choiceDAO.addChoice(new Choice("Choice B")));

        ArrayList<Choice> choices2 = new ArrayList<>();
        choices2.add(choiceDAO.addChoice(new Choice("Choice C")));
        choices2.add(choiceDAO.addChoice(new Choice("Choice D")));

        ArrayList<Choice> choices3 = new ArrayList<>();
        choices3.add(choiceDAO.addChoice(new Choice("Choice D")));
        choices3.add(choiceDAO.addChoice(new Choice("Choice E")));
        choices3.add(choiceDAO.addChoice(new Choice("Choice F")));

        for (int i = 0; i < 100; i++)
        {
            comparisonDAO.addComparison(new Comparison(poster, choices1));
            comparisonDAO.addComparison(new Comparison(poster, choices2));
            comparisonDAO.addComparison(new Comparison(poster, choices3));
        }
    }
}
