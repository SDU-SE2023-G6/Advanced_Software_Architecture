package dk.sdu.se23g6.arch.projecttitle.example.models.AssemblySystem;

import lombok.*;

@Builder(setterPrefix = "with")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssemblyOrder {

    String stepId;
    String orderId;
    String robotId;
    long timestamp;
    String status;

}