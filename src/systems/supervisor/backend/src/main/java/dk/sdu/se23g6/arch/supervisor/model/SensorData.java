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

    @Column(tag = true)
    private String sensorId;

    @Column
    private Double value;

    @Column
    private long assemblyTimestamp;

    @Column
    private long supervisorTimestamp;

    @Column(timestamp = true)
    private Instant influxDBTimestamp;

}
