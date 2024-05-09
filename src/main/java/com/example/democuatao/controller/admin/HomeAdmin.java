package com.example.democuatao.controller.admin;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${api.prefix}/layout")
public class HomeAdmin {
    @GetMapping("")
    public String displayAdmin(){
        return "layouts/admin";
    }
}
