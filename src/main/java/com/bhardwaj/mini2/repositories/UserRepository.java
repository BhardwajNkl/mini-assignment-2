package com.bhardwaj.mini2.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bhardwaj.mini2.entities.UserEntity;
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	@Query(value = "SELECT * FROM users LIMIT :limit OFFSET :offset ;" ,nativeQuery = true)
	public List<UserEntity> getUsersBasedOnLimitAndOffset(@Param("limit") int limit, @Param("offset") int offset);
}
