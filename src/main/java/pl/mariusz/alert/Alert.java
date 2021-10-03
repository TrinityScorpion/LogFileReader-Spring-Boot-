package pl.mariusz.alert;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@RequiredArgsConstructor
public class Alert {

    @Id
    private String id;
    private long timestamp;
    private String type;
    private String host;
    private boolean alert;


    public Alert(String id, long timestamp, String type, String host, boolean alert) {
        this.id = id;
        this.timestamp = timestamp;
        this.type = type;
        this.host = host;
        this.alert = alert;
    }
}
