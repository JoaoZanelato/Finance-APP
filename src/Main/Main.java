package Main;

import dao.Connect;
import ui.Card;

import java.sql.SQLException;
import java.text.ParseException;

public class  Main {
    public static void main(String[] args) throws SQLException, ParseException {
        Connect conectar = new Connect();
        conectar.criar();
        new Card();
}
}