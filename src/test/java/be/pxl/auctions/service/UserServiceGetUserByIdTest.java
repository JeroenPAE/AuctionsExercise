package be.pxl.auctions.service;

import be.pxl.auctions.dao.UserDao;
import be.pxl.auctions.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

public class UserServiceGetUserByIdTest {
	private static final long USER_ID = 5L;

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
	public void returnsNullWhenNoUserWithGivenIdFound() {
		// Hier word gezegd welke waarde een bepaalde methode van die doa moet terug geven.
		when(userDao.findUserById(USER_ID)).thenReturn(null);
		// En daarna kan dit dan worden getest.
		assertNull(userService.getUserById(USER_ID));
	}

	@Test
	public void returnsUserWhenUserFoundWithGivenId() {
		// Hier word gezegd welke waarde een bepaalde methode van die doa moet terug geven.
		when(userDao.findUserById(USER_ID)).thenReturn(user);
		// En daarna kan dit dan worden getest.
		assertEquals(user, userService.getUserById(USER_ID));
	}
}
