package com.invoice.Invoice_management.repository;

import com.invoice.Invoice_management.entity.Role;
import com.invoice.Invoice_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleName(String rolename);
}
