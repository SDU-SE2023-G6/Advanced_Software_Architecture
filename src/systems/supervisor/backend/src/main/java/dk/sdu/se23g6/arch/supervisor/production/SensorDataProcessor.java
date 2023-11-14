package dk.sdu.se23g6.arch.supervisor.production;

import com.influxdb.client.BucketsApi;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.OrganizationsApi;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import dk.sdu.se23g6.arch.supervisor.model.SensorData;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class SensorDataProcessor {

    private static final String SENSOR_DATA_QUEUE_NAME = "sensorData";
    private static final String INFLUX_SENSOR_DATA_BUCKET_NAME = "sensor-data";
    private static final String INFLUX_ORGANIZATION_NAME = "group-6";
    private final WriteApi asyncWriteAPI;
    private final BucketsApi bucketsApi;
    private final OrganizationsApi organizationsApi;

    public SensorDataProcessor(@Autowired InfluxDBClient influxDBClient) {
        this.asyncWriteAPI = influxDBClient.makeWriteApi();
        this.bucketsApi = influxDBClient.getBucketsApi();
        this.organizationsApi = influxDBClient.getOrganizationsApi();
    }

    // Receives the incoming messages from AMQP
    @RabbitListener(queues = SENSOR_DATA_QUEUE_NAME)
    public void receiveSensorData(SensorData sensorData) {
        sensorData.setSupervisorTimestamp(Instant.now().toEpochMilli());
        asyncWriteAPI.writeMeasurement(
                INFLUX_SENSOR_DATA_BUCKET_NAME,
                INFLUX_ORGANIZATION_NAME,
                WritePrecision.MS,
                sensorData
        );
    }

    // Use to empty the bucket 'sensor-data' in InfluxDB
    public void resetInfluxBucket() {
        bucketsApi.findBuckets().forEach(bucket -> {
            if (bucket.getName().equals(INFLUX_SENSOR_DATA_BUCKET_NAME)) {
                bucketsApi.deleteBucket(bucket.getId());
            }
        });
        organizationsApi.findOrganizations().forEach(org -> {
            if (org.getName().equals(INFLUX_ORGANIZATION_NAME)) {
                bucketsApi.createBucket(INFLUX_SENSOR_DATA_BUCKET_NAME, org);
            }
        });
    }

}
