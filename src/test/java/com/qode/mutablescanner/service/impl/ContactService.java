package com.qode.mutablescanner.service.impl;

import com.qode.mutablescanner.object.Contact;
import com.qode.mutablescanner.service.IContactService;

public class ContactService implements IContactService {

    @Override
    public Contact getContact() {
        Contact contact = new Contact();
        contact.setEmail("test@gmail.com");
        contact.setMobileNum("1234567890");
        return contact;
    }
}
