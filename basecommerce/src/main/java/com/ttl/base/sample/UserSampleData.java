package com.ttl.base.sample;

import com.ttl.core.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class UserSampleData implements SampleData {

    @Resource
    private UserService userService;

    @Override
    public void createData() {

    }
}
