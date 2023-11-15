package dk.sdu.se23g6.arch.assembly.production;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sensor-data")
public class SensorDataController {

    private final SensorDataSender sensorDataSender;

    public SensorDataController(@Autowired SensorDataSender dataSender) {
        sensorDataSender = dataSender;
    }

    @PostMapping("/start")
    @ResponseBody
    public String startSendingSensorData(@RequestParam Integer messagesPerSecond) {
        sensorDataSender.startSendingSensorData(messagesPerSecond);
        return "200 OK - Sending started.";
    }

    @GetMapping("/stop")
    @ResponseBody
    public String stopSendingSensorData() {
        return "200 OK - Sending stopped.\n" + sensorDataSender.stopSendingSensorData();
    }

}
