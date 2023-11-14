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
    private ScheduledExecutorService executorService;
    private final AsyncSensorDataSender asyncSensorDataSender;
    private int amountOfMessages;
    private Instant experimentStart;

    public SensorDataSender(
            @Autowired AsyncSensorDataSender asyncSender
    ) {
        this.asyncSensorDataSender = asyncSender;
        this.executorService = Executors.newScheduledThreadPool(1);
        log.info(this.getClass().getSimpleName() + " initialized.");
    }

    public void startSendingSensorData(int amountOfMessages) {
        this.amountOfMessages = amountOfMessages;
        SensorDataSenderRunnable runnable = new SensorDataSenderRunnable(asyncSensorDataSender, amountOfMessages);
        experimentStart = Instant.now();
        executorService.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.SECONDS);
    }

    public String stopSendingSensorData() {
        executorService.shutdownNow();
        while (!executorService.isShutdown()) {
        }
        Instant experimentEnd = Instant.now();
        log.info("Old ScheduledExecutorService successfully shutdown.");
        this.executorService = Executors.newScheduledThreadPool(1);
        long millis = Duration.between(experimentStart, experimentEnd).toMillis();
        double seconds = millis / 1000d;
        return "Duration: " + DurationFormatUtils.formatDurationHMS(millis)
                + "\nExpected Entries: " + this.amountOfMessages * (seconds + 1) + "\n";
    }

}
