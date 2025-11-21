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

        // ADDED: Setters for Admin to directly modify score and admin status - Shubham
        public void setScore(int score) { this.score = score; }
        public void setAdmin(boolean admin) { this.admin = admin; }
        
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
        } 

    }