package models;

import javax.persistence.*;
import java.io.Serializable;

@NamedQueries(value = {
        @NamedQuery(name = "choice.getById", query = "SELECT c FROM Choice c WHERE c.id = :id"),
        @NamedQuery(name = "choice.getByDescription", query = "SELECT c FROM Choice c where c.description = :description")
})
@Entity(name = "Choice")
public class Choice implements Serializable, Comparable {

    @Id
    @GeneratedValue
    private long id;
    private String description;

    public Choice() {}

    public Choice(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int compareTo(Object other) {
        if (other.getClass() == Choice.class)
        {
            Choice otherChoice = (Choice) other;
            if (this.description.equals(otherChoice.getDescription()))
            {
                return 1;
            }
        }
        return 0;
    }

    public long getId() {
        return id;
    }
}
