package com.ttl.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ttl.core.entities.RefreshToken;

@Repository
public interface RefreshTokenRepository extends BaseRepository<RefreshToken>{

	Optional<RefreshToken> findByTokenId(String pRefreshToken);

	Optional<RefreshToken> findByTokenValue(String pRefreshToken);

	@Query(value = "DELETE FROM screfresh_token WHERE tokenvalue =: pRefreshToken", nativeQuery = true)
	void deleteByTokenValue(String pRefreshToken);
	
	@Transactional
	@Modifying
	@Query("update RefreshToken set revoked = true where tokenValue =:tokenValue")
	void disableToken(@Param("tokenValue") String pRefreshToken);
}
