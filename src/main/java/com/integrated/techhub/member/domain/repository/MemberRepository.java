package com.integrated.techhub.member.domain.repository;

import com.integrated.techhub.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
