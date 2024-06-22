package DAO;

import java.util.Optional;

import Model.Account;

public interface AccountDAOInterface {

    // Register a new account
    Optional<Account> registerAccount(Account account);

    // Login user
    Optional<Account> loginAccount(String username, String password);

}