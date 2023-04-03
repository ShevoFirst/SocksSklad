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
    private ArrayList<Socks> socksMap = new ArrayList<>();
    private ArrayList<Operation> operationList = new ArrayList<>();
    private final FilesService filesService;

    public SocksService(FilesService filesService) {
        this.filesService = filesService;
    }

    public void addSocks(Socks socks){
        for(Socks test : socksMap){
            if (test.getSizeOfSocks().equals(socks.getSizeOfSocks()) && test.getColor().equals(socks.getColor())
                    && test.getCottonPart() == socks.getCottonPart()){
                test.setQuantity(socks.getQuantity()+ test.getQuantity());
                operationList.add(new Operation(Type.add,socks));
                saveToFile();
                return;
            }
        }
        operationList.add(new Operation(Type.add,socks));
        socksMap.add(socks);
        saveToFile();
    }
    public Socks shipmentSocks(Socks socks){
        for (int i = 0; i < socksMap.size(); i++) {
            if (socksMap.get(i).getSizeOfSocks().equals(socks.getSizeOfSocks())
                    && socksMap.get(i).getCottonPart() == socks.getCottonPart() && socksMap.get(i).getColor().equals(socks.getColor())){

                if (socksMap.get(i).getQuantity() >= socks.getQuantity()) {
                    socksMap.get(i).setQuantity(socksMap.get(i).getQuantity()-socks.getQuantity());
                    socksMap.set(i,socksMap.get(i));
                    return socksMap.get(i);
                }
            }
        }
        operationList.add(new Operation(Type.put,socks));
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
                operationList.add(new Operation(Type.del,socks));
            }
        }
        saveToFile();
        return finded1;
    }
    private void saveToFile(){
        filesService.saveToJsonFile(socksMap,"/socks.json");
        filesService.saveToJsonFile(operationList,"/operation.json");
    }
    private void readFromFile(){
        String SocksJson = filesService.readFromFile("/socks.json");
        String OperationJson = filesService.readFromFile("/operation.json");
        try {
            socksMap = new ObjectMapper().readValue(SocksJson, new TypeReference<>() {
            });
            operationList = new ObjectMapper().readValue(OperationJson, new TypeReference<ArrayList<Operation>>() {
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
