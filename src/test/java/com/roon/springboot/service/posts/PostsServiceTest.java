package com.roon.springboot.service.posts;

import com.roon.springboot.web.dto.PostsResponseDto;
import com.roon.springboot.web.dto.PostsSaveRequestDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsServiceTest {
    @Autowired
    private PostsService postsService;

    @Test
    public void notNullTest(){
        assertThat(postsService).isNotNull();
    }

    @Test
    public void 페이징키워드검색_테스트(){
        postsService.save(PostsSaveRequestDto.builder()
                .title("title1")
                .content("content1")
                .author("author1")
                .build()
        );

        postsService.save(PostsSaveRequestDto.builder()
                .title("title2")
                .content("content1")
                .author("author1")
                .build()
        );

        List<PostsResponseDto> result = postsService.findByKeywordAndPaged("2",0);
        assertThat(result.size()).isGreaterThan(0);

        result.forEach(System.out::println);
    }
}