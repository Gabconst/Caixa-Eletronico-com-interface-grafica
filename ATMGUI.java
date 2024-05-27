import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ATMGUI {
    private JFrame frame;
    private JPanel panel;
    private JTextField userText;
    private JPasswordField passwordText;
    private JLabel balanceLabel;
    private JTextField amountText;
    private BankAccount currentAccount;
    private AccountManager accountManager;

    public ATMGUI() {
        accountManager = new AccountManager("accounts.txt");
    }

    public void createAndShowGUI() {
        frame = new JFrame("ATM");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("User:");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 50, 165, 25);
        panel.add(passwordText);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 80, 25);
        panel.add(loginButton);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                authenticateUser();
            }
        });

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(100, 80, 100, 25);
        panel.add(registerButton);
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });

        balanceLabel = new JLabel("Balance: ");
        balanceLabel.setBounds(10, 110, 300, 25);
        panel.add(balanceLabel);

        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setBounds(10, 140, 80, 25);
        panel.add(amountLabel);

        amountText = new JTextField(20);
        amountText.setBounds(100, 140, 165, 25);
        panel.add(amountText);

        JButton depositButton = new JButton("Deposit");
        depositButton.setBounds(10, 170, 100, 25);
        panel.add(depositButton);
        depositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                depositAmount();
            }
        });

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setBounds(120, 170, 100, 25);
        panel.add(withdrawButton);
        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                withdrawAmount();
            }
        });
    }

    private void authenticateUser() {
        String user = userText.getText();
        String password = new String(passwordText.getPassword());

        try {
            BankAccount account = accountManager.authenticate(user, password);
            if (account != null) {
                currentAccount = account;
                updateBalance();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error reading account data", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registerUser() {
        String user = userText.getText();
        String password = new String(passwordText.getPassword());

        try {
            if (accountManager.register(user, password)) {
                JOptionPane.showMessageDialog(frame, "User registered successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "User already exists", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error writing account data", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateBalance() {
        balanceLabel.setText("Balance: " + currentAccount.getBalance());
    }

    private void depositAmount() {
        double amount = Double.parseDouble(amountText.getText());
        currentAccount.deposit(amount);
        try {
            accountManager.updateAccount(currentAccount);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error updating account data", "Error", JOptionPane.ERROR_MESSAGE);
        }
        updateBalance();
    }

    private void withdrawAmount() {
        double amount = Double.parseDouble(amountText.getText());
        if (currentAccount.withdraw(amount)) {
            try {
                accountManager.updateAccount(currentAccount);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Error updating account data", "Error", JOptionPane.ERROR_MESSAGE);
            }
            updateBalance();
        } else {
            JOptionPane.showMessageDialog(frame, "Insufficient funds", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}