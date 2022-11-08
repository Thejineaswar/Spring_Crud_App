package com.example.basiccrudpostgrespring.dao;

import com.example.basiccrudpostgrespring.model.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CourseJdbcDAO implements DAO<Course>{

    private static final Logger log = LoggerFactory.getLogger(CourseJdbcDAO.class);
    private JdbcTemplate jdbcTemplate;

    RowMapper<Course> rowMapper = (rs,rowNum) -> {
        Course course =  new Course();
        course.setCourseId(rs.getInt("course_id"));
        course.setTitle(rs.getString("title"));
        course.setDescription(rs.getString("description"));
        course.setLink(rs.getString("link"));
        return course;
    };

    public CourseJdbcDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Course> list() {
        String sql = "SELECT course_id,title, description, link from course";
        return jdbcTemplate.query(sql,rowMapper);
    }

    @Override
    public int create(Course course) {
        String sql = "INSERT INTO COURSE(TITLE, DESCRIPTION,LINK) VALUES(?,?,?)";
        int rowsAffected = jdbcTemplate.update(sql, course.getTitle(), course.getDescription(), course.getLink());
        if(rowsAffected == 1){
            log.info("New Course created : " + course.getTitle());
            return 1;
        }else{
            return 0;
        }
    }

    @Override
    public Optional<Course> get(int aid) {
        String sql = "SELECT course_id,title, description, link from course where course_id = ?";
        Course course = null;
        try{
            course = jdbcTemplate.queryForObject(sql, new Object[]{aid}, rowMapper);
        }catch(DataAccessException e){
            log.info("Course not found : " + aid);
        }
        return Optional.ofNullable(course);
    }

    @Override
    public int update(Course course, int id) {
        String sql = "UPDATE course set title = ?, description = ?, link = ? where course_id = ?";
        int update = jdbcTemplate.update(sql, course.getTitle(), course.getDescription(), course.getLink(),id);
        if(update == 1){
            log.info("Course Updated : " + course.getTitle());
            return 1;
        }
        return 0;
    }

    @Override
    public int delete(int id) {

        int output = jdbcTemplate.update("delete from course where course_id = ?", id);
        return output;
    }
}
