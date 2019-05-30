package services;

import dao.blueprint.IChoiceDAO;
import dao.blueprint.IComparisonDAO;
import dao.blueprint.IUserDAO;
import dao.blueprint.IVoteDAO;
import models.Choice;
import models.Comparison;
import models.User;
import models.Vote;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class VoteService {
    @Inject
    private IUserDAO userDAO;

    @Inject
    private IChoiceDAO choiceDAO;

    @Inject
    private IComparisonDAO comparisonDAO;

    @Inject
    private IVoteDAO voteDAO;

    public VoteService() {}

    public Vote vote(long userId, long comparisonId, long choiceId) {
        Choice choice = choiceDAO.getById(choiceId);
        if (choice != null)
        {
            Comparison comparison = comparisonDAO.getById(comparisonId);
            if (comparison != null)
            {
                User votingUser = userDAO.getById(userId);

                Vote vote = new Vote(votingUser, choice);
                if(comparison.addOrUpdateVote(vote))
                {
                    comparisonDAO.updateComparison(comparison);
                    return vote;
                }
            }
        }
        return null;
    }
}
