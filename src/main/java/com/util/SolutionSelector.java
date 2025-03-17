package com.util;


import com.solution.Solution;
import org.reflections.Reflections;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public class SolutionSelector {
    public static Solution findLatestSolution() {
        Reflections reflections = new Reflections("com.solution");  // 검색할 패키지 지정
        Set<Class<?>> solveClasses = reflections.getTypesAnnotatedWith(Solve.class);

        Class<?> solveClass = solveClasses.stream()
                .max((c1, c2) -> {
                    LocalDate date1 = parseDate(c1.getAnnotation(Solve.class).value());
                    LocalDate date2 = parseDate(c2.getAnnotation(Solve.class).value());
                    return date1.compareTo(date2);
                })
                .orElseThrow(() -> new RuntimeException("No Solution implementation found"));

        try {
            return (Solution) solveClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Error loading Solution implementation", e);
        }
    }

    private static LocalDate parseDate(String dateStr) {
        return LocalDate.parse(dateStr, DateTimeFormatter.BASIC_ISO_DATE);  // "yyyyMMdd" 파싱
    }
}
