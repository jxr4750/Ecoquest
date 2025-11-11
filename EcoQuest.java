import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class EcoQuest {
    public static void main(String[] args) {
        Scanner Scanner = new Scanner(System.in);
        //Load up the files, if it doesnt exist, then create

        //Log in screen
        String username;
        int Decision = 0;
        int pin = 0000;
        boolean admin = false; //admin status for the current user
        boolean signin = false;
        System.out.println("Hello, Welcome to EcoQuest (recycle emoji)\nPlease log in\nUsername:\n");
        //check to see if user exists. 
        System.out.println("We dont recognize the user name, would you like to create an account or retry?"); //username doesnt exist

        System.out.println("Please enter your 4 digit pin: ");
        System.out.println("Incorrect pin, please try again");//pin wrong, or doesnt match 4 digits

        signin = true;

        //Admin Log in if(ADMIN TRUE && SIGNINSUCCESSFUL TRUE) : Switch function of controls, no regular controls, purely utility 
        System.out.println("Admin: Firstname Lastname\nPlease use regular signin to track stats");

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

        switch(Decision){
            case 1:
            System.out.println("Leaderboard");
            break;
            
            case 2:
            System.out.println("Add Daily Task");
            break;
            
            case 3:
            System.out.println("Select Region");
            break;
            
            case 4:
            System.out.println("Points History");
            break;
            
            case 5:
            System.out.println("Modify Account");
            break;

            case 6:
            System.out.println("Exit");
            break;

            default:
            System.out.println("Default Statement");
            break;
        }//close regular switch

        //After the chose switch statement exits, close the files and scanner


        Scanner.close();
    }//main statement close

}//file close
