package dk.sdu.se23g6.arch.projecttitle.models;

public record AssemblySystemResponseDTO(
        String stepId,
        String orderId,
        String robotId,
        long timestamp,
        String status
) {
}
