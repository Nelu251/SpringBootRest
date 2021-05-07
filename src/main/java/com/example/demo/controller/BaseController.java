package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BaseController {
    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index.html");
    }

    @GetMapping("/add")
    public ModelAndView addStudent() {
        return new ModelAndView("addStudent.html");
    }

    @GetMapping("/update")
    public ModelAndView updateStudent() {
        return new ModelAndView("updateStudent.html");
    }
}
