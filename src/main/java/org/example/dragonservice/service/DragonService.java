package org.example.dragonservice.service;

import org.example.dragonservice.model.Dragon;
import org.example.dragonservice.model.DragonGroupByName;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class DragonService {

    private static Map<Integer, Dragon> dragonMap = new HashMap<>();
    private static int currentId = 1;

    public List<Dragon> getDragons(int page, int size, String sort, String filter) {
        List<Dragon> dragons = new ArrayList<>(dragonMap.values());

        // Реализация фильтрации
        if (filter != null) {
            String[] filterParts = filter.split(":");
            if (filterParts.length == 2) {
                String field = filterParts[0];
                String value = filterParts[1];
                dragons = dragons.stream()
                        .filter(dragon -> filterDragon(dragon, field, value))
                        .collect(Collectors.toList());
            }
        }

        // Реализация сортировки
        if (sort != null) {
            dragons.sort((d1, d2) -> {
                Comparable value1 = getFieldValue(d1, sort).toString();
                Comparable value2 = getFieldValue(d2, sort).toString();

                // Обработка null значений
                if (value1 == null && value2 == null) {
                    return 0;
                } else if (value1 == null) {
                    return 1; // null значения идут после
                } else if (value2 == null) {
                    return -1;
                } else {
                    return value1.compareTo(value2);
                }
            });
        }

        // Реализация пагинации
        int fromIndex = (page - 1) * size;
        int toIndex = Math.min(fromIndex + size, dragons.size());
        if (fromIndex > toIndex) {
            return Collections.emptyList();
        }
        return dragons.subList(fromIndex, toIndex);
    }

    public void addDragon(Dragon dragon) {
        dragon.setId(currentId++);
        dragon.setCreationDate(LocalDateTime.now());
        dragonMap.put(dragon.getId(), dragon);
    }

    public List<DragonGroupByName> groupByName() {
        Map<String, Long> groupMap = dragonMap.values().stream()
                .collect(Collectors.groupingBy(Dragon::getName, Collectors.counting()));

        return groupMap.entrySet().stream()
                .map(entry -> {
                    DragonGroupByName group = new DragonGroupByName();
                    group.setName(entry.getKey());
                    group.setCount(entry.getValue().intValue());
                    return group;
                })
                .collect(Collectors.toList());
    }

    public int countSpeaking() {
        return (int) dragonMap.values().stream()
                .filter(Dragon::getSpeaking)
                .count();
    }

    public List<Dragon> searchByName(String prefix) {
        return dragonMap.values().stream()
                .filter(dragon -> dragon.getName().startsWith(prefix))
                .collect(Collectors.toList());
    }

    public Dragon getDragonById(Integer id) {
        return dragonMap.get(id);
    }

    public boolean updateDragon(Integer id, Dragon updatedDragon) {
        if (dragonMap.containsKey(id)) {
            updatedDragon.setId(id);
            updatedDragon.setCreationDate(dragonMap.get(id).getCreationDate());
            dragonMap.put(id, updatedDragon);
            return true;
        }
        return false;
    }

    public boolean deleteDragon(Integer id) {
        return dragonMap.remove(id) != null;
    }

    // Вспомогательные методы

    private boolean filterDragon(Dragon dragon, String field, String value) {
        switch (field.toLowerCase()) {
            case "name":
                return dragon.getName() != null && dragon.getName().equalsIgnoreCase(value);
            case "age":
                return dragon.getAge() != null && dragon.getAge().toString().equals(value);
            case "wingspan":
                return dragon.getWingspan() != null && dragon.getWingspan().toString().equals(value);
            // Добавьте дополнительные поля по мере необходимости
            default:
                return false;
        }
    }

    private Object getFieldValue(Dragon dragon, String fieldName) {
        // Простая реализация получения значения поля для сортировки
        switch (fieldName.toLowerCase()) {
            case "name":
                return dragon.getName();
            case "age":
                return dragon.getAge();
            case "wingspan":
                return dragon.getWingspan();
            default:
                return null;
        }
    }
}