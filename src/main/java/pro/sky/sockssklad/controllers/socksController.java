package pro.sky.sockssklad.controllers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.sockssklad.models.Socks;
import pro.sky.sockssklad.service.SocksService;

import java.util.ArrayList;

@RestController
@RequestMapping("/api")
@Tag(name = "Работа с файлами", description = "CRUD операции и другие эндпоинты с файлами")
public class socksController {
    private final SocksService socksService;

    public socksController(SocksService socksService) {
        this.socksService = socksService;
    }

    @PostMapping
    @Operation(
            summary = "Регистрирует отпуск носков со склада"
    )
    public ResponseEntity addSocks(@RequestBody Socks socks){
        socksService.addSocks(socks);
        return ResponseEntity.ok(socks);
    }

    @GetMapping("/employees/{color}/{size}/{cottonMin}/{cottonMax}")
    @Operation(
            summary = "Возвращает общее количество носков на складе, соответствующих переданным в параметрах критериям запроса"
    )
    public ResponseEntity getSocks(@PathVariable String color, @PathVariable int size, @PathVariable int cottonMin, @PathVariable int cottonMax){
        ArrayList<Socks> searchList= socksService.getSocks(color, size, cottonMin, cottonMax);
        return ResponseEntity.ok(searchList);
    }//
}
