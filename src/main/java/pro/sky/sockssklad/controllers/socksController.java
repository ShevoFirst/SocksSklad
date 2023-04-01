package pro.sky.sockssklad.controllers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
            summary = "Регистрирует приход товара на склад",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "удалось добавить приход",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "параметры запроса отсутствуют или имеют некорректный формат",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "произошла ошибка, не зависящая от вызывающей стороны",
                            content = @Content
                    )
            }
    )
    public ResponseEntity addSocks(@RequestBody Socks socks){
        socksService.addSocks(socks);
        return ResponseEntity.ok(socks);
    }
    @PutMapping
    @Operation(
            summary = "Регистрирует отпуск носков со склада",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "удалось произвести отпуск носков со склада",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "товара нет на складе в нужном количестве или параметры запроса имеют некорректный формат",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "произошла ошибка, не зависящая от вызывающей стороны",
                            content = @Content
                    )
            }
    )
    public ResponseEntity shipmentSocks(@RequestBody Socks socks){
        Socks socks1 = socksService.shipmentSocks(socks);
        if (socks1 == null) {
            return ResponseEntity.status(400).build();
        }
        return ResponseEntity.ok(socks);
    }

    @GetMapping("/employees/{color}/{size}/{cottonMin}/{cottonMax}")
    @Operation(
            summary = "Возвращает общее количество носков на складе, соответствующих переданным в параметрах критериям запроса",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "запрос выполнен, результат в теле ответа в виде строкового представления целого числа",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "параметры запроса отсутствуют или имеют некорректный формат",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "произошла ошибка, не зависящая от вызывающей стороны",
                            content = @Content
                    )
            }
    )
    public ResponseEntity getSocks(@PathVariable String color, @PathVariable int size, @PathVariable int cottonMin, @PathVariable int cottonMax){
        ArrayList searchList= socksService.getSocks(color, size, cottonMin, cottonMax);
        if (!searchList.isEmpty())
            return ResponseEntity.ok(searchList);
        return ResponseEntity.status(400).build();
    }
    @DeleteMapping
    @Operation(
            summary = "Регистрирует списание испорченных (бракованных) носков",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "запрос выполнен, товар списан со склада",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "параметры запроса отсутствуют или имеют некорректный формат",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "произошла ошибка, не зависящая от вызывающей стороны",
                            content = @Content
                    )
            }
    )
    public ResponseEntity delSocks(@RequestBody Socks socks){
        ArrayList<Socks> socksArrayList = socksService.delSocks(socks);
        if (socksArrayList == null) {
            ResponseEntity.status(400).build();
        }
        return ResponseEntity.ok(socksArrayList);
    }
}
