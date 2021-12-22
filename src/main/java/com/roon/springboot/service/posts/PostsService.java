package com.roon.springboot.service.posts;

import com.roon.springboot.domain.posts.Posts;
import com.roon.springboot.domain.posts.PostsRepository;
import com.roon.springboot.web.dto.PostsListResponseDto;
import com.roon.springboot.web.dto.PostsResponseDto;
import com.roon.springboot.web.dto.PostsSaveRequestDto;
import com.roon.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;
    private static final int TIME_OUT_SECOND = 1;
    private static final String EXCEPTION_NO_POST = "글이 존재하지 않습니다.";

    @Transactional(timeout = TIME_OUT_SECOND)
    public long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(EXCEPTION_NO_POST+" 글 id = " + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(EXCEPTION_NO_POST+" 글 id = " + id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(EXCEPTION_NO_POST+" 글 id = " + id));

        postsRepository.delete(posts);
    }
}
