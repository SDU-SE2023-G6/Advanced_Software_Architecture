package dk.sdu.se23g6.arch.supervisor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application entrypoint.
 */
@SpringBootApplication
public class MainApplication {

//  private final OrdersRepository ordersRepository;
//  private final AssemblyOrderRepository assemblyOrderRepository;

    public MainApplication() {
//    this.ordersRepository = ordersRepository;
//    this.assemblyOrderRepository = assemblyOrderRepository;
    }

//  public static void main(String[] args) {
//    ConfigurableApplicationContext app = null;
//    try {
//      app = SpringApplication.run(MainApplication.class, args);
//    } catch (Exception e) {
//      System.out.println(e);
//      if (app != null) {
//        app.close();
//      }
//    }
//  }

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

//  @RabbitListener(queues = "assemblyOrders")
//  public void listenToAssemblyResponse(@Payload OrderStepDTO in) {
//    System.out.println("Received order with id "
//            + in.getOrderId() + " and " + in.getStepId() + " and status " + in.getStatus());
////    Optional<Order> foundOrder = ordersRepository.findById(in.getOrderId());
//    if (foundOrder.isPresent()) {
//      Order order = foundOrder.get();
//      Optional<OrderStep> step = order.getSteps().stream()
//              .filter(o -> o.getStepId().equals(in.getStepId())).findFirst();
//      if (step.isPresent()) {
//        step.get().setOrderStatus(in.getStatus());
////        ordersRepository.save(order);
//      }
//    }
//  }
//  @Bean
//  public CommandLineRunner initDatabase(RabbitTemplate template, MessageConverter converter) {
//    return args -> {
//      try {
//        ordersRepository.deleteAll();
//        assemblyOrderRepository.deleteAll();
//      } catch (Exception e)  {
//        System.out.println(e);
//
//      }
//
//      List<String> orderSteps = new ArrayList<>();
//      orderSteps.add("./attach-wheel.py");
//      orderSteps.add("./attach-pedals.py");
//      orderSteps.add("./attach-chain.py");
//
//      Order initOrder = new Order("10", orderSteps);
//      Order order = ordersRepository.save(initOrder);
//      template.send("orders", converter.toMessage(initOrder, null));
//    };
//  }
}
