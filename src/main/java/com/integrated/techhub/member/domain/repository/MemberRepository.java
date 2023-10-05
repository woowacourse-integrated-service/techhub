package com.integrated.techhub.member.domain.repository;

import com.integrated.techhub.member.domain.Member;
import com.integrated.techhub.member.exception.MemberNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(String email);

    default Member getById(final Long id) {
        return findById(id)
                .orElseThrow(() -> new MemberNotFoundException(id));
    }

}
