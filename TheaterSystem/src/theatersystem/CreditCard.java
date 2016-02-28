package theatersystem;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Represents a single credit card
 * 
 * @author Robert Novak, Piseth Khoem, Jared Johnson, Daniel Clark 3/2/2016
 * @source Adapted from Brahma Dathan and Sarnath Ramnath 2010
 */
public class CreditCard implements Serializable {
    private static final long serialVersionUID = 1L;
    private String memberID;
    private String cardNumber;
    private Calendar expirationDate;
    
    /**
     * Creates a credit card with the given member ID, number, and expiration date
     * @param cardNumber 
     * @param member ID of the card holder
     * @param number of the credit card
     * @param expiration date of the credit card
     */
    public CreditCard(String memberID, String cardNumber, Calendar expirationDate) {
        this.memberID = memberID;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
    }
    
    /**
     * Getter for the credit card number
     * @return number of the credit card
     */
    public String getMemberID() {
        return memberID;
    }
    
    /**
     * Getter for the credit card number
     * @return number of the credit card
     */
    public String getCardNumber() {
        return cardNumber;
    }
    
    /**
     * Returns the expiration date as a String
     * @return expiration date with month, date, and year
     */
    public String getExpirationDate() {
        return expirationDate.get(Calendar.MONTH) + 1 + "/" + expirationDate.get(Calendar.DATE) + "/" + 
                expirationDate.get(Calendar.YEAR);
    }

    /** 
    * String form of the credit card
    */
    public String toString() {
        String string = "Credit Card: Member ID {" + memberID + "}, Number {" + 
                cardNumber + "}, Expiration Date: {" + this.getExpirationDate() + "}";
        return string;
    }
}
