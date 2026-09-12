import java.util.Scanner;
import java.sql.*;
    public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        while (true) {

            System.out.println();
            System.out.println("==============================================");
            System.out.println("       STUDENT ATTENDANCE MANAGEMENT");
            System.out.println("==============================================");

            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Mark Attendance");
            System.out.println("4. View Attendance");
            System.out.println("5. View Attendance Percentage");
            System.out.println("6. Search Student");
            System.out.println("7. Update Student");
            System.out.println("8. Delete Student");
            System.out.println("9. Exit");

            System.out.println("==============================================");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            // 1. Add Student
if (choice == 1) {

    System.out.print("Enter student ID: ");
    String id = sc.nextLine();

    System.out.print("Enter student name: ");
    String name = sc.nextLine();

    try {
        Connection con = DBConnection.getConnection();

        String sql = "INSERT INTO students (id, name) VALUES (?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, id);
        ps.setString(2, name);

        ps.executeUpdate();

        System.out.println("Student added successfully!");

        ps.close();
        con.close();

    } catch (SQLException e) {
      System.out.println("Student ID already exists or invalid data!");  
        e.printStackTrace();
    }
}
    // 2. View Students
else if (choice == 2) {

    try {
        Connection con = DBConnection.getConnection();

        String sql = "SELECT id, name FROM students";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        System.out.println();
        System.out.println("Student List");
        System.out.println("----------------------------------------------");
        System.out.println("ID\t\tName");
        System.out.println("----------------------------------------------");

        boolean found = false;

        while (rs.next()) {
            found = true;

            System.out.println(
                rs.getString("id") + "\t\t" +
                rs.getString("name")
            );
        }

        if (!found) {
            System.out.println("No students found!");
        }

        System.out.println("----------------------------------------------");

        rs.close();
        ps.close();
        con.close();

    } catch (SQLException e) {
        System.out.println("Error viewing students!");
        e.printStackTrace();
    }
}
            // 3. Mark Attendance
else if (choice == 3) {

    System.out.print("Enter student ID: ");
    String id = sc.nextLine();

    System.out.print("Enter attendance (Present/Absent): ");
    String attendance = sc.nextLine();

    if (!attendance.equalsIgnoreCase("Present")
            && !attendance.equalsIgnoreCase("Absent")) {

        System.out.println("Invalid attendance! Enter Present or Absent.");
        continue;
    }

    System.out.print("Enter date (DD/MM/YYYY): ");
    String date = sc.nextLine();

    try {
        Connection con = DBConnection.getConnection();

        String sql = "INSERT INTO attendance (student_id, status, date) VALUES (?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, id);
        ps.setString(2, attendance);
        ps.setString(3, date);

        ps.executeUpdate();

        System.out.println("Attendance marked successfully!");

        ps.close();
        con.close();

    } catch (SQLException e) {
        System.out.println("Error marking attendance!");
        e.printStackTrace();
    }
}
            
     // 4. View Attendance
else if (choice == 4) {

    try {
        Connection con = DBConnection.getConnection();

        String sql = "SELECT a.student_id, s.name, a.status, a.date " +
                     "FROM attendance a " +
                     "JOIN students s ON a.student_id = s.id";

        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        System.out.println();
        System.out.println("Attendance Details");
        System.out.println("----------------------------------------------");

        boolean found = false;

        while (rs.next()) {
            found = true;

            System.out.println(
                rs.getString("student_id") + "\t" +
                rs.getString("name") + "\t" +
                rs.getString("status") + "\t" +
                rs.getString("date")
            );
        }

        if (!found) {
            System.out.println("No attendance records found!");
        }

        System.out.println("----------------------------------------------");

        rs.close();
        ps.close();
        con.close();

    } catch (SQLException e) {
        System.out.println("Error viewing attendance!");
        e.printStackTrace();
    }
}
        // 5. Attendance Percentage
else if (choice == 5) {

    try {
        Connection con = DBConnection.getConnection();

        String sql = "SELECT s.id, s.name, " +
                     "COUNT(a.student_id) AS total_classes, " +
                     "SUM(CASE WHEN a.status = 'Present' THEN 1 ELSE 0 END) AS present_classes " +
                     "FROM students s " +
                     "LEFT JOIN attendance a ON s.id = a.student_id " +
                     "GROUP BY s.id, s.name";

        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        System.out.println();
        System.out.println("Attendance Percentage");
        System.out.println("----------------------------------------------");

        while (rs.next()) {

            int totalClasses = rs.getInt("total_classes");
            int presentClasses = rs.getInt("present_classes");

            double percentage = 0;

            if (totalClasses > 0) {
                percentage = (presentClasses * 100.0) / totalClasses;
            }

            System.out.printf(
                "%s\t%s\t%.2f%%%n",
                rs.getString("id"),
                rs.getString("name"),
                percentage
            );
        }

        System.out.println("----------------------------------------------");

        rs.close();
        ps.close();
        con.close();

    } catch (SQLException e) {
        System.out.println("Error calculating attendance percentage!");
        e.printStackTrace();
    }
} // 5. Attendance Percentage
else if (choice == 5) {

    try {
        Connection con = DBConnection.getConnection();

        String sql = "SELECT s.id, s.name, " +
                     "COUNT(a.student_id) AS total_classes, " +
                     "SUM(CASE WHEN a.status = 'Present' THEN 1 ELSE 0 END) AS present_classes " +
                     "FROM students s " +
                     "LEFT JOIN attendance a ON s.id = a.student_id " +
                     "GROUP BY s.id, s.name";

        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        System.out.println();
        System.out.println("Attendance Percentage");
        System.out.println("----------------------------------------------");

        while (rs.next()) {

            int totalClasses = rs.getInt("total_classes");
            int presentClasses = rs.getInt("present_classes");

            double percentage = 0;

            if (totalClasses > 0) {
                percentage = (presentClasses * 100.0) / totalClasses;
            }

            System.out.printf(
                "%s\t%s\t%.2f%%%n",
                rs.getString("id"),
                rs.getString("name"),
                percentage
            );
        }

        System.out.println("----------------------------------------------");

        rs.close();
        ps.close();
        con.close();

    } catch (SQLException e) {
        System.out.println("Error calculating attendance percentage!");
        e.printStackTrace();
    }
}
         // 6. Search Student
else if (choice == 6) {

    System.out.print("Enter student ID: ");
    String id = sc.nextLine();

    try {
        Connection con = DBConnection.getConnection();

        String sql = "SELECT id, name FROM students WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            System.out.println();
            System.out.println("Student Found!");
            System.out.println("ID: " + rs.getString("id"));
            System.out.println("Name: " + rs.getString("name"));
        } else {
            System.out.println("Student not found!");
        }

        rs.close();
        ps.close();
        con.close();

    } catch (SQLException e) {
        System.out.println("Error searching student!");
        e.printStackTrace();
    }
}
            // 7. Update Student
else if (choice == 7) {

    System.out.print("Enter student ID: ");
    String id = sc.nextLine();

    System.out.print("Enter new student name: ");
    String newName = sc.nextLine();

    try {
        Connection con = DBConnection.getConnection();

        String sql = "UPDATE students SET name = ? WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, newName);
        ps.setString(2, id);

        int rows = ps.executeUpdate();

        if (rows > 0) {
            System.out.println("Student updated successfully!");
        } else {
            System.out.println("Student not found!");
        }

        ps.close();
        con.close();

    } catch (SQLException e) {
        System.out.println("Error updating student!");
        e.printStackTrace();
    }
}
// 8. Delete Student
else if (choice == 8) {

    System.out.print("Enter student ID: ");
    String id = sc.nextLine();

    try {
        Connection con = DBConnection.getConnection();

        String sql = "DELETE FROM students WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, id);

        int rows = ps.executeUpdate();

        if (rows > 0) {
            System.out.println("Student deleted successfully!");
        } else {
            System.out.println("Student not found!");
        }

        ps.close();
        con.close();

    } catch (SQLException e) {
        System.out.println("Error deleting student!");
        e.printStackTrace();
    }
}
            // 9. Exit
            else if (choice == 9) {

                System.out.println("Thank you for using the system!");
                System.out.println("Exiting...");
                break;
            }

            // Invalid Choice
            else {

                System.out.println("Invalid choice!");
            }
        }

        sc.close();
    }
}