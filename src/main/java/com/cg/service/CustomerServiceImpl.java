package com.cg.service;

import com.cg.model.Customer;
import com.cg.utils.MySQLConnUtils;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    private final String GET_ALL_CUSTOMERS = "SELECT * FROM vw_get_all_customers;";

    private final String GET_CUSTOMER_BY_ID = "" +
        "SELECT " +
            "c.id, " +
            "c.full_name, " +
            "c.email, " +
            "c.phone, " +
            "c.address, " +
            "c.balance " +
        "FROM customers AS c " +
        "WHERE c.deleted = 0 " +
        "AND c.id = ?;";

    private final String GET_ALL_RECIPIENTS_WITHOUT_SENDER_BY_SENDER_ID = "" +
        "SELECT " +
            "c.id, " +
            "c.full_name " +
        "FROM customers AS c " +
        "WHERE c.deleted = 0 " +
        "AND c.id <> ?;";

    private final String CHECK_EXISTING_CUSTOMER_BY_ID = "SELECT COUNT(*) AS count FROM customers AS c WHERE c.id = ?;";

    private final String CHECK_EXISTING_CUSTOMER_BY_EMAIL = "SELECT COUNT(*) AS count FROM customers AS c WHERE c.email = ?;";

    private final String CHECK_EXISTING_CUSTOMER_BY_EMAIL_AND_ID = "" +
            "SELECT COUNT(*) AS count " +
            "FROM customers AS c " +
            "WHERE c.email = ? " +
            "AND c.id <> ?;";

    private final String UPDATE_CUSTOMER_BY_ID = "" +
        "UPDATE customers AS c " +
        "SET " +
            "c.full_name = ?, " +
            "c.email = ?, " +
            "c.phone = ?, " +
            "c.address = ?, " +
            "c.updated_at = NOW()" +
        "WHERE c.id = ?;";

    private final String DELETE_CUSTOMER_BY_ID = "" +
            "UPDATE customers AS c " +
            "SET " +
                "c.deleted = 1, " +
                "c.updated_at = NOW()" +
            "WHERE c.id = ? " +
            "AND c.deleted = 0;";

    private final String SP_CREATE_CUSTOMER = "{CALL sp_create_customer(?, ?, ?, ?)}";

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();

        try {
            Connection connection = MySQLConnUtils.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_CUSTOMERS);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String fullName = rs.getString("full_name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                BigDecimal balance = rs.getBigDecimal("balance");

                customers.add(new Customer(id, fullName, email, phone, address, balance));
            }

        } catch (SQLException e) {
            MySQLConnUtils.printSQLException(e);
        }

        return customers;
    }

    @Override
    public Customer findById(int customerId) {
        Customer customer = null;

        try {
            Connection connection = MySQLConnUtils.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(GET_CUSTOMER_BY_ID);
            preparedStatement.setInt(1, customerId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String fullName = rs.getString("full_name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                BigDecimal balance = rs.getBigDecimal("balance");

                customer = new Customer(id, fullName, email, phone, address, balance);
            }

        } catch (SQLException e) {
            MySQLConnUtils.printSQLException(e);
        }

        return customer;
    }

    @Override
    public List<Customer> findAllRecipientsWithOutSenderBySenderId(int senderId) {
        List<Customer> customers = new ArrayList<>();

        try {
            Connection connection = MySQLConnUtils.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_RECIPIENTS_WITHOUT_SENDER_BY_SENDER_ID);
            preparedStatement.setInt(1, senderId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String fullName = rs.getString("full_name");

                customers.add(new Customer(id, fullName));
            }

        } catch (SQLException e) {
            MySQLConnUtils.printSQLException(e);
        }

        return customers;
    }

    @Override
    public boolean checkExistById(int id) {
        boolean isExist = false;

        try {
            Connection connection = MySQLConnUtils.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(CHECK_EXISTING_CUSTOMER_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                isExist = rs.getInt("count") > 0;
            }

        } catch (SQLException e) {
            MySQLConnUtils.printSQLException(e);
        }

        return isExist;
    }

    @Override
    public boolean checkExistingEmail(String email) {
        boolean isExist = false;

        try {
            Connection connection = MySQLConnUtils.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(CHECK_EXISTING_CUSTOMER_BY_EMAIL);
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                isExist = rs.getInt("count") > 0;
            }

        } catch (SQLException e) {
            MySQLConnUtils.printSQLException(e);
        }

        return isExist;
    }

    @Override
    public boolean checkExistingEmailAndId(String email, int id) {
        boolean isExist = false;

        try {
            Connection connection = MySQLConnUtils.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(CHECK_EXISTING_CUSTOMER_BY_EMAIL_AND_ID);
            preparedStatement.setString(1, email);
            preparedStatement.setInt(2, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                isExist = rs.getInt("count") > 0;
            }

        } catch (SQLException e) {
            MySQLConnUtils.printSQLException(e);
        }

        return isExist;
    }

    @Override
    public boolean save(Customer customer) {
        boolean isSuccess = false;

        try {
            Connection connection = MySQLConnUtils.getConnection();

            CallableStatement statement = connection.prepareCall(SP_CREATE_CUSTOMER);
            statement.setString(1, customer.getFullName());
            statement.setString(2, customer.getEmail());
            statement.setString(3, customer.getPhone());
            statement.setString(4, customer.getAddress());
            statement.execute();

            isSuccess = true;

        } catch (SQLException e) {
            MySQLConnUtils.printSQLException(e);
        }

        return isSuccess;
    }

    @Override
    public boolean update(Customer customer) {
        boolean isSuccess = false;

        try {
            Connection connection = MySQLConnUtils.getConnection();

            PreparedStatement statement = connection.prepareStatement(UPDATE_CUSTOMER_BY_ID);
            statement.setString(1, customer.getFullName());
            statement.setString(2, customer.getEmail());
            statement.setString(3, customer.getPhone());
            statement.setString(4, customer.getAddress());
            statement.setInt(5, customer.getId());
            statement.execute();

            isSuccess = true;
        } catch (SQLException e) {
            MySQLConnUtils.printSQLException(e);
        }

        return isSuccess;
    }

    @Override
    public boolean remove(int id) {
        boolean isSuccess = false;

        try {
            Connection connection = MySQLConnUtils.getConnection();

            PreparedStatement statement = connection.prepareStatement(DELETE_CUSTOMER_BY_ID);
            statement.setInt(1, id);
            isSuccess = statement.executeUpdate() > 0;

        } catch (SQLException e) {
            MySQLConnUtils.printSQLException(e);
        }

        return isSuccess;
    }
}
