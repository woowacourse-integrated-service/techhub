package com.integrated.techhub.auth.domain.repository;

import com.integrated.techhub.auth.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByMemberIdAndType(final Long memberId, final String type);

}
