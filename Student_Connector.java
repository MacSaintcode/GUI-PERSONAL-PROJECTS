

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Student_Connector {

	public static Statement createStatement() {
        String user = "root",
        password = "",
        url = "jdbc:mysql://localhost:3306/matriculation";
        Statement st2 = null;
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Driver loaded");
            st2 = conn.createStatement();
            System.out.println("Statement created");
            System.out.println(st2);
            String query = "CREATE TABLE IF NOT EXISTS Registration(Fullname VARCHAR (50),DATE_OF_BIRTH DATE,Gender VARCHAR (20),Faculty VARCHAR(30),Departmant VARCHAR(30),Registration_Number VARCHAR(30),Matric_Number VARCHAR(30),Date DATE)";
            st2.execute(query);
            System.out.println("Query executed");
        } catch (SQLException e) {
            System.out.println("Error occured: " + e.getMessage());
        }
        return st2;
    }
}

