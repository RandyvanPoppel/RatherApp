package models;

import models.hateoas.Link;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Vote")
public class Vote {
    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    private Choice choice;
    @ManyToOne
    private User user;
    @Transient
    private List<Link> links = new ArrayList<>();

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

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public void addLink(Link link)
    {
        links.add(link);
    }
}
