package pro.sky.sockssklad.service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import pro.sky.sockssklad.models.Color;
import pro.sky.sockssklad.models.Operation;
import pro.sky.sockssklad.models.Socks;
import pro.sky.sockssklad.models.Type;
import javax.annotation.PostConstruct;
import java.util.ArrayList;

@Service
public class SocksService {
    private ArrayList<Socks> socksArrayList = new ArrayList<>();
    private ArrayList<Operation> operationArrayList = new ArrayList<>();
    private final FilesService filesService;

    public SocksService(FilesService filesService) {
        this.filesService = filesService;
    }

    public void addSocks(Socks socks){
        for(Socks test : socksArrayList){
            if (test.getSizeOfSocks().equals(socks.getSizeOfSocks()) && test.getColor().equals(socks.getColor())
                    && test.getCottonPart() == socks.getCottonPart()){
                test.setQuantity(socks.getQuantity()+ test.getQuantity());
                operationArrayList.add(new Operation(Type.add,socks));
                saveToFile();
                return;
            }
        }
        operationArrayList.add(new Operation(Type.add,socks));
        socksArrayList.add(socks);
        saveToFile();
    }
    public Socks shipmentSocks(Socks socks){
        for (int i = 0; i < socksArrayList.size(); i++) {
            if (socksArrayList.get(i).getSizeOfSocks().equals(socks.getSizeOfSocks())
                    && socksArrayList.get(i).getCottonPart() == socks.getCottonPart() && socksArrayList.get(i).getColor().equals(socks.getColor())){

                if (socksArrayList.get(i).getQuantity() >= socks.getQuantity()) {
                    socksArrayList.get(i).setQuantity(socksArrayList.get(i).getQuantity()-socks.getQuantity());
                    socksArrayList.set(i, socksArrayList.get(i));
                    return socksArrayList.get(i);
                }
            }
        }
        operationArrayList.add(new Operation(Type.put,socks));
        saveToFile();
        return null;
    }
    public ArrayList<Socks> getSocks(String color, int size, int cottonMin , int cottonMax){
        ArrayList<Socks> finded = new ArrayList<>();
        for(Socks test : socksArrayList){
            if (test.getSizeOfSocks().getIntSizeOfSocks() == size && test.getColor() == Color.valueOf(color)
                    && test.getCottonPart()>cottonMin && test.getCottonPart()<cottonMax){
                finded.add(test);
            }
        }
        return finded;
    }
    public ArrayList<Socks> delSocks(Socks socks){
        ArrayList<Socks> finded1 = new ArrayList<>();
        for(Socks test : socksArrayList){
            if (test.equals(socks)){
                finded1.add(test);
                socksArrayList.remove(test);
                operationArrayList.add(new Operation(Type.del,socks));
            }
        }
        saveToFile();
        return finded1;
    }
    private void saveToFile(){
        filesService.saveToJsonFile(socksArrayList,"/socks.json");
        filesService.saveToJsonFile(operationArrayList,"/operation.json");
    }
    private void readFromFile(){
        String SocksJson = filesService.readFromFile("/socks.json");
        String OperationJson = filesService.readFromFile("/operation.json");
        try {
            socksArrayList = new ObjectMapper().readValue(SocksJson, new TypeReference<>() {
            });
            operationArrayList = new ObjectMapper().readValue(OperationJson, new TypeReference<ArrayList<Operation>>() {
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
