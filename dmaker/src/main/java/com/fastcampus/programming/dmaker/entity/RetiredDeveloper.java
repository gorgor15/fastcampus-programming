package com.fastcampus.programming.dmaker.entity;

import com.fastcampus.programming.dmaker.type.DeveloperLevel;
import com.fastcampus.programming.dmaker.type.DeveloperSkillType;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

//퇴직한 개발자 모아두는 곳
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class RetiredDeveloper {


        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        protected Long id;

        private String memberId;
        private String name;

        @CreatedDate
        private LocalDateTime createAt;

        @LastModifiedDate
        private LocalDateTime updateAt;


}
