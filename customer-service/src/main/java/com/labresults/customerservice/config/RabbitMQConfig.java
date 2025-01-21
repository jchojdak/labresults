package com.labresults.customerservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue customerCreateQueue() {
        return new Queue("customer.create.queue", true);
    }

    @Bean
    public Queue customerDeleteQueue() {
        return new Queue("customer.delete.queue", true);
    }

    @Bean
    public TopicExchange customerExchange() {
        return new TopicExchange("customer.exchange");
    }

    @Bean
    public Binding customerCreateBinding(Queue customerCreateQueue, TopicExchange customerExchange) {
        return BindingBuilder.bind(customerCreateQueue).to(customerExchange).with("customer.create");
    }

    @Bean
    public Binding customerDeleteBinding(Queue customerDeleteQueue, TopicExchange customerExchange) {
        return BindingBuilder.bind(customerDeleteQueue).to(customerExchange).with("customer.delete");
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        return rabbitTemplate;
    }
}
