package com.ems.posts.infrastructure.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final
        String QUEUE_PROCESS_TEMPERATURE = "text-processor-service.post-processing.v1.q";

    public static final
    String QUEUE_PROCESS_CONSUMING_TEMPERATURE = "post-service.post-processing-result.v1.q";

    public static final
    String FANOUT_EXCHANGE = "text-processor-service.post-processing.v1.e";
    @Bean
    public MessageConverter jacksonJsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public Queue queueProcessTemperature() {
        return QueueBuilder.durable(QUEUE_PROCESS_TEMPERATURE).build();
    }

    @Bean
    public FanoutExchange exchange() {
        return ExchangeBuilder.fanoutExchange("text-processor-service.post-processing.v1.e").build();
    }

    @Bean
    public Binding bindingProcessTemperature() {
        return BindingBuilder.bind(queueProcessTemperature()).to(exchange());
    }
}