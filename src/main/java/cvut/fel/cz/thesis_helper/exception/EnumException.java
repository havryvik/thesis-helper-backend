package cvut.fel.cz.thesis_helper.exception;

public class EnumException extends AbstractException{
    public EnumException(String message) {
        super(message);
    }

    public static EnumException badArgumentException(){
        return new EnumException("Some of approach`s types are not correct!");
    }
}
