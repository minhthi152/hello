package com.cg.service;

import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.model.Transfer;
import com.cg.model.Withdraw;

public interface TransactionService extends IGeneralService<Customer> {

    boolean deposit(Deposit deposit);

    boolean depositRollback(Deposit deposit);

    boolean withdraw(Withdraw withdraw);

    boolean transfer(Transfer transfer);

    boolean transferRollback(Transfer transfer);
}
