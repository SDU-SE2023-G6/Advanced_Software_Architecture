package dk.sdu.se23g6.arch.assembly.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensorData {

    private String sensorId;
    private long assemblyTimestampEpochMillis;
    private long supervisorTimestampEpochMillis;
    private String assemblyTimestampAndSupervisorTimestamp;
    private Instant influxDBTimestamp;

    public SensorData(String sensorId) {
        this.sensorId = sensorId;
        this.assemblyTimestampEpochMillis = Instant.now().toEpochMilli();
    }

}
