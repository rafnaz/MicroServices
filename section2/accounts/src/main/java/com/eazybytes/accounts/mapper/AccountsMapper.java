package com.eazybytes.accounts.mapper;

import com.eazybytes.accounts.dto.AccountsDTO;
import com.eazybytes.accounts.entity.Accounts;

public class AccountsMapper {

    public static AccountsDTO toAccountsDTO(Accounts accounts, AccountsDTO dto) {

        dto.setAccountNumber(accounts.getAccountNumber());
        dto.setAccountType(accounts.getAccountType());
        dto.setBranchAddress(accounts.getBranchAddress());
        return dto;
    }

    public static Accounts toAccounts(AccountsDTO accountsDTO, Accounts  accounts) {
        accounts.setAccountNumber(accountsDTO.getAccountNumber());
        accounts.setAccountType(accountsDTO.getAccountType());
        accounts.setBranchAddress(accountsDTO.getBranchAddress());
        return accounts;
    }
}
