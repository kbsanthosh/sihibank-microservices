package com.sihibank.accounts.service.impl;

import com.sihibank.accounts.constants.AccountsConstants;
import com.sihibank.accounts.dto.AccountsDto;
import com.sihibank.accounts.dto.CustomerDto;
import com.sihibank.accounts.entity.Accounts;
import com.sihibank.accounts.entity.Customer;
import com.sihibank.accounts.exception.CustomerAlreadyExistsException;
import com.sihibank.accounts.exception.ResourceNotFoundException;
import com.sihibank.accounts.mapper.AccountsMapper;
import com.sihibank.accounts.mapper.CustomerMapper;
import com.sihibank.accounts.repository.AccountsRepository;
import com.sihibank.accounts.repository.CustomerRepository;
import com.sihibank.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountsService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());

        //
        String mobileNumber = customerDto.getMobileNumber();
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(mobileNumber);
        if(optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("Customer already registered with the given mobileNumber: "
                    + mobileNumber);
        }

        Customer savedCustomer = customerRepository.save(customer);
        Accounts newAccount = createNewAccount(savedCustomer);
        accountsRepository.save(newAccount);

    }

    /**
     * @param customer - Customer Object
     * @return the new account details
     */
    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );

        Long customerId = customer.getCustomerId();
        Accounts accounts = accountsRepository.findByCustomerId(customerId).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customerId.toString())
        );

        AccountsDto accountsDto = AccountsMapper.mapToAccountsDto(accounts, new AccountsDto());
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(accountsDto);

        return customerDto;
    }


    /**
     * @param customerDto - CustomerDto Object
     * @return boolean indicating if the update of Account details is successful or not
     */
    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto !=null ){

            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
            );
            AccountsMapper.mapToAccounts(accountsDto, accounts);
            accounts = accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDto,customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return  isUpdated;
    }

    /**
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if the delete of Account details is successful or not
     */
    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }

}
