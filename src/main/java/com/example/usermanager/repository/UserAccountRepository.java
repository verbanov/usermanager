package com.example.usermanager.repository;

import com.example.usermanager.model.UserAccount;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    Optional<UserAccount> getUserById(Long id);

    @Query("from UserAccount u join fetch u.roles where u.username = :userName")
    Optional<UserAccount> getUserByUsername(String userName);
}
