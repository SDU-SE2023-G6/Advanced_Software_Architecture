package dk.sdu.se23g6.arch.supervisor.production;

import dk.sdu.se23g6.arch.supervisor.model.SensorData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/sensor-data")
public class SensorDataController {

    private final SensorDataProcessor sensorDataProcessor;

    public SensorDataController(@Autowired SensorDataProcessor sensorDataProcessor) {
        this.sensorDataProcessor = sensorDataProcessor;
    }

    @GetMapping("/reset-bucket")
    @ResponseBody
    public String resetInfluxBucket() {
        sensorDataProcessor.resetInfluxBucket();
        return "200 OK - Bucket reset (deleted and recreated).";
    }

    @GetMapping("/csv")
    @ResponseBody
    public String getInfluxCSV() {
        List<SensorData> csvEntries = sensorDataProcessor.getSensorDataFromInflux();
        return parseCSV(csvEntries);
    }

    @GetMapping("/message-count")
    @ResponseBody
    public Integer getMessageCount() {
        List<SensorData> entries = sensorDataProcessor.getSensorDataFromInflux();
        return entries.size();
    }

    private String parseCSV(List<SensorData> csvEntries) {
        StringBuilder sb = new StringBuilder();
        sb.append("sensorId,assemblyEpochMillis,supervisorEpochMillis,influxEpochMillis\n");
        csvEntries.forEach(entry -> {
            sb.append(entry.getSensorId());
            sb.append(",");
            sb.append(entry.getAssemblyTimestampEpochMillis());
            sb.append(",");
            sb.append(entry.getSupervisorTimestampEpochMillis());
            sb.append(",");
            sb.append(entry.getInfluxDBTimestamp().toEpochMilli());
            sb.append("\n");
        });
        return sb.toString();
    }

}
