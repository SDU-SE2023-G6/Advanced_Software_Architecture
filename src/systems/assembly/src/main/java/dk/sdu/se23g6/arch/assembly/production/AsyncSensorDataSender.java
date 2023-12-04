package dk.sdu.se23g6.arch.assembly.production;

import dk.sdu.se23g6.arch.assembly.model.SensorData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncSensorDataSender {

    private static final String SENSOR_DATA_QUEUE_1 = "sensor-data-1";
    private static final String SENSOR_DATA_QUEUE_2 = "sensor-data-2";
    private static final String SENSOR_DATA_QUEUE_3 = "sensor-data-3";
    private final RabbitTemplate rabbitTemplate;


    public AsyncSensorDataSender(@Autowired RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        // We need to send some data to the AMQP queue in order to let Spring Boot create it automatically
        // This SensorData data point won't be considered in the experiment.
        this.rabbitTemplate.convertAndSend(SENSOR_DATA_QUEUE_1, new SensorData("INIT"));
        this.rabbitTemplate.convertAndSend(SENSOR_DATA_QUEUE_2, new SensorData("INIT"));
        this.rabbitTemplate.convertAndSend(SENSOR_DATA_QUEUE_3, new SensorData("INIT"));
    }

    @Async
    public void sendSensorData(String queueName, SensorData sensorData) {
        rabbitTemplate.convertAndSend(queueName, sensorData);
    }

}
