package com.github.alextokarew.javadebugging.jdb;


import java.util.List;

import com.github.alextokarew.javadebugging.jdb.domain.*;
import com.github.alextokarew.javadebugging.jdb.report.SchoolReport;

public class JdbExample {
    public static void main(String[] args) {
        // (Прежний код создания школы, учителей, учеников, классов)

        Subject math = new Subject("Математика");
        Subject physics = new Subject("Физика");
        Subject literature = new Subject("Литература");

        Teacher teacherMath = new Teacher("Анна Ивановна", math);
        Teacher teacherPhysics = new Teacher("Сергей Петрович", physics);
        Teacher teacherLit = new Teacher("Мария Семёновна", literature);

        Student student1 = new Student("Алексей", 16);
        student1.addSubject(math);
        student1.addSubject(physics);

        Student student2 = new Student("Елена", 17);
        student2.addSubject(literature);
        student2.addSubject(math);

        ClassRoom class10A = new ClassRoom("10А", teacherMath);
        class10A.addStudent(student1);
        class10A.addStudent(student2);

        Student student3 = new Student("Дмитрий", 16);
        student3.addSubject(physics);
        student3.addSubject(literature);

        ClassRoom class10B = new ClassRoom("10Б", teacherLit);
        class10B.addStudent(student3);

        School school = new School("Средняя школа №15");
        school.addClass(class10A);
        school.addClass(class10B);

        // === ГЕНЕРАЦИЯ ОТЧЁТА ===
        SchoolReport report = new SchoolReport(school);

        System.out.println(report.generateReport());

        // Дополнительные запросы
        System.out.println("\n=== ДОПОЛНИТЕЛЬНО ===");
        System.out.println("Топ-2 популярных предмета:");
        for (Subject s : report.getTopSubjects(2)) {
            System.out.println(" - " + s.getName());
        }

        System.out.println("\nУченики, изучающие Физику:");
        List<Student> physicsStudents = report.getStudentsBySubject(physics);
        for (Student s : physicsStudents) {
            System.out.println(" - " + s.getName());
        }
    }
}

