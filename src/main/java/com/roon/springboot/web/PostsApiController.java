package com.roon.springboot.web;

import com.roon.springboot.service.posts.PostsService;
import com.roon.springboot.web.dto.ExceptionDto;
import com.roon.springboot.web.dto.PostsResponseDto;
import com.roon.springboot.web.dto.PostsSaveRequestDto;
import com.roon.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
public class PostsApiController {
    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id) {
        return postsService.findById(id);
    }

    @GetMapping("/api/v1/posts")
    public List<PostsResponseDto> findByKeywordAndPaged(String keyword, int pageNumber){
        log.info(keyword+" "+pageNumber);

        return postsService.findByKeywordAndPaged(keyword,pageNumber);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);
        return id;
    }

    @ResponseStatus
    @ExceptionHandler(IllegalArgumentException.class)
    public ExceptionDto illegalArgumentHandler(IllegalArgumentException argumentException){
        return new ExceptionDto("BAD_REQUEST",argumentException.getMessage());
    }
}
