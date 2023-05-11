package ustis.fitnesscentre.util;

import java.time.Duration;

public class DurationUtil {
    public static Duration stringToDuration(String source) {
        String[] dateAndTimeParts = source.split(" ");
        if (dateAndTimeParts.length == 1) {
            String[] timeParts = dateAndTimeParts[0].split(":");
            return Duration.ofDays(0)
                    .plusDays(Long.parseLong(timeParts[0]))
                    .plusHours(Long.parseLong(timeParts[1]))
                    .plusSeconds(Long.parseLong(timeParts[2].substring(0, 2)));
        }
        String[] timeParts = dateAndTimeParts[2].split(":");
        return Duration.ofDays(Long.parseLong(dateAndTimeParts[0]))
                .plusDays(Long.parseLong(timeParts[0]))
                .plusHours(Long.parseLong(timeParts[1]))
                .plusSeconds(Long.parseLong(timeParts[2].substring(0, 2)));
    }
}
