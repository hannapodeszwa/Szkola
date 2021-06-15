package pl.polsl.controller.administratorActions;
import pl.polsl.model.UserModel;

import java.util.Random;

public interface CredentialsGenerator {
    default String GenerateLogin(String imie, String nazwisko) {
        String generatedLogin = (imie.length() >= 4 ? imie.substring(0,4) : imie) + (nazwisko.length() >= 3 ? nazwisko.substring(0,3) : nazwisko);
        Random rand = new Random();
        while (true) {
        Integer num = rand.nextInt(900) + 100;
        if ((new UserModel()).getUserByLogin(generatedLogin + num) == null)
            return generatedLogin + num;
        }
    }
    default String GeneratePassword() {
        String generatedLogin = "";
        String letters = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rand = new Random();
        for (int i = 0; i < 8; i++) {
            Integer index = rand.nextInt(letters.length());
            generatedLogin += letters.charAt(index);
        }
        return generatedLogin;
    }
    default void SendCredentialsByEmail(String login, String password) {
        //todo()
    }
}
