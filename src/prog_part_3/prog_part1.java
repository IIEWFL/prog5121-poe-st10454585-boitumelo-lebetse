package prog_part_3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class prog_part1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login login = new Login();
        Message message = new Message();

        System.out.println("=== User Registration ===");

        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();

        String username;
        while (true) {
            System.out.print("Enter Username: ");
            username = scanner.nextLine();
            if (login.checkUserName(username)) {
                System.out.println("Username successfully captured.");
                break;
            } else {
                System.out.println("Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.");
            }
        }

        String password;
        while (true) {
            System.out.print("Enter Password: ");
            password = scanner.nextLine();
            if (login.checkPasswordComplexity(password)) {
                System.out.println("Password successfully captured.");
                break;
            } else {
                System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
            }
        }

        String cellphone;
        while (true) {
            System.out.print("Enter Cellphone Number (+27...): ");
            cellphone = scanner.nextLine();
            if (login.checkCellPhoneNumber(cellphone)) {
                System.out.println("Cellphone number successfully captured.");
                break;
            } else {
                System.out.println("Cellphone number is incorrectly formatted. It must start with +27 and be followed by 9-10 digits (max 12 chars total).");
            }
        }

        String registrationResult = login.registerUser(username, password, firstName, lastName);
        System.out.println(registrationResult);

        if (registrationResult.equals("Registration successful")) {
            System.out.println("\n=== Login ===");
            System.out.print("Enter Username: ");
            String enteredUsername = scanner.nextLine();

            System.out.print("Enter Password: ");
            String enteredPassword = scanner.nextLine();

            String loginStatus = login.returnLoginStatus(enteredUsername, enteredPassword);
            System.out.println(loginStatus);

            if (loginStatus.startsWith("Welcome")) {
                System.out.println("\nWelcome to QuickChat.");
                int choice;
                do {
                    System.out.println("\nMenu:");
                    System.out.println("1) Send Messages");
                    System.out.println("2) Show recently sent messages");
                    System.out.println("3) Display sender and recipient of all sent messages");
                    System.out.println("4) Display longest sent message");
                    System.out.println("5) Search for a message ID");
                    System.out.println("6) Search for messages by recipient");
                    System.out.println("7) Delete a message by hash");
                    System.out.println("8) Display report");
                    System.out.println("9) Quit");
                    System.out.print("Enter your choice: ");
                    choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    if (choice == 1) {
                        System.out.print("Enter the number of messages to send: ");
                        int numMessages = scanner.nextInt();
                        scanner.nextLine();
                        List<String> messageDetailsList = new ArrayList<>();
                        for (int i = 0; i < numMessages; i++) {
                            String msgId = message.generateMessageID();
                            System.out.println("Generated Message ID: " + msgId);

                            System.out.print("Enter Recipient Number (starts with +27, max 12 chars): ");
                            String recipient = scanner.nextLine();
                            while (!message.checkRecipientCell(recipient)) {
                                System.out.print("Recipient number invalid, enter again (starts with +27, max 12 chars): ");
                                recipient = scanner.nextLine();
                            }

                            System.out.print("Enter Message (max 250 chars): ");
                            String msg = scanner.nextLine();

                            String msgHash = message.createMessageHash(msgId, msg);
                            System.out.print("Send (1), Discard (2), Store (3): ");
                            int action = scanner.nextInt();
                            scanner.nextLine(); // Consume newline
                            String result = message.sendMessage(msgId, recipient, msg, msgHash, action);
                            JOptionPane.showMessageDialog(null, result);
                            if (!result.contains("exceeds")) {
                                messageDetailsList.add(message.printMessages().trim());
                            }
                        }
                        if (!messageDetailsList.isEmpty()) {
                            JOptionPane.showMessageDialog(null, String.join("\n", messageDetailsList));
                        }
                        System.out.println("Total messages sent: " + message.returnTotalMessages());
                    } else if (choice == 2) {
                        String recent = message.printMessages();
                        JOptionPane.showMessageDialog(null, recent.isEmpty() ? "No recently sent messages." : recent);
                    } else if (choice == 3) {
                        String display = message.displaySenderRecipient();
                        JOptionPane.showMessageDialog(null, display.isEmpty() ? "No sent messages." : display);
                    } else if (choice == 4) {
                        String longest = message.displayLongestMessage();
                        JOptionPane.showMessageDialog(null, longest.isEmpty() ? "No sent messages." : longest);
                    } else if (choice == 5) {
                        System.out.print("Enter Message ID to search: ");
                        String msgId = scanner.nextLine();
                        String result = message.searchByMessageID(msgId);
                        JOptionPane.showMessageDialog(null, result.isEmpty() ? "Message ID not found." : result);
                    } else if (choice == 6) {
                        System.out.print("Enter Recipient Number to search: ");
                        String recipient = scanner.nextLine();
                        String result = message.searchByRecipient(recipient);
                        JOptionPane.showMessageDialog(null, result.isEmpty() ? "No messages found." : result);
                    } else if (choice == 7) {
                        System.out.print("Enter Message Hash to delete: ");
                        String msgHash = scanner.nextLine();
                        String result = message.deleteMessageByHash(msgHash);
                        JOptionPane.showMessageDialog(null, result);
                    } else if (choice == 8) {
                        String report = message.displayReport();
                        JOptionPane.showMessageDialog(null, report.isEmpty() ? "No sent messages." : report);
                    }
                } while (choice != 9);
            }
        }

        scanner.close();
    }
}
