package com.cg.servlet;

import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.model.Transfer;
import com.cg.model.Withdraw;
import com.cg.service.CustomerService;
import com.cg.service.CustomerServiceImpl;
import com.cg.service.TransactionService;
import com.cg.service.TransactionServiceImpl;
import com.cg.utils.ValidateUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


@WebServlet(name = "TransactionServlet", urlPatterns = "/transactions")
public class TransactionServlet extends HttpServlet {

    private CustomerService customerService;
    private TransactionService transactionService;

    @Override
    public void init() throws ServletException {
        customerService = new CustomerServiceImpl();
        transactionService = new TransactionServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }

        switch (action) {
            case "deposit":
                showDepositPage(req, resp);
                break;
            case "withdraw":
                showWithdrawPage(req, resp);
                break;
            case "transfer":
                showTransferPage(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }

        switch (action) {
            case "deposit":
                doDeposit(req, resp);
                break;
            case "withdraw":
                doWithdraw(req, resp);
                break;
            case "transfer":
                doTransfer(req, resp);
                break;
        }
    }

    private void showDepositPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        RequestDispatcher dispatcher = req.getRequestDispatcher("/transaction/deposit.jsp");

        if (!ValidateUtils.isNumberValid(id) || id.length() > 9) {
            req.setAttribute("errors", "Invalid customer ID");
        }
        else {
            Customer customer = customerService.findById(Integer.parseInt(id));

            if(customer == null){
                req.setAttribute("errors", "Can't find customer with ID = " + id);
            } else {
                req.setAttribute("customer", customer);
            }
        }

        dispatcher.forward(req, resp);
    }

    private void showWithdrawPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        RequestDispatcher dispatcher = req.getRequestDispatcher("/transaction/withdraw.jsp");

        if (!ValidateUtils.isNumberValid(id) || id.length() > 9) {
            req.setAttribute("errors", "Invalid customer ID");
        }
        else {
            Customer customer = customerService.findById(Integer.parseInt(id));

            if(customer == null){
                req.setAttribute("errors", "Can't find customer with ID = " + id);
            } else {
                req.setAttribute("customer", customer);
            }
        }

        dispatcher.forward(req, resp);
    }

    private void showTransferPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        RequestDispatcher dispatcher = req.getRequestDispatcher("/transaction/transfer.jsp");

        if (!ValidateUtils.isNumberValid(id) || id.length() > 9) {
            req.setAttribute("errors", "Invalid sender ID");
        }
        else {
            int senderId = Integer.parseInt(id);
            Customer sender = customerService.findById(senderId);

            if(sender == null){
                req.setAttribute("errors", "Can't find sender with ID = " + id);
            } else {
                List<Customer> recipients = customerService.findAllRecipientsWithOutSenderBySenderId(senderId);
                req.setAttribute("sender", sender);
                req.setAttribute("recipients", recipients);
            }
        }

        dispatcher.forward(req, resp);
    }

    private void doDeposit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/transaction/deposit.jsp");

        String id = req.getParameter("id");
        long transactionAmountLength = req.getParameter("transactionAmount").length();
        BigDecimal minMoney = BigDecimal.valueOf(50);
        BigDecimal maxMoney = BigDecimal.valueOf(1000000000);
        DecimalFormat formatter = new DecimalFormat("#,###");

        List<String> errors = new ArrayList<>();

        if (!ValidateUtils.isNumberValid(id) || id.length() > 9) {
            errors.add("Invalid customer ID");
        }
        else {
            int customerId = Integer.parseInt(id);

            boolean isExistCustomer = customerService.checkExistById(customerId);

            if (!isExistCustomer) {
                errors.add("Can't find customer with ID = " + id);
            }
            else if (req.getParameter("transactionAmount") == null || req.getParameter("transactionAmount").equals("")) {
                errors.add("Transaction amount cannot be blank");
            }
            else if (!ValidateUtils.isNumberValid(req.getParameter("transactionAmount"))) {
                errors.add("Transaction amount invalidate");
            }
            else if (transactionAmountLength > 10) {
                errors.add("Transaction Amount must be less than or equal to " + formatter.format(maxMoney).replace(",", "."));
            }
            else {
                BigDecimal transactionAmount = BigDecimal.valueOf(Long.parseLong(req.getParameter("transactionAmount")));

                Deposit deposit = new Deposit(customerId, transactionAmount);

                if (transactionAmount.compareTo(minMoney) < 0) {
                    errors.add("Transaction Amount must be greater than or equal to " + formatter.format(minMoney));
                }
                else if (transactionAmount.compareTo(maxMoney) > 0) {
                    errors.add("Transaction Amount must be less than or equal to " + formatter.format(maxMoney).replace(",", "."));
                }

                if (errors.size() == 0) {
                    boolean isSuccess =  transactionService.depositRollback(deposit);

                    if (isSuccess) {
                        req.setAttribute("success", "Successful deposit transaction");
                    }
                    else {
                        errors.add("Deposit failed, please contact the system administrator");
                    }
                }
            }

            Customer customer = customerService.findById(customerId);
            req.setAttribute("customer", customer);
        }

        if (errors.size() > 0) {
            req.setAttribute("errors", errors);
        }

        dispatcher.forward(req, resp);
    }

    private void doWithdraw(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/transaction/withdraw.jsp");

        String id = req.getParameter("id");
        long transactionAmountLength = req.getParameter("transactionAmount").length();
        BigDecimal minMoney = BigDecimal.valueOf(50);
        BigDecimal maxMoney = BigDecimal.valueOf(1000000000);
        DecimalFormat formatter = new DecimalFormat("#,###");

        List<String> errors = new ArrayList<>();

        if (!ValidateUtils.isNumberValid(id) || id.length() > 9) {
            errors.add("Invalid customer ID");
        }
        else {
            int customerId = Integer.parseInt(id);

            Customer customer = customerService.findById(customerId);

            if (customer == null) {
                errors.add("Can't find customer with ID = " + id);
            }
            else if (req.getParameter("transactionAmount") == null || req.getParameter("transactionAmount").equals("")) {
                errors.add("Transaction amount cannot be blank");
            }
            else if (!ValidateUtils.isNumberValid(req.getParameter("transactionAmount"))) {
                errors.add("Transaction amount invalidate");
            }
            else if (transactionAmountLength > 10) {
                errors.add("Transaction Amount must be less than or equal to " + formatter.format(maxMoney).replace(",", "."));
            }
            else {
                BigDecimal transactionAmount = BigDecimal.valueOf(Long.parseLong(req.getParameter("transactionAmount")));

                Withdraw withdraw = new Withdraw(customerId, transactionAmount);

                if (transactionAmount.compareTo(minMoney) < 0) {
                    errors.add("Transaction Amount must be greater than or equal to " + formatter.format(minMoney));
                }
                else if (transactionAmount.compareTo(maxMoney) > 0) {
                    errors.add("Transaction Amount must be less than or equal to " + formatter.format(maxMoney).replace(",", "."));
                }
                else {
                    BigDecimal currentBalance = customer.getBalance();

                    if (transactionAmount.compareTo(currentBalance) > 0) {
                        errors.add("Customer's balance is not enough to make a withdrawal");
                    }
                }

                if (errors.size() == 0) {
                    boolean isSuccess =  transactionService.withdraw(withdraw);

                    if (isSuccess) {
                        req.setAttribute("success", "Successful withdrawal transaction");
                    }
                    else {
                        errors.add("Withdraw failed, please contact the system administrator");
                    }
                }
            }

            customer = customerService.findById(customerId);
            req.setAttribute("customer", customer);
        }

        if (errors.size() > 0) {
            req.setAttribute("errors", errors);
        }

        dispatcher.forward(req, resp);
    }

    private void doTransfer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/transaction/transfer.jsp");

        String strSenderId = req.getParameter("id");
        String strRecipientId = req.getParameter("recipientId");
        String strTransactionAmount = req.getParameter("transactionAmount");
        long transactionAmountLength = strTransactionAmount.length();
        BigDecimal minMoney = BigDecimal.valueOf(50);
        BigDecimal maxMoney = BigDecimal.valueOf(1000000000);
        DecimalFormat formatter = new DecimalFormat("#,###");

        List<String> errors = new ArrayList<>();

        if (!ValidateUtils.isNumberValid(strSenderId) || strSenderId.length() > 9) {
            errors.add("Invalid sender ID");
        }
        else if (strRecipientId == null) {
            errors.add("Recipient ID cannot be blank");
        }
        else if (!ValidateUtils.isNumberValid(strRecipientId) || strRecipientId.length() > 9) {
            errors.add("Invalid recipient ID");
        }
        else {
            int senderId = Integer.parseInt(strSenderId);
            int recipientId = Integer.parseInt(strRecipientId);

            Customer sender = customerService.findById(senderId);
            Customer recipient = customerService.findById(recipientId);

            if (sender == null) {
                errors.add("Can't find sender with ID = " + senderId);
            }
            else if (recipient == null) {
                errors.add("Can't find recipient with ID = " + recipientId);
            }
            else if (senderId == recipientId) {
                errors.add("ID of the Sender must be different with ID of Recipient");
            }
            else if (req.getParameter("transactionAmount") == null || req.getParameter("transactionAmount").equals("")) {
                errors.add("Transaction amount cannot be blank");
            }
            else if (!ValidateUtils.isNumberValid(req.getParameter("transactionAmount"))) {
                errors.add("Transaction amount invalidate");
            }
            else if (transactionAmountLength > 10) {
                errors.add("Transaction Amount must be less than or equal to " + formatter.format(maxMoney).replace(",", "."));
            }
            else {
                BigDecimal transferAmount = BigDecimal.valueOf(Long.parseLong(req.getParameter("transferAmount")));
                int fees = 10;
                BigDecimal feesAmount = transferAmount.divide(BigDecimal.valueOf(fees));
                BigDecimal transactionAmount = transferAmount.add(feesAmount);

                if (transferAmount.compareTo(minMoney) < 0) {
                    errors.add("Transaction Amount must be greater than or equal to " + formatter.format(minMoney));
                }
                else if (transferAmount.compareTo(maxMoney) > 0) {
                    errors.add("Transaction Amount must be less than or equal to " + formatter.format(maxMoney).replace(",", "."));
                }
                else {
                    BigDecimal senderCurrentBalance = sender.getBalance();

                    if (transactionAmount.compareTo(senderCurrentBalance) > 0) {
                        errors.add("Sender's balance is not enough to make a transaction");
                    }
                }

                if (errors.size() == 0) {
                    Transfer transfer = new Transfer(senderId, recipientId, fees, feesAmount, transferAmount, transactionAmount);

                    boolean isSuccess =  transactionService.transferRollback(transfer);

                    if (isSuccess) {
                        req.setAttribute("success", "Successful transfer transaction");
                    }
                    else {
                        errors.add("Transfer failed, please contact the system administrator");
                    }
                }
            }

            sender = customerService.findById(senderId);
            List<Customer> recipients = customerService.findAllRecipientsWithOutSenderBySenderId(senderId);

            req.setAttribute("sender", sender);
            req.setAttribute("recipients", recipients);
        }

        if (errors.size() > 0) {
            req.setAttribute("errors", errors);
        }

        dispatcher.forward(req, resp);
    }
}
