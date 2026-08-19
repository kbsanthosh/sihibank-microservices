package com.sihibank.graphql.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerSummaryView {
    private String fullName;
    private String cellNumber;
    private String emailId;
    private AccountDetails accounts;
    private CardDetails cards;
    private LoanDetails loans;
    // Append your new mixed metadata variables here:
    private String kycStatus;
    private Integer riskScore;
}

