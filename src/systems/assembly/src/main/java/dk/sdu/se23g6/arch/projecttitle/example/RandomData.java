package dk.sdu.se23g6.arch.projecttitle.example;

import lombok.*;

@Builder(setterPrefix = "with")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RandomData {

    String stepId;
    String orderId;
    String robotId;
    long timestamp;
    String status;

}
