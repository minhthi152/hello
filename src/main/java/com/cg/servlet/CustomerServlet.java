package com.cg.servlet;

import com.cg.model.Customer;
import com.cg.service.CustomerService;
import com.cg.service.CustomerServiceImpl;
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

@WebServlet(name = "CustomerServlet", urlPatterns = "/customers")
public class CustomerServlet extends HttpServlet {

    private CustomerService customerService;

    @Override
    public void init() throws ServletException {
        customerService = new CustomerServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }

        switch (action) {
            case "create":
                showCreatePage(req, resp);
                break;
            case "edit":
                showEditPage(req, resp);
                break;
            case "delete":
                showDeleteCustomerPage(req, resp);
                break;
            default:
                showListPage(req, resp);
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
            case "create":
                createCustomer(req, resp);
                break;
            case "edit":
                updateCustomer(req, resp);
                break;
            case "delete":
                deleteCustomer(req, resp);
                break;
        }
    }

    private void showCreatePage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/customer/create.jsp");
        dispatcher.forward(req, resp);
    }

    private void showEditPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/customer/edit.jsp");

        String id = req.getParameter("id");

        if (!ValidateUtils.isNumberValid(id)) {
            req.setAttribute("errors", "Invalid customer ID");
            dispatcher.forward(req, resp);
        }

        Customer customer = customerService.findById(Integer.parseInt(id));

        if(customer == null){
            req.setAttribute("errors", "Can't find customer with ID = " + id);
        } else {
            req.setAttribute("customer", customer);
        }

        dispatcher.forward(req, resp);
    }

    private void showDeleteCustomerPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/customer/delete.jsp");

        String id = req.getParameter("id");

        if (!ValidateUtils.isNumberValid(id)) {
            req.setAttribute("errors", "Invalid customer ID");
            dispatcher.forward(req, resp);
        }

        Customer customer = customerService.findById(Integer.parseInt(id));

        if(customer == null){
            req.setAttribute("errors", "Can't find customer with ID = " + id);
        } else {
            req.setAttribute("customer", customer);
        }

        dispatcher.forward(req, resp);
    }

    private void showListPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Customer> customers = customerService.findAll();
        req.setAttribute("customers", customers);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/customer/list.jsp");
        dispatcher.forward(req, resp);
    }

    private void createCustomer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/customer/create.jsp");

        String fullName = req.getParameter("fullName");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");

        List<String> errors = new ArrayList<>();

        if (fullName == null || fullName.equals("")) {
            errors.add("Name cannot be blank");
        }
        else if (!ValidateUtils.isLetterWithoutNumberValid(fullName)) {
            errors.add("Name invalidate");
        }

        if (email == null || email.equals("")) {
            errors.add("Email cannot be blank");
        }
        else if (!ValidateUtils.isEmailValid(email)) {
            errors.add("Email invalidate");
        }
        else if (customerService.checkExistingEmail(email)) {
            errors.add("Email already exists");
        }

        if (errors.size() == 0) {
            Customer customer = new Customer(fullName, email, phone, address);

            boolean isCreated = customerService.save(customer);

            if (isCreated) {
                req.setAttribute("success", "Successfully added new customer");
            }
            else {
                errors.add("Add new customer failed");
            }
        }

        if (errors.size() > 0) {
            req.setAttribute("errors", errors);
        }

        dispatcher.forward(req, resp);
    }

    private void updateCustomer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/customer/edit.jsp");

        String id = req.getParameter("id");
        String fullName = req.getParameter("fullName");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");

        Customer customer = null;

        List<String> errors = new ArrayList<>();

        if (!ValidateUtils.isNumberValid(id) || id.length() > 9) {
            errors.add("Invalid customer ID");
        }
        else {
            int customerId = Integer.parseInt(id);

            customer = new Customer(customerId, fullName, email, phone, address);

            if (fullName == null || fullName.equals("")) {
                errors.add("Name cannot be blank");
            }
            else if (!ValidateUtils.isLetterWithoutNumberValid(fullName)) {
                errors.add("Name invalidate");
            }

            if (email == null || email.equals("")) {
                errors.add("Email cannot be blank");
            }
            else if (!ValidateUtils.isEmailValid(email)) {
                errors.add("Email invalidate");
            }
            else {
                if (customerService.checkExistingEmailAndId(email, customerId)) {
                    errors.add("Email already exists");
                }

                if (errors.size() == 0) {
                    boolean isUpdated =  customerService.update(customer);

                    if (isUpdated) {
                        req.setAttribute("success", "Update customer information successfully");
                    }
                    else {
                        errors.add("Customer information update failed");
                    }
                }
            }
        }

        if (errors.size() > 0) {
            req.setAttribute("errors", errors);
        }

        req.setAttribute("customer", customer);
        dispatcher.forward(req, resp);
    }

    private void deleteCustomer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/customer/delete.jsp");

        String id = req.getParameter("id");

        List<String> errors = new ArrayList<>();

        if (!ValidateUtils.isNumberValid(id) || id.length() > 9) {
            errors.add("Invalid customer ID");
        }

        if (errors.size() == 0) {
            int customerId = Integer.parseInt(id);

            boolean isSuccess =  customerService.remove(customerId);

            if (isSuccess) {
                req.setAttribute("success", "Delete customer successfully");
            }
            else {
                errors.add("Deleting failed customers");
            }
        }

        if (errors.size() > 0) {
            req.setAttribute("errors", errors);
        }

        dispatcher.forward(req, resp);
    }

}
