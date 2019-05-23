package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity(name = "Vote")
public class Vote {
    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    private Choice choice;
    @ManyToOne
    private User user;

    public Vote() {}

    public Vote(User user, Choice choice) {
        this.user = user;
        this.choice = choice;
    }

    public long getId() {
        return id;
    }

    public Choice getChoice() {
        return choice;
    }

    public User getUser() {
        return user;
    }
}
