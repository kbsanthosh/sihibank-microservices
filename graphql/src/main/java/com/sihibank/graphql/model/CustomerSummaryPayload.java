package com.sihibank.graphql.model;

import lombok.AllArgsConstructor;
import lombok.Data;

// This wraps the customer object to match your required GraphQL response root shape
@Data
@AllArgsConstructor
public class CustomerSummaryPayload {
    private CustomerSummaryView customer;
}
