package be.pxl.auctions.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserGetAgeTest {

	private final int YEARS = 15;

	@Test
	public void returnsCorrectAgeWhenHavingBirthdayToday() {
		User user = new User();
		user.setDateOfBirth(LocalDate.now().minusYears(YEARS));

		assertEquals(user.getAge(), YEARS);
	}

	@Test
	public void returnsCorrectAgeWhenHavingBirthdayTomorrow() {
		User user = new User();
		user.setDateOfBirth(LocalDate.now().minusYears(YEARS).plusDays(1));

		assertEquals(user.getAge(), YEARS -1);
	}

	@Test
	public void returnsCorrectAgeWhenBirthdayWasYesterday() {
		User user = new User();
		user.setDateOfBirth(LocalDate.now().minusYears(YEARS).minusDays(1));
		assertEquals(user.getAge(), YEARS);
	}

}
