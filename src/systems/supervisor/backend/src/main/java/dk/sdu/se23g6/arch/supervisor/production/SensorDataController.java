package dk.sdu.se23g6.arch.supervisor.production;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sensor-data")
public class SensorDataController {

    private final SensorDataProcessor sensorDataProcessor;

    public SensorDataController(@Autowired SensorDataProcessor sensorDataProcessor) {
        this.sensorDataProcessor = sensorDataProcessor;
    }

    @GetMapping("/amount")
    @ResponseBody
    public int getAmountOfSensorDataMessages() {
        return sensorDataProcessor.getSensorDataCount();
    }

}
