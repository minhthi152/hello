package com.cg.service;

import com.cg.model.Customer;

import java.util.List;


public interface CustomerService extends IGeneralService<Customer> {

    List<Customer> findAllRecipientsWithOutSenderBySenderId(int senderId);

    boolean checkExistById(int id);

    boolean checkExistingEmail(String email);

    boolean checkExistingEmailAndId(String email, int id);


}
