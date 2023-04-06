package pro.sky.sockssklad.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Socks {
    private Color color; //цвет
    private SizeOfSocks sizeOfSocks; //размер
    private int cottonPart; //количество хлопка
    private int quantity; //количество на складе
}