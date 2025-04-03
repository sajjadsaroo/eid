package example;
import db.*;
import db.exception.*;

public class Main {
    public static void main(String[] args) {
        Human ali = new Human("Ali");
        Database.add(ali);

        ali.name = "Ali Hosseini";

        Human aliFromTheDatabase = (Human) Database.get(ali.id);

        System.out.println("ali's name in the database: " + aliFromTheDatabase.name);
    }
}