package com.example.dangkyhoc.model;

import com.example.dangkyhoc.Store;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Classroom extends Model{
    private String name;
    private Integer max, currentStudent;
    private boolean isClose;
    private Integer courseId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Integer getCurrentStudent() {
        return currentStudent;
    }

    public void setCurrentStudent(Integer currentStudent) {
        this.currentStudent = currentStudent;
    }

    public boolean isClose() {
        return isClose;
    }

    public void setClose(boolean close) {
        isClose = close;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Classroom(Integer id, String name, Integer max, Integer currentStudent, boolean isClose, Integer courseId) {
        super();
        table="class";
        this.id = id;
        this.name = name;
        this.max = max;
        this.currentStudent = currentStudent;
        this.isClose = isClose;
        this.courseId = courseId;
    }

    public Classroom() {
        super();
        table="class";
    }

    public Classroom(String name, Integer max, Integer courseId) {
        super();
        table="class";
        this.name = name;
        this.max = max;
        this.courseId = courseId;
        setCurrentStudent(0);
        setClose(true);
    }

    public Integer getIsCloseInt(){
        return isClose ? 1 : 0;
    }

    public void setAttribute(ResultSet resultSet) throws SQLException {
        setName(resultSet.getString("name"));
        setMax(resultSet.getInt("max"));
        setClose(resultSet.getBoolean("is_close"));
        setCourseId(resultSet.getInt("course_id"));
        id = resultSet.getInt("id");
    }

    public boolean save() {
        String query;

        if (id != null) {
            query = "UPDATE `" + table + "` SET `name` = '" + name + "', `max` = '" + max + "', `current_student` = '" + currentStudent.toString() + "',`course_id` = '" + courseId.toString() + "', `is_close` = '" + getIsCloseInt().toString() + "' WHERE (`id` = '" + id + "');";
        } else {
            query = "INSERT INTO `" + table + "` (`name`, `max`, `current_student`, `is_close`, `course_id`) VALUES ('" + name + "', '" + max.toString() + "', '" + currentStudent.toString() + "', '" + getIsCloseInt().toString() + "', '" + courseId.toString() + "');";
        }

        return executeUpdate(query);
    }

    public ResultSet allOpenWithCourse(){
        String query = "SELECT class.*, course.name as course_name, course.credit, course.credit_condition  FROM class inner join course on class.course_id = course.id\n" +
                "where is_close = 0;";
        return executeQuery(query);
    }

    public ResultSet allWithCourse(){
        String query = "SELECT class.*, course.name as course_name, course.credit, course.credit_condition  FROM class inner join course on class.course_id = course.id;";
        return executeQuery(query);
    }

    public ResultSet accountClassWithCourse(Integer account_id){
        String query = "SELECT class.*, course.name as course_name, course.credit, course.credit_condition  FROM class inner join course on class.course_id = course.id where is_close = 0 and class.id in \n" +
                "(select class_id from account_class where account_id = "+account_id.toString()+");";
        return executeQuery(query);
    }

    public boolean isInClass(Integer account_id){
        String query = "SELECT * from account_class where class_id = "+id.toString()+" and  account_id ="+account_id.toString();
        return executeQuery(query) == null ? false : true;
    }

    @Override
    public String toString()  {
        return this.name;
    }

    public boolean register() {
        String query = "INSERT INTO `dangkyhoc`.`account_class` (`class_id`, `account_id`) VALUES ('"+id.toString()+"', '"+ Store.account.id.toString() +"');";
        if (!executeUpdate(query)){
            return false;
        }
        setCurrentStudent(currentStudent+1);
        save();
        return true;
    }

    public boolean cancel(Integer account_id) {
        if (isClose){
            return false;
        }
        String query = "DELETE FROM `dangkyhoc`.`account_class` WHERE `class_id` = "+id.toString()+" and `account_id` ="+account_id.toString()+";";
        if(! executeUpdate(query)){
            return false;
        }
        setCurrentStudent(currentStudent-1);
        return false;
    }
}
