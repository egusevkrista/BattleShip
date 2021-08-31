package ru.krista.battleship;

import ru.krista.battleship.entities.Winner;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

@Stateless
public class JavaBean implements Serializable {

    @PersistenceContext(unitName = "myUnit")
    EntityManager entityManager;

    public void saveGame(Winner winner) {
        entityManager.persist(winner);
    }

    public List<Winner> getWinners() {
        TypedQuery<Winner> namedQuery = entityManager.createNamedQuery("Winner.getAll", Winner.class);
        return namedQuery.getResultList();
    }
}
