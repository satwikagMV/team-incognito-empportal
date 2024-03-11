package com.moneyview.employeePortal.repository;

import com.moneyview.employeePortal.entity.Tag;
import com.moneyview.employeePortal.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository  extends JpaRepository<Tag,Long> {
    Tag findByNameAndType(String name, Type type);

    List<Tag> findByName(String name);
    List<Tag> findByType(Type type);


    List<Tag> findByNameStartsWith(String prefix);
}
