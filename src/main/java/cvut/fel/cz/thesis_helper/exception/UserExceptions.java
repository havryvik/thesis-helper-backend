package cvut.fel.cz.thesis_helper.exception;

import cvut.fel.cz.thesis_helper.model.Account;

public class UserExceptions extends AbstractException{
    public UserExceptions(String message) {
        super(message);
    }

    public static UserExceptions studentNotFoundException(String email){
        return new UserExceptions("Student with email: "+email+" was not found!");
    }
    public static UserExceptions userNotFoundException(String email){
        return new UserExceptions("User with email: "+email+" was not found!");
    }

    public static UserExceptions supervisorNotFoundException(String email){
        return new UserExceptions("Supervisor with email: "+email+" was not found!");
    }

    public static UserExceptions userNotFoundById(Integer id){
        return new UserExceptions("User with id: "+id+" was not found!");
    }

    public static UserExceptions userAlreadyExists(String email) {
        return new UserExceptions("User with email: "+email+" already exists!");
    }

    public static UserExceptions passwordsDoesNotMatch() {
        return new UserExceptions("Passwords does not match!");
    }

    public static UserExceptions studentIsAlreadyAdded(String email) {
        return new UserExceptions("Student "+email+" is already in your student list!");
    }

    public static UserExceptions studentIsNotAdded(String email) {
        return new UserExceptions("Student "+email+" is not in your student list!");
    }

    public static UserExceptions accessDeniedException() {
        return new UserExceptions("Access denied exception");
    }


}
