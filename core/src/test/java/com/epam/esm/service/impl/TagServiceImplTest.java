package com.epam.esm.service.impl;

import com.epam.esm.config.H2DataBaseTestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = H2DataBaseTestConfig.class)
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class TagServiceImplTest {

    @Test
    void save() {
    }
}