package HospitalManagement;

import java.sql.*;
import java.util.*;

public class Patient {
	private Connection con;
	private Scanner sc;
	
	public Patient (Connection con, Scanner sc) {
		this.con = con;
		this.sc = sc;
	}
	public void addPatient() {
		System.out.print("Enter Patient Name:- ");
		String name = sc.next();
		System.out.print("Enter Patenet Age:- ");
		int age = sc.nextInt();
		System.out.print("Enter Patenet Gender:- ");
		String gender = sc.next();
		
		try {
			String query = "INSERT INTO PATIENT(NAME, AGE, GENDER) VALUES (?,?,?)";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1,name);
			pst.setInt(2, age);
			pst.setString(3, gender);
			
			int ar = pst.executeUpdate();
			if(ar > 0) {
				System.out.println("Patient Added Successfully.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void viewPatient() {
		String query = "SELECT * FROM PATIENT";
		try {
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rst = pst.executeQuery();
			System.out.println("Patients:-");
			System.out.println("+------------+--------------------+----------+-----------+");
			System.out.println("| Patient ID | Name               | Age      | Gender    |");
			System.out.println("+------------+--------------------+----------+-----------+");
			while (rst.next()) {
				int id = rst.getInt("ID");
				String name = rst.getString("NAME");
				int age = rst.getInt("AGE");
				String gender = rst.getString("GENDER");
				System.out.printf("| %-10s | %-18s | %-8s | %-10s |\n",id,name,age,gender);
				System.out.println("+------------+--------------------+----------+-----------+");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public boolean getPatientById(int id) {
		String query = "SELECT * FROM PATIENT WHERE ID = ?";
		try {
			PreparedStatement stm = con.prepareStatement(query);
			stm.setInt(1, id);
			ResultSet rst = stm.executeQuery();
			if (rst.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
