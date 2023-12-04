package dk.sdu.se23g6.arch.assembly.production;

import dk.sdu.se23g6.arch.assembly.model.SensorData;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@AllArgsConstructor
public class SensorDataSenderRunnable implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(SensorDataSenderRunnable.class);
    private AsyncSensorDataSender asyncSensorDataSender;
    private int amountOfMessages;

    private static final String SENSOR_DATA_QUEUE_1 = "sensor-data-1";
    private static final String SENSOR_DATA_QUEUE_2 = "sensor-data-2";
    private static final String SENSOR_DATA_QUEUE_3 = "sensor-data-3";

    @Override
    public void run() {
        StopWatch stopWatch = StopWatch.createStarted();
        IntStream.range(0, amountOfMessages).boxed().forEach(i -> {
            SensorData data = new SensorData("sensor-" + i);
//            if (i % 3 == 0) {
//                asyncSensorDataSender.sendSensorData(SENSOR_DATA_QUEUE_3, data);
//            } else
            if (i % 2 == 0) {
                asyncSensorDataSender.sendSensorData(SENSOR_DATA_QUEUE_2, data);
            } else {
                asyncSensorDataSender.sendSensorData(SENSOR_DATA_QUEUE_1, data);
            }
        });
        stopWatch.stop();
        long millis = stopWatch.getTime(TimeUnit.MILLISECONDS);
        log.info(amountOfMessages + " sensor data messages sent in " + millis + " ms.");
    }
}
