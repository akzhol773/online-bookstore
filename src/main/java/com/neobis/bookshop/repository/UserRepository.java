package com.neobis.bookshop.repository;


import com.neobis.bookshop.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM users u WHERE u.accountStatus = 'ACTIVE' AND u.username = :username")
    Optional<User> findByUsername(@Param("username") String username);


    @Query("SELECT a FROM  users a JOIN a.roles r WHERE a.accountStatus = 'ACTIVE' AND r.name = 'ROLE_USER'")
    List<User> findAllActiveCustomers();


    @Query("SELECT a FROM users a WHERE a.accountStatus = 'ACTIVE' AND a.id = :userId")
    Optional<User> findActiveCustomersById(@Param("userId") Long userId);



    @Query("SELECT a FROM users a WHERE a.accountStatus = 'ACTIVE' AND a.emailAddress = :email")
    Optional<User> findActiveCustomerByEmail(@Param("email") String email);

    @Query("SELECT u.id FROM users u WHERE u.username = :username AND u.accountStatus = 'ACTIVE'")
    Optional<Long> findUserIdByUsername(@Param("username") String username);


}
