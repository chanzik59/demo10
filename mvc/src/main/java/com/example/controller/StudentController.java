package com.example.controller;

import com.example.entity.AA;
import com.example.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * @author czq
 * @date 2023/9/7 11:20
 */

@RequestMapping("student")
@Controller
@ConditionalOnExpression("'${school.name}'.equals('sms1')")
@ConditionalOnResource(resources = "classpath:test.xml")
//@ConditionalOnProperty(prefix = "school",name ="name" ,havingValue = "sms")
public class StudentController {

    //    @Lazy
    @Autowired
    private AA aa;


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object studentException(Exception e) {
        Student student = new Student();
        student.setId("30");
        student.setName("晓明");
        return student;
    }


    @InitBinder("oStudent")
    public void initBinder2(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("oStudent.");
    }


    @RequestMapping("test")
    @ResponseBody
    public String test(Student student, @ModelAttribute("oStudent") Student oStudent) {
        System.out.println(student);
        System.out.println(oStudent);

        return "aa";
    }


    @RequestMapping("scope")
    @ResponseBody
    public String scop() {
        System.out.println(aa);

        return "";
    }


    @RequestMapping(value = "user")
    @ResponseBody
    public Student getUser() {

        Student student = new Student();
        student.setName("aa");
        student.setId("20");
        return student;
    }


}
