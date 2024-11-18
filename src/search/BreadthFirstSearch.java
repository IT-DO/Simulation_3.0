package search;

import entities.Entity;

import java.util.Comparator;
import java.util.List;

// Алгоритм поиска в ширину (BFS)
public class BreadthFirstSearch implements SearchAlgorithm {
    @Override
    public Entity findNearest(List<Entity> entities, int startX, int startY, Class<?> targetType) { // Реализация метода поиска
        return entities.stream().filter(e -> targetType.isInstance(e)) // Фильтруем только объекты нужного типа
                .min(Comparator.comparingInt(e -> Math.abs(e.getX() - startX) + Math.abs(e.getY() - startY))) // Ищем ближайший по Манхэттенскому расстоянию
                .orElse(null); // Возвращаем null, если ничего не найдено
    }
}
