package com.cg.service;

import com.cg.dto.TransferDTO;

import java.util.List;

public interface TransactionHistoryService {
    List<TransferDTO> findAllTransfers();

    List<TransferDTO> findAllTransfersByDate(String created_at);

    List<TransferDTO> findAllTransfersByMonthYear(int month, int year);

    List<TransferDTO> findAllTransfersByDateRange(String fromDate, String toDate);

}
