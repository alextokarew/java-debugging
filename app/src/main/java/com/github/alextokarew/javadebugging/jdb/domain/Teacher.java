package com.github.alextokarew.javadebugging.jdb.domain;

// Teacher.java
public class Teacher {
    private final String name;
    private final Subject subject;

    public Teacher(String name, Subject subject) {
        this.name = name;
        this.subject = subject;
    }

    public String getName() {
        return name;
    }

    public Subject getSubject() {
        return subject;
    }

    @Override
    public String toString() {
        return name + " (преподает " + subject.getName() + ")";
    }
}
