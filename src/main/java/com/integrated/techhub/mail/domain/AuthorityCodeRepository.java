package com.integrated.techhub.mail.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityCodeRepository extends CrudRepository<AuthorityCode, String> {

}
