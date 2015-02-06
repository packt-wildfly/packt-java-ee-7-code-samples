package com.packtpub.wflydevelopment.chapter7.boundary;

import com.packtpub.wflydevelopment.chapter7.entity.Account;

public class AccountDto {
    private int balance;

    public AccountDto() {
        /* empty for serialization */
    }

    public AccountDto(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public static AccountDto fromAccount(Account account) {
        return new AccountDto(account.getBalance());
    }

    @Override
    public String toString() {
        return "AccountDto [balance=" + balance + "]";
    }

}
