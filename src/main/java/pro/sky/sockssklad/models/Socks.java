package pro.sky.sockssklad.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Socks {
    Color color;
    SizeOfSocks sizeOfSocks;
    int cottonPart;
    int quantity;
    private boolean InStock;
}