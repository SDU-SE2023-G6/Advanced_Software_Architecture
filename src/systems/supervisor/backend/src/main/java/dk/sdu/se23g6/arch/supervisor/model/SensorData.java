package dk.sdu.se23g6.arch.supervisor.model;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Measurement(name = "sensor-data")
public class SensorData {

    @Column(name = "sensorId", tag = true)
    private String sensorId;

    private long assemblyTimestampEpochMillis;

    private long supervisorTimestampEpochMillis;

    @Column(name = "assemblyAndSupervisorTimestamp")
    private String assemblyTimestampAndSupervisorTimestamp;

    @Column(name = "influxTimestamp", timestamp = true)
    private Instant influxDBTimestamp;

    // Because InfluxDB stores only one value per row,
    // we use a hack and use a string format to store
    // multiple values in the same column.
    public void setAssemblyAndSupervisorTimestamp() {
        assemblyTimestampAndSupervisorTimestamp = assemblyTimestampEpochMillis + " | " + supervisorTimestampEpochMillis;
    }

}
