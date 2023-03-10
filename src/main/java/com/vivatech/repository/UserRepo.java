package com.vivatech.repository;

import com.vivatech.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<Users,Long> {
    Users findByEmail(String email);
    Users findByUid(String uid);
}
