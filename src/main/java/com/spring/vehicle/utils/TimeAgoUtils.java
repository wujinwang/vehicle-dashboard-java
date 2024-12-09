package com.spring.vehicle.utils;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

public class TimeAgoUtils {

    /**
     * Converts a given date to a human-readable "time ago" format.
     *
     * @param createdDate The date to be converted.
     * @return A string representing how long ago the date was, e.g., "1 minute ago", "2 hours ago".
     */
    public static String timeAgo(Instant createdDate) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime createdDateTime = toLocalDateTime(createdDate);

        Duration duration = Duration.between(createdDateTime, now);

        long seconds = duration.getSeconds();
        if (seconds < 60) {
            return seconds + " second" + (seconds == 1 ? "" : "s") + " ago";
        }

        long minutes = duration.toMinutes();
        if (minutes < 60) {
            return minutes + " minute" + (minutes == 1 ? "" : "s") + " ago";
        }

        long hours = duration.toHours();
        if (hours < 24) {
            return hours + " hour" + (hours == 1 ? "" : "s") + " ago";
        }

        long days = duration.toDays();
        if (days < 30) {
            return days + " day" + (days == 1 ? "" : "s") + " ago";
        }

        return "more than a month ago"; // For more than a month ago, you can extend this logic further
    }

    /**
     * Converts a Date to LocalDateTime for comparison.
     *
     * @param date The date to convert.
     * @return A LocalDateTime object.
     */
    private static LocalDateTime toLocalDateTime(Instant date) {
        return LocalDateTime.ofInstant(date, java.time.ZoneId.systemDefault());
    }

    public static void main(String[] args) {
        // Example usage:
        Date createdDate = new Date(System.currentTimeMillis() - 5 * 60 * 1000); // 5 minutes ago
        System.out.println(timeAgo(createdDate.toInstant())); // Output: "5 minutes ago"
    }
}