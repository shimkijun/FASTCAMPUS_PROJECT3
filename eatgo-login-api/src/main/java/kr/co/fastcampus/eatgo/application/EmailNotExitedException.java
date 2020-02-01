package kr.co.fastcampus.eatgo.application;

public class EmailNotExitedException extends RuntimeException{

    public EmailNotExitedException(String email) {
        super("Email is not registered " + email);
    }
}
