package com.example.contactmanagementapi.domain.valueobjects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(value = "addresses", schema = "db")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressEntity {
    @Id
    private Long id;

    private String street;

    private String number;

    @Column("postalCode")
    private String postalCode;

    @Column("contactId")
    private Long contactId;
}
