package com.integrated.techhub.member.domain.repository;

import com.integrated.techhub.member.domain.Member;
import com.integrated.techhub.member.exception.MemberNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(final String email);

    Optional<Member> findByEmail(final String email);

    default Member getById(final Long id) {
        return findById(id)
                .orElseThrow(() -> new MemberNotFoundException(id));
    }

    default Member getByEmail(final String email) {
        return findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(email));
    }

}
