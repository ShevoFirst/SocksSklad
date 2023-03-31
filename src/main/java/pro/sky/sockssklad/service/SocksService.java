package pro.sky.sockssklad.service;

import org.springframework.stereotype.Service;
import pro.sky.sockssklad.models.Color;
import pro.sky.sockssklad.models.Socks;

import java.util.ArrayList;

@Service
public class SocksService {
    private final ArrayList<Socks> socksMap = new ArrayList<>();

    public void addSocks(Socks socks){
        socks.setQuantity(socks.getQuantity()+1);
        socksMap.add(socks);
    }
    public ArrayList getSocks(String color, int size, int cottonMin , int cottonMax){
        ArrayList<Socks> finded = new ArrayList<>();
        for(Socks test : socksMap){
            if (test.getSizeOfSocks().getIntSizeOfSocks() == size && test.getColor() == Color.valueOf(color)
                    && test.getCottonPart()>cottonMin && test.getCottonPart()<cottonMax){
                finded.add(test);
            }
        }
        return finded;
}
}
