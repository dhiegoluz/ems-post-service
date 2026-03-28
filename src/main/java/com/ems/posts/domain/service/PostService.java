package com.ems.posts.domain.service;

import com.ems.posts.api.controller.exceptions.PostNotFoundException;
import com.ems.posts.domain.model.Post;
import com.ems.posts.domain.model.PostOutput;
import com.ems.posts.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void atualizarPost(PostOutput postOutput) {

        Post post = postRepository.findById(postOutput.getPostId()).orElse(null);

        if(post != null) {
            post.setCalculatedValue(postOutput.getCalculatedValue());
            post.setWordCount(postOutput.getWordCount());
            postRepository.save(post);
        }
    }
}
