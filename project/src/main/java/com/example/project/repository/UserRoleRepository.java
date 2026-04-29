package com.example.project.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.project.entity.UserRole;
import com.example.project.entity.User;
import com.example.project.entity.Role;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

     boolean existsByUserAndRole(User user, Role role);
}