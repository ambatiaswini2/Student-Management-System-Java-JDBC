package com.student;
import java.sql.*;
import java.util.Scanner;

public class StudentManagementSYstem {

    static Connection con = null;
    static Scanner sc = new Scanner(System.in);

    // Connect Database
    public static void connectDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/studentdb",
                    "root",
                    "your passowrd"
            );

            System.out.println("Database Connected Successfully");

        } catch (Exception e) {
            System.out.println("Database Connection Failed!");
            e.printStackTrace();
        }
    }

    // Add Student
    public static void addStudent() {
        try {
            System.out.print("Enter ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Age: ");
            int age = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Course: ");
            String course = sc.nextLine();

            System.out.print("Enter Email: ");
            String email = sc.nextLine();

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO student(s_id, name, age, course, email) VALUES(?,?,?,?,?)"
            );

            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setInt(3, age);
            ps.setString(4, course);
            ps.setString(5, email);

            int n = ps.executeUpdate();
            System.out.println(n + " Record Inserted Successfully");

        } catch (Exception e) {
            System.out.println("Error while adding student");
            e.printStackTrace();
        }
    }

    // View Students
    public static void viewStudents() {
        try {
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM student");

            System.out.println("\n--- Student Records ---");

            while (rs.next()) {
                System.out.println(
                        rs.getInt("s_id") + " | " +
                        rs.getString("name") + " | " +
                        rs.getInt("age") + " | " +
                        rs.getString("course") + " | " +
                        rs.getString("email")
                );
            }

        } catch (Exception e) {
            System.out.println("Error while fetching students");
            e.printStackTrace();
        }
    }

    // Search Student
    public static void searchStudent() {
        try {
            System.out.print("Enter Student ID: ");
            int id = sc.nextInt();

            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM student WHERE s_id=?"
            );

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println(
                        rs.getInt("s_id") + " | " +
                        rs.getString("name") + " | " +
                        rs.getInt("age") + " | " +
                        rs.getString("course") + " | " +
                        rs.getString("email")
                );
            } else {
                System.out.println("Student Not Found");
            }

        } catch (Exception e) {
            System.out.println("Error while searching student");
            e.printStackTrace();
        }
    }

    // Update Student
    public static void updateStudent() {
        try {
            System.out.print("Enter Student ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter New Course: ");
            String course = sc.nextLine();

            System.out.print("Enter New Email: ");
            String email = sc.nextLine();

            PreparedStatement ps = con.prepareStatement(
                    "UPDATE student SET course=?, email=? WHERE s_id=?"
            );

            ps.setString(1, course);
            ps.setString(2, email);
            ps.setInt(3, id);

            int n = ps.executeUpdate();
            System.out.println(n + " Record Updated Successfully");

        } catch (Exception e) {
            System.out.println("Error while updating student");
            e.printStackTrace();
        }
    }

    // Delete Student
    public static void deleteStudent() {
        try {
            System.out.print("Enter Student ID: ");
            int id = sc.nextInt();

            PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM student WHERE s_id=?"
            );

            ps.setInt(1, id);

            int n = ps.executeUpdate();
            System.out.println(n + " Record Deleted Successfully");

        } catch (Exception e) {
            System.out.println("Error while deleting student");
            e.printStackTrace();
        }
    }

    // Main Method
    public static void main(String[] args) {

        connectDB();

        while (true) {
            System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Search Student");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Exit");

            System.out.print("Enter Choice: ");
            int choice = sc.nextInt();

            switch (choice) {

                case 1 -> addStudent();
                case 2 -> viewStudents();
                case 3 -> searchStudent();
                case 4 -> updateStudent();
                case 5 -> deleteStudent();

                case 6 -> {
                    try {
                        if (con != null) con.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    sc.close();
                    System.out.println("Thank You!");
                    System.exit(0);
                }

                default -> System.out.println("Invalid Choice!");
            }
        }
    }
}
