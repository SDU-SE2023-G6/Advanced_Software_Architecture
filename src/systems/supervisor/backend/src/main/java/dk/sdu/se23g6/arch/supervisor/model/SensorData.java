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
@Measurement(name = "sensorData")
public class SensorData {

    @Column(tag = true)
    private String sensorId;

    @Column
    private Double value;

    @Column(timestamp = true)
    private Instant timestamp;

}
