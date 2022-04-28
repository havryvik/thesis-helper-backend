package cvut.fel.cz.thesis_helper.exception;

public class ApproachException extends AbstractException{
    public ApproachException(String message) {
        super(message);
    }

    public static ApproachException approachByIdNotFound(Integer id){
        return new ApproachException("Approach with id: "+id+" was not found");
    }

    public static ApproachException approachDoesNotConfigured(Integer studentId) {
        return new ApproachException("Approach for student with id: "+studentId+" was not configured!");
    }

    public static ApproachException weightsDoesNotExists() {
        return new ApproachException("Weights are not configured!");
    }

    public static ApproachException approachDoesNotSupportWeights() {
        return new ApproachException("To add weights you should configure approach accordingly!");
    }
}
