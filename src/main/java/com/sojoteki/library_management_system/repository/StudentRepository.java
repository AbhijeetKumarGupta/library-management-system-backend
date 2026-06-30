package com.sojoteki.library_management_system.repository;

import com.sojoteki.library_management_system.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    @Query(nativeQuery = true, value = "select * from student where email = :email")
    Optional<Student> getStudentByEmail (String email);
}
