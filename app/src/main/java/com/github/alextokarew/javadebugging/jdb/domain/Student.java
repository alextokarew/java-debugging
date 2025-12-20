package com.github.alextokarew.javadebugging.jdb.domain;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private final String name;
    private final int age;
    private final List<Subject> subjects;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
        this.subjects = new ArrayList<>();
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    @Override
    public String toString() {
        return name + " (" + age + " лет), изучает: " + subjects;
    }
}
