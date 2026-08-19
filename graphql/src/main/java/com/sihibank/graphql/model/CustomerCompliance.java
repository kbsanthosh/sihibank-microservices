package com.sihibank.graphql.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "customer_compliance")
public class CustomerCompliance {
    @Id
    private String id; // Handled dynamically by MongoDB
    private String mobileNumber;
    private String kycStatus;
    private Integer riskScore;
}
