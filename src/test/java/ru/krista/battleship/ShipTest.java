package ru.krista.battleship;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.krista.battleship.entities.Field;
import ru.krista.battleship.entities.Ship;
import ru.krista.battleship.entities.ShipDirection;

public class ShipTest {
    private Field field;

    @BeforeEach
    public void setUp() {
        field = new Field();
    }

    @Test
    public void placementOneCellCheck() {
        Ship firstShip = new Ship(1, 1, 1, ShipDirection.LEFT);
        Ship secondShip = new Ship(1, 1, 2, ShipDirection.DOWN);

        field.placeShip(firstShip);
        int response = field.placeShip(secondShip);
        Assertions.assertEquals(-1, response);
    }

    @Test
    public void collisionCheck() {
        Ship firstShip = new Ship(1, 1, 4, ShipDirection.DOWN);
        Ship secondShip = new Ship(3, 3, 3, ShipDirection.LEFT);

        field.placeShip(firstShip);
        int response = field.placeShip(secondShip);

        Assertions.assertEquals(-1, response);
    }

    @Test
    public void neighboringShipsCheck() {
        Ship firstShip = new Ship(1, 1, 4, ShipDirection.DOWN);
        Ship secondShip = new Ship(4, 2, 1, ShipDirection.DOWN);

        field.placeShip(firstShip);
        int response = field.placeShip(secondShip);

        Assertions.assertEquals(-1, response);
    }

    @Test
    public void countCheck4Deck() {
        Ship firstShip = new Ship(10, 10, 4, ShipDirection.LEFT);
        Ship secondShip = new Ship(1, 1, 4, ShipDirection.DOWN);

        field.placeShip(firstShip);
        int response = field.placeShip(secondShip);
        Assertions.assertEquals(-1, response);
    }

    @Test
    public void countCheck3Deck() {
        Ship firstShip = new Ship(1, 10, 3, ShipDirection.LEFT);
        Ship secondShip = new Ship(3, 10, 3, ShipDirection.LEFT);
        Ship thirdShip = new Ship(5, 10, 3, ShipDirection.LEFT);

        field.placeShip(firstShip);
        field.placeShip(secondShip);
        int response = field.placeShip(thirdShip);

        Assertions.assertEquals(-1, response);
    }

    @Test
    public void countCheck2Deck() {
        Ship firstShip = new Ship(1, 10, 2, ShipDirection.LEFT);
        Ship secondShip = new Ship(3, 10, 2, ShipDirection.LEFT);
        Ship thirdShip = new Ship(5, 10, 2, ShipDirection.LEFT);
        Ship fourthShip = new Ship(7, 10, 2, ShipDirection.LEFT);

        field.placeShip(firstShip);
        field.placeShip(secondShip);
        field.placeShip(thirdShip);
        int response = field.placeShip(fourthShip);

        Assertions.assertEquals(-1, response);
    }

    @Test
    public void countCheck1Deck() {
        Ship firstShip = new Ship(1, 10, 1, ShipDirection.LEFT);
        Ship secondShip = new Ship(3, 10, 1, ShipDirection.LEFT);
        Ship thirdShip = new Ship(5, 10, 1, ShipDirection.LEFT);
        Ship fourthShip = new Ship(7, 10, 1, ShipDirection.LEFT);
        Ship fifthShip = new Ship(9, 10, 1, ShipDirection.LEFT);

        field.placeShip(firstShip);
        field.placeShip(secondShip);
        field.placeShip(thirdShip);
        field.placeShip(fourthShip);
        int response = field.placeShip(fifthShip);

        Assertions.assertEquals(-1, response);
    }
}
