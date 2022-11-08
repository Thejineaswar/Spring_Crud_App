package com.example.basiccrudpostgrespring.controller;

import com.example.basiccrudpostgrespring.model.Course;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.basiccrudpostgrespring.dao.CourseJdbcDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class dbController {
    private final CourseJdbcDAO courseJdbcDAO;
    dbController(CourseJdbcDAO courseJdbcDAO){
        this.courseJdbcDAO = courseJdbcDAO;
    }

    @GetMapping(value = "/list")
    public ResponseEntity<List<Course>> check(){
            List<Course> courses =  courseJdbcDAO.list();
            return ResponseEntity.ok(courses);
    }

    @PostMapping(value="/insert")
    public ResponseEntity<HashMap<String, String>> insert(@RequestBody Course course){
        HashMap<String, String> mp = new HashMap();
        if(course.getTitle() == null || course.getDescription() == null || course.getLink() == null){
            mp.put("text", "Check the format of the json");
            return ResponseEntity.status(400).body(mp);
        }

        int output = 0;
        output = courseJdbcDAO.create(course);

        if(output == 1){
            mp.put("text" , "New Record Inserted");
            return ResponseEntity.ok(mp);
        }else{
            mp.put("text", "Insertion not successful. Try after some time.");
            return ResponseEntity.internalServerError().body(mp);
        }
    }

    @PutMapping(value="/update")
    public ResponseEntity<HashMap<String, String>> update(@RequestBody Course course){
        HashMap<String, String> mp = new HashMap();
        if(course.getTitle() == null || course.getDescription() == null || course.getLink() == null){
            mp.put("text", "Check the format of the json or look for missing fields");
            return ResponseEntity.status(400).body(mp);
        }
        int output = courseJdbcDAO.update(course ,course.getCourseId());
        if(output == 1){
            mp.put("text" , "New Record Updated");
            return ResponseEntity.ok(mp);
        }else{
            mp.put("text", "Record update not possible. Potentially id not found");
            return ResponseEntity.status(400).body(mp);
        }
    }

    @DeleteMapping(value="/delete")
    public ResponseEntity<HashMap<String, String>> delete(@RequestBody Map<String, Object> payload){
        HashMap<String, String> mp = new HashMap();
        if(!payload.containsKey("course_id")){
            mp.put("text", "Mention the course_id in the json file");
            return ResponseEntity.status(400).body(mp);
        }
        int course_id;
        try {
            course_id = (int) payload.get("course_id");
        }catch (Exception e){
            mp.put("text", "Please pass course id as a number alone");
            return ResponseEntity.status(400).body(mp);
        }
        int output = courseJdbcDAO.delete(course_id);
        if(output == 1){
            mp.put("text" , "Record with id(" + course_id + ") deleted");
            return ResponseEntity.ok(mp);
        }else{
            mp.put("text" , "Potentially the id was not present in the db. Pass a valid ID");
            return ResponseEntity.ok(mp);
        }

    }
}
