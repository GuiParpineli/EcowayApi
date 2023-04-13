package com.ecoway.api.repository;

import com.ecoway.api.model.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<SystemUser, UUID> {
    @Transactional
    @Modifying
    @Query("update SystemUser s set s.name = ?1, s.lastname = ?2, s.phone = ?3, s.cpf = ?4, s.cnh = ?5, s.birthday = ?6")
    int updateNameAndLastnameAndPhoneAndCpfAndCnhAndBirthdayBy(String name, String lastname, String phone, String cpf, String cnh, Date birthday);
    Optional<SystemUser> findByEmail(String email);
}
