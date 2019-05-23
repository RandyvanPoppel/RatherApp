package dao.blueprint;

import models.Choice;

public interface IChoiceDAO {
    Choice addChoice(Choice choice);

    Choice getById(long choiceId);
}
