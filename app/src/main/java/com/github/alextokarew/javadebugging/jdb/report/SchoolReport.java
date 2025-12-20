package com.github.alextokarew.javadebugging.jdb.report;

import java.util.*;

import com.github.alextokarew.javadebugging.jdb.domain.*;

public class SchoolReport {

    private School school;
    private Map<Subject, Integer> subjectFrequency; // частота предметов
    private int totalStudents;
    private double avgSubjectsPerStudent;

    public SchoolReport(School school) {
        this.school = school;
        this.subjectFrequency = new HashMap<>();
        calculateStatistics();
    }

    // Основной метод: формирует полный текстовый отчёт
    public String generateReport() {
        StringBuilder report = new StringBuilder();
        report.append("=== ОТЧЁТ ПО ШКОЛЕ ===\n");
        report.append(school).append("\n\n");

        for (ClassRoom cls : school.getClasses()) {
            report.append("Класс: ").append(cls.getClassName())
                    .append(" | Классный руководитель: ").append(cls.getClassTeacher().getName())
                    .append(" | Учеников: ").append(cls.getStudents().size())
                    .append("\n");

            for (Student student : cls.getStudents()) {
                report.append("  - ").append(student.getName())
                        .append(" (").append(student.getAge()).append(" лет) | Предметы: ")
                        .append(student.getSubjects()).append("\n");
            }
            report.append("\n");
        }

        // Добавляем статистику
        report.append("=== СТАТИСТИКА ===\n");
        report.append("Всего учеников в школе: ").append(totalStudents).append("\n");
        report.append("Среднее число предметов на ученика: ").append(String.format("%.2f", avgSubjectsPerStudent)).append("\n");
        report.append("Самые популярные предметы:\n");

        // Сортируем предметы по частоте (по убыванию)
        List<Map.Entry<Subject, Integer>> sortedSubjects = new ArrayList<>(subjectFrequency.entrySet());
        sortedSubjects.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        for (Map.Entry<Subject, Integer> entry : sortedSubjects) {
            report.append("  - ").append(entry.getKey().getName())
                    .append(": ").append(entry.getValue()).append(" учеников\n");
        }

        return report.toString();
    }

    // Вспомогательный метод: подсчёт статистики
    private void calculateStatistics() {
        totalStudents = 0;
        int totalSubjects = 0;

        for (ClassRoom cls : school.getClasses()) {
            for (Student student : cls.getStudents()) {
                totalStudents++;
                List<Subject> subjects = student.getSubjects();
                totalSubjects += subjects.size();

                // Обновляем частоту каждого предмета
                for (Subject subject : subjects) {
                    subjectFrequency.put(subject, subjectFrequency.getOrDefault(subject, 0) + 1);
                }
            }
        }

        avgSubjectsPerStudent = totalStudents > 0 ? (double) totalSubjects / totalStudents : 0;
    }

    // Дополнительный метод: получить топ-N популярных предметов
    public List<Subject> getTopSubjects(int n) {
        List<Map.Entry<Subject, Integer>> sorted = new ArrayList<>(subjectFrequency.entrySet());
        sorted.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        List<Subject> top = new ArrayList<>();
        for (int i = 0; i < n && i < sorted.size(); i++) {
            top.add(sorted.get(i).getKey());
        }
        return top;
    }

    // Получить всех учеников, изучающих заданный предмет
    public List<Student> getStudentsBySubject(Subject subject) {
        List<Student> result = new ArrayList<>();
        for (ClassRoom cls : school.getClasses()) {
            for (Student student : cls.getStudents()) {
                if (student.getSubjects().contains(subject)) {
                    result.add(student);
                }
            }
        }
        return result;
    }
}
