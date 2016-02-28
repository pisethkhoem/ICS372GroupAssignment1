package theatersystem;

import theatersystem.Client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * This class implements the user interface for the Theater project.
 * The commands are encoded as integers using a number of
 * static final variables. A number of utility methods exist to
 * make it easier to parse the input.
 * 
 * @author Robert Novak, Piseth Khoem, Jared Johnson, Daniel Clark 3/2/2016
 * @source Brahma Dathan and Sarnath Ramnath 2010
 */
public class UserInterface {
    private static UserInterface userInterface;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private boolean retrieveDisabled = false;
    private static Theater theater;
    private static final int EXIT = 0;
    private static final int ADD_CLIENT = 1;
    private static final int REMOVE_CLIENT = 2;
    private static final int GET_CLIENTS = 3;
    private static final int ADD_MEMBER = 4;
    private static final int REMOVE_MEMBER = 5;
    private static final int ADD_CREDIT_CARD = 6;
    private static final int REMOVE_CREDIT_CARD = 7;
    private static final int GET_MEMBER = 8;
    private static final int ADD_SHOW = 9;
    private static final int GET_SHOWS = 10;
    private static final int SAVE = 11;
    private static final int RETRIEVE = 12;
    private static final int HELP = 13;

   /**
    * Made private for singleton pattern.
    * Conditionally looks for any saved data
    * Otherwise, it gets a singleton Theater object.
    */
    private UserInterface() {
        if (yesOrNo("Look for saved data and use it?\n")) {
            retrieve();
        } else {
            theater = Theater.instance();
        }
    }

    /**
     * Supports the singleton pattern
     * 
     * @return the singleton object
     */
    public static UserInterface instance() {
        if (userInterface == null) {
            return userInterface = new UserInterface();
        } else {
            return userInterface;
        }
    }

    /**
     * Gets a token after prompting
     * 
     * @param prompt whatever the user wants as prompt
     * @return the token from the keyboard
     */
    public String getToken(String prompt) {
        do {
            try {
                System.out.println(prompt);
                String line = reader.readLine();
                StringTokenizer tokenizer = new StringTokenizer(line,"\n\r\f");
                if (tokenizer.hasMoreTokens()) {
                    return tokenizer.nextToken();
                }
            } catch (IOException ioe) {
                System.exit(0);
            }
        } while (true);
    }

    /**
     * Queries for a yes or no and returns true for yes and false for no
     * 
     * @param prompt the string to be prepended to the yes/no prompt
     * @return true for yes and false for no
     */
    private boolean yesOrNo(String prompt) {
        String more = getToken(prompt + "Y or y for YES, anything else for no:");
        if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
            return false;
        }
        return true;
    }

    /**
     * Converts the string to a number
     * @param prompt the string for prompting
     * @return the integer corresponding to the string
     */
    public int getNumber(String prompt) {
        do {
            try {
                String item = getToken(prompt);
                Integer number = Integer.valueOf(item);
                return number.intValue();
            } catch (NumberFormatException nfe) {
                System.out.println("Please input a number ");
            }
        } while (true);
    }

    /**
     * Prompts for a date and gets a date object
     * @param prompt
     * @return date as a Calendar object
     */
    public Calendar getDate(String prompt) {
        do {
            try {
                Calendar date = new GregorianCalendar();
                String item = getToken(prompt);
                DateFormat dateFormat = SimpleDateFormat.getDateInstance(DateFormat.SHORT);
                date.setTime(dateFormat.parse(item));
                return date;
            } catch (Exception fe) {
                System.out.println("Please input the date as mm/dd/yy...");
            }
        } while (true);
    }

    /**
     * Prompts for a command from the keyboard
     * @return a valid command
     */
    public int getCommand() {
        do {
            try {
                int value = Integer.parseInt(getToken("Enter command (" + HELP + " for help):"));
                if (value >= EXIT && value <= HELP) {
                    return value;
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Enter a number:");
            }
        } while (true);
    }

    /**
     * Displays the help screen
     */
    public void help() {
        System.out.println("Enter a number between 0 and 13 as explained below:");
        System.out.println(EXIT + " to exit");
        System.out.println(ADD_CLIENT + " to add a client");
        System.out.println(REMOVE_CLIENT + " to remove a client");
        System.out.println(GET_CLIENTS + " to print clients");
        System.out.println(ADD_MEMBER + " to add a member");
        System.out.println(REMOVE_MEMBER + " to remove a member");
        System.out.println(ADD_CREDIT_CARD + " to add a credit card");
        System.out.println(REMOVE_CREDIT_CARD + " to remove a credit card");
        System.out.println(GET_MEMBER + " to print members");
        System.out.println(ADD_SHOW + " to add a show");
        System.out.println(GET_SHOWS + " to print shows");
        System.out.println(SAVE + " to save data");
        System.out.println(RETRIEVE + " to retrieve");
        System.out.println(HELP + " for help\n");
    }
    
    /**
     * Method to be called for adding a client.
     * Prompts the user for the appropriate values and
     * uses the appropriate Theater method for adding the client.  
     */
    public void addClient() {
        String name = getToken("Enter client name:");
        String address = getToken("Enter address:");
        String phone = getToken("Enter phone:");
        Client result;
        result = theater.addClient(name, address, phone);
        if (result == null) {
            System.out.println("Could not add client.\n");
        } else {
            System.out.println(result + "\n");
            disableRetrieve();
        }
    }
    
    /**
     * Method to be called for removing books.
     * Prompts the user for the appropriate values and
     * uses the appropriate Library method for removing books.
     *    
     */
    public void removeClient() {
        int result;
        do {
            String clientID = getToken("Enter client id:");
            result = theater.removeClient(clientID);
            switch(result){
                case Theater.NO_SUCH_CLIENT:
                    System.out.println("Not a valid client ID.\n");
                    break;
                case Theater.ACTIVE_CLIENT:
                    System.out.println("The removal failed since this client has a present or upcoming show.\n");
                    break;
                case Theater.OPERATION_COMPLETED:
                    System.out.println("The client has been removed.\n");
                    break;
                default:
                    System.out.println("An error has occurred.\n");
            }
            if (!yesOrNo("Remove another client?\n")) {
                break;
            }
        } while (true);
    }
    
    /**
     * Method to be called for displaying transactions.
     * Prompts the user for the appropriate values and
     * uses the appropriate Library method for displaying transactions.
     */
    public void getClients() {
        Iterator result;
        result = theater.getClients();
        String string = "There are no clients.\n";
        if (result == null) {
            System.out.println("There are no clients.\n");
        } else {
            while(result.hasNext()) {
                Client client = (Client) result.next();
                System.out.println(client);
                string = "";
            }
            System.out.println(string);
        }
    }
    
    /**
     * Method to be called for adding a member.
     * Prompts the user for the appropriate values and
     * uses the appropriate Theater method for adding the member. 
     */
    public void addMember() {
        String name = getToken("Enter member name:");
        String address = getToken("Enter address:");
        String phone = getToken("Enter phone:");
        String cardNumber = getToken("Enter credit card number:");
        Calendar expirationDate = getDate("Enter expiration date (mm/dd/yy):");
        if (!theater.validCreditCard(cardNumber, expirationDate)) {
            System.out.println("Could not add the member. The credit card is expired or invalid.");
        } else {
            Member memberResult = theater.addMember(name, address, phone, cardNumber, expirationDate);
            if (memberResult == null) {
                System.out.println("Could not add the member.\n");
            } else {
                System.out.println(memberResult + "\n");
                disableRetrieve();
            }
        }
    }
    
    /**
     * Method to be called for removing a member.
     * Prompts the user for the appropriate values and
     * uses the appropriate Theater method for removing a member.
     */
    public void removeMember() {
        int result;
        do {
            String memberID = getToken("Enter member ID:");
            result = theater.removeMember(memberID);
            switch(result){
                case Theater.NO_SUCH_MEMBER:
                    System.out.println("Not a valid member ID.\n");
                    break;
                case Theater.OPERATION_COMPLETED:
                    System.out.println("The member has been removed.\n");
                    break;
                default:
                    System.out.println("An error has occurred.\n");
            }
            if (!yesOrNo("Remove another member?\n")) {
                break;
            }
        } while (true);
    }
    
    /**
     * Method to be called for placing a hold.
     * Prompts the user for the appropriate values and
     * uses the appropriate Theater method for placing a hold.
     */
    public void addCreditCard() {
        String memberID = getToken("Enter member ID:");
        String cardNumber = getToken("Enter credit card number:");
        Calendar expirationDate = getDate("Enter expiration date (mm/dd/yy):");
        int result = theater.addCreditCard(memberID, cardNumber, expirationDate);
        switch(result){
            case Theater.NO_SUCH_MEMBER:
                System.out.println("Not a valid member ID.\n");
                break;
            case Theater.CREDIT_CARD_ADDED:
                System.out.println("The credit card has been added.\n");
                break;
            default:
                System.out.println("An error has occurred.\n");
        }
    }
    
    /**
     * Method to be called for removing a credit card.
     * Prompts the user for the appropriate values and
     * uses the appropriate Theater method for removing a 
     * credit card.
     */
    public void removeCreditCard() {
        int result;
        do {
            String memberID = getToken("Enter member ID:");
            String cardNumber = getToken("Enter credit card number:");
            result = theater.removeCreditCard(memberID, cardNumber);
            switch(result){
                case Theater.NO_SUCH_CREDIT_CARD:
                    System.out.println("Not a valid credit card number.\n");
                    break;
                case Theater.ONLY_CREDIT_CARD:
                        System.out.println("This member only has one credit card,");
                        System.out.print("so it was not removed.\n");
                        break;
                case Theater.OPERATION_COMPLETED:
                    System.out.println("The credit card has been removed.\n");
                    break;
                default:
                    System.out.println("An error has occurred.\n");
            }
            if (!yesOrNo("Remove another credit card?\n")) {
                break;
            }
        } while (true);
    }
  
    /**
     * Method to be called for displaying transactions.
     * Prompts the user for the appropriate values and
     * uses the appropriate Library method for displaying transactions. 
     */
    public void getMembers() {
        Iterator result = theater.getMembers();
        String string = "There are no members.\n";
        if (result == null) {
            System.out.println("There are no members.\n");
        } else {
            while(result.hasNext()) {
                Member member = (Member) result.next();
                System.out.println(member);
                string = "";
            }
            System.out.println(string);   
        }
    }
  
    /**
     * Method to be called for adding a show.
     * Prompts the user for the appropriate values and
     * uses the appropriate Theater method for adding a show. 
     */
    public void addShow() {
        String name = getToken("Enter show name:");
        String clientID = getToken("Enter client ID:");
        Calendar startDate = getDate("Enter start date (mm/dd/yy):");
        Calendar endDate = getDate("Enter end date (mm/dd/yy):");
        int result = theater.addShow(name, clientID, startDate, endDate);
        switch(result){
            case Theater.NO_SUCH_CLIENT:
                System.out.println("Not a valid client ID.\n");
                break;
            case Theater.SHOW_DATES_INVALID:
                System.out.println("The show has not been added... ");
                System.out.println("The dates are either past, invalid or conflict with another show.\n");
                break;
            case Theater.SHOW_ADDED:
                System.out.println("The show has been added.\n");
                break;
            default:
                System.out.println("An error has occurred.\n");
        }
    }
    
    /**
     * Method to be called for displaying shows.
     * Prompts the user for the appropriate values and
     * uses the appropriate Theater method for displaying shows.
     */
    public void getShows() {
        Iterator clientResult = theater.getClients();
        String string = "There are no shows.\n";
        if (clientResult == null) {
            System.out.println("There are no shows.\n");
        } else {
            while(clientResult.hasNext()) {
                Client client = (Client) clientResult.next();
                Iterator showResult = client.getShows();
                if (showResult == null) {
                    System.out.println("There are no shows.\n");
                } else {
                    while(showResult.hasNext()) {
                        Show show = (Show) showResult.next();
                        System.out.println(show);
                        string = "";
                    }

                }
            }
            System.out.println(string);
        }
    }
  
    /**
     * Method to be called for saving the Theater object.
     * Uses the appropriate Theater method for saving. 
     */
    private void save() {
        if (theater.save()) {
            System.out.println("The theater has been successfully saved in the file TheaterData.\n" );
        } else {
            System.out.println("There has been an error in saving.\n" );
        }
    }

    /**
     * Method to be called for retrieving saved data.
     * Uses the appropriate Theater method for retrieval. 
     */
    private void retrieve() {
        if (retrieveDisabled) {
            System.out.println("Retrieve is no longer possible since data has been loaded or added.\n");
            return;
        }
        try {
            Theater tempTheater = Theater.retrieve();
            if (tempTheater != null) {
                System.out.println("The theater has been successfully retrieved from the file TheaterData.\n" );
                theater = tempTheater;
                disableRetrieve();
            } else {
                System.out.println("File doesnt exist... creating a new theater.\n" );
                theater = Theater.instance();             
            }
        } catch(Exception cnfe) {
            cnfe.printStackTrace();
        }
    }
    
    /**
     * Disable the retrieving process
     * @return true if retrieving has been disabled
     */
    public void disableRetrieve() {
        retrieveDisabled = true;
    }
    
    /**
     * Orchestrates the whole process.
     * Calls the appropriate method for the different functionalties.
     */
    public void process() {
        int command;
        help();
        while ((command = getCommand()) != EXIT) {
            switch (command) {
                case ADD_CLIENT:            addClient();
                                            break;
                case REMOVE_CLIENT:         removeClient();
                                            break;
                case GET_CLIENTS:           getClients();
                                            break;
                case ADD_MEMBER:            addMember();
                                            break;
                case REMOVE_MEMBER:         removeMember();
                                            break;
                case ADD_CREDIT_CARD:       addCreditCard();
                                            break;
                case REMOVE_CREDIT_CARD:    removeCreditCard();
                                            break;
                case GET_MEMBER:            getMembers();
                                            break;
                case ADD_SHOW:              addShow();
                                            break;
                case GET_SHOWS:             getShows();
                                            break;
                case SAVE:                  save();
                                            break;
                case RETRIEVE:              retrieve();
                                            break;
                case HELP:                  help();
                                            break;
            }
        }
    }

    /**
     * The method to start the application. Simply calls process().
     * @param args not used
     */
    public static void main(String[] args) {
        UserInterface.instance().process();
    }
}
