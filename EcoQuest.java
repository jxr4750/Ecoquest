import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.util.Collections;
import java.util.Comparator;

public class EcoQuest {
    private static ArrayList<User> users = new ArrayList<>();
    
    public static void main(String[] args) {
        Scanner Scanner = new Scanner(System.in);
        //Created Main Statement/Structure, made interface/user input - Joseph
        //Load up the files, if it doesnt exist, then create
        String username;
        int Decision = 0;
        int pin = 0000;
        int region = 0;

        // ADDED: ArrayList to hold users-Saish
        ArrayList<User> users = loadUsers();
        
        //Start, Select Region
        System.out.println("Hello, Welcome to EcoQuest ♻️ \nPlease select your region:\n1.Dallas, Texas (Beta)\n");
        region = Scanner.nextInt(); //Really a placeholder for now, we only have 1 region. 

        //Enter User details 
        System.out.println("Username:");
        username = Scanner.next();

        System.out.println("Please enter your 4 digit pin: ");
        pin = Scanner.nextInt();

        // ADDED: Simple login check using users list-Saish
        User currentUser = null;
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPin() == pin) {
                currentUser = u;
                break;
            }
        }

        // ADDED: Create new user if not found-Saish
        if (currentUser == null) {
            System.out.println("We don't recognize this username. Would you like to create an account? (y/n)");
            char ch = Scanner.next().charAt(0);
            if (ch == 'y' || ch == 'Y') {
                System.out.print("First name: ");
                String first = Scanner.next();
                System.out.print("Last name: ");
                String last = Scanner.next();
                System.out.print("Are you an admin? (y/n): ");
                boolean adminFlag = Scanner.next().equalsIgnoreCase("y");
                currentUser = new User(first, last, username, pin, 0, adminFlag);
                users.add(currentUser);
                saveUsers(users);
                System.out.println("✅ Account created successfully!");
            } else {
                System.out.println("Exiting...");
                Scanner.close();
                return;
            }
        }

        if (currentUser.isAdmin()) {
            System.out.println("\n--- ADMIN LOGIN SUCCESSFUL ---");
            // Call the admin menu
            adminMenu(Scanner, users); 
            saveUsers(users);
        }
        
        //Regular Log in: Select Region, Display LeaderBoard, Add activity, Points shop(?), "battle pass"(?), history, account management
        System.out.println("Welcome back " + currentUser.getFirstName() + " " + currentUser.getLastName());

        // ADDED: Reset Decision for user menu-Saish
        Decision = 0;

        while (Decision != 3) { // ADDED: loop for user menu-Saish
            System.out.println("\n--- USER MENU ---");
            System.out.println("1. Leaderboard");
            System.out.println("2. Add Daily Task (Add Points)");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            Decision = Scanner.nextInt();

            switch(Decision){
                case 1:
                    displayLeaderboard(users); // ADDED-Saish
                    break;
                    
                case 2:
                    addPointsMenu(Scanner, currentUser); // ADDED-Saish
                    saveUsers(users);
                    break;

                case 3:
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Default Statement");
                    break;
            }//close regular switch
        }

        saveUsers(users); // ADDED: save before closing-Saish
        Scanner.close();
    }//main statement close

    public enum Points { //Created Enumerated list for points - Joseph
        RECYCLED(10),
        CLEAN_STREET(50),
        VOLUNTEER(100);

        private final int points;
        Points(int points) { this.points = points; }
        public int addPoints() { return points; }
        //How to add points: int addPoints = Points.CLEAN_STREET.addPoints();

    }

        // ADDED: Load users from file-Saish
    public static ArrayList<User> loadUsers() {
        ArrayList<User> users = new ArrayList<>();
        File file = new File("users.txt");
        if (!file.exists()) {
            System.out.println("No user file found. A new file will be created later.");
            return users;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    users.add(new User(
                        parts[0], parts[1], parts[2],
                        Integer.parseInt(parts[3]),
                        Integer.parseInt(parts[4]),
                        Boolean.parseBoolean(parts[5])
                    ));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading users file.");
        }
        return users;
    }

    // ADDED: Save users to file-Saish
    public static void saveUsers(ArrayList<User> users) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("users.txt"))) {
            for (User u : users) {
                bw.write(u.getFirstName() + "," + u.getLastName() + "," +
                         u.getUsername() + "," + u.getPin() + "," +
                         u.getScore() + "," + u.isAdmin());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving users.");
        }
    }

    // ADDED: Display leaderboard-Saish
    public static void displayLeaderboard(ArrayList<User> users) {
        System.out.println("\n🏆 ECOQUEST LEADERBOARD 🏆");
        ArrayList<User> sorted = new ArrayList<>(users);
        Collections.sort(sorted, Comparator.comparingInt(User::getScore).reversed());

        for (int i = 0; i < sorted.size(); i++) {
            User u = sorted.get(i);
            System.out.printf("%d. %-15s %5d pts\n", i + 1, u.getUsername(), u.getScore());
        }
    }

    // ADDED: Menu for adding points-Saish
    public static void addPointsMenu(Scanner sc, User user) {
        System.out.println("Select a task:");
        System.out.println("1. Recycled (+10)");
        System.out.println("2. Clean Street (+50)");
        System.out.println("3. Volunteer (+100)");
        int choice = sc.nextInt();
        int earned = 0;
        switch (choice) {
            case 1 -> earned = Points.RECYCLED.addPoints();
            case 2 -> earned = Points.CLEAN_STREET.addPoints();
            case 3 -> earned = Points.VOLUNTEER.addPoints();
            default -> System.out.println("Invalid choice!");
        }
        user.addPoints(earned);
        System.out.println("✅ You earned " + earned + " points!");
    }
    /*
      Dedicated menu for Admin users - Shubham
     */
    public static void adminMenu(Scanner sc, ArrayList<User> users) {
        int adminDecision = 0;

        while (adminDecision != 5) {
            System.out.println("\n--- ADMIN MENU ---");
            System.out.println("1. Leaderboard Modify (Beta)");
            System.out.println("2. Region Select (Beta)");
            System.out.println("3. Modify User"); 
            System.out.println("4. Display Leaderboard");
            System.out.println("5. Exit Admin Menu");
            System.out.print("Enter choice: ");

            if (sc.hasNextInt()) {
                adminDecision = sc.nextInt();
            } else {
                System.out.println("Invalid input. Please enter a number.");
                sc.next(); 
                continue;
            }

            switch (adminDecision) {
                case 1:
                    
                    System.out.println("Leaderboard Modify");
                    break;
                case 2:
                    
                    System.out.println("More Regions to come soon (Beta)");
                    break;
                case 3:
                    // ADDED: Call method to manage user data - Shubham
                    modifyUserMenu(sc, users); 
                    break; 
                case 4:
                
                    displayLeaderboard(users);
                    break;
                case 5:
                    System.out.println("Exiting Admin Menu. Returning to regular user flow");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    /**
     * Allows an Admin to search for a user by username and modify their data (score/admin status).
     */
    public static void modifyUserMenu(Scanner sc, ArrayList<User> users) {
        System.out.println("\n--- MODIFY USER DATA ---");
        System.out.print("Enter the username of the user to modify: ");
        String targetUsername = sc.next();

        User targetUser = null;
        
        for (User u : users) {
            if (u.getUsername().equals(targetUsername)) {
                targetUser = u;
                break;
            }
        }

        if (targetUser == null) {
            System.out.println("❌ User not found with username: " + targetUsername);
            return;
        }

        System.out.println("\nFound User: " + targetUser);
        System.out.println("What would you like to modify?");
        System.out.println("1. Change Score");
        System.out.println("2. Toggle Admin Status (Current: " + (targetUser.isAdmin() ? "ADMIN" : "REGULAR") + ")");
        System.out.println("3. Back to Admin Menu");
        System.out.print("Enter choice: ");

        int modifyChoice;
        if (sc.hasNextInt()) {
            modifyChoice = sc.nextInt();
        } else {
            System.out.println("Invalid input. Returning to Admin Menu.");
            sc.next();
            return;
        }

        switch (modifyChoice) {
            case 1:
                System.out.print("Enter NEW score for " + targetUsername + " (Current: " + targetUser.getScore() + "): ");
                if (sc.hasNextInt()) {
                    int newScore = sc.nextInt();
                    targetUser.setScore(newScore); 
                    System.out.println("✅ Score updated to " + newScore + " for " + targetUsername);
                } else {
                    System.out.println("Invalid score input. Score not changed.");
                    sc.next();
                }
                break;

            case 2:
                
                targetUser.setAdmin(!targetUser.isAdmin()); 
                System.out.println("✅ Admin status toggled to: " + (targetUser.isAdmin() ? "ADMIN" : "REGULAR"));
                break;

            case 3:
                System.out.println("Returning to Admin Menu.");
                break;
                
            default:
                System.out.println("Invalid choice. Returning to Admin Menu.");
                break;
        }
    }

}//file close





