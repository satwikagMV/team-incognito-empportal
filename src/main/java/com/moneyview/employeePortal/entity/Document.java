package com.moneyview.employeePortal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Document")
@Table(name = "document")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Document {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String url;


    @Column(unique = true)
    private String name;


    @JsonIgnore
    @ManyToOne (cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Employee employee;
}
