package dk.sdu.se23g6.arch.projecttitle.example.orders.models;

import dk.sdu.se23g6.arch.projecttitle.example.models.Order;

public record OrderError(Order order, String message) {
}
