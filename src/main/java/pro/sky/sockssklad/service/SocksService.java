package pro.sky.sockssklad.service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import pro.sky.sockssklad.models.Color;
import pro.sky.sockssklad.models.Socks;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class SocksService {
    private ArrayList<Socks> socksMap = new ArrayList<>();
    private final FilesService filesService;

    public SocksService(FilesService filesService) {
        this.filesService = filesService;
    }

    public void addSocks(Socks socks){
        for(Socks test : socksMap){
            if (test.getSizeOfSocks().equals(socks.getSizeOfSocks()) && test.getColor().equals(socks.getColor())
                    && test.getCottonPart() == socks.getCottonPart()){
                test.setQuantity(socks.getQuantity()+ test.getQuantity());
                saveToFile();
                System.out.println("rrer");
                return;
            }
        }
        socksMap.add(socks);
        saveToFile();
    }
    public Socks shipmentSocks(Socks socks){
        for (int i = 0; i <= socksMap.size(); i++) {
            if (socksMap.get(i).getSizeOfSocks() == socks.getSizeOfSocks()
                    && socksMap.get(i).getCottonPart() == socks.getCottonPart() && socksMap.get(i).getColor() == socks.getColor()){

                if (socksMap.get(i).getQuantity() > socks.getQuantity()) {
                    socksMap.get(i).setQuantity(socksMap.get(i).getQuantity()-socks.getQuantity());
                    socksMap.set(i,socksMap.get(i));
                    return socksMap.get(i);
                }
            }
        }
        saveToFile();
        return null;
    }
    public ArrayList<Socks> getSocks(String color, int size, int cottonMin , int cottonMax){
        ArrayList<Socks> finded = new ArrayList<>();
        for(Socks test : socksMap){
            if (test.getSizeOfSocks().getIntSizeOfSocks() == size && test.getColor() == Color.valueOf(color)
                    && test.getCottonPart()>cottonMin && test.getCottonPart()<cottonMax){
                finded.add(test);
            }
        }
        return finded;
    }
    public ArrayList<Socks> delSocks(Socks socks){
        ArrayList<Socks> finded1 = new ArrayList<>();
        for(Socks test : socksMap){
            if (test.equals(socks)){
                finded1.add(test);
                socksMap.remove(test);
            }
        }
        saveToFile();
        return finded1;
    }
    private void saveToFile(){
        filesService.saveToJsonFile(socksMap);
    }
    private void readFromFile(){
        String json = filesService.readFromFile();
        try {
            socksMap = new ObjectMapper().readValue(json, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    @PostConstruct
    private void init() {
        try {
            readFromFile();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
