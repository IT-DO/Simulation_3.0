package entities;

import map.Map;

import java.util.List;

// Класс хищника
public class Predator extends Creature {
    private int attackPower = 10;
    List<String> messages;


    public Predator(int x, int y, int speed, int hp, int attackPower) {
        super(x, y, speed, hp);

    }

    @Override
    public void makeMove(Map map, List<String> messages) {
        // Поиск ближайшей жертвы
        Entity herbivore = map.findNearestHerbivore(x, y);
        if (herbivore != null) {
            // Движение в сторону жертвы
            int nextX = x + Integer.signum(herbivore.getX() - x); // Определение направления по X
            int nextY = y + Integer.signum(herbivore.getY() - y); // Определение направления по Y

            map.moveEntity(this, nextX, nextY);
            //после каждого хода уменьшаем hp на 1 ед
            this.hp--;


            // Если травоядное находится на траве, "съедаем" её
            if (nextX == herbivore.getX() && nextY == herbivore.getY()) {
                attack((Herbivore) herbivore, map);
                messages.add("Тиранозавр по координатам (" + nextX + ", " + nextY + ") съел зайца!!! \uD83D\uDC80");
                this.hp += 3;
            }
            if (this.hp == 0) { // если hp = 0 - хищник умерает от голода
                map.removeEntity(this);
                messages.add("Тиранозавр по координатам (" + nextX + ", " + nextY + ") умер от голода!!! ❌");
            }
        }

    }

    private void attack(Herbivore herbivore, Map map) {
        herbivore.hp -= attackPower;
        if (!herbivore.isAlive()) {
            map.removeEntity(herbivore);

        }
    }

    @Override
    public void act(Map map, List<String> messages) {
        if (!isAlive()) return;
        makeMove(map, messages);
    }
}
