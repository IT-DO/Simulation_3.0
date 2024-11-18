package entities;

import map.Map;

import java.util.List;

// Класс травы
public class Grass extends Entity {
    public Grass(int x, int y) { // Конструктор для создания травы
        super(x, y);
    }

    @Override
    public void act(Map map, List<String> messages) {

    }

}
