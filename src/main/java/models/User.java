package models;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "User")
public class User implements Serializable {
    @Id
    @GeneratedValue
    private long id;

    public User() {
    }

    public User(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
