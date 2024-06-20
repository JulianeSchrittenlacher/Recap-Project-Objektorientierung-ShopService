import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public class UtilService {
    public String generateRandomId() {
        return UUID.randomUUID().toString();

    }
    public Instant getCurrentTime() {
        return Instant.now();
    }
}
