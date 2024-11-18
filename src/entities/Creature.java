package entities;

import map.Map;

import java.util.List;

// Абстрактный класс для существ
abstract public class Creature extends Entity {
    protected int speed; // Скорость движения существа
    protected int hp;

    public Creature(int x, int y, int speed, int hp) { // Конструктор существа
        super(x, y);
        this.speed = speed;
        this.hp = hp;
    }

    public boolean isAlive() { // Проверка, живо ли существо
        return hp > 0;
    }

    public abstract void makeMove(Map map,List<String> messages); // Абстрактный метод движения существа


}
