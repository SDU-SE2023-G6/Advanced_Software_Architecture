package dk.sdu.se23g6.arch.supervisor.production;

import com.influxdb.client.*;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import dk.sdu.se23g6.arch.supervisor.model.SensorData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class InfluxManager {

    private static final String INFLUX_SENSOR_DATA_BUCKET_NAME = "sensor-data";
    private static final String INFLUX_ORGANIZATION_NAME = "group-6";
    private final WriteApi asyncWriteAPI;
    private final QueryApi queryApi;
    private final BucketsApi bucketsApi;
    private final OrganizationsApi organizationsApi;

    public InfluxManager(@Autowired InfluxDBClient influxDBClient) {
        this.asyncWriteAPI = influxDBClient.makeWriteApi();
        this.queryApi = influxDBClient.getQueryApi();
        this.bucketsApi = influxDBClient.getBucketsApi();
        this.organizationsApi = influxDBClient.getOrganizationsApi();
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

    public void writeSensorDataToInflux(SensorData sensorData) {
        asyncWriteAPI.writeMeasurement(
                INFLUX_SENSOR_DATA_BUCKET_NAME,
                INFLUX_ORGANIZATION_NAME,
                WritePrecision.MS,
                sensorData
        );
    }

    public List<SensorData> getSensorDataFromInflux() {
        String flux = "from(bucket:\"sensor-data\") |> range(start: 0)";
        List<SensorData> sensorDataList = new LinkedList<>();
        List<FluxTable> tables = queryApi.query(flux);
        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();
            for (FluxRecord fluxRecord : records) {
                SensorData sensorData = new SensorData();
                sensorData.setSensorId((String) fluxRecord.getValues().get("sensorId"));
                sensorData.setAssemblyTimestampEpochMillis(Long.parseLong(
                        ((String) fluxRecord.getValue()).split("\\|")[0].trim()
                ));
                sensorData.setSupervisorTimestampEpochMillis(Long.parseLong(
                        ((String) fluxRecord.getValue()).split("\\|")[1].trim()
                ));
                sensorData.setInfluxDBTimestamp(fluxRecord.getTime());
                sensorDataList.add(sensorData);
            }
        }
        return sensorDataList;
    }

}
