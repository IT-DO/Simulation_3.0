package map;

import entities.Entity;
import entities.Grass;
import entities.Herbivore;
import search.SearchAlgorithm;

import java.util.*;

// Класс карты
public class Map {
    private final int width; // Ширина карты
    private final int height; // Высота карты
    public final List<Entity> entities = new ArrayList<>(); // Список всех объектов на карте
    private final SearchAlgorithm searchAlgorithm; // Выбранный алгоритм поиска

    public Map(int width, int height, SearchAlgorithm searchAlgorithm) { // Конструктор карты
        this.width = width;
        this.height = height;
        this.searchAlgorithm = searchAlgorithm;
    }

    public void addEntity(Entity entity) { // Добавление объекта на карту
        entities.add(entity);
    }

    public void removeEntity(Entity entity) { // Удаление объекта с карты
        entities.remove(entity);
    }

    public Entity findNearestGrass(int x, int y) {
        return entities.stream()
                .filter(entity -> entity.getClass() == Grass.class) // Проверяем, что это именно Grass
                .min(Comparator.comparingInt(entity -> Math.abs(entity.getX() - x) + Math.abs(entity.getY() - y))) // Ищем ближайший объект
                .orElse(null); // Возвращаем null, если трава не найдена
    }

    public Entity findNearestHerbivore(int x, int y) {
        return entities.stream()
                .filter(entity -> entity.getClass() == Herbivore.class) // Проверяем, что это именно Herbivore
                .min(Comparator.comparingInt(entity -> Math.abs(entity.getX() - x) + Math.abs(entity.getY() - y))) // Ищем ближайший объект
                .orElse(null); // Возвращаем null, если травоядное не найдено
    }

    public void regenerateGrass(List<String> mess) {
        // Проверяем, есть ли трава на карте
        long grassCount = entities.stream()
                .filter(entity -> entity.getClass() == Grass.class) // Считаем только Grass
                .count();

        if (grassCount == 0) {
            Random random = new Random();
            int c = random.nextInt(width);
            // Генерируем 10 новых объектов Grass
            for (int i = 0; i < c; i++) {
                int x = random.nextInt(width);  // Случайная координата X
                int y = random.nextInt(height); // Случайная координата Y

                // Проверяем, чтобы на случайных координатах не было других объектов
                boolean isOccupied = entities.stream()
                        .anyMatch(entity -> entity.getX() == x && entity.getY() == y);

                if (!isOccupied) {
                    entities.add(new Grass(x, y)); // Добавляем траву
                } else {
                    i--; // Если место занято, повторяем попытку
                }
            }

            mess.add(c + " новых морковки сгенерированы на карте!");

        }
    }


    // Переместить существо.
    public void moveEntity(Entity entity, int targetX, int targetY) {
        entity.x = targetX;
        entity.y = targetY;
    }

    public void render(List<String> messages) throws InterruptedException {
        String[][] grid = new String[height][width]; // Создаем сетку для визуализации
        for (String[] row : grid) Arrays.fill(row, "⬛\uFE0F"); // Заполняем сетку символами пустоты
        for (Entity entity : entities) { // Проходим по всем объектам на карте
            String symbol = switch (entity.getClass().getSimpleName()) { // Определяем символ объекта
                case "Grass" -> "\uD83E\uDD55"; // Символ травы
                case "Tree" -> "\uD83C\uDF33"; // Символ дерева
                case "Rock" -> "\uD83E\uDDF1"; // Символ камня
                case "Herbivore" -> "\uD83D\uDC07"; // Символ травоядного
                case "Predator" -> "\uD83E\uDD96"; // Символ хищника
                default -> "\uD83C\uDFFB"; // Символ для неизвестного объекта
            };
            grid[entity.getY()][entity.getX()] = symbol; // Размещаем символ объекта на карте
        }

        // Определяем максимальную высоту для синхронизации строк карты и сообщений
        int maxLines = Math.max(height, messages.size());

        // Построчно отображаем карту и сообщения
        for (int i = 0; i < maxLines; i++) {
            // Печатаем строку карты
            if (i < height) {
                for (String cell : grid[i]) {
                    System.out.print(cell + ""); // Печатаем символы карты
                }
            } else {
                // Если карта закончилась, добавляем пустую строку
                System.out.print(" ".repeat(width * 2));
            }

            // Печатаем сообщение, если оно есть
            if (i < messages.size()) {
                System.out.print("    " + messages.get(i)); // Отступ перед сообщением
            }
            System.out.println(); // Переход на следующую строку
        }
    }
}
