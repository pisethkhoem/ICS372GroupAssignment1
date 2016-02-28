package theatersystem;

import theatersystem.Client;
import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * The collection class for Client objects
 * 
 * @author Robert Novak, Piseth Khoem, Jared Johnson, Daniel Clark 3/2/2016
 * @source Brahma Dathan and Sarnath Ramnath 2010
 */
public class ClientList implements Serializable {
    private static final long serialVersionUID = 1L;
    private List clients = new LinkedList();
    private static ClientList clientList;
    
    /**
     * Private constructor for singleton pattern
     * 
     */
    private ClientList() {
    }
    
    /**
     * Supports the singleton pattern
     * @return the singleton object
     */
    public static ClientList instance() {
        if (clientList == null) {
            return (clientList = new ClientList());
        } else {
            return clientList;
        }
    }
    
    /**
     * Inserts a client into the collection
     * @param client to be inserted
     * @return true if the client could be inserted
     */
    public boolean insertClient(Client client) {
        clients.add(client);
        return true;
    }
    
    /**
     * Deletes a client in the collection
     * @param client to be deleted
     * @return true if the client could be inserted
     */
    public boolean deleteClient(Client client) {
        clients.remove(client);
        return true;
    }
       
    /**
     * Checks whether a client with a given client ID exists
     * @param client ID the ID of the client
     * @return true if client exists
     */
    public Client search(String clientId) {
        for (Iterator iterator = clients.iterator(); iterator.hasNext(); ) {
            Client client = (Client) iterator.next();
            if (client.getId().equals(clientId)) {
            return client;
            }
        }
        return null;
    }
    
    /**
     * Gets an iterator to a collection of all clients
     * @return the iterator to the collection
     */
    public Iterator getClients() {
        return clients.iterator();
    }

    /**
     * Supports serialization
     * @param output the stream to be written to
     */
    private void writeObject(java.io.ObjectOutputStream output) {
        try {
            output.defaultWriteObject();
            output.writeObject(clientList);
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    /**
     * Supports serialization
     * @param input the stream to be read from
     */
    private void readObject(java.io.ObjectInputStream input) {
        try {
            if (clientList != null) {
            return;
            } else {
            input.defaultReadObject();
            if (clientList == null) {
                clientList = (ClientList) input.readObject();
            } else {
                input.readObject();
            }
            }
        } catch(IOException ioe) {
            ioe.printStackTrace();
        } catch(ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
    }
    
    /**
     * String form of the collection
     */
    @Override
    public String toString() {
        return clients.toString();
    }
}
