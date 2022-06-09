package com.cg.service;

import com.cg.dto.TransferDTO;
import com.cg.utils.MySQLConnUtils;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionHistoryServiceImpl implements TransactionHistoryService {

    private final String VW_GET_ALL_TRANSFERS = "SELECT * FROM vw_get_all_transfers;";

    private final String SP_GET_ALL_TRANSFERS_BY_DATE = "{CALL sp_get_all_transfers_by_date(?)}";

    private final String SP_GET_ALL_TRANSFERS_BY_DATE_RANGE = "{CALL sp_get_all_transfers_by_date_range(?, ?)}";

    private final String SP_GET_ALL_TRANSFERS_BY_MONTH_YEAR = "{CALL sp_get_all_transfers_by_month_year(?, ?)}";

    public List<TransferDTO> getList(String str) {
        List<TransferDTO> transferDTOList = new ArrayList<>();

        try {
            Connection connection = MySQLConnUtils.getConnection();

            CallableStatement statement = connection.prepareCall(str);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int senderId = rs.getInt("sender_id");
                String senderName = rs.getString("sender_name");
                int recipientId = rs.getInt("recipient_id");
                String recipientName = rs.getString("recipient_name");
                Date createdOn = rs.getDate("created_at");
                Time createdAt = rs.getTime("created_at");
                BigDecimal transferAmount = rs.getBigDecimal("transfer_amount");
                int fees = rs.getInt("fees");
                BigDecimal feesAmount = rs.getBigDecimal("fees_amount");
                BigDecimal transactionAmount = rs.getBigDecimal("transaction_amount");

                transferDTOList.add(new TransferDTO(id, senderId, senderName, recipientId, recipientName, createdOn, createdAt, transferAmount, fees, feesAmount, transactionAmount));
            }

        } catch (SQLException e) {
            MySQLConnUtils.printSQLException(e);
        }

        return transferDTOList;
    }

    @Override
    public List<TransferDTO> findAllTransfers() {
        List<TransferDTO> transferDTOList = new ArrayList<>();

        try {
            Connection connection = MySQLConnUtils.getConnection();

            CallableStatement statement = connection.prepareCall(VW_GET_ALL_TRANSFERS);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int senderId = rs.getInt("sender_id");
                String senderName = rs.getString("sender_name");
                int recipientId = rs.getInt("recipient_id");
                String recipientName = rs.getString("recipient_name");
                Date createdOn = rs.getDate("created_at");
                Time createdAt = rs.getTime("created_at");
                BigDecimal transferAmount = rs.getBigDecimal("transfer_amount");
                int fees = rs.getInt("fees");
                BigDecimal feesAmount = rs.getBigDecimal("fees_amount");
                BigDecimal transactionAmount = rs.getBigDecimal("transaction_amount");

                transferDTOList.add(new TransferDTO(id, senderId, senderName, recipientId, recipientName, createdOn, createdAt, transferAmount, fees, feesAmount, transactionAmount));
            }

        } catch (SQLException e) {
            MySQLConnUtils.printSQLException(e);
        }

        return transferDTOList;
    }

    @Override
    public List<TransferDTO> findAllTransfersByDate(String created_at) {
        List<TransferDTO> transferDTOList = new ArrayList<>();

        try {
            Connection connection = MySQLConnUtils.getConnection();

            CallableStatement statement = connection.prepareCall(SP_GET_ALL_TRANSFERS_BY_DATE);
            statement.setString(1, created_at);
            statement.execute();

            ResultSet rs = statement.getResultSet();

            if (rs != null) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int senderId = rs.getInt("sender_id");
                    String senderName = rs.getString("sender_name");
                    int recipientId = rs.getInt("recipient_id");
                    String recipientName = rs.getString("recipient_name");
                    Date createdOn = rs.getDate("created_at");
                    Time createdAt = rs.getTime("created_at");
                    BigDecimal transferAmount = rs.getBigDecimal("transfer_amount");
                    int fees = rs.getInt("fees");
                    BigDecimal feesAmount = rs.getBigDecimal("fees_amount");
                    BigDecimal transactionAmount = rs.getBigDecimal("transaction_amount");

                    transferDTOList.add(new TransferDTO(id, senderId, senderName, recipientId, recipientName, createdOn, createdAt, transferAmount, fees, feesAmount, transactionAmount));
                }
            }
        } catch (SQLException e) {
            MySQLConnUtils.printSQLException(e);
        }

        return transferDTOList;
    }

    @Override
    public List<TransferDTO> findAllTransfersByMonthYear(int month, int year) {
        List<TransferDTO> transferDTOList = new ArrayList<>();

        try {
            Connection connection = MySQLConnUtils.getConnection();

            CallableStatement statement = connection.prepareCall(SP_GET_ALL_TRANSFERS_BY_MONTH_YEAR);
            statement.setInt(1, month);
            statement.setInt(2, year);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int senderId = rs.getInt("sender_id");
                String senderName = rs.getString("sender_name");
                int recipientId = rs.getInt("recipient_id");
                String recipientName = rs.getString("recipient_name");
                Date createdOn = rs.getDate("created_at");
                Time createdAt = rs.getTime("created_at");
                BigDecimal transferAmount = rs.getBigDecimal("transfer_amount");
                int fees = rs.getInt("fees");
                BigDecimal feesAmount = rs.getBigDecimal("fees_amount");
                BigDecimal transactionAmount = rs.getBigDecimal("transaction_amount");

                transferDTOList.add(new TransferDTO(id, senderId, senderName, recipientId, recipientName, createdOn, createdAt, transferAmount, fees, feesAmount, transactionAmount));
            }

        } catch (SQLException e) {
            MySQLConnUtils.printSQLException(e);
        }

        return transferDTOList;
    }

    @Override
    public List<TransferDTO> findAllTransfersByDateRange(String fromDate, String toDate) {
        List<TransferDTO> transferDTOList = new ArrayList<>();

        try {
            Connection connection = MySQLConnUtils.getConnection();

            CallableStatement statement = connection.prepareCall(SP_GET_ALL_TRANSFERS_BY_DATE_RANGE);
            statement.setString(1, fromDate);
            statement.setString(2, toDate);
            statement.execute();

            ResultSet rs = statement.getResultSet();

            if (rs != null) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int senderId = rs.getInt("sender_id");
                    String senderName = rs.getString("sender_name");
                    int recipientId = rs.getInt("recipient_id");
                    String recipientName = rs.getString("recipient_name");
                    Date createdOn = rs.getDate("created_at");
                    Time createdAt = rs.getTime("created_at");
                    BigDecimal transferAmount = rs.getBigDecimal("transfer_amount");
                    int fees = rs.getInt("fees");
                    BigDecimal feesAmount = rs.getBigDecimal("fees_amount");
                    BigDecimal transactionAmount = rs.getBigDecimal("transaction_amount");

                    transferDTOList.add(new TransferDTO(id, senderId, senderName, recipientId, recipientName, createdOn, createdAt, transferAmount, fees, feesAmount, transactionAmount));
                }
            }
        } catch (SQLException e) {
            MySQLConnUtils.printSQLException(e);
        }

        return transferDTOList;
    }
}
