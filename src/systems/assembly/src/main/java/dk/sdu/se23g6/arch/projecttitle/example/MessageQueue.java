package dk.sdu.se23g6.arch.projecttitle.example;


import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;

public class MessageQueue {

    private ConnectionFactory connectionFactory;

    @Bean
    public Queue myQueue() {
        return new Queue("myQueue", false);
    }

//    @Bean
//    public ConnectionFactory connectionFactory(ConnectionFactory connectionFactory) {
//        return this.connectionFactory = connectionFactory;
//    }
//
//
//    public void sendMessage() {
//        template.convertAndSend("myQueue", "Hello, world!");
//    }

    @RabbitListener(queues = "myQueue")
    public void listen(String in) {
        System.out.println("Message read from myQueue : " + in);
    }

}
