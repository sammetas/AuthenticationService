package com.auth.AuthenticationService;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
public class SampleTestRequest {
    public SampleTestRequest(){
        System.out.println("When are you coming here?");
    }

    private List<Student> STUDENTS=
            Arrays.asList(new Student(1,"samm"),new Student(2,"Allah"),new Student(3,"jesus"));

    @GetMapping(path = "{studentId}")
    public  Student getStudent(@PathVariable("studentId") Integer id){
        System.out.println("getStudent method");
        return    STUDENTS.stream().filter(student -> student.getId()==id.intValue()).findFirst()
                .orElseThrow(()->new IllegalStateException("Student "+id.intValue() +" does not exists!"));
    }

    @PostMapping
    public Integer putStudent(@RequestBody Student student){
        System.out.println(student);
        STUDENTS.add(student);
        return 1;
    }

    @PutMapping
    public void updateStudent(Integer studentId,@RequestBody Student student){
        System.out.println(student);

    }

    @DeleteMapping(path = "studentId")
    public int deleteStudent(@PathVariable ("studentId") Integer studentId){
        System.out.println(studentId.intValue());
        return studentId;
    }



}
