package com.example.demo.RabbitMQ;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SenderConfig {

    @Value("${exchange.name}")
    private String exchangeName;

    @Value("${routing.key}")
    private String routingKey;
    @Bean
    public FanoutExchange exchange() {
        return new FanoutExchange(exchangeName);
    }

    @Bean
    public Binding binding(FanoutExchange exchange) {
        return BindingBuilder.bind(exchange).to(exchange);
    }
}
