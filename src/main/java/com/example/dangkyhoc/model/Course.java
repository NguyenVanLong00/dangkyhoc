package com.example.dangkyhoc.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Course extends Model {

    private String name;
    private String description;
    private Integer credit;
    private Integer creditCondition;

    public Course(String name, String description, Integer credit, Integer creditCondition) {
        super();
        this.name = name;
        this.description = description;
        this.credit = credit;
        this.creditCondition = creditCondition;
        this.table = "course";
    }

    public Course() {
        super();
        this.table = "course";
    }

    public void setCourse(Integer id,String name, String description, Integer credit, Integer creditCondition) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.credit = credit;
        this.creditCondition = creditCondition;
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

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        if (credit >= 0)
            this.credit = credit;
    }

    public Integer getCreditCondition() {
        return creditCondition;
    }

    public void setCreditCondition(Integer creditCondition) {
        if (creditCondition >= 0)
            this.creditCondition = creditCondition;
    }

    public void setAttribute(ResultSet resultSet) throws SQLException {
        setName(resultSet.getString("name"));
        setCredit(resultSet.getInt("credit"));
        setCreditCondition(resultSet.getInt("credit_condition"));
        setDescription(resultSet.getString("description"));
        id = resultSet.getInt("id");
    }



    public boolean save() {
        String query;

        if (id != null) {
            query = "UPDATE `" + table + "` SET `name` = '" + name + "', `description` = '" + description + "', `credit` = '" + credit.toString() + "', `credit_condition` = '" + creditCondition.toString() + "' WHERE (`id` = '" + id + "');";
        } else {
            query = "INSERT INTO `" + table + "` (`name`, `description`, `credit`, `credit_condition`) VALUES ('" + name + "', '" + description + "', '" + credit.toString() + "', '" + creditCondition.toString() + "');";
        }

        return executeUpdate(query);
    }

    @Override
    public String toString()  {
        return this.name;
    }
}
