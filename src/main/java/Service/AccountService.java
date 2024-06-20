package Service;

import DAO.AccountDAO;
import Model.Account;

import java.util.Optional;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        this(new AccountDAO());
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Optional<Account> registerAccount(Account account) {
        if (account.getUsername() != null && !account.getUsername().isEmpty() &&
            account.getPassword() != null && !account.getPassword().isEmpty() &&
            account.getPassword().length() >= 4) {
            return accountDAO.registerAccount(account);
        }
        return Optional.empty();
    }

    public Optional<Account> loginAccount(String username, String password) {
        if (username != null && !username.isEmpty() &&
            password != null && !password.isEmpty()) {
            return accountDAO.loginAccount(username, password);
        }
        return Optional.empty();
    }
}
