import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static ArrayList<User> users = new ArrayList<>();
    static User currentUser = null;

    public static void main(String[] args) {

        while (true) {
            if (currentUser == null) {
                System.out.println("\n*** Welcome to Instagram ***");
                System.out.println("1. Sign up");
                System.out.println("2. Log in");
                System.out.println("3. Exit");
                System.out.print("\nYour selection: ");

                try {
                    int choice = scanner.nextInt();
                    scanner.nextLine();

                    switch (choice) {
                        case 1 -> register();
                        case 2 -> login();
                        case 3 -> {
                            System.out.println("Exiting...");
                            return;
                        }
                        default -> System.out.println("Invalid selection!");
                }
                }catch (InputMismatchException e){
                    System.out.println("Please enter number only!");
                    scanner.nextLine();
                }
            }
            else {
                System.out.println("\n--- Menu ---");
                System.out.println("1. Share post");
                System.out.println("2. View my posts");
                System.out.println("3. Search user");
                System.out.println("4. Log out");
                System.out.print("\nYour selection: ");

                try {
                    int choice = scanner.nextInt();
                    scanner.nextLine();

                    switch (choice) {
                        case 1 -> sharePost();
                        case 2 -> currentUser.viewPosts();
                        case 3 -> searchUser();
                        case 4 -> {
                            currentUser = null;
                            System.out.println("Logged out.");
                        }
                        default -> System.out.println("Invalid selection!");
                    }
                }catch (InputMismatchException e) {
                    System.out.println("Please enter numbers only!");
                    scanner.nextLine();
                }
            }
        }
    }

    static void register() {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine().trim();

        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Username or password cannot be blank!");
            return;
        }

        if (username.length() < 3 || username.length() > 12) {
            System.out.println("❌ Username must be between 3 and 12 characters!");
            return;
        }

        if (password.length() < 3 || password.length() >12) {
            System.out.println("❌ Password must be between 3 and 12 characters!");
            return;
        }

        for (User user : users) {
            if (user.username.equalsIgnoreCase(username)){
                System.out.println("This username is already taken! Try another one.");
                return;
            }
        }

        boolean hasLetter = false;
        boolean hasDigit = false;

        for (char c : password.toCharArray()){
            if (Character.isLetter(c)) {
                hasLetter = true;
            }
            if (Character.isDigit(c)) {
                hasDigit =true;
            }
        }

        if (!hasLetter || !hasDigit){
            System.out.println("❌ Password must contain both letters and numbers!");
            return;
        }

        User newUser = new User(username,password);
        users.add(newUser);
        System.out.println("✅ Registration successful, you can now log in");
    }

    static void login() {

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        for (User user : users) {
            if (user.username.equalsIgnoreCase(username) && user.password.equals(password)){
                currentUser = user;
                System.out.println("Login successful! Welcome, " + currentUser.username);
                return;
            }
        }
        System.out.println("Login failed! Username or password is incorrect.");
    }

    static void sharePost() {
        System.out.print("Post content: ");
        String content = scanner.nextLine();
        currentUser.addPost(content);
        System.out.println("Post shared!");
    }

    static void commentOnUserPost(User user) {
        if (user.posts.isEmpty()) {
            System.out.println("This user has no posts, no comments allowed");
            return;
        }

        System.out.println("\nWhich post would you like to comment on? (Enter the post number)");
        for (int i = 0; i< user.posts.size();i++){
            System.out.println((i+1)+ ". " + user.posts.get(i).content);
        }

        System.out.print("Selection: ");
        try {
            int postIndex = scanner.nextInt()-1;
            scanner.nextLine();

            if (postIndex >= 0 && postIndex < user.posts.size()){
                System.out.print("Your comment: ");
                String comment = scanner.nextLine();
                user.posts.get(postIndex).addComment(comment);
                System.out.println("Comment added!");
            }
            else {
                System.out.println("Invalid selection!");
            }
        }catch (InputMismatchException e) {
            System.out.println("Please enter numbers only!");
            scanner.nextLine();
            return;
        }

    }

    static void searchUser() {
        System.out.println("Enter the username you want to search for: ");
        String searchUsername = scanner.nextLine();

        for (User user : users) {
            if (user.username.equalsIgnoreCase(searchUsername)){
                System.out.println("\n" + user.username + "'s profile: ");
                user.viewPosts();
                if (user.posts.isEmpty()) {
                    System.out.println("Has not posted yet.");
                }
                else {
                    System.out.print("\nWant to comment on a post? (yes/no): ");
                    String answer = scanner.nextLine();
                    if (answer.equalsIgnoreCase("yes")) {
                        commentOnUserPost(user);
                    }
                }
                return;
            }
        }
        System.out.println("No such user found.");
        System.out.println("Returning to main menu...");
    }
}