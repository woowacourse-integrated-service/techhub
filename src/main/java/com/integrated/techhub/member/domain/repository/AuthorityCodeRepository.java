package com.integrated.techhub.member.domain.repository;

import com.integrated.techhub.member.domain.AuthorityCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityCodeRepository extends CrudRepository<AuthorityCode, String> {

}
