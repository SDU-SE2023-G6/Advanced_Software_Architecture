package dk.sdu.se23g6.arch.supervisor.config;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfluxDBConfiguration {

    @Bean
    public InfluxDBClient buildConnection() {
        return InfluxDBClientFactory.create(
                "http://localhost:8086",
                "random-token-value-123-456-789".toCharArray(),
                "group-6",
                "sensor-data"
        );
    }

}
