package services;

import dao.blueprint.IChoiceDAO;
import dao.blueprint.IComparisonDAO;
import dao.blueprint.IVoteDAO;
import models.Choice;
import models.Comparison;
import models.Vote;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class VoteService {

    @Inject
    private IVoteDAO voteDAO;

    @Inject
    private IChoiceDAO choiceDAO;

    @Inject
    private IComparisonDAO comparisonDAO;

    public VoteService() {}

    public Vote vote(long comparisonId, long choiceId) {
        Choice choice = choiceDAO.getById(choiceId);
        System.out.println(choice);
        System.out.println("AFTER CHOICE");
        if (choice != null)
        {
            System.out.println("Choice found");
            Comparison comparison = comparisonDAO.getById(comparisonId);
            if (comparison != null)
            {
                System.out.println("Comparison found");
                Vote vote = new Vote(choice);
                if(comparison.addOrUpdateVote(vote))
                {
                    System.out.println("Choice found in Comparison");
                    voteDAO.addVote(vote);
                    return vote;
                    //TODO: Update comparison and persist/update vote
                }
            }
        }
        return null;
    }
}
