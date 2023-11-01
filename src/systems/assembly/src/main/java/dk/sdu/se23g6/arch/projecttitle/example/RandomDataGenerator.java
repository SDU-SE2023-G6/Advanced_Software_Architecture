package dk.sdu.se23g6.arch.projecttitle.example;

import java.util.UUID;
import java.time.Instant;
import java.security.SecureRandom;
import java.util.Random;

public class RandomDataGenerator {
    private static SecureRandom secureRandom = new SecureRandom();
    private static Random random = new Random();

    // Generate a random UUID as an Id
    public static String generateRandomId() {
        return UUID.randomUUID().toString();
    }

    // Generate a random timestamp
    public static long generateRandomTimestamp() {
        return Instant.now().toEpochMilli();
    }

    // Generate a random string from a predefined list
    public static String generateRandomStatus() {
        String[] statusList = {"running", "error", "Idle", "waiting"};
        int randomIndex = random.nextInt(statusList.length);
        return statusList[randomIndex];
    }

    public static RandomData generateRandomData() {
        return RandomData.builder()
                .withStepId(generateRandomId())
                .withOrderId(generateRandomId())
                .withRobotId(generateRandomId())
                .withTimestamp(generateRandomTimestamp())
                .withStatus(generateRandomStatus())
                .build();
    }

}
