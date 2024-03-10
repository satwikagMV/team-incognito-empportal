package com.moneyview.employeePortal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Entity(name = "Tag")
@Table(name="tag",uniqueConstraints = @UniqueConstraint(columnNames = {"name","type"}))
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Type type;

    @JsonIgnore
    @ManyToMany (cascade = CascadeType.ALL,mappedBy = "assignedTags")
    private Set<Employee> associatedEmployees=new HashSet<>();
}
