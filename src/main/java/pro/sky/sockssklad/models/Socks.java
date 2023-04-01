package pro.sky.sockssklad.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Socks {
    Color color; //цвет
    SizeOfSocks sizeOfSocks; //размер
    int cottonPart; //количество хлопка
    int quantity; //количество на складе
}