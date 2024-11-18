import entities.*;
import map.Map;
import search.AStarSearch;
import search.BreadthFirstSearch;
import search.SearchAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


// Главный класс симуляции
public class Simulation {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in); // Создаем объект Scanner для ввода данных
        System.out.println("Введите размер карты (ширина высота):");
        int width = scanner.nextInt(); // Ввод ширины карты
        int height = scanner.nextInt(); // Ввод высоты карты


        System.out.println("Введите количество травоядных:");
        int herbivores = scanner.nextInt(); // Ввод количества травоядных


        System.out.println("Введите количество хищников:");
        int predators = scanner.nextInt(); // Ввод количества хищников


        System.out.println("Выберите алгоритм поиска: 1 - BFS, 2 - A*:");
        int algorithmChoice = scanner.nextInt(); // Выбор алгоритма поиска
        SearchAlgorithm algorithm = switch (algorithmChoice) { // Определяем выбранный алгоритм
            case 1 -> new BreadthFirstSearch(); // Алгоритм BFS
            case 2 -> new AStarSearch(); // Алгоритм A*
            default -> throw new IllegalArgumentException("Некорректный выбор алгоритма!"); // Ошибка при неверном вводе
        };


        Map map = new Map(width, height, algorithm); // Создаем карту с выбранным алгоритмом поиска

        Random r = new Random();


        for (int i = 0; i < r.nextInt(((width * height) / 2)); i++) {
            map.addEntity(new Grass(r.nextInt(width), r.nextInt(height)));
            map.addEntity(new Tree(r.nextInt(width), r.nextInt(height)));
            map.addEntity(new Rock(r.nextInt(width), r.nextInt(height)));
        }

        for (int j = 0; j < herbivores; j++) {
            map.addEntity(new Herbivore(r.nextInt(width), r.nextInt(height), 1, 10));
        }

        for (int j = 0; j < predators; j++) {
            map.addEntity(new Predator(r.nextInt(width), r.nextInt(height), 1, 10, 10));
        }


        for (int turn = 0; turn < 1000; turn++) {
            System.out.println("");

            // Список для сообщений

            Thread.sleep(1250);
            System.out.println("Ход " + (turn + 1));
            List<String> messages = new ArrayList<>();


            // Проверка наличия травоядных и хищников
            boolean hasHerbivores = map.entities.stream().anyMatch(entity -> entity instanceof Herbivore);
            boolean hasPredators = map.entities.stream().anyMatch(entity -> entity instanceof Predator);

            if (!hasHerbivores || !hasPredators) {
                messages.add("Симуляция окончена");
                break; // Останавливаем симуляцию
            }

            // Каждый объект выполняет свои действия
            for (Entity entity : new ArrayList<>(map.entities)) {
                if (entity instanceof Herbivore) {
                    ((Herbivore) entity).makeMove(map, messages);
                } else if (entity instanceof Predator) {
                    ((Predator) entity).makeMove(map, messages);
                }
            }

            //Регенерация травы
            map.regenerateGrass(messages);
            // Отрисовка карты с сообщениями
            map.render(messages);

        }
    }
}



