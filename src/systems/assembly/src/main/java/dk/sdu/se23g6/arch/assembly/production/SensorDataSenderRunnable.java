package dk.sdu.se23g6.arch.assembly.production;

import dk.sdu.se23g6.arch.assembly.model.SensorData;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@AllArgsConstructor
public class SensorDataSenderRunnable implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(SensorDataSenderRunnable.class);
    private AsyncSensorDataSender asyncSensorDataSender;
    private int amountOfMessages;

    @Override
    public void run() {
        StopWatch stopWatch = StopWatch.createStarted();
        IntStream.range(0, amountOfMessages).boxed().forEach(i -> {
            SensorData data = new SensorData("sensor-" + i, Math.random());
            asyncSensorDataSender.sendSensorData(data);
        });
        stopWatch.stop();
        long millis = stopWatch.getTime(TimeUnit.MILLISECONDS);
        log.info(amountOfMessages + " sensor data messages sent in " + millis + " ms.");
    }
}
