package services;

import dao.blueprint.IChoiceDAO;
import models.Choice;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class ChoiceService {
    @Inject
    private IChoiceDAO choiceDAO;

    public ChoiceService() {}

    public Choice getById(long choiceId) {
        return choiceDAO.getById(choiceId);
    }
}
