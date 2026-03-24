package com.qode.mutablescanner.service.impl;

import com.qode.mutablescanner.object.User;
import com.qode.mutablescanner.service.IContactService;
import com.qode.mutablescanner.service.IUserDeviceService;
import com.qode.mutablescanner.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserService implements IUserService {

    private IContactService contactService;

    private final IUserDeviceService userDeviceService;

    @Autowired
    public UserService(IContactService contactService, IUserDeviceService userDeviceService) {
        this.contactService = contactService;
        this.userDeviceService = userDeviceService;
    }

    @Override
    public User getUser() {
        User user = new User();
        user.setUserId("user001");
        user.setName("Test User");
        user.setContact(contactService.getContact());
        user.setUserDevices(userDeviceService.getUserDevices());
        return user;
    }

}
