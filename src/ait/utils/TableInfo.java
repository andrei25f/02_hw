package ait.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TableInfo {
    public static void runInfo(Object obj) {
        Class<?> clazz = obj.getClass();
        runInfo(clazz);
    }

    public static void runInfo(String className) throws ClassNotFoundException {
        Class<?> clazz = Class.forName(className);
        runInfo(clazz);
    }

    public static void runInfo(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Table.class)) {
            System.out.println(clazz.getName() + " not a Scheme");
            return;
        }
        Table tableAnn = clazz.getAnnotation(Table.class);
        String tableName = "".equals(tableAnn.name()) ? clazz.getSimpleName().toLowerCase() : tableAnn.name();
        Field[] fields = clazz.getDeclaredFields();
        String idField = Arrays.stream(fields)
                .filter(x -> x.isAnnotationPresent(Id.class))
                .map(x -> x.getName())
                .findFirst().orElse(null);
        if (idField == null) {
            throw new RuntimeException("Id is not defined");
        }
        if (Arrays.stream(fields).filter(x -> x.isAnnotationPresent(Id.class)).count() > 1) {
            throw new RuntimeException("Id duplicated");
        }
        List<String> uniqueIndexes = Arrays.stream(fields)
                .filter(x -> x.getAnnotation(Index.class) != null && x.getAnnotation(Index.class).unique())
                .map(x -> x.getName())
                .toList();
        List<String> nonUniqueIndexes = Arrays.stream(fields)
                .filter(x -> x.getAnnotation(Index.class) != null && !x.getAnnotation(Index.class).unique())
                .map(x -> x.getName())
                .toList();
        /*for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class)) {
                if (idField != null) {
                    throw new RuntimeException("Id duplicated");
                }
                idField = field.getName();
            }
            Index indexAnn = field.getAnnotation(Index.class);
            if (indexAnn != null) {
                if (indexAnn.unique()) {
                    uniqueIndexes.add(field.getName());
                } else {
                    nonUniqueIndexes.add(field.getName());
                }
            }
        }*/
        printTableInfo(tableName, idField, uniqueIndexes, nonUniqueIndexes);
    }

    private static void printTableInfo(String tableName, String idField, List<String> uniqueIndexes, List<String> nonUniqueIndexes) {
        System.out.println("Table: " + tableName);
        System.out.println("Id: " + idField);
        System.out.println("\tUnique Indexes");
        uniqueIndexes.forEach(System.out::println);
        System.out.println("\tNon Unique Indexes");
        nonUniqueIndexes.forEach(System.out::println);
    }
}
