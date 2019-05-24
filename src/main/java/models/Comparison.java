package models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NamedQueries(value = {
        @NamedQuery(name = "comparison.getById", query = "SELECT c FROM Comparison c where c.id = :id"),
        @NamedQuery(name = "comparison.getLatest", query = "SELECT c FROM Comparison c WHERE c.unixTimeStamp < :unixTimeStamp ORDER BY c.unixTimeStamp ASC")
})
@Entity(name = "Comparison")
public class Comparison implements Serializable {

    @Id
    @GeneratedValue
    private long id;
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Choice> choices = new ArrayList<>();
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Vote> votes = new ArrayList<>();
    private long unixTimeStamp;
    @ManyToOne
    private User user;

    public Comparison() {
        this.unixTimeStamp = System.currentTimeMillis();
    }

    public Comparison(User user, List<Choice> choices) {
        this.unixTimeStamp = System.currentTimeMillis();
        this.user = user;
        this.choices = choices;
    }

    public boolean addOrUpdateVote(Vote vote) {

        if (checkIfChoiceInComparisonChoices(vote.getChoice())) {
            Vote voteToRemove = null;
            for (Vote existingVote : votes) {
                if (existingVote.getUser() == vote.getUser()) {
                    voteToRemove = existingVote;
                }
            }
            votes.remove(voteToRemove);
            votes.add(vote);
            return true;
        }
        return false;
    }

    private boolean checkIfChoiceInComparisonChoices(Choice choiceToCheck) {
        for (Choice choice : choices) {
            if (choice.compareTo(choiceToCheck) > 0) {
                return true;
            }
        }
        return false;
    }

    public long getId() {
        return id;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public long getUnixTimeStamp() {
        return unixTimeStamp;
    }

    public User getUser() {
        return user;
    }
}
