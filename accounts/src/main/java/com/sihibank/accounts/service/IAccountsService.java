package com.sihibank.accounts.service;

import com.sihibank.accounts.dto.CustomerDto;
import com.sihibank.accounts.entity.Customer;

/**
 *
 // @param customerDto - CustomerDto Object
 */
public interface IAccountsService {

    void createAccount(CustomerDto customerDto);

    CustomerDto fetchAccount(String mobileNumber);

    boolean updateAccount(CustomerDto customerDto);

    boolean deleteAccount(String mobileNumber);

}
