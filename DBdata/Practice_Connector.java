package DBdata;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class Practice_Connector {

    public static Statement createStatement() {
        String user = "root",
                password = "",
                url = "jdbc:mysql://localhost:3306/registration";
        Statement st2 = null;
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Driver loaded");
            st2 = conn.createStatement();
            System.out.println("Statement created");
            System.out.println(st2);
            // String Query2 = "DROP TABLE IF EXISTS Register";
            // st2.execute(Query2);
            // String Query2 = "DROP TABLE IF EXISTS department";
            // st2.execute(Query2);
            // String Query2 = "DROP TABLE IF EXISTS identity";
            // st2.execute(Query2);
            // String Query2 = "DROP TABLE IF EXISTS Facaulty";
            // st2.execute(Query2);
            // String Query2 = "TRUNCATE TABLE IF EXISTS department";
            // st2.execute(Query2);
            // System.out.println("Table Droped!");
            String query = "CREATE TABLE IF NOT EXISTS register(Firstname VARCHAR (50),Lastname VARCHAR(50),Other_names VARCHAR(20),Date_Of_Birth DATE,Gender VARCHAR(20),Registration_Number VARCHAR (50)Primary key)";
            String query2 = "CREATE TABLE IF NOT EXISTS identity(Registration_Number VARCHAR (50),Matric_Number VARCHAR (50) ,Facaulty VARCHAR (50),Department VARCHAR (50), FOREIGN KEY (Registration_Number) REFERENCES register(Registration_Number))";
            String query3 = "CREATE TABLE IF NOT EXISTS Facaulty(Facaultytid int UNIQUE KEY,Facaultyname varchar (20))";
            String query4 = "create TABLE IF NOT EXISTS department(deptid int UNIQUE KEY,Science Varchar (30),Art Varchar (30),Agriculture_Forestry Varchar (30),Pharmacy Varchar (30),Social_Science Varchar (30))";
            String query7 = "CREATE TABLE IF NOT EXISTS acess(Username varchar(30), Administrative_Pin int, FOREIGN key (Username) REFERENCES administrative(Username))";
            String query8 = "CREATE TABLE IF NOT EXISTS administrative(Firstname VARCHAR (50),lastname VARCHAR (50),Phone_Number VARCHAR (20) UNIQUE key,Username VARCHAR (50) Primary key, Password VARCHAR(20),Gender VARCHAR (20))";

            String query5 = "INSERT INTO Facaulty VALUES(1,'Science'),(2,'Art'),(3,'Agriculture_Forestry'),(4,'Pharmacy'),(5,'Social_Science')";
            String query6 = "INSERT INTO department VALUES(1,'Computer Science','Literature','Fishing','Bio Chemistry','social Networking'),(2,'Science','Science','Science','Science','Science')";
            st2.execute(query);
            // System.out.println("Register Table created!");
            st2.execute(query2);
            // System.out.println("Identity Table created!");
            st2.execute(query3);
            // System.out.println("Facaulty Table created!");
            st2.execute(query4);
            // System.out.println("Administrative Table created!");
            st2.execute(query8);
            // System.out.println("access Table created!");
            st2.execute(query7);
            // System.out.println("Department Table created!");
            // st2.execute(query5);
            // st2.execute(query6);

            System.out.println("Query executed");
        } catch (SQLException e) {
            System.out.println("Error occured: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Server Communications Failure!", "Error Occured!", 0);
            System.exit(0);
        }
        return st2;
    }

}
