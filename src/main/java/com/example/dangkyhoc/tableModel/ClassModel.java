package com.example.dangkyhoc.tableModel;

import com.example.dangkyhoc.Store;
import com.example.dangkyhoc.model.Classroom;
import com.example.dangkyhoc.model.Course;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClassModel {
    Integer id;
    String name;
    String max;
    String currentStudent;
    String isClose;
    String courseName;
    Integer courseId;
    Integer credit;
    Integer creditCondition;
    Integer accountId;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public Integer getCreditCondition() {
        return creditCondition;
    }

    public void setCreditCondition(Integer creditCondition) {
        this.creditCondition = creditCondition;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }



    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public ClassModel() {
    }

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

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getCurrentStudent() {
        return currentStudent;
    }

    public void setCurrentStudent(String currentStudent) {
        this.currentStudent = currentStudent;
    }

    public String getIsClose() {
        return isClose;
    }

    public Boolean getIsCloseBool() {
        return isClose.equals("Đóng") ? true : false;
    }

    public void setIsCloseBool(boolean isClose) {
        setIsClose(isClose ? "Đóng" : "Mở");
    }

    public void setIsClose(String isClose) {
        this.isClose = isClose;
    }

    public ClassModel(Integer id, String name, String max, String currentStudent, String isClose, Integer courseId) {
        this.id = id;
        this.name = name;
        this.max = max;
        this.currentStudent = currentStudent;
        this.isClose = isClose;
        this.courseId = courseId;
    }

    private Classroom getClassroom() {
        Classroom classroom = new Classroom(id,name,Integer.parseInt(max),Integer.parseInt(currentStudent),getIsCloseBool(),courseId);
        return classroom;
    }

    public boolean updateClass() {
        return getClassroom().save();
    }

    public boolean deleteClass() {
        return getClassroom().delete();
    }

    public boolean registerClass(){
        return getClassroom().register();
    }

    public boolean cancelClass(){ return getClassroom().cancel(Store.account.getId());}

    public Boolean isInClass() {
        return getClassroom().isInClass(Store.account.getId());
    }


    public void setAttribute(ResultSet resultSet) throws SQLException {
        setName(resultSet.getString("name"));
        setMax(String.valueOf(resultSet.getInt("max")));
        setCurrentStudent(String.valueOf(resultSet.getInt("current_student")));
        setIsCloseBool(resultSet.getBoolean("is_close"));
        setCourseId(resultSet.getInt("course_id"));
        setId(resultSet.getInt("id"));
    }

    public void setAttributeWithCourse(ResultSet resultSet) throws SQLException {
        setAttribute(resultSet);
        setCourseName(resultSet.getString("course_name"));
        setCredit(resultSet.getInt("credit"));
        setCreditCondition(resultSet.getInt("credit_condition"));
    }



}
