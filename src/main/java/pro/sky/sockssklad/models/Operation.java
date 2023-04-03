package pro.sky.sockssklad.models;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class Operation {
    private Type type;
    private String time;
    private Socks socks;

    public Operation(Type type, Socks socks) {
        this.type = type;
        this.time = LocalDateTime.now().toString();
        this.socks = socks;
    }
    public Operation() {
    }
}

