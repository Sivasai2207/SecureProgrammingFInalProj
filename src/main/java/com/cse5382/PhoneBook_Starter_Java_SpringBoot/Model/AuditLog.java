package com.cse5382.PhoneBook_Starter_Java_SpringBoot.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_log")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INTEGER PRIMARY KEY AUTOINCREMENT")
    private Long id;

    @Column(name = "operation", nullable = false)
    private String operation;

    @Column(name = "target", nullable = false)
    private String target;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    public AuditLog() {}

    public AuditLog(String operation, String target, LocalDateTime timestamp) {
        this.operation = operation;
        this.target    = target;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public String getOperation() {
        return operation;
    }

    public String getTarget() {
        return target;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
