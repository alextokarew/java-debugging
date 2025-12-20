package com.github.alextokarew.javadebugging.jdb.domain;

// ClassRoom.java
import java.util.ArrayList;
import java.util.List;

// TODO rename this class
public class ClassRoom {
    private final String className;  // например, "10А"
    private final Teacher classTeacher;
    private final List<Student> students;

    public ClassRoom(String className, Teacher classTeacher) {
        this.className = className;
        this.classTeacher = classTeacher;
        this.students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public String getClassName() {
        return className;
    }

    public Teacher getClassTeacher() {
        return classTeacher;
    }

    public List<Student> getStudents() {
        return students;
    }

    @Override
    public String toString() {
        return "Класс: " + className +
                ", Классный руководитель: " + classTeacher.getName() +
                ", Учеников: " + students.size();
    }
}
