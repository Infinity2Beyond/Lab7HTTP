package server.commands;

import java.time.LocalDateTime ;
import common.data.*;
import common.exceptions.CollectionIsEmpty;
import common.exceptions.HumanNotFound;
import common.exceptions.WrongAmountOfElements;
import common.exceptions.databaseHandling;
import common.exceptions.manualDatabaseEdit;
import common.exceptions.permissionDenied;
import common.interaction.User;
import common.interaction.humanRaw;
import server.utility.responseOutputer;
import server.utility.database.databaseCollectionManager;
import server.utility.CollectionManager;

public class UpdateIdCommand extends AbstractCommand {
	private final CollectionManager collectionManager ;
    private final databaseCollectionManager DatabaseCollectionManager;

	
	public UpdateIdCommand (CollectionManager collectionManager, databaseCollectionManager DatabaseCollectionManager) {
        super("Update_By_ID","", ": Update the value of the collection element whose id is equal to the given one");
        this.collectionManager = collectionManager;
		this.DatabaseCollectionManager = DatabaseCollectionManager;

	}
	/**
     * Executes the command.
     * 
     * @return Command exit status.
     */
    /**
     * The command updates organization with the given ID 
     * 
     * @param argument the argument that the user entered.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String argument, Object objectArgument, User user) {
        try {
            if (argument.isEmpty() || objectArgument == null) throw new WrongAmountOfElements();

            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmpty();

            Long id = Long.parseLong(argument);
            if (id <= 0) throw new NumberFormatException();
            humanbeing oldHumanbeing = collectionManager.getById(id);
            if (oldHumanbeing == null) throw new HumanNotFound();
            if (!oldHumanbeing.getOwner().equals(user)) throw new permissionDenied();
            if (!DatabaseCollectionManager.checkHumanUserId(oldHumanbeing.getId(), user)) throw new manualDatabaseEdit();

            
            humanRaw humanraw = (humanRaw) objectArgument;
            DatabaseCollectionManager.updateHumanById(id, humanraw);
            
            String name = humanraw.getName() == null ? oldHumanbeing.getName() : humanraw.getName();
            coordinates coord = humanraw.getCoordinates() == null ? oldHumanbeing.getCoordinates() : humanraw.getCoordinates();            
            LocalDateTime creationDate = oldHumanbeing.getCreationDate();
            boolean realHero = humanraw.getHero() == null ? oldHumanbeing.getHero() : humanraw.getHero();
            Boolean Toothpick = humanraw.getToothpick() == null ? oldHumanbeing.getToothpick() : humanraw.getToothpick();
            Double Speed = humanraw.getSpeed() == null ? oldHumanbeing.getSpeed() : humanraw.getSpeed();
            Integer Minutes = humanraw.getMinutesWaiting() == null ? oldHumanbeing.getMinutesWaiting() : humanraw.getMinutesWaiting();
            weapontype weapon = humanraw.getWeaponType() == null ? oldHumanbeing.getWeaponType() : humanraw.getWeaponType();
            mood mood = humanraw.getMood() == null ? oldHumanbeing.getMood() : humanraw.getMood();
            car car = humanraw.getCar() == null ? oldHumanbeing.getCar() : humanraw.getCar();
            
            collectionManager.removeFromCollection(oldHumanbeing);
            collectionManager.addToCollection(new humanbeing(
            		id,
                    name,
                    coord,
                    creationDate,
                    realHero,
                    Toothpick,
                    Speed,
                    Minutes,
                    weapon,
                    mood,
                    car ,
                    user));
            collectionManager.fromAtoZ();
            //collectionManager.regenerateID();
            responseOutputer.appendln("Humanbeing successfully changed!");
            return true;
        } catch (WrongAmountOfElements exception) {
        	responseOutputer.appendln("Wrong Syntax! Type '" + getName() + "' to use this command");
        } catch (CollectionIsEmpty exception) {
        	responseOutputer.appenderror("Collection is empty!");
        } catch (HumanNotFound exception) {
        	responseOutputer.appenderror("There is no humanbeing with this ID in the collection!");
        } catch (NumberFormatException exception) {
            responseOutputer.appenderror("ID must be represented as a positive number!");
        } catch (ClassCastException exception) {
            responseOutputer.appenderror("The object passed by the client is invalid!");
        } catch (databaseHandling exception) {
            responseOutputer.appenderror("An error occurred while accessing the database!");
        } catch (permissionDenied exception) {
            responseOutputer.appenderror("Insufficient rights to execute this command!");
            responseOutputer.appendln("Objects owned by other users are read-only.");
        } catch (manualDatabaseEdit exception) {
            responseOutputer.appenderror("A direct database change has occurred!");
            responseOutputer.appendln("Restart the client to avoid possible errors.");
        }
        return false;
    }
}
