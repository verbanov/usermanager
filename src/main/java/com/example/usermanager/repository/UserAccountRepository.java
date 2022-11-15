package com.example.usermanager.repository;

import com.example.usermanager.model.UserAccount;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long>,
        JpaSpecificationExecutor<UserAccount>, PagingAndSortingRepository<UserAccount, Long> {
    Optional<UserAccount> getUserById(Long id);

    @Query("from UserAccount u join fetch u.roles where u.username = :userName")
    Optional<UserAccount> getUserByUsername(String userName);

    List<UserAccount> findAll(Specification<UserAccount> specification);
}
