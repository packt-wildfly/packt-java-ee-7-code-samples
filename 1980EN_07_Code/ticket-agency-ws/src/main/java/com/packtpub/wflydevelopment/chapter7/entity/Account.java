package com.packtpub.wflydevelopment.chapter7.entity;

public class Account {

    private final int balance;

    public Account(int initialBalance) {
        this.balance = initialBalance;
    }

    public Account charge(int amount) {
        final int newBalance = balance - amount;
        if (newBalance < 0) {
            throw new IllegalArgumentException("Debit value on account!");
        }
        return new Account(newBalance);
    }

    public int getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "Account [balance = " + balance + "]";
    }
}
