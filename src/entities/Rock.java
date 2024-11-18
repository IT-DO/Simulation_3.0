package entities;

import map.Map;

import java.util.List;

// Класс камня
public class Rock extends Entity {
    public Rock(int x, int y) { // Конструктор для создания камня
        super(x, y);
    }

    @Override
    public void act(Map map, List<String> messages) { // Камень ничего не делает
    }
}
