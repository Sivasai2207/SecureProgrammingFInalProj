package com.cse5382.PhoneBook_Starter_Java_SpringBoot.Repository;

import com.cse5382.PhoneBook_Starter_Java_SpringBoot.Model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
