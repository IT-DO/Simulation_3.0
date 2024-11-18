package entities;

import map.Map;

import java.util.List;

// Класс травоядного
public class Herbivore extends Creature {
    public Herbivore(int x, int y, int speed, int hp) {
        super(x, y, speed, hp);
    }

    public void makeMove(Map map, List<String> messages) {
        // Поиск ближайшей травы
        Entity grass = map.findNearestGrass(x, y);

        if (grass != null) {
            // Определение направления движения (в сторону травы)
            int nextX = x + Integer.signum(grass.getX() - x); // Движение по оси X
            int nextY = y + Integer.signum(grass.getY() - y); // Движение по оси Y

            // Перемещение травоядного
            map.moveEntity(this, nextX, nextY);

            // Если травоядное находится на траве, "съедаем" её
            if (nextX == grass.getX() && nextY == grass.getY()) {
                map.removeEntity(grass); // Удаляем траву из карты
                messages.add("Заяц по координатам (" + nextX + ", " + nextY + ") съел морковку!"+ "Родился еще один заяц по координатам (" + grass.getX() + ", " + grass.getY() + ")  \uD83D\uDC30");
                map.addEntity(new Herbivore(grass.getX(), grass.getY(), 1, 10));
            }
        }
    }

    @Override
    public void act(Map map, List<String> messages) {
        if (!isAlive()) return;

        makeMove(map, messages);
    }
}
