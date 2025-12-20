package com.github.alextokarew.javadebugging.jdb.domain;

// School.java
import java.util.ArrayList;
import java.util.List;

public class School {
    private final String name;
    private final List<ClassRoom> classes;

    public School(String name) {
        this.name = name;
        this.classes = new ArrayList<>();
    }

    public void addClass(ClassRoom classRoom) {
        classes.add(classRoom);
    }

    public String getName() {
        return name;
    }

    public List<ClassRoom> getClasses() {
        return classes;
    }

    @Override
    public String toString() {
        return "Школа: " + name + ", Количество классов: " + classes.size();
    }
}
