package com.jojoldu.book.springboot.domain.posts;


import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @After // 단위메소드가 끝날떄마다 수행되는 메소드를 지정.
    // 지금은 끝날떄마다 데이터를 제거하는 과정
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void PostSave_Load(){
        // given
        String title = "titleTest";
        String content = "contentTest";

        // 테이블posts에 insert/update쿼리를 실행함. id값이 있다면 update 없다면 delete
        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("yeo@gamil.com")
                .build()
        );

        // when
        // 테이블에 있는 모든 데이터를 조회
        List<Posts> postsList = postsRepository.findAll();


        // then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntity_create() {
        //given
        LocalDateTime now = LocalDateTime.of(2019, 6, 4, 0, 0, 0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build()
        );

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);

        System.out.println(">>>>>> createDt" + posts.getCreatedDate() + ", modifiedDate=" + posts.getModifiedDate());

        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }
}