package dk.sdu.se23g6.arch.assembly.production;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class SensorDataSender {

    private static final Logger log = LoggerFactory.getLogger(SensorDataSender.class);
    private ScheduledExecutorService executorService;
    private final AsyncSensorDataSender asyncSensorDataSender;

    public SensorDataSender(
            @Autowired AsyncSensorDataSender asyncSender
    ) {
        this.asyncSensorDataSender = asyncSender;
        this.executorService = Executors.newScheduledThreadPool(1);
        log.info(this.getClass().getSimpleName() + " initialized.");
    }

    public void startSendingSensorData(int amountOfMessages) {
        SensorDataSenderRunnable runnable = new SensorDataSenderRunnable(asyncSensorDataSender, amountOfMessages);
        executorService.scheduleAtFixedRate(runnable, 0, 5, TimeUnit.SECONDS);
    }

    public void stopSendingSensorData() {
        executorService.shutdownNow();
        while (!executorService.isShutdown()) {
        }
        log.info("Old ScheduledExecutorService successfully shutdown.");
        this.executorService = Executors.newScheduledThreadPool(1);
        log.info("New ScheduledExecutorService created. Idle...");
    }

//    public void sendMessagesPerSecond(int amountOfMessages) {
//        int messagesPerSecond = amountOfMessages;
//        int totalMessages = 100000; // Adjust the total number of messages as needed
//
//        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
//
//        // Calculate the delay between messages based on the desired messages per second
//        long delay = Math.round(1000.0 / messagesPerSecond);
//
//        Runnable sendMessageTask = () -> {
//            asyncSender.sendSensorData();
//
//            // Reduce the totalMessages count
//            totalMessages--;
//
//            // If all messages are sent, shutdown the scheduler
//            if (totalMessages <= 0) {
//                scheduler.shutdown();
//            }
//        };
//
//        // Schedule the sendMessageTask with a fixed delay
//        scheduler.scheduleAtFixedRate(sendMessageTask, 0, delay, TimeUnit.MILLISECONDS);
//    }

}
