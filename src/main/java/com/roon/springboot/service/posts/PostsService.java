package com.roon.springboot.service.posts;

import com.roon.springboot.domain.posts.Posts;
import com.roon.springboot.domain.posts.PostsRepository;
import com.roon.springboot.web.dto.PostsResponseDto;
import com.roon.springboot.web.dto.PostsSaveRequestDto;
import com.roon.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;
    private static final String EXCEPTION_NO_POST = "글이 존재하지 않습니다.";
    private static final int PAGE_COUNT = 5;

    @Transactional
    public long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(EXCEPTION_NO_POST + " 글 id = " + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    @Transactional(readOnly = true)
    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(EXCEPTION_NO_POST + " 글 id = " + id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostsResponseDto> findByKeywordAndPaged(String keyword,int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber,PAGE_COUNT);
        Page<Posts> posts = postsRepository.findByKeywordAndPaged(keyword,pageable);

        List<PostsResponseDto> result = new ArrayList<>();
        for(Posts post : posts){
            result.add(new PostsResponseDto(post));
        }

        return result;
    }


    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(EXCEPTION_NO_POST + " 글 id = " + id));

        postsRepository.delete(posts);
    }
}
