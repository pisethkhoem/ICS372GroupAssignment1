package theatersystem;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Represents a single show
 * 
 * @author Robert Novak, Piseth Khoem, Jared Johnson, Daniel Clark 3/2/2016
 * @source Brahma Dathan and Sarnath Ramnath 2010
 */
public class Show implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String clientID;
    private Calendar startDate;
    private Calendar endDate;
    
    /**
     * Creates a show with the given name, client ID, start date and end date
     * @param name of the show
     * @param ID of the client
     * @param start date of the show
     * @param end date of the show
     */
    public Show(String name, String clientID, Calendar startDate, Calendar endDate) {
        this.name = name;
        this.clientID = clientID;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    /**
     * Getter for clientID
     * @return ID of the client for the show
     */
    public String getClientID() {
        return clientID;
    }
    
    /**
     * Getter for name
     * @return the name of the show
     */
    public String getName() {
        return name;
    }
    
    /**
     * Returns the start date
     * @return start date
     */
    public Calendar getStartDate() {
        return startDate;
    } 
    
    /**
     * Returns the end date
     * @return end date with
     */
    public Calendar getEndDate() {
        return endDate;
    }
    
    /**
     * Returns the start date as a String
     * @return start date with month, date, and year
     */
    public String getPrintableStartDate() {
        return startDate.get(Calendar.MONTH) + 1 + "/" + startDate.get(Calendar.DATE) + "/" 
                + startDate.get(Calendar.YEAR);
    } 
    
    /**
     * Returns the end date as a String
     * @return end date with month, date, and year
     */
    public String getPrintableEndDate() {
        return endDate.get(Calendar.MONTH) + 1 + "/" + endDate.get(Calendar.DATE) + "/" 
                + endDate.get(Calendar.YEAR);
    }

    /** 
     * String form of the show
     */
    public String toString() {
        return "Show: Name {" + name + "}, Dates {" + getPrintableStartDate() + " - " 
                + getPrintableEndDate() + "}";
    }
}
