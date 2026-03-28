package com.ems.posts.infrastructure.rabbitmq;

import com.ems.posts.domain.model.PostOutput;
import com.ems.posts.domain.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMQListener {

    private final PostService postService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_PROCESS_CONSUMING_TEMPERATURE, concurrency = "2-3")
    public void handle(@Payload PostOutput postOutput,
                       @Headers Map<String, Object> headers) {

        postService.atualizarPost(postOutput);
    }
}