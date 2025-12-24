package com.eazybytes.accounts.service.impl;

import com.eazybytes.accounts.constants.AccountsConstants;
import com.eazybytes.accounts.dto.AccountsDTO;
import com.eazybytes.accounts.dto.CustomerDTO;
import com.eazybytes.accounts.entity.Accounts;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.exception.CustomerAlreadyExistsException;
import com.eazybytes.accounts.exception.ResourceNotFoundException;
import com.eazybytes.accounts.mapper.AccountsMapper;
import com.eazybytes.accounts.mapper.CustomerMapper;
import com.eazybytes.accounts.repository.AccountsRepository;
import com.eazybytes.accounts.repository.CustomerRepository;
import com.eazybytes.accounts.service.IAccountService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    /**
     *
     * @param customerDTO
     */
    @Override
    public void createAccount(CustomerDTO customerDTO) {
        Customer customer = CustomerMapper.toCustomer(customerDTO,new Customer());

        if (customerRepository.existsByMobileNumber(customerDTO.getMobileNumber())) {
            throw new CustomerAlreadyExistsException("Customer already registered with mobile: " + customerDTO.getMobileNumber());
        }


        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));

    }

    /**
     *
     * @param customer
     * @return new account details
     */
    public Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getId());
        long randomAccountNumber = 1000000000L  + new Random().nextLong(900000000);
        newAccount.setAccountNumber(randomAccountNumber);

        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);


        return newAccount;
    }

    /**
     *
     * @param mobileNumber
     * @return CustomerDTO
     */
    @Override
    public CustomerDTO fetchAccountByMobileNumber(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Customer","mobileNumber", mobileNumber));

        Accounts accounts = accountsRepository.findByCustomerId(customer.getId()).orElseThrow(() -> new ResourceNotFoundException("Account","CustomerId", customer.getId().toString()));

        CustomerDTO customerDTO = CustomerMapper.toCustomerDTO(customer,new CustomerDTO());
        customerDTO.setAccountsDTO(AccountsMapper.toAccountsDTO(accounts,new AccountsDTO()));
        return customerDTO;

    }

    @Override
    public boolean updateAccount(CustomerDTO customerDTO) {

        boolean isUpdated = false;
        AccountsDTO accountsDTO = customerDTO.getAccountsDTO();
        if(accountsDTO != null) {

            Accounts accounts = accountsRepository.findById(accountsDTO.getAccountNumber()).orElseThrow(() ->
                    new ResourceNotFoundException("Account","accountNumber",accountsDTO.getAccountNumber().toString()));

            AccountsMapper.toAccounts(accountsDTO,accounts);
            accounts = accountsRepository.save(accounts);


            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(() ->
                    new ResourceNotFoundException("Customer","customerId", customerId.toString()));
            CustomerMapper.toCustomer(customerDTO,customer);
            customerRepository.save(customer);
            isUpdated = true;

        }
        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {


        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(() ->
                new ResourceNotFoundException("Customer","mobileNumber", mobileNumber));

        accountsRepository.deleteByCustomerId(customer.getId());
        customerRepository.deleteById(customer.getId());
        return true;
    }

}
