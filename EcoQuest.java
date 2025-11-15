import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
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
    public static void main(String[] args) {
        Scanner Scanner = new Scanner(System.in);
        //Load up the files, if it doesnt exist, then create
        String username;
        int Decision = 0;
        int pin = 0000;
        int region = 0;
        
        //Start, Select Region
        System.out.println("Hello, Welcome to EcoQuest (recycle emoji)\nPlease select your region:\n1.Dallas, Texas (Beta)\n");
        region = Scanner.nextInt(); //Really a placeholder for now, we only have 1 region. 

        //Enter User details 
        System.out.println("Username:");
        username = Scanner.next();

        System.out.println("Please enter your 4 digit pin: ");
        pin = Scanner.nextInt();

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
            System.out.println("Leaderboard");
            break;
            
            case 5:
            System.out.println("Exit");
            break;

            default:
            System.out.println("Default Statement");
            break;
        }//close admin switch

        //Regular Log in: Select Region, Display LeaderBoard, Add activity, Points shop(?), "battle pass"(?), history, account management
        System.out.println("Welcome back Firstname Lastname");

        //while(Decision != 4) //marker
        switch(Decision){
            case 1:
            System.out.println("Leaderboard");
            break;
            
            case 2:
            System.out.println("Add Daily Task (Add Points)"); //create a separate switch case class to add points, the enum is already made below
            break;
            
            case 3:
            System.out.println("Modify Account"); //Latter priority, to be able to switch PIN number, name, etc
            break;

            case 4:
            System.out.println("Exit");
            break;

            default:
            System.out.println("Default Statement");
            break;
        }//close regular switch

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

}
