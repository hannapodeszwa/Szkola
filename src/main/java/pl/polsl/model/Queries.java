package pl.polsl.model;

public abstract class Queries {

    //User
    static final String GET_USER_BY_LOGIN_AND_PASSWORD = "SELECT U FROM Uzytkownicy U WHERE U.login = :LOGIN AND U.haslo = :PASSWORD";

    static final String GET_USER_BY_LOGIN = "SELECT U FROM Uzytkownicy U WHERE U.login = :LOGIN";

    //Verification codes
    static final String GET_VERIFICATION_CODE_DATA_BY_CODE = "SELECT K FROM Kodyweryfikacyjne K WHERE K.kod = :CODE";

    //Parenthood
    static final String GET_PARENTS_BY_CHILD_ID = "SELECT R.idRodzica, R.idUcznia FROM Rodzicielstwo R WHERE R.idRodzica = ?";

    //Parent
    static final String GET_PARENT_EMAIL_BY_ID = "SELECT R.email FROM Rodzice R WHERE R.ID = :ID";
}
