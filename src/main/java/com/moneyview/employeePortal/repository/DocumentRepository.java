package com.moneyview.employeePortal.repository;

import com.moneyview.employeePortal.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document,Long> {
    Document findByName(String name);
}
