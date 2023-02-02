package ru.glebova.homeworkweb2.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Пробный контролер", description = "Проверка работы и инфо приложения")

public class InfoController {
    @GetMapping("/")
    @Operation(description = "Проверка работы приложения")
    public String getAMessage(){
        return "Приложение запущено";
    }
    @GetMapping("/info")
    @Operation(description = "Информация о приложении")
    public String getInfo(){
        return "Екатерина Глебова\n" +
                "Учебный проект\n" +
                "22.01.2023\n" +
                "Описание: введение в web - разработку";
    }
}
