package com.example.dangkyhoc.tableModel;

import com.example.dangkyhoc.model.Course;
import javafx.scene.control.Button;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseModel {
    Integer id;
    String name, description;
    String credit, creditCondition;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getCreditCondition() {
        return creditCondition;
    }

    public void setCreditCondition(String creditCondition) {
        this.creditCondition = creditCondition;
    }

    public CourseModel() {
    }

    private Course getCourse() {
        Course course = new Course();
        course.setCourse(id, name, description, Integer.parseInt(credit), Integer.parseInt(creditCondition));
        return course;
    }

    public boolean updateCourse() {
        return getCourse().save();
    }

    public boolean deleteCourse() {
        return getCourse().delete();
    }


    public void setAttribute(ResultSet resultSet) throws SQLException {
        setName(resultSet.getString("name"));
        setCredit(String.valueOf(resultSet.getInt("credit")));
        setCreditCondition(String.valueOf(resultSet.getInt("credit_condition")));
        setDescription(resultSet.getString("description"));
        setId(resultSet.getInt("id"));
    }
}
