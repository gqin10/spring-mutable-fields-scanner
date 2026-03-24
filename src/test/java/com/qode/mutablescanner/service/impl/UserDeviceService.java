package com.qode.mutablescanner.service.impl;

import com.qode.mutablescanner.object.DeviceType;
import com.qode.mutablescanner.object.UserDevice;
import com.qode.mutablescanner.service.IUserDeviceService;

import java.util.ArrayList;
import java.util.List;

public class UserDeviceService implements IUserDeviceService {

    @Override
    public List<UserDevice> getUserDevices() {
        List<UserDevice> list = new ArrayList<>();
        UserDevice userDevice = new UserDevice();
        userDevice.setDeviceId("device001");
        userDevice.setDeviceType(DeviceType.MOBILE);
        userDevice.setBrand("Apple");
        list.add(userDevice);
        return list;
    }

}
