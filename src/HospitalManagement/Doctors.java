package HospitalManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Doctors {
	private Connection con;
	
	public Doctors (Connection con) {
		this.con = con;
	}
	public void viewDoctor() {
		String query = "SELECT * FROM DOCTORS";
		try {
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rst = pst.executeQuery();
			System.out.println("Doctors:-");
			System.out.println("+------------+--------------------+----------------------+");
			System.out.println("| DOCTOR ID  | Name               | SPECIALIZATION       |");
			System.out.println("+------------+--------------------+----------------------+");
			while (rst.next()) {
				int id = rst.getInt("ID");
				String name = rst.getString("NAME");
				String specialization = rst.getString("SPECIALIZATION");
				System.out.printf("| %-10s | %-18s | %-20s |\n",id,name,specialization);
				System.out.println("+------------+--------------------+----------------------+");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public boolean getDoctorById(int id) {
		String query = "SELECT * FROM DOCTORS WHERE ID = ?";
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
