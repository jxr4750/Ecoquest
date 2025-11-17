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
/* Currently needed
 * Login/Admin Check
 * Create Admin Class actions for Switch case
 * Create Functionality for user switch case
 * Create nested loop functions so the menus work properly
 * Integrate User class into switch case
 * ---After Functionality, Before Submission---
 * Code Organizing
 * Optional features if we have time
 * Quality Test
 */

public class EcoQuest {
    private static ArrayList<User> users = new ArrayList<>();
    
    public static void main(String[] args) {
        Scanner Scanner = new Scanner(System.in);
        //Load up the files, if it doesnt exist, then create
        String username;
        int Decision = 0;
        int pin = 0000;
        int region = 0;

        // ADDED: ArrayList to hold users-Saish
        ArrayList<User> users = loadUsers();
        
        //Start, Select Region
        System.out.println("Hello, Welcome to EcoQuest (recycle emoji)\nPlease select your region:\n1.Dallas, Texas (Beta)\n");
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

        boolean admin = adminCheck(username,pin); //MARKER: check admin status for the current user
        boolean signin = login.authenticate(username,pin); //THIS IS JUST A MARKER FOR NOW
        
        if(signin == false){
            System.out.println("Log in failed, make new account (PlaceHodlder text)");
        }

        //Admin Log in if(ADMIN TRUE && SIGNINSUCCESSFUL TRUE) : Switch function of controls, no regular controls, purely utility 
        //add if admin true, load admin switch. 
        System.out.println("Admin: Firstname Lastname\nPlease use regular signin to track stats");

        //while(Decision != 5) //marker
        switch(Decision){
            case 1:
            System.out.println("Leaderboard Modify");
            break;
            
            case 2:
            System.out.println("Region Select");
            break;
            
            case 3:
            System.out.println("User Modify (Per Region)");
            break;
            
            case 4:
            displayLeaderboard(users); // ADDED: call leaderboard display-Saish
            break;
            
            case 5:
            System.out.println("Exit");
            break;

            default:
            System.out.println("Default Statement");
            break;
        }//close admin switch

        //Regular Log in: Select Region, Display LeaderBoard, Add activity, Points shop(?), "battle pass"(?), history, account management
        //Regular Log in: Select Region, Display LeaderBoard, Add activity, Points shop(?), "battle pass"(?), history, account management
        System.out.println("Welcome back " + currentUser.getFirstName() + " " + currentUser.getLastName());

        // ADDED: Reset Decision for user menu-Saish
        Decision = 0;

        while (Decision != 4) { // ADDED: loop for user menu-Saish
            System.out.println("\n--- USER MENU ---");
            System.out.println("1. Leaderboard");
            System.out.println("2. Add Daily Task (Add Points)");
            System.out.println("3. Modify Account");
            System.out.println("4. Exit");
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
                    System.out.println("Modify Account (placeholder)");
                    break;

                case 4:
                    System.out.println("Exit");
                    break;

                default:
                    System.out.println("Default Statement");
                    break;
            }//close regular switch
        }

        saveUsers(users); // ADDED: save before closing-Saish
        Scanner.close();
    }//main statement close

    public class Loginsystem {
        //WIP, code is fussy
    }

    public class User {

        private String firstName;
        private String lastName;
        private int score;
        private String username;
        private int pin;
        private boolean admin; // ADDED: admin flag-saish

        // ADDED: constructor-Saish
        public User(String firstName, String lastName, String username, int pin, int score, boolean admin) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.username = username;
            this.pin = pin;
            this.score = score;
            this.admin = admin;
        }

        // ADDED: getters and setters-Saish
        public String getFirstName() { return firstName; }
        public String getLastName() { return lastName; }
        public String getUsername() { return username; }
        public int getPin() { return pin; }
        public int getScore() { return score; }
        public boolean isAdmin() { return admin; }
        public void addPoints(int pts) { this.score += pts; }

        @Override
        public String toString() {
            return String.format("%s %s (%s) - %d pts | Admin: %b", firstName, lastName, username, score, admin);

        //do we need to create constructor, setter, and getter?

    }

    public enum Points {
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
                    users.add(new EcoQuest().new User(
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

}//file close

}

