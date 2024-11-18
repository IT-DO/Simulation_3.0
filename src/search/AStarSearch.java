package search;

import entities.Entity;

import java.util.*;

// Алгоритм A* для поиска пути
public class AStarSearch implements SearchAlgorithm {
    @Override
    public Entity findNearest(List<Entity> entities, int startX, int startY, Class<?> targetType) { // Реализация метода поиска
        List<Entity> targets = entities.stream().filter(targetType::isInstance).toList(); // Находим все цели нужного типа
        if (targets.isEmpty()) return null; // Если целей нет, возвращаем null

        Entity closestTarget = null; // Хранит ближайшую цель
        int shortestPathCost = Integer.MAX_VALUE; // Минимальная стоимость пути

        for (Entity target : targets) { // Проходим по всем целям
            int pathCost = calculatePathCost(startX, startY, target.getX(), target.getY()); // Вычисляем стоимость пути
            if (pathCost < shortestPathCost) { // Если нашли более короткий путь
                shortestPathCost = pathCost; // Обновляем минимальную стоимость
                closestTarget = target; // Обновляем ближайшую цель
            }
        }
        return closestTarget; // Возвращаем ближайшую цель
    }

    private int calculatePathCost(int startX, int startY, int goalX, int goalY) { // Вычисление стоимости пути
        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingInt(n -> n.fCost)); // Очередь с приоритетом для узлов
        Set<Node> closedList = new HashSet<>(); // Список посещенных узлов
        Node startNode = new Node(startX, startY, 0, heuristic(startX, startY, goalX, goalY)); // Создаем стартовый узел
        openList.add(startNode); // Добавляем стартовый узел в очередь

        while (!openList.isEmpty()) { // Пока есть узлы для обработки
            Node current = openList.poll(); // Берем узел с минимальным fCost
            if (current.x == goalX && current.y == goalY)
                return current.fCost; // Если достигли цели, возвращаем стоимость пути

            closedList.add(current); // Добавляем узел в посещенные

            for (Node neighbor : getNeighbors(current, goalX, goalY)) { // Получаем соседей
                if (closedList.contains(neighbor)) continue; // Пропускаем уже посещенные узлы

                Optional<Node> openNeighbor = openList.stream().filter(n -> n.equals(neighbor)).findFirst(); // Проверяем, есть ли узел в очереди
                if (openNeighbor.isPresent() && openNeighbor.get().gCost <= neighbor.gCost)
                    continue; // Если найденный узел имеет меньшую стоимость, пропускаем

                openList.add(neighbor); // Добавляем узел в очередь
            }
        }
        return Integer.MAX_VALUE; // Если путь не найден, возвращаем максимальную стоимость
    }

    private int heuristic(int x1, int y1, int x2, int y2) { // Эвристическая функция (Манхэттенское расстояние)
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    private List<Node> getNeighbors(Node current, int goalX, int goalY) { // Генерация соседних узлов
        List<Node> neighbors = new ArrayList<>(); // Список соседей
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}}; // Возможные направления движения

        for (int[] dir : directions) { // Проходим по всем направлениям
            int newX = current.x + dir[0]; // Новая координата X
            int newY = current.y + dir[1]; // Новая координата Y
            neighbors.add(new Node(newX, newY, current.gCost + 1, heuristic(newX, newY, goalX, goalY))); // Добавляем соседа
        }
        return neighbors; // Возвращаем список соседей
    }

    private static class Node { // Класс узла для алгоритма A*
        int x, y; // Координаты узла
        int gCost; // Стоимость пути от старта до узла
        int hCost; // Эвристическая стоимость до цели
        int fCost; // Общая стоимость (gCost + hCost)

        public Node(int x, int y, int gCost, int hCost) { // Конструктор узла
            this.x = x;
            this.y = y;
            this.gCost = gCost;
            this.hCost = hCost;
            this.fCost = gCost + hCost;
        }

        @Override
        public boolean equals(Object obj) { // Переопределение equals для сравнения узлов
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Node node = (Node) obj;
            return x == node.x && y == node.y;
        }

        @Override
        public int hashCode() { // Переопределение hashCode
            return Objects.hash(x, y);
        }
    }
}
