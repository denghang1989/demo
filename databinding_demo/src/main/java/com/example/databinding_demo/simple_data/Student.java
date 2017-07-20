package com.example.databinding_demo.simple_data;

/**
 * @date 2017/6/12 10
 */
public class Student {
    private String name;
    private String school;

    public Student(String name, String school) {
        this.name = name;
        this.school = school;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}
