package accounts.web;

import accounts.internal.StubAccountManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import rewards.internal.account.Account;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A JUnit test case testing the AccountController.
 */
public class AccountControllerTests {

	private static final long expectedAccountId = StubAccountManager.TEST_ACCOUNT_ID;
	private static final String expectedAccountNumber = StubAccountManager.TEST_ACCOUNT_NUMBER;

	private AccountController controller;

	@BeforeEach
	public void setUp() throws Exception {
		controller = new AccountController(new StubAccountManager());
	}

	@Test
	//@Disabled
	public void testHandleListRequest() {
		List<Account> accounts = controller.accountList();

		// Non-empty list containing the one and only test account
		assertNotNull(accounts);
		assertEquals(1, accounts.size());

		// Validate that account
		Account account = accounts.get(0);
		assertEquals(expectedAccountId, (long) account.getEntityId());
		assertEquals(expectedAccountNumber, account.getNumber());
	}

	@Test
	//@Disabled
	public void testHandleDetailsRequest() {
		// - It will take one parameter - use "expectedAccountId" defined above
		// - It will return an Account
		Account account = controller.accountDetails(expectedAccountId);
		// - The account is not null
		// - The account id matches "expectedAccountId" defined above
		// - The account number matches "expectedAccountNumber" defined above
		assertTrue(account != null && account.getEntityId().equals(expectedAccountId) && account.getNumber().equals(expectedAccountNumber));
	}

}
