package com.backend;

import com.backend.profile.model.Profile;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.util.List;

@Component
public class DatabaseInitializer implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        List<Profile> profiles = new ObjectMapper().readValue(
                ResourceUtils.getFile("classpath:employee.json"),
                new TypeReference<List<Profile>>() {
                });
        profiles.stream().forEach(e -> System.out.println(e.getName() + ":" + e.getJoindate()));
    }
}
