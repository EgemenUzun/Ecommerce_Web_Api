package com.example.demo.RabbitMQ;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class QueueSender {

    private final RabbitTemplate rabbitTemplate;
    private final Exchange exchange;
    private final String routingKey;

    @Autowired
    public QueueSender(RabbitTemplate rabbitTemplate, Exchange exchange, @Value("${routing.key}") String routingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.routingKey = routingKey;
    }

    public void send(String message) {
        rabbitTemplate.convertAndSend(exchange.getName(), routingKey, message);
    }
}
