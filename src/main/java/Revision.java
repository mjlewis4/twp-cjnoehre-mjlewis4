import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Revision {

    public String utcTime;
    public String timestamp;

    public Revision(String user) {
        timestamp = changeTimeZone(utcTime);
        this.timestamp = timestamp;
    }

    private String changeTimeZone(String utcTime){
        Instant timestamp = Instant.parse(utcTime);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd MM:mm:ss");
        ZonedDateTime estZonedDate = timestamp.atZone(ZoneId.of("America/New_York"));
        return estZonedDate.format(formatter);
    }

    public String getUtcTime() { return this.utcTime; }
    public String getTimestamp() { return this.timestamp; }
}
