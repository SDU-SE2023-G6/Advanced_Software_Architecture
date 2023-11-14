package dk.sdu.se23g6.arch.supervisor.production;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.Bucket;
import com.influxdb.client.domain.Organization;
import com.influxdb.client.domain.WritePrecision;
import dk.sdu.se23g6.arch.supervisor.model.SensorData;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class SensorDataProcessor {

    private static final String SENSOR_DATA_QUEUE = "sensorData";
    private static final String INFLUX_SENSOR_DATA_BUCKET = "sensor-data";
    private int sensorDataCount = 0;
    private final WriteApi asyncWriteAPI;

    public SensorDataProcessor(@Autowired InfluxDBClient influxDBClient) {
        List<Bucket> buckets = influxDBClient.getBucketsApi().findBuckets();
        buckets.forEach(bucket -> {
            if (bucket.getName().equals(INFLUX_SENSOR_DATA_BUCKET)) {
                influxDBClient.getBucketsApi().deleteBucket(bucket.getId());
            }
        });
        List<Organization> organizations = influxDBClient.getOrganizationsApi().findOrganizations();
        organizations.forEach(org -> {
            if (org.getName().equals("group-6")) {
                influxDBClient.getBucketsApi().createBucket(INFLUX_SENSOR_DATA_BUCKET, org);
            }
        });
        this.asyncWriteAPI = influxDBClient.makeWriteApi();
    }

    @RabbitListener(queues = SENSOR_DATA_QUEUE)
    public void receiveSensorData(SensorData sensorData) {
        sensorDataCount++;
        sensorData.setSupervisorTimestamp(Instant.now().toEpochMilli());
        asyncWriteAPI.writeMeasurement(INFLUX_SENSOR_DATA_BUCKET, "group-6", WritePrecision.MS, sensorData);
    }

    public int getSensorDataCount() {
        return sensorDataCount;
    }

}
