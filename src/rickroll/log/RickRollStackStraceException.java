package rickroll.log;

public class RickRollStackStraceException extends RuntimeException {

	public RickRollStackStraceException(String errorMessage) {
        super(errorMessage);
    }
	
	public RickRollStackStraceException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
	
}
