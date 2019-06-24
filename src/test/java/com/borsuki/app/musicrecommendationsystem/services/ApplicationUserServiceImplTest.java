package com.borsuki.app.musicrecommendationsystem.services;

import com.borsuki.app.musicrecommendationsystem.entities.ApplicationUser;
import com.borsuki.app.musicrecommendationsystem.exceptions.ApplicationException;
import com.borsuki.app.musicrecommendationsystem.exceptions.NotUniqueValueException;
import com.borsuki.app.musicrecommendationsystem.repositories.ApplicationUserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class ApplicationUserServiceImplTest {

    @InjectMocks
    private ApplicationUserServiceImpl applicationUserService;
    @Mock
    private ApplicationUserRepository applicationUserRepository;
    @Spy
    private BCryptPasswordEncoder passwordEncoder;

    private ApplicationUser applicationUser;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        applicationUser = new ApplicationUser();
        applicationUser.setUsername("username");
        applicationUser.setPassword("password");
        applicationUser.setEmail("example@gmail.com");
    }

    @Test
    public void loadByUsername_Success() {
        //mock
        doReturn(applicationUser).when(applicationUserRepository).findByUsername(applicationUser.getUsername());

        //when
        ApplicationUser found = applicationUserService.loadUserByUsername(applicationUser.getUsername());

        //then
        assertThat(found.getUsername(), is(applicationUser.getUsername()));
    }

    @Test
    public void loadByUsername_Failure() {
        //mock
        doThrow(UsernameNotFoundException.class).when(applicationUserRepository).findByUsername(applicationUser.getUsername());

        //when-then
        assertThrows(UsernameNotFoundException.class, () -> {
            applicationUserService.loadUserByUsername(applicationUser.getUsername());
        });
    }

    @Test
    public void addUser_UserExists() {
        //mock
        doReturn(true).when(applicationUserRepository).existsByUsername(applicationUser.getUsername());
        doReturn(false).when(applicationUserRepository).existsByEmail(applicationUser.getEmail());

        //when-then
        assertThrows(NotUniqueValueException.class, () -> {
            applicationUserService.addUser(applicationUser);
        });
    }

    @Test
    public void addUser_EmailExists() {
        //mock
        doReturn(false).when(applicationUserRepository).existsByUsername(applicationUser.getUsername());
        doReturn(true).when(applicationUserRepository).existsByEmail(applicationUser.getEmail());

        //when
        assertThrows(NotUniqueValueException.class, () -> {
            applicationUserService.addUser(applicationUser);
        });
    }

    @Test
    public void addUser_Success() throws ApplicationException {
        //mock
        doReturn(false).when(applicationUserRepository).existsByUsername(applicationUser.getUsername());
        doReturn(false).when(applicationUserRepository).existsByEmail(applicationUser.getEmail());
        doCallRealMethod().when(passwordEncoder).encode(any(String.class));

        //when
        applicationUserService.addUser(applicationUser);

        //then
        verify(applicationUserRepository, times(1)).save(applicationUser);
    }
}
