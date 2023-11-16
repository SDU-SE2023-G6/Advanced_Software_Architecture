package dk.sdu.se23g6.arch.supervisor.config;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfluxDBConfiguration {

    @Value("${influx.host:localhost}")
    private String host;

    @Bean
    public InfluxDBClient buildConnection() {
        return InfluxDBClientFactory.create(
                String.format("http://%s:8086", host),
                "random-token-value-123-456-789".toCharArray(),
                "group-6",
                "sensor-data"
        );
    }

}
