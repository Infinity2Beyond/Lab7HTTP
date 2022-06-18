package clients.utility;

import common.exceptions.MustBeNotEmpty;
import common.exceptions.NotInDeclaredLimit;
import common.utility.outPuter;

import java.util.NoSuchElementException;
import java.util.Scanner;

import clients.app;

public class authorizedAsker {
	private Scanner userScanner;

    public authorizedAsker(Scanner userScanner) {
        this.userScanner = userScanner;
    }

    /**
     * Asks user a login.
     *
     * @return login.
     */
    public String askLogin() {
        String login;
        while (true) {
            try {
            	outPuter.println("Enter username:");
            	outPuter.print(app.PS2);
                login = userScanner.nextLine().trim();
                if (login.equals("")) throw new MustBeNotEmpty();
                break;
            } catch (NoSuchElementException exception) {
            	outPuter.printerror("This username does not exist!");
            } catch (MustBeNotEmpty exception) {
            	outPuter.printerror("Name cannot be empty!");
            } catch (IllegalStateException exception) {
            	outPuter.printerror("Unexpected error!");
                System.exit(0);
            }
        }
        return login;
    }

    /**
     * Asks user a password.
     *
     * @return password.
     */
    public String askPassword() {
        String password;
        while (true) {
            try {
            	outPuter.println("Enter password:");
            	outPuter.print(app.PS2);
                password = userScanner.nextLine().trim();
                break;
            } catch (NoSuchElementException exception) {
            	outPuter.printerror("Wrong login or password!");
            } catch (IllegalStateException exception) {
            	outPuter.printerror("Unexpected error!");
                System.exit(0);
            }
        }
        return password;
    }

    /**
     * Asks a user a question.
     *
     * @param question A question.
     * @return Answer (true/false).
     */
    public boolean askQuestion(String question) {
        String finalQuestion = question + " (Yes/No):";
        String answer;
        while (true) {
            try {
            	outPuter.println(finalQuestion);
            	outPuter.print(app.PS2);
                answer = userScanner.nextLine().trim();
                if (!answer.toUpperCase().equals("YES") && !answer.toUpperCase().equals("NO")) throw new NotInDeclaredLimit();
                break;
            } catch (NoSuchElementException exception) {
            	outPuter.printerror("Answer not recognized!");
            } catch (NotInDeclaredLimit exception) {
            	outPuter.printerror("The answer must be represented by 'Yes' or 'No'!");
            } catch (IllegalStateException exception) {
            	outPuter.printerror("Unforeseen error!");
                System.exit(0);
            }
        }
        return answer.toUpperCase().equals("YES");
    }

}
