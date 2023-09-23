package com.integrated.techhub.member.domain;

import com.integrated.techhub.auth.domain.PasswordEncoder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(length = 32, nullable = false, unique = true)
    private String email;

    @Column(length = 128, nullable = false)
    private String password;

    @Column(length = 4, nullable = false)
    private String nickname;

    @Column(nullable = false)
    private Integer cardinalNumber;

    @Column(nullable = false)
    private String githubUsername;

    @Column(length = 6, nullable = false)
    private String name;

    @Column(length = 128)
    private String bio;

    @Column
    @Enumerated(value = STRING)
    private Position position;

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

}
