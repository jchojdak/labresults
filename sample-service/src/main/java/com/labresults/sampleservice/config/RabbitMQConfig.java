package com.labresults.sampleservice.config;

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
    public Queue sampleCreateQueue() {
        return new Queue("sample.create.queue", true);
    }

    @Bean
    public Queue sampleDeleteQueue() {
        return new Queue("sample.delete.queue", true);
    }

    @Bean
    public TopicExchange sampleExchange() {
        return new TopicExchange("sample.exchange");
    }

    @Bean
    public Binding sampleCreateBinding(Queue sampleCreateQueue, TopicExchange sampleExchange) {
        return BindingBuilder.bind(sampleCreateQueue).to(sampleExchange).with("sample.create");
    }

    @Bean
    public Binding sampleDeleteBinding(Queue sampleDeleteQueue, TopicExchange sampleExchange) {
        return BindingBuilder.bind(sampleDeleteQueue).to(sampleExchange).with("sample.delete");
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
