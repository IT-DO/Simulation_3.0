package entities;

import map.Map;

import java.util.List;

// Абстрактный класс для всех объектов мира
public abstract class Entity {
    public int x; // Координата X объекта
    public int y; // Координата Y объекта

    public Entity(int x, int y) { // Конструктор для задания начальных координат объекта
        this.x = x;
        this.y = y;
    }

    public int getX() { // Метод для получения координаты X
        return x;
    }

    public int getY() { // Метод для получения координаты Y
        return y;
    }

    public abstract void act(Map map, List<String> messages); // Абстрактный метод, который выполняет действие объекта
}