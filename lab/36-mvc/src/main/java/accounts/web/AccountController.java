package accounts.web;

import accounts.AccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import rewards.internal.account.Account;

import java.util.List;

/**
 * A Spring MVC REST Controller handling requests to retrieve Account information.
 *
 * Note that some of the Account related classes are imported from the
 * rewards-db project:
 *
 * -Domain objects: Account and Beneficiary
 * -Service layer: AccountManager interface
 * -Repository layer: AccountRepository interface
 *
 */
@RestController
public class AccountController {

	private final AccountManager accountManager;

	/**
	 * Creates a new AccountController with a given account manager.
	 */
	@Autowired
	public AccountController(AccountManager accountManager) {
		this.accountManager = accountManager;
	}

	/**
	 * Return a list of all accounts
	 */
	@GetMapping("/accounts")
	public List<Account> accountList() {

		// - Use "accountManger" object to get all accounts
		// - Recompile this class if necessary, and wait for the application to restart (via devtools)
		// - From the home page, click the link - this should now work
		// - If you prefer, access http://localhost:8080/accounts using curl or Postman

		return this.accountManager.getAllAccounts();
	}

	// - Call the method accountDetails().
	// - Annotate to define URL mapping /accounts/{entityId}
	//   this method will respond to.
	// - Use a method parameter to obtain the URI template parameter
	//   needed to retrieve an account.
	// - Use the accountManager to obtain an account. This is the value to return
	// - Save all work.

	@GetMapping("/accounts/{entityId}")
	public Account accountDetails(@PathVariable Long entityId) {
		return this.accountManager.getAccount(entityId);
	}


	// TODO-10b: If AccountControllerTests.testHandleDetailsRequest()
	//  fails, fix errors before moving on

	// TODO-11: Run the application
	// - You should now be able to invoke http://localhost:8080/accounts/N
	//   where N is 0-20 and get a response. You can use curl, Postman or
	//   your browser to do this.

}
