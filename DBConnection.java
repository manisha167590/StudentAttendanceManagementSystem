    import java.sql.Connection;
    import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {

        try {
            return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/student_attendance",
                "root",
System.getenv("DB_PASSWORD")
            );

        } catch (Exception e) {
            System.out.println("Unable to connect to the database!");
            e.printStackTrace();
            return null;
        }
    }
}

