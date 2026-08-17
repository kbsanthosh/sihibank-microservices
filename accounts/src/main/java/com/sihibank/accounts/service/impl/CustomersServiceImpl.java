package com.sihibank.accounts.service.impl;

import com.sihibank.accounts.dto.*;
import com.sihibank.accounts.entity.Accounts;
import com.sihibank.accounts.entity.Customer;
import com.sihibank.accounts.exception.ResourceNotFoundException;
import com.sihibank.accounts.mapper.AccountsMapper;
import com.sihibank.accounts.mapper.CustomerMapper;
import com.sihibank.accounts.repository.AccountsRepository;
import com.sihibank.accounts.repository.CustomerRepository;
import com.sihibank.accounts.service.ICustomersService;
import com.sihibank.accounts.service.client.CardsFeignClient;
import com.sihibank.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomersServiceImpl implements ICustomersService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;

    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );

        Long customerId = customer.getCustomerId();
        Accounts accounts = accountsRepository.findByCustomerId(customerId).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customerId.toString())
        );

       // AccountsDto accountsDto = AccountsMapper.mapToAccountsDto(accounts, new AccountsDto());
       // CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
       // customerDto.setAccountsDto(accountsDto);

        AccountsDto accountsDto = AccountsMapper.mapToAccountsDto(accounts, new AccountsDto());
        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(accountsDto);
        //customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(correlationId, mobileNumber);
        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());

        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(correlationId, mobileNumber);
        customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());


        return customerDetailsDto;    }
}
