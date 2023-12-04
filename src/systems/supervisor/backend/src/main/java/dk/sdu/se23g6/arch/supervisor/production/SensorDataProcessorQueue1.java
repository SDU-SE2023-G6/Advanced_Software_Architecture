package dk.sdu.se23g6.arch.supervisor.production;

import dk.sdu.se23g6.arch.supervisor.model.SensorData;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Profile("queue-1")
public class SensorDataProcessorQueue1 {

    private static final String SENSOR_DATA_QUEUE_NAME_1 = "sensor-data-1";
    private final InfluxManager influxManager;

    public SensorDataProcessorQueue1(
            @Autowired InfluxManager influxManager
    ) {
        this.influxManager = influxManager;
    }

    // Receives the incoming messages from AMQP (Queue 1)
    @RabbitListener(queues = SENSOR_DATA_QUEUE_NAME_1)
    public void receiveSensorDataQueue1(SensorData sensorData) {
        // Filter out the INIT message that is used to trigger the
        // automatic creation of the AMQP queue.
        if (sensorData.getSensorId().contains("INIT")) {
            return;
        }
        sensorData.setSupervisorTimestampEpochMillis(Instant.now().toEpochMilli());
        sensorData.setAssemblyAndSupervisorTimestamp();
        influxManager.writeSensorDataToInflux(sensorData);
    }

}
