package com.sihibank.accounts.service;

import com.sihibank.accounts.dto.CustomerDetailsDto;
import com.sihibank.accounts.dto.CustomerDto;

public interface ICustomersService {
    CustomerDetailsDto fetchCustomerDetails(String mobileNumber);

}
