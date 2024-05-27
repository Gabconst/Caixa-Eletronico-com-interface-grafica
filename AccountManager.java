import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class AccountManager {
    private String fileName;
    private Map<String, BankAccount> accounts;

    public AccountManager(String fileName) {
        this.fileName = fileName;
        accounts = new HashMap<>();
        loadAccounts();
    }

    private void loadAccounts() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                BankAccount account = BankAccount.fromString(line);
                accounts.put(account.getUser(), account);
            }
        } catch (IOException e) {
            System.err.println("Error loading accounts: " + e.getMessage());
        }
    }

    public boolean register(String user, String password) throws IOException {
        if (accounts.containsKey(user)) {
            return false;
        }

        BankAccount newAccount = new BankAccount(user, password, 0.0);
        accounts.put(user, newAccount);
        saveAccounts();
        return true;
    }

    public BankAccount authenticate(String user, String password) throws IOException {
        BankAccount account = accounts.get(user);
        if (account != null && account.authenticate(user, password)) {
            return account;
        }
        return null;
    }

    public void updateAccount(BankAccount account) throws IOException {
        accounts.put(account.getUser(), account);
        saveAccounts();
    }

    private void saveAccounts() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (BankAccount account : accounts.values()) {
                writer.write(account.toString());
                writer.newLine();
            }
        }
    }
}