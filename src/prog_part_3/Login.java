package prog_part_3;

/*
 * GROK AI was used to help me fix complex bug/errors in my code,
 * and references were made to the following online resources:
 * - W3Schools Java Tutorial: https://www.w3schools.com/java
 * - freeCodeCamp - Java Unit Testing: https://www.freecodecamp.org/news/java-unit-testing/
 * - GeeksforGeeks Java Programming: https://www.geeksforgeeks.org/java/
 * - Oracle Java Documentation: https://docs.oracle.com/javase/tutorial/
 */


public class Login {

    private String registeredUsername;
    private String registeredPassword;
    private String firstName;
    private String lastName;

    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    public boolean checkPasswordComplexity(String password) {
        return password.length() >= 8 &&
               password.matches(".*[A-Z].*") &&
               password.matches(".*[0-9].*") &&
               password.matches(".*[!@#$%^&*()_+=<>?/{}|~`\\[\\]-].*");
    }

    public boolean checkCellPhoneNumber(String phone) {
        return phone.startsWith("+27") && phone.length() <= 12 && phone.substring(3).matches("\\d{9,10}");
    }

    public String registerUser(String username, String password, String firstName, String lastName) {
        if (!checkUserName(username)) {
            return "Username is incorrectly formatted, please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        this.registeredUsername = username;
        this.registeredPassword = password;
        this.firstName = firstName;
        this.lastName = lastName;
        return "Registration successful";
    }

    public boolean loginUser(String enteredUsername, String enteredPassword) {
        return enteredUsername.equals(registeredUsername) && enteredPassword.equals(registeredPassword);
    }

    public String returnLoginStatus(String enteredUsername, String enteredPassword) {
        if (loginUser(enteredUsername, enteredPassword)) {
            return "Welcome " + firstName + " " + lastName + ", it is great to see you.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}