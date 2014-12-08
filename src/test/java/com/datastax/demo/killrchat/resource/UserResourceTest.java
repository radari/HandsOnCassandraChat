package com.datastax.demo.killrchat.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.datastax.demo.killrchat.model.UserModel;
import com.datastax.demo.killrchat.resource.model.UserPasswordModel;
import com.datastax.demo.killrchat.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class UserResourceTest {

    @InjectMocks
    private UserResource resource;

    @Mock
    private UserService service;

    @Test
    public void should_create_user() throws Exception {
        //Given
        final UserModel userModel = new UserModel("jdoe", "pass", "John", "DOE", "johnny", "jdoe@gmail.com", "bio");

        //When
        resource.createUser(userModel);

        //Then
        verify(service).createUser(userModel);
    }

    @Test
    public void should_list_users_from_beginning() throws Exception {
        //Given
        final UserModel userModel = new UserModel("jdoe", "pass", "John", "DOE", "johnny", "jdoe@gmail.com", "bio");

        when(service.listUsers("", UserResource.DEFAULT_CHAT_ROOMS_LIST_FETCH_SIZE)).thenReturn(Arrays.asList(userModel));

        //When
        final List<UserModel> found = resource.listUsers(null, 0);

        //Then
        assertThat(found).containsExactly(userModel);
    }

    @Test
    public void should_list_users_from_login() throws Exception {
        //Given
        final UserModel userModel = new UserModel("jdoe", "pass", "John", "DOE", "johnny", "jdoe@gmail.com", "bio");

        when(service.listUsers("a", 12)).thenReturn(Arrays.asList(userModel));

        //When
        final List<UserModel> found = resource.listUsers("a", 12);

        //Then
        assertThat(found).containsExactly(userModel);
    }


    @Test
    public void should_login() throws Exception {
        //Given
        final UserPasswordModel userPasswordModel = new UserPasswordModel("jdoe", "pass");

        //When
        resource.login(userPasswordModel);

        //Then
        verify(service).validatePasswordForUser("jdoe","pass");
    }

    @Test
    public void should_change_user_password() throws Exception {
        //When
        final UserPasswordModel userPasswordModel = new UserPasswordModel("jdoe", "pass","new_pass");

        resource.changeUserPassword(userPasswordModel);

        //Then
        verify(service).changeUserPassword("jdoe", "pass", "new_pass");
    }
}