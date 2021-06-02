package com.jojoldu.book.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor // 기본 생성자 자동 추가
@Entity // 테이블과 링크될 클래스임을 나타냄
// Entity 클래스에서는 Setter를 생성하지 않음.
public class Posts extends BaseTimeEntity{
    @Id // 해당 테이블의 PK필드를 나타냄
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // PK의 생성 규칙을 나타냄
    private Long id;

    @Column(length = 500, nullable = false)
    // 테이블 칼럼을 나타내며 굳이 선언하지 않더라도 해당 클래스의 필드는 모두 컬럼이됨
    // 사용하는 이뉴는 기본값 외에 추가로 변경이 필요한 옵션이 있으면 선언함.
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder // 해당 클래스의 빌더 패턴 클래스 생성
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
