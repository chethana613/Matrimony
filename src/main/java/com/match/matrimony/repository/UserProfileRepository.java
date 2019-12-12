package com.match.matrimony.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.match.matrimony.entity.UserProfile;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

	Optional<UserProfile> findByUserProfileIdAndUserProfilePassword(Long userProfileId, String userProfilePassword);

	Optional<UserProfile> findByUserProfileId(Long userProfileId);

	Optional<UserProfile> findByUserProfileId(UserProfile userMatchId);

}
