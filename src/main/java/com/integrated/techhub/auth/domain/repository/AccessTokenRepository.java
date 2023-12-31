package com.integrated.techhub.auth.domain.repository;

import com.integrated.techhub.auth.domain.AccessToken;
import com.integrated.techhub.auth.domain.type.Type;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccessTokenRepository extends CrudRepository<AccessToken, Long> {

    Optional<AccessToken> findByMemberIdAndType(final Long memberId, final Type type);

}
