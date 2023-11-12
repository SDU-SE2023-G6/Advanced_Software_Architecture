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
    private Double value;
    private Instant timestamp;

    public SensorData(String sensorId, Double value) {
        this.sensorId = sensorId;
        this.value = value;
        this.timestamp = Instant.now();
    }

}
