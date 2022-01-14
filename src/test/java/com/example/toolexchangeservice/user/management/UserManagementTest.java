package com.example.toolexchangeservice.user.management;

import com.example.toolexchangeservice.model.entity.UserDetail;
import com.example.toolexchangeservice.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserManagementTest {
    @Autowired
    private UserRepository userManagementService;

    @Test
    public void testNewUser() {
        UserDetail userDetail = new UserDetail();
        userDetail.setFirstName("Marko");
        userDetail.setLastName("Jerkić");
        userDetail.setUsername("marko");
        userDetail.setEmail("marko@marko.com");
        UserDetail savedUser = this.userManagementService.save(userDetail);

        Assertions.assertNotNull(savedUser);
    }
}
