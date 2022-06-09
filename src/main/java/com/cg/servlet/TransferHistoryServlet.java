package com.cg.servlet;

import com.cg.dto.TransferDTO;
import com.cg.service.TransactionHistoryService;
import com.cg.service.TransactionHistoryServiceImpl;
import com.cg.utils.ValidateUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebServlet(name = "TransferHistoryServlet", urlPatterns = "/transfer-histories")
public class TransferHistoryServlet extends HttpServlet {

    private TransactionHistoryService transactionHistoryService;

    @Override
    public void init() throws ServletException {
        transactionHistoryService = new TransactionHistoryServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }

        switch (action) {
            case "search-by-date":
                getTransferHistoryByDate(req, resp);
                break;
            case "search-by-month":
                getTransferHistoryByMonthYear(req, resp);
                break;
            case "search-by-date-range":
                getTransferHistoryByDateRange(req, resp);
                break;
            default:
                showTransferHistoryPage(req, resp);
                break;
        }
    }

    private void getTransferHistoryByDate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/history/transfer.jsp");

        String sDate = req.getParameter("sDate");

        List<String> errors = new ArrayList<>();

        if (!ValidateUtils.isDateValid(sDate)) {
            errors.add("Invalid date");
        }

        if (errors.size() == 0) {
            List<TransferDTO> transferDTOList = transactionHistoryService.findAllTransfersByDate(sDate);
            req.setAttribute("transferDTOList", transferDTOList);
        }

        if (errors.size() > 0) {
            req.setAttribute("errors", errors);
        }

        req.setAttribute("sDate", sDate);
        req.setAttribute("navTab", "date");
        dispatcher.forward(req, resp);
    }

    private void getTransferHistoryByMonthYear(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/history/transfer.jsp");

        String sMonth = req.getParameter("sMonth");
        String sYear = req.getParameter("sYear");

        List<String> errors = new ArrayList<>();

        if (!ValidateUtils.isNumberValid(sMonth)) {
            errors.add("Invalid month");
        }

        if (!ValidateUtils.isNumberValid(sYear)) {
            errors.add("Invalid year");
        }

        if (errors.size() == 0) {
            int month = Integer.parseInt(sMonth);
            int year = Integer.parseInt(sYear);
            List<TransferDTO> transferDTOList = transactionHistoryService.findAllTransfersByMonthYear(month, year);
            req.setAttribute("transferDTOList", transferDTOList);
        }

        if (errors.size() > 0) {
            req.setAttribute("errors", errors);
        }

        req.setAttribute("sMonth", sMonth);
        req.setAttribute("sYear", sYear);
        req.setAttribute("navTab", "month");
        dispatcher.forward(req, resp);
    }

    private void getTransferHistoryByDateRange(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/history/transfer.jsp");

        String sFromDate = req.getParameter("sFromDate");
        String sToDate = req.getParameter("sToDate");

        List<String> errors = new ArrayList<>();

        if (!ValidateUtils.isDateValid(sFromDate)) {
            errors.add("Invalid From date");
        }

        if (!ValidateUtils.isDateValid(sToDate)) {
            errors.add("Invalid To date");
        }

        if (errors.size() == 0) {
            List<TransferDTO> transferDTOList = transactionHistoryService.findAllTransfersByDateRange(sFromDate, sToDate);
            req.setAttribute("transferDTOList", transferDTOList);
        }

        if (errors.size() > 0) {
            req.setAttribute("errors", errors);
        }

        req.setAttribute("sFromDate", sFromDate);
        req.setAttribute("sToDate", sToDate);
        req.setAttribute("navTab", "date-range");
        dispatcher.forward(req, resp);
    }

    private void showTransferHistoryPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/history/transfer.jsp");

        List<TransferDTO> transferDTOList = transactionHistoryService.findAllTransfers();
        req.setAttribute("transferDTOList", transferDTOList);

        dispatcher.forward(req, resp);
    }
}
