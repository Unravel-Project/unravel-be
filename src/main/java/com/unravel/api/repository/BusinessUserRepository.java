package com.unravel.api.repository;

import com.unravel.api.entity.BusinessUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusinessUserRepository extends JpaRepository<BusinessUser, Integer> {

    Optional<BusinessUser> findFirstByEmail(String email);

}
