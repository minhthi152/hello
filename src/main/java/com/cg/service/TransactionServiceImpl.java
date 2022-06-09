package com.cg.service;

import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.model.Transfer;
import com.cg.model.Withdraw;
import com.cg.utils.MySQLConnUtils;

import java.sql.*;
import java.util.List;

public class TransactionServiceImpl implements TransactionService {

    private final String SP_DEPOSIT = "{CALL sp_deposit(?, ?)}";

    private final String SP_DEPOSIT_ROLLBACK = "{CALL sp_deposit_rollback(?, ?)}";

    private final String SP_WITHDRAW = "{CALL sp_withdraw(?, ?)}";

    private final String SP_TRANSFER = "{CALL sp_transfer(?, ?, ?, ?, ?, ?)}";

    private final String SP_TRANSFER_ROLLBACK = "{CALL sp_transfer_rollback(?, ?, ?, ?, ?)}";


    @Override
    public List<Customer> findAll() {
        return null;
    }

    @Override
    public Customer findById(int id) {
        return null;
    }

    @Override
    public boolean save(Customer customer) {
        return false;
    }

    @Override
    public boolean update(Customer customer) {
        return false;
    }

    @Override
    public boolean deposit(Deposit deposit) {
        boolean isSuccess = false;

        try {
            Connection connection = MySQLConnUtils.getConnection();

            CallableStatement statement = connection.prepareCall(SP_DEPOSIT);
            statement.setInt(1, deposit.getCustomerId());
            statement.setBigDecimal(2, deposit.getTransactionAmount());
            statement.execute();

            isSuccess = true;

        } catch (SQLException e) {
            MySQLConnUtils.printSQLException(e);
        }

        return isSuccess;
    }

    @Override
    public boolean depositRollback(Deposit deposit) {
        boolean isSuccess = false;

        try {
            Connection connection = MySQLConnUtils.getConnection();

            CallableStatement statement = connection.prepareCall(SP_DEPOSIT_ROLLBACK);
            statement.setInt(1, deposit.getCustomerId());
            statement.setBigDecimal(2, deposit.getTransactionAmount());
            statement.execute();

            isSuccess = statement.getBoolean("success");

        } catch (SQLException e) {
            MySQLConnUtils.printSQLException(e);
        }

        return isSuccess;
    }

    @Override
    public boolean withdraw(Withdraw withdraw) {
        boolean isSuccess = false;

        try {
            Connection connection = MySQLConnUtils.getConnection();

            CallableStatement statement = connection.prepareCall(SP_WITHDRAW);
            statement.setInt(1, withdraw.getCustomerId());
            statement.setBigDecimal(2, withdraw.getTransactionAmount());
            statement.execute();

            isSuccess = true;

        } catch (SQLException e) {
            MySQLConnUtils.printSQLException(e);
        }

        return isSuccess;
    }

    @Override
    public boolean transfer(Transfer transfer) {
        boolean isSuccess = false;

        try {
            Connection connection = MySQLConnUtils.getConnection();

            CallableStatement statement = connection.prepareCall(SP_TRANSFER);
            statement.setInt(1, transfer.getSenderId());
            statement.setInt(2, transfer.getRecipientId());
            statement.setBigDecimal(3, transfer.getTransferAmount());
            statement.setInt(4, transfer.getFees());
            statement.setBigDecimal(5, transfer.getFeesAmount());
            statement.setBigDecimal(6, transfer.getTransactionAmount());
            statement.execute();

            isSuccess = true;

        } catch (SQLException e) {
            MySQLConnUtils.printSQLException(e);
        }

        return isSuccess;
    }

    @Override
    public boolean transferRollback(Transfer transfer) {
        boolean isSuccess = false;

        try {
            Connection connection = MySQLConnUtils.getConnection();

            CallableStatement statement = connection.prepareCall(SP_TRANSFER_ROLLBACK);
            statement.setInt(1, transfer.getSenderId());
            statement.setInt(2, transfer.getRecipientId());
            statement.setBigDecimal(3, transfer.getTransferAmount());
            statement.registerOutParameter(4, Types.VARCHAR);
            statement.registerOutParameter(5, Types.BOOLEAN);
            statement.execute();

            isSuccess = statement.getBoolean("success");

        } catch (SQLException e) {
            MySQLConnUtils.printSQLException(e);
        }

        return isSuccess;
    }

    @Override
    public boolean remove(int id) {
        return false;
    }
}
