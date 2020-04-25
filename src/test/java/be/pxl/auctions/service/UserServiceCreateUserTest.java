package be.pxl.auctions.service;

import be.pxl.auctions.dao.UserDao;
import be.pxl.auctions.model.User;
import be.pxl.auctions.util.exception.DuplicateEmailException;
import be.pxl.auctions.util.exception.InvalidDateException;
import be.pxl.auctions.util.exception.InvalidEmailException;
import be.pxl.auctions.util.exception.RequiredFieldException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

public class UserServiceCreateUserTest {


    //Mock word gebruikt om aan te duiden welke doa we willen "overnemen"
    @Mock
    private UserDao userDao;

    //Met InjectMocks gaan we zeggen in welke classen we willen dat die doa word overgenomen.
    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        user = new User();
        user.setFirstName("Mark");
        user.setLastName("Zuckerberg");
        user.setDateOfBirth(LocalDate.of(1989, 5, 3));
        user.setEmail("mark@facebook.com");
    }

	@Test
    public void throwsCorrectRequiredFieldExceptionWhenMissingFirstName(){
        try {
            user.setFirstName(null);
            userService.createUser(user);
            fail();
        } catch (RequiredFieldException rfe){
            assertEquals(rfe.getMessage(), "[FirstName] is required.");
        } catch (Exception e){
            fail();
        }
    }

    @Test
    public void throwsCorrectRequiredFieldExceptionWhenMissingLastName(){
        try {
            user.setLastName(null);
            userService.createUser(user);
            fail();
        } catch (RequiredFieldException rfe){
            assertEquals(rfe.getMessage(), "[LastName] is required.");
        } catch (Exception e){
            fail();
        }
    }

    @Test
    public void throwsCorrectRequiredFieldExceptionWhenMissingEmail(){
        try {
            user.setEmail(null);
            userService.createUser(user);
            fail();
        } catch (RequiredFieldException rfe){
            assertEquals(rfe.getMessage(), "[Email] is required.");
        } catch (Exception e){
            fail();
        }
    }

    @Test
    public void throwsRequiredFieldExceptionWhenEmailAddressIsWrong(){
        try {
            user.setEmail("Dit is niet juist");
            userService.createUser(user);
            fail();
        } catch (InvalidEmailException iee){
            assertEquals(iee.getMessage(), "[" + user.getEmail() + "] is not a valid email.");
        } catch (Exception e){
            fail();
        }
    }

    @Test
    public void throwsCorrectRequiredFieldExceptionWhenMissingDateOfBirth(){
        try {
            user.setDateOfBirth(null);
            userService.createUser(user);
            fail();
        } catch (RequiredFieldException rfe){
            assertEquals(rfe.getMessage(), "[DateOfBirth] is required.");
        } catch (Exception e){
            fail();
        }
    }

    @Test
    public void throwsInvalidDateExceptionWhenDateOfBirthIsInTheFuture(){
        try {
            user.setDateOfBirth(LocalDate.now().plusDays(1));
            userService.createUser(user);
            fail();
        } catch (InvalidDateException rfe){
            assertEquals(rfe.getMessage(), "DateOfBirth cannot be in the future.");
        } catch (Exception e){
            fail();
        }
    }

    @Test
    public void throwsDuplicateEmailExceptionWhenEmailAddressIsAlreadyUsed(){

        when(userDao.findUserByEmail(user.getEmail())).thenReturn(user);

        try {
            userService.createUser(user);
            fail();
        } catch (DuplicateEmailException rfe){
            assertEquals(rfe.getMessage(), "Email [" + user.getEmail() + "] is already in use.");
        } catch (Exception e){
            fail();
        }
    }

    @Test
    public void returnsUserObjectIfAllFieldsFilledCorrect(){
        when(userDao.findUserByEmail(user.getEmail())).thenReturn(null);
        when(userDao.saveUser(user)).thenReturn(user);
        User returnUser = null;
        try {
            returnUser = userService.createUser(user);
        } catch (Exception e) {
            fail();
        }

        assertEquals(user, returnUser);
    }
}
