package pl.polsl.model;

public abstract class Queries {

    static final String GET_USER_BY_LOGIN_AND_PASSWORD = "SELECT C FROM Uzytkownicy C WHERE C.login = :LOGIN AND C.haslo = :PASSWORD";
}
