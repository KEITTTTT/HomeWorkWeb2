package ru.glebova.homeworkweb2.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {

    @GetMapping("/")
    public String getAMessage(){
        return "Приложение запущено";
    }
    @GetMapping("/info")
    public String getInfo(){
        return "Екатерина Глебова\n" +
                "Учебный проект\n" +
                "22.01.2023\n" +
                "Описание: введение в web - разработку";





    }
}
