package models;

import org.mongodb.morphia.annotations.Entity;
import services.DBConnection;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Entity
public class Log extends BaseModel {
    private String username;
    private String action;
    private Date timestamp;

    public String getUsername() {
        return username;
    }

    public String getAction() {
        return action;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public static List<Log> getLogs(){
        return DBConnection.getDatastore()
                .createQuery(Log.class)
                .asList();
    }

    public static void LogAction(String username, String action){
        Log log = new Log();
        log.username = username;
        log.action = action;
        log.timestamp = Date.from(Instant.now());
        log.save();
    }
}
