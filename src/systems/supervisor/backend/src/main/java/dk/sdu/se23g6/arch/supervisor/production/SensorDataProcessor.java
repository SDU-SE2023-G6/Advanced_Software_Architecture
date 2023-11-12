package dk.sdu.se23g6.arch.supervisor.production;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import dk.sdu.se23g6.arch.supervisor.model.SensorData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SensorDataProcessor {

    private static final Logger log = LoggerFactory.getLogger(SensorDataProcessor.class);
    private static final String SENSOR_DATA_QUEUE = "sensorData";
    private int sensorDataCount = 0;
    private final WriteApiBlocking writeApiBlocking;

    public SensorDataProcessor(@Autowired InfluxDBClient influxDBClient) {
        this.writeApiBlocking = influxDBClient.getWriteApiBlocking();
    }

    @RabbitListener(queues = SENSOR_DATA_QUEUE)
    public void receiveSensorData(SensorData sensorData) {
        log.info(String.valueOf(sensorData));
        sensorDataCount++;
        writeApiBlocking.writeMeasurement("sensor-data", "group-6", WritePrecision.MS, sensorData);
    }

    public int getSensorDataCount() {
        return sensorDataCount;
    }

}
