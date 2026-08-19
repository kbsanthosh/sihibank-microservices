package com.sihibank.graphql.controller;

import com.sihibank.graphql.model.*;
import com.sihibank.graphql.service.ExternalCustomerService;
import com.sihibank.graphql.repository.CustomerComplianceRepository; // Added Repository Import
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;
import java.util.Map;

@Controller
public class CustomerQueryController {

    private final ExternalCustomerService customerService;
    private final CustomerComplianceRepository complianceRepository; // Added Repository Field

    // Updated constructor to inject the new Reactive MongoDB Repository
    public CustomerQueryController(ExternalCustomerService customerService,
                                   CustomerComplianceRepository complianceRepository) {
        this.customerService = customerService;
        this.complianceRepository = complianceRepository;
    }

    @QueryMapping
    @SuppressWarnings("unchecked") // This handles all the internal raw map casts cleanly
    public Mono<CustomerSummaryPayload> fetchCustomerSummary(@Argument String mobileNumber) {

        // 1. Pass the raw method calls directly into the zip operator to avoid local type mismatch errors
        return Mono.zip(
                        customerService.getRawCustomerDetails(mobileNumber),
                        complianceRepository.findByMobileNumber(mobileNumber)
                                .defaultIfEmpty(new CustomerCompliance(null, mobileNumber, "NOT_FOUND", 0))
                )
                .map(tuple -> {
                    // 2. Safely unpack using raw Map tokens inside the closure scope
                    Map rawJson = tuple.getT1();
                    CustomerCompliance compliance = tuple.getT2();

                    // 1. Extract and map top-level parameters
                    String fullName = (String) rawJson.get("name");
                    String cellNumber = (String) rawJson.get("mobileNumber");
                    String emailId = (String) rawJson.get("email");

                    // 2. Parse and map accountsDto layer safely
                    Map<String, Object> rawAcc = (Map<String, Object>) rawJson.get("accountsDto");
                    AccountDetails accounts = null;
                    if (rawAcc != null) {
                        accounts = new AccountDetails(
                                String.valueOf(rawAcc.get("accountNumber")),
                                (String) rawAcc.get("accountType")
                        );
                    }

                    // 3. Parse and map cardsDto layer safely
                    Map<String, Object> rawCard = (Map<String, Object>) rawJson.get("cardsDto");
                    CardDetails cards = null;
                    if (rawCard != null) {
                        cards = new CardDetails(
                                (String) rawCard.get("cardNumber"),
                                (String) rawCard.get("cardType")
                        );
                    }

                    // 4. Parse and map loansDto layer safely
                    Map<String, Object> rawLoan = (Map<String, Object>) rawJson.get("loansDto");
                    LoanDetails loans = null;
                    if (rawLoan != null) {
                        loans = new LoanDetails(
                                (String) rawLoan.get("loanNumber"),
                                (String) rawLoan.get("loanType")
                        );
                    }

                    // 5. Assemble into the target CustomerSummaryView structure
                    CustomerSummaryView summaryView = CustomerSummaryView.builder()
                            .fullName(fullName)
                            .cellNumber(cellNumber)
                            .emailId(emailId)
                            .accounts(accounts)
                            .cards(cards)
                            .loans(loans)
                            .kycStatus(compliance.getKycStatus()) // Mixed from MongoDB
                            .riskScore(compliance.getRiskScore()) // Mixed from MongoDB
                            .build();

                    // 6. Wrap it in the payload container to force the "customer" root JSON block
                    return new CustomerSummaryPayload(summaryView);
                });
    }
}
