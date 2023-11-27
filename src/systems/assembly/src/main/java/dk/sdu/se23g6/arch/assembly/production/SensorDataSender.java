package dk.sdu.se23g6.arch.assembly.production;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class SensorDataSender {

    private static final Logger log = LoggerFactory.getLogger(SensorDataSender.class);
    private static final long SEND_MESSAGE_INITIAL_DELAY = 1;
    private static final long SEND_MESSAGE_EVERY_X_SECOND = 1;

    private ScheduledExecutorService executorService;
    private final AsyncSensorDataSender asyncSensorDataSender;
    private int messagesPerSecond;
    private Instant experimentStart;
    private Instant experimentEnd;

    public SensorDataSender(
            @Autowired AsyncSensorDataSender asyncSender
    ) {
        this.asyncSensorDataSender = asyncSender;
        this.executorService = Executors.newScheduledThreadPool(1);
        log.info(this.getClass().getSimpleName() + " initialized.");
    }

    public void startSendingSensorData(int messagesPerSecond) {
        this.messagesPerSecond = messagesPerSecond;
        SensorDataSenderRunnable runnable = new SensorDataSenderRunnable(asyncSensorDataSender, messagesPerSecond);
        experimentStart = Instant.now();
        executorService.scheduleAtFixedRate(
                runnable,
                SEND_MESSAGE_INITIAL_DELAY,
                SEND_MESSAGE_EVERY_X_SECOND,
                TimeUnit.SECONDS
        );
    }

    public String stopSendingSensorData() {
        executorService.shutdown();
        while (!executorService.isShutdown()) {
        }
        experimentEnd = Instant.now();
        log.info("Old ScheduledExecutorService successfully shutdown.");
        this.executorService = Executors.newScheduledThreadPool(1);
        log.info("New ScheduledExecutorService successfully created");
        return calculateExpectedMessages();
    }

    private String calculateExpectedMessages() {
        long millis = Duration.between(experimentStart, experimentEnd).toMillis();
        long seconds = millis / 1000;
        return "Duration: " + DurationFormatUtils.formatDurationHMS(millis)
                + "\nExpected Entries: " + this.messagesPerSecond * (seconds) + "\n";
    }

}
