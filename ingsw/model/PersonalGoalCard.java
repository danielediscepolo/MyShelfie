package it.polimi.ingsw.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class PersonalGoalCard implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String guiReference ;

    private final Bookshelf pattern;

    public PersonalGoalCard(Bookshelf pattern,String guiReference) {
        this.pattern = pattern;
        this.guiReference =guiReference;
    }

    public Bookshelf getPattern() {
        return pattern;
    }

    public String getGuiReference() {
        return guiReference;
    }
}
