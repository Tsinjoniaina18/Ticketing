package exception;

public class MismatchException extends Exception{

    public MismatchException(String message) {
        super("Colonnes dans la classe et dans la base ne sont pas identique: "+message);
    }
    
}
