package controller;

public class Variables {
    public static String USERNAME = "timterwijn";
    public static String PASSWORD = "timterwijn";
    public static String DATABASE_NAME = "template_database";
    public static String URL = String.format("jdbc:mysql://localhost/%s?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", DATABASE_NAME);
}