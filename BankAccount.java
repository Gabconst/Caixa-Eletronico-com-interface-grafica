public class BankAccount {
    private String user;
    private String password;
    private double balance;

    public BankAccount(String user, String password, double balance) {
        this.user = user;
        this.password = password;
        this.balance = balance;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            return true;
        } else {
            return false;
        }
    }

    public boolean authenticate(String user, String password) {
        return this.user.equals(user) && this.password.equals(password);
    }

    @Override
    public String toString() {
        return user + "," + password + "," + balance;
    }

    public static BankAccount fromString(String accountData) {
        String[] parts = accountData.split(",");
        String user = parts[0];
        String password = parts[1];
        double balance = Double.parseDouble(parts[2]);
        return new BankAccount(user, password, balance);
    }
}