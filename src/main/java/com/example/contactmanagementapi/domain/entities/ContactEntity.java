package com.example.contactmanagementapi.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.Date;

@Table(value = "contacts", schema = "db")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactEntity {
   @Id
   private Long id;
   private String name;
   private String email;
   private String phone;
   @Column("birthDate")
   private LocalDate birthDate;
}
