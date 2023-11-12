package dk.sdu.se23g6.arch.assembly.production;

import dk.sdu.se23g6.arch.assembly.model.SensorData;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncSensorDataSender {

    private static final String SENSOR_DATA_QUEUE = "sensorData";

    private final RabbitTemplate rabbitTemplate;

    @Bean
    public Queue sensorDataQueue() {
        return new Queue(SENSOR_DATA_QUEUE, true);
    }

    public AsyncSensorDataSender(@Autowired RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Async
    public void sendSensorData(SensorData sensorData) {
        rabbitTemplate.convertAndSend(SENSOR_DATA_QUEUE, sensorData);
    }

}
