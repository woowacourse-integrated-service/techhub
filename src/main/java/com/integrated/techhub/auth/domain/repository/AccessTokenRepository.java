package com.integrated.techhub.auth.domain.repository;

import com.integrated.techhub.auth.domain.AccessToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccessTokenRepository extends CrudRepository<AccessToken, Long> {

    Optional<AccessToken> findByEmail(final String email);

}
