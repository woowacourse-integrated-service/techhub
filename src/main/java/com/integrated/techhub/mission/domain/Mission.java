package com.integrated.techhub.mission.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Mission {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(length = 64, nullable = false)
    private String title;

    @Column(nullable = false)
    private String repoName;

    @Column(nullable = false)
    private String repoUrl;

}
