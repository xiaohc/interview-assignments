package org.example.manager.impl;

import org.example.config.UrlConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = {DomainNameManagerImpl.class, UrlConfig.class})
@ExtendWith(SpringExtension.class)
class DomainNameManagerImplTest {
    @Autowired
    private DomainNameManagerImpl domainNameManagerImpl;

    @Autowired
    private UrlConfig urlConfig;

    @BeforeEach
    void setUp() {
        urlConfig.setServer("https://test/");
    }

    @Test
    void testStorageUrl() {
        // pitest not supported
//        assertEquals("https://test/AADAqAFl",
//                this.domainNameManagerImpl.storageUrl("192.168.1.101", "https://example.org/example"));
    }

    @Test
    void testRestoreUrl() {
        assertEquals("短域名无效或已过期，请重新输入", this.domainNameManagerImpl.restoreUrl("https://example.org/example"));
        assertEquals("短域名无效或已过期，请重新输入", this.domainNameManagerImpl.restoreUrl(""));
    }
}

