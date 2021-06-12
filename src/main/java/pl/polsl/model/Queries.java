package pl.polsl.model;

public abstract class Queries {

    //SignIn
    static final String GET_USER_BY_LOGIN_AND_PASSWORD = "SELECT C FROM Uzytkownicy C WHERE C.login = :LOGIN AND C.haslo = :PASSWORD";

    //SignUp
    static final String GET_USER_BY_LOGIN = "SELECT C FROM Uzytkownicy C WHERE C.login = :LOGIN";
}
