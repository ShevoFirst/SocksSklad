package pro.sky.sockssklad.service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FilesService {
    String dataFilePath = "src/main/resources/";
    public void saveToJsonFile(Object object,String fileName) {

        Path path = Path.of(dataFilePath, fileName);
        try {
            String json = new ObjectMapper().writeValueAsString(object);
            Files.createDirectories(path.getParent());
            Files.deleteIfExists(path);
            Files.createFile(path);
            Files.writeString(path, json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public File getDataFile(String fileName){
        return new File(dataFilePath+"/"+fileName);
    }
    public String readFromFile(String fileName) {
        try {
            return Files.readString(Path.of(dataFilePath, fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean cleanFile(){
        try {
            Files.deleteIfExists(Path.of(dataFilePath));
            Files.createFile(Path.of(dataFilePath));
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}

