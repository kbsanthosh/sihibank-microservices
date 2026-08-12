package com.sihibank.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
        name = "Accounts",
        description = "Schema to hold Account information"
)
public class AccountsDto {

    @Schema(
            description = "Account number of SihiBank account",
            example = "1234567890"
    )
    @NotEmpty(message = "Account Number cannot be null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Account Number must be 10 digits")
    private Long accountNumber;

    @Schema(
            description = "Account type of SihiBank account",
            example = "Savings"
    )
    @NotEmpty(message = "Account Type cannot be null or empty")
    private String accountType;

    @Schema(
            description = "SihiBank branch address",
            example = "2497 Amherst Buffalo NY USA"
    )
    @NotEmpty(message = "Branch Address cannot be null or empty")
    private String branchAddress;
}
