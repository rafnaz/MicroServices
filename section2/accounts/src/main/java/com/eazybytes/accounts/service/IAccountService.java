package com.eazybytes.accounts.service;

import com.eazybytes.accounts.dto.CustomerDTO;

public interface IAccountService {

    /**
     *
     * @param customerDTO
     */
    void createAccount(CustomerDTO customerDTO);
    CustomerDTO fetchAccountByMobileNumber(String mobileNumber);

    boolean updateAccount(CustomerDTO customerDTO);

    public boolean deleteAccount(String mobileNumber);


}
