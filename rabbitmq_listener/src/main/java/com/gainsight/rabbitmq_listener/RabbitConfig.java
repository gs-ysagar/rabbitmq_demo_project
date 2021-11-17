package com.gainsight.rabbitmq_listener;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    private static final String TEST_QUEUE = "yashQueue";
    private static final String TEST_EXCHANGE = "yashExchange";

    @Bean
    Queue myTestQueue() {
        return new Queue(TEST_QUEUE, false);
    }

    @Bean
    Exchange myTestExchange() {
        return new TopicExchange(TEST_EXCHANGE);
    }

    @Bean
    Binding queueBinding() {
        return new Binding(TEST_QUEUE, Binding.DestinationType.QUEUE, TEST_EXCHANGE, "yashRoutingKey", null);
    }

    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    }

    @Bean
    MessageListenerContainer messageListenerContainer() {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory());
        simpleMessageListenerContainer.setQueues(myTestQueue());
        simpleMessageListenerContainer.setMessageListener(new RabbitListener());
        return simpleMessageListenerContainer;
    }
}
