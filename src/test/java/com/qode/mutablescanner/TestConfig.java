package com.qode.mutablescanner;

import com.qode.mutablescanner.service.IContactService;
import com.qode.mutablescanner.service.IUserDeviceService;
import com.qode.mutablescanner.service.IUserService;
import com.qode.mutablescanner.service.impl.ContactService;
import com.qode.mutablescanner.service.impl.UserDeviceService;
import com.qode.mutablescanner.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

    @Autowired
    private ConfigurableListableBeanFactory beanFactory;

    @Bean
    public IContactService contactService() {
        return new ContactService();
    }

    @Bean
    public IUserService userService() {
        return new UserService(contactService(), userDeviceService());
    }

    @Bean
    public IUserDeviceService userDeviceService() {
        return new UserDeviceService();
    }

    @Bean
    public MutableScannerPostProcessor mutableScannerPostProcessor() {
        return new MutableScannerPostProcessor(beanFactory);
    }

}
