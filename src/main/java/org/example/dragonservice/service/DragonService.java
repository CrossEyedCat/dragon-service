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
        if (filter != null && !filter.isEmpty()) {
            String[] filterParams = filter.split(","); // Разделяем параметры фильтрации
            for (String param : filterParams) {
                String[] filterParts = param.split(":"); // Разделяем поле и значение
                if (filterParts.length == 2) {
                    String field = filterParts[0]; // Поле для фильтрации
                    String value = filterParts[1]; // Значение для фильтрации

                    // Применяем фильтр
                    dragons = dragons.stream()
                            .filter(dragon -> filterDragon(dragon, field, value))
                            .collect(Collectors.toList());
                }
            }
        }

        // Реализация сортировки
        if (sort != null && !sort.isEmpty()) {
            String[] sortParams = sort.split(","); // Разделяем параметры сортировки
            dragons.sort((d1, d2) -> {
                for (String param : sortParams) {
                    String[] parts = param.split(":"); // Разделяем поле и режим сортировки
                    if (parts.length != 2) {
                        continue; // Пропускаем некорректные параметры
                    }

                    String field = parts[0]; // Поле для сортировки
                    String order = parts[1]; // Режим сортировки (asc или desc)

                    // Получаем значения для сравнения
                    Comparable value1 = (Comparable) getFieldValue(d1, field);
                    Comparable value2 = (Comparable) getFieldValue(d2, field);

                    // Обработка null значений
                    if (value1 == null && value2 == null) {
                        continue; // Пропускаем это поле
                    } else if (value1 == null) {
                        return order.equals("asc") ? 1 : -1; // null значения идут в конце или начале
                    } else if (value2 == null) {
                        return order.equals("asc") ? -1 : 1;
                    }

                    // Сравниваем значения
                    int comparison = value1.compareTo(value2);
                    if (comparison != 0) {
                        return order.equals("asc") ? comparison : -comparison; // Учитываем режим сортировки
                    }
                }
                return 0; // Если все поля равны
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
                .filter(dragon -> dragon != null && Boolean.TRUE.equals(dragon.getSpeaking()))
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
        if (dragon == null || field == null || value == null) {
            System.out.println("[DEBUG] Неверные входные данные: dragon, field или value равны null");
            return false;
        }

        System.out.println("[DEBUG] Проверяем поле: " + field + " со значением: " + value);

        try {
            switch (field.toLowerCase()) {
                case "name":
                    return dragon.getName() != null && dragon.getName().toLowerCase().contains(value.toLowerCase());
                case "age":
                    return dragon.getAge() != null && dragon.getAge().equals(Integer.parseInt(value));
                case "wingspan":
                    return dragon.getWingspan() != null && dragon.getWingspan().equals(Double.parseDouble(value));
                case "type":
                    return dragon.getType() != null && dragon.getType().toString().toLowerCase().contains(value.toLowerCase());
                case "speaking":
                    return dragon.getSpeaking() != null && dragon.getSpeaking().toString().equalsIgnoreCase(value);
                case "killer.name":
                    return dragon.getKiller() != null && dragon.getKiller().getName() != null
                            && dragon.getKiller().getName().toLowerCase().contains(value.toLowerCase());
                case "killer.nationality":
                    return dragon.getKiller() != null && dragon.getKiller().getNationality() != null
                            && dragon.getKiller().getNationality().toString().toLowerCase().contains(value.toLowerCase());
                case "killer.haircolor":
                    return dragon.getKiller() != null && dragon.getKiller().getHairColor() != null
                            && dragon.getKiller().getHairColor().toString().toLowerCase().contains(value.toLowerCase());
                case "killer.birthday":
                    return dragon.getKiller() != null && dragon.getKiller().getBirthday() != null
                            && dragon.getKiller().getBirthday().toString().contains(value);
                case "coordinates.x":
                    return dragon.getCoordinates() != null && dragon.getCoordinates().getX() != null
                            && dragon.getCoordinates().getX().equals(Float.parseFloat(value));
                case "coordinates.y":
                    return dragon.getCoordinates() != null && dragon.getCoordinates().getY() != null
                            && dragon.getCoordinates().getY().equals(Float.parseFloat(value));
                case "killer.location.x":
                    return dragon.getKiller() != null && dragon.getKiller().getLocation() != null
                            && dragon.getKiller().getLocation().getX() != null
                            && dragon.getKiller().getLocation().getX().equals(Float.parseFloat(value));
                case "killer.location.y":
                    return dragon.getKiller() != null && dragon.getKiller().getLocation() != null
                            && dragon.getKiller().getLocation().getY() != null
                            && dragon.getKiller().getLocation().getY().equals(Float.parseFloat(value));
                case "killer.location.z":
                    return dragon.getKiller() != null && dragon.getKiller().getLocation() != null
                            && dragon.getKiller().getLocation().getZ() != null
                            && dragon.getKiller().getLocation().getZ().equals(Float.parseFloat(value));
                default:
                    System.out.println("[DEBUG] Неизвестное поле: " + field);
                    return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("[DEBUG] Ошибка преобразования числа: " + value);
            return false;
        }
    }


    private Object getFieldValue(Dragon dragon, String fieldName) {
        if (dragon == null || fieldName == null) {
            return null;
        }

        switch (fieldName.toLowerCase()) {
            case "id":
                return dragon.getId();
            case "name":
                return dragon.getName();
            case "coordinates":
                return dragon.getCoordinates();
            case "creationdate":
                return dragon.getCreationDate();
            case "age":
                return dragon.getAge() != null ? Integer.valueOf(dragon.getAge()) : null;
            case "wingspan":
                return dragon.getWingspan() != null ? Double.valueOf(dragon.getWingspan()) : null;
            case "speaking":
                return dragon.getSpeaking();
            case "type":
                return dragon.getType();
            case "killer":
                return dragon.getKiller();
            case "killer.name":
                return dragon.getKiller() != null ? dragon.getKiller().getName() : null;
            case "killer.nationality":
                return dragon.getKiller() != null ? dragon.getKiller().getNationality() : null;
            case "killer.haircolor":
                return dragon.getKiller() != null ? dragon.getKiller().getHairColor() : null;
            case "killer.birthday":
                return dragon.getKiller() != null ? dragon.getKiller().getBirthday() : null;
            case "coordinates.x":
                return dragon.getCoordinates() != null ? Double.valueOf(dragon.getCoordinates().getX()) : null;
            case "coordinates.y":
                return dragon.getCoordinates() != null ? Double.valueOf(dragon.getCoordinates().getY()) : null;
            case "killer.location.x":
                return dragon.getKiller() != null && dragon.getKiller().getLocation() != null
                        ? Double.valueOf(dragon.getKiller().getLocation().getX()) : null;
            case "killer.location.y":
                return dragon.getKiller() != null && dragon.getKiller().getLocation() != null
                        ? Double.valueOf(dragon.getKiller().getLocation().getY()) : null;
            case "killer.location.z":
                return dragon.getKiller() != null && dragon.getKiller().getLocation() != null
                        ? Double.valueOf(dragon.getKiller().getLocation().getZ()) : null;
            default:
                return null;
        }
    }
}