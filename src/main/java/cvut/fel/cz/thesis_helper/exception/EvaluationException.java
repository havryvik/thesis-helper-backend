package cvut.fel.cz.thesis_helper.exception;

public class EvaluationException extends AbstractException{
    public EvaluationException(String message) {
        super(message);
    }

    public static EvaluationException studentDoesNotHaveEvaluation(){
        return new EvaluationException("Evaluation for that student was not created!");
    }
    public static EvaluationException evaluationByIdNotFound(Integer evaluationId) {
        return new EvaluationException("Evaluation with id: "+evaluationId+" was not found!");

    }

    public static EvaluationException canNotFindBlockEval(Integer blockNumber) {
        return new EvaluationException("Evaluation for block "+blockNumber+" is not found!");

    }
}