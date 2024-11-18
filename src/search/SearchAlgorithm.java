package search;

import entities.Entity;

import java.util.List;

// Интерфейс для алгоритма поиска
public interface SearchAlgorithm {
    Entity findNearest(List<Entity> entities, int startX, int startY, Class<?> targetType); // Метод поиска ближайшего объекта определенного типа
}
