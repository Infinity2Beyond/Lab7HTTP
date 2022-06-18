package server.utility;

import common.data.*;
import common.exceptions.CollectionIsEmpty;
import common.exceptions.databaseHandling;
import common.utility.outPuter;
import server.app;
import server.utility.database.databaseCollectionManager;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Stack;


public class CollectionManager {
	private Stack<humanbeing> humanCollection ;
	private LocalDateTime lastInputTime;
	private LocalDateTime lastSaveTime;
    private final databaseCollectionManager DatabaseCollectionManager;

	public CollectionManager(databaseCollectionManager DatabaseCollectionManager) {
		this.lastInputTime = null;
        this.lastSaveTime = null;
        this.DatabaseCollectionManager = DatabaseCollectionManager;

        
        loadCollection();
        fromAtoZ();
        //regenerateID();
	}
	
	/**
	 * sort collection from A to Z by name
	 */
	public void fromAtoZ() {
		for (int i = 0; i < humanCollection.size(); i++)
        {
            for (int j = i + 1; j < humanCollection.size(); j++) {
                if (humanCollection.get(i).getName().toLowerCase().compareTo
                		(humanCollection.get(j).getName().toLowerCase())>0)
                {
                    humanbeing temp = humanCollection.get(i);
                    humanCollection.set(i, humanCollection.get(j)) ;
                    humanCollection.set(j, temp);
                }
            }
        }
	}
	
	
     //* `regenerateID()`: Regenerates the ID for each organization in the collection
     //*
	public void regenerateID(){
    	Stack<humanbeing> collection = getCollection();
        long id = 1;
        for(humanbeing humanbeing : collection){
        	humanbeing.setId(id++);
        }
    }
	/**
     * Get the collection of organizations
     * 
     * @return An ArrayList of Organization objects.
     */
    public Stack<humanbeing> getCollection() {
        return humanCollection;
    }
	/**
     * @return Last initialization time or null if there wasn't initialization.
     */
    public LocalDateTime getLastInputTime() {
        return lastInputTime;
    }

    /**
     * @return Last save time or null if there wasn't saving.
     */
    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }
    /**
     * @return Name of the collection's type.
     */
    public String collectionType() {
        return humanCollection.getClass().getName();
    }
    /**
     * @return Size of the collection.
     */
    public int collectionSize() {
        return humanCollection.size();
    }

    /**
     * @return The first element of the collection or null if collection is empty.
     */
    public humanbeing getFirst() {
        return humanCollection.stream().findFirst().orElse(null);    }
    /**
     * @return The last element of the collection or null if collection is empty.
     */
    public humanbeing getLast() { 
    	if (humanCollection.isEmpty())
            return null;
        return humanCollection.lastElement();
    }
    /**
     * @param id ID of the human.
     * @return A human by his ID or null if human isn't found.
     */
    public humanbeing getById(long id) {
        for (humanbeing humanbeing : humanCollection) {
            if (Long.valueOf(humanbeing.getId()).equals(id)) {
                return humanbeing;
            }
        }
        return null;
    }
    /**
     * Add an humanbeing to the collection of organizations
     * 
     * @param organization The organization to add to the collection.
     */
    public void addToCollection(humanbeing human) {
    	humanCollection.add(human);
    }
    /**
     * Remove an humanbeing from the collection
     * 
     * @param organization The organization to be removed from the collection.
     */
    public void removeFromCollection(humanbeing human) {
    	humanCollection.remove(human);
    }
    /**
     * Clear all humanbeing in the collection.
     */
    public void clearCollection() {
    	humanCollection.clear();
    }
    /**
     * Generates next ID. It will be (the bigger one + 1).
     * 
     * @return Next ID.
     */
    public Long generateNextId() {
        if (humanCollection.isEmpty())
            return 1L;
        return getLast().getId() + 1;
    }
    /**
     * Saves the collection to file.
     *
    public void saveCollection() {
        fileManager.writeCollection(humanCollection);
        lastSaveTime = LocalDateTime.now();
    }
    /**
     * Loads the collection from file.
     */
    private void loadCollection() {
    try {
    	humanCollection = DatabaseCollectionManager.getCollection();
    	lastInputTime = LocalDateTime.now();
        outPuter.println("The collection is loaded.");
        app.logger.info("The collection is loaded.");
    } catch (databaseHandling exception) {
    	humanCollection = new Stack<>();
    	outPuter.printerror("The collection could not be loaded!");
    	app.logger.error("The collection could not be loaded!");    
    }
}
    
    /**
     * Find the human with smallest speed
     * @return
     * @throws CollectionIsEmpty
     */
    public humanbeing MinByImpactSpeed() throws CollectionIsEmpty {	
        if (humanCollection.isEmpty()) throw new CollectionIsEmpty();      
        humanbeing minHuman = getFirst();
        for (humanbeing humanbeing : humanCollection) {
            if (humanbeing.getSpeed().compareTo(minHuman.getSpeed()) < 0) {
            	minHuman = humanbeing;
            }
        }     
        return minHuman;
    }
    /**
     * Print out all human, who have the smallest speed
     * @param humanbeing
     * @return
     * @throws CollectionIsEmpty
     */
    public String PrintMinSpeed(humanbeing humanbeing) throws CollectionIsEmpty {
    	humanbeing = MinByImpactSpeed();
    	String info = humanbeing.toString() + "\n\n" ;
    	for (humanbeing humanbeing2 : humanCollection) {
    		if (humanbeing2.getSpeed().compareTo(humanbeing.getSpeed()) == 0 
    				&& humanbeing2.compareTo(humanbeing) != 0 ) {
    			info += humanbeing2.toString() + "\n\n" ;
    		}
    	}
    	return info;  	
    }
    /**
     * Print out all human, who have speed smaller than client's choose
     * @param ImpactSpeed
     * @return
     */
    public String FilterLessByImpactSpeed (Double ImpactSpeed) {
        String info = "";
        
        for (humanbeing humanbeing : humanCollection) {
        	if (humanbeing.getSpeed() - ImpactSpeed < 0 ) 
        		info += humanbeing + "\n\n";
        }
        return info.trim();
    }
    /**
     * Print out the collection
     */
    @Override
    public String toString() {
        if (humanCollection.isEmpty())
            return "The collection is empty!";

        String info = "";
        
        for (humanbeing humanbeing : humanCollection) {
            info += humanbeing;
            if (humanbeing != getLast())
                info += "\n\n";
        }
        return info ;
    }

}
