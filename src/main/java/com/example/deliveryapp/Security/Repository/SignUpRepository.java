package com.example.deliveryapp.Security.Repository;

import com.example.deliveryapp.Security.Entity.SignUp;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SignUpRepository extends JpaRepository<SignUp, Long> {

  List<SignUp> findByFirstName(String firstName);

  SignUp findByEmailId(String emailId);

  @Modifying // update and creation queries can also be performed under @Query annotation.
  @Transactional // will execute if the transaction is successful
  @Query(value = "UPDATE sign_up u SET u.enabled = TRUE WHERE u.email_address = ?1", nativeQuery = true)
  void enableUser(String emailId);
}
