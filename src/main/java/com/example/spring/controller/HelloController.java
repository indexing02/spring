package com.example.spring.controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class HelloController {

        @GetMapping("/hello")
        public String hello() {
            System.out.println("hello");
            return "hello";
        }

        @GetMapping("greet")
        public String greet(@RequestParam String name) {
            return name + "님 안녕하세요!";
        }

}
