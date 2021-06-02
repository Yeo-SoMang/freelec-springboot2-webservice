package com.jojoldu.book.springboot.domain.posts;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass // 이 클래스를 상속할경우 createdDate와 modifiedDate로 칼럼으로 인식
@EntityListeners(AuditingEntityListener.class)// Auditing기능 포함
public class BaseTimeEntity {

    @CreatedDate// entity가 생성되어 저정될떄 시간 자동저장
    private LocalDateTime createdDate;

    @LastModifiedDate // 값을 변경할떄 시간 자동저장
    private LocalDateTime modifiedDate;
}
