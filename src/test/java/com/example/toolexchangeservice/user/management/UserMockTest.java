package com.example.toolexchangeservice.user.management;

import com.example.toolexchangeservice.config.exception.EmailAlreadyExistsException;
import com.example.toolexchangeservice.config.exception.UsernameAlreadyExistsException;
import com.example.toolexchangeservice.model.entity.UserDetail;
import com.example.toolexchangeservice.repository.UserRepository;
import com.example.toolexchangeservice.service.UserManagementService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UserMockTest {

    @Mock
    private UserRepository repoMock;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserManagementService service;

    @Test()
    public void testAddExistingUser() {
        UserDetail userDetail = new UserDetail();
        userDetail.setFirstName("Marko");
        userDetail.setLastName("Jerkić");
        userDetail.setUsername("marko");
        userDetail.setEmail("marko@marko.com");

        Mockito.when(repoMock.existsByEmail(userDetail.getEmail())).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> service.saveUser(userDetail));
    }

    @Test()
    public void testAddExistingUsernameUser() {
        UserDetail userDetail = new UserDetail();
        userDetail.setFirstName("Marko");
        userDetail.setLastName("Jerkić");
        userDetail.setUsername("marko");
        userDetail.setEmail("marko@marko.com");

        Mockito.when(repoMock.existsByUsername(userDetail.getUsername())).thenReturn(true);

        assertThrows(UsernameAlreadyExistsException.class, () -> service.saveUser(userDetail));
    }
}
