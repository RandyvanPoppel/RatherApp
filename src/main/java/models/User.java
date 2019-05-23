package models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "User")
public class User {
    @Id
    @GeneratedValue
    private long id;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Vote> votes = new ArrayList<>();

    public User() {}

    public User(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }
}
