package com.moneyview.employeePortal.entity;

import lombok.*;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "Employee")
@Table(name = "employee")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String username;

    private String password;

    private String name;

    private String designation;

    private Integer level;

    private String phoneNo;

    private String slackId=null;

    private String displayImgUrl=null;

    private String badgeImgUrl=null;

    @ManyToOne (cascade = CascadeType.REMOVE)
    private Employee manager=null;

    @OneToMany (cascade = CascadeType.ALL,mappedBy = "manager")
    private Set<Employee> reportee;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name = "employee_tag",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> assignedTags;
}
