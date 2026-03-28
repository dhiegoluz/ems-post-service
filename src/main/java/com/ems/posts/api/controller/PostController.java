package com.ems.posts.api.controller;

import com.ems.posts.domain.model.PostInput;
import com.ems.posts.domain.model.PostOutput;
import com.ems.posts.domain.model.PostSummaryOutput;
import com.ems.posts.domain.model.Post;
import com.ems.posts.domain.repository.PostRepository;
import com.ems.posts.infrastructure.rabbitmq.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostRepository postRepository;

    private final RabbitTemplate rabbitTemplate;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostOutput save(@RequestBody PostInput postInput) {

        Post post = Post.builder().id(UUID.randomUUID())
                .author(postInput.getAuthor())
                .title(postInput.getTitle())
                .body(postInput.getBody()).build();

        postRepository.save(post);

        PostOutput postOutput = PostOutput.builder().postId(post.getId())
                .author(post.getAuthor())
                .title(post.getTitle())
                .body(post.getBody()).build();
        rabbitTemplate.convertAndSend(RabbitMQConfig.FANOUT_EXCHANGE, "", postOutput);

        return postOutput;
    }

    @GetMapping("{postId}")
    @ResponseStatus(HttpStatus.OK)
    public PostOutput getPostById(@PathVariable UUID postId) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return PostOutput.builder().postId(post.getId())
                .title(post.getTitle())
                .body(post.getBody())
                .author(post.getBody())
                .calculatedValue(post.getCalculatedValue())
                .wordCount(post.getWordCount())
                .build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<PostSummaryOutput> getAllPost(@PageableDefault Pageable pegeable) {

        //busca todos
        Page<Post> postPage = postRepository.findAll(pegeable);
        return postPage.map(this::getPostSummaryOutput);
    }

    private PostSummaryOutput getPostSummaryOutput(Post post) {
        return PostSummaryOutput.builder().id(post.getId())
                .build();
    }
}
