package HospitalManagement;

import java.sql.*;
import java.util.*;

public class Driver {
	public static final String url = "jdbc:mysql://localhost:3306/hospital";
	public static final String userName = "root";
	public static final String password = "Tiger";
	
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			
		}
		Scanner sc = new Scanner(System.in);
		try {
			Connection con = DriverManager.getConnection(url,userName,password);
			Patient patient = new Patient(con,sc);
			Doctors doctor = new Doctors(con);
			while(true) {
				System.out.println("-:HOSPITAL MANAGEMENT SYSTEM:-");
				System.out.println("1. ADD PATIENT");
				System.out.println("2. VIEW PATIENT");
				System.out.println("3. VIEW DOCTORS");
				System.out.println("4. FIX APPOINTMENT");
				System.out.println("5. EXIT");
				System.out.println("ENTER YOUR CHOICE:- ");
				int choice = sc.nextInt();
				
				switch(choice) {
				case 1:
					//add patient
					patient.addPatient();
					System.out.println();
					break;
				case 2:
					//view patient
					patient.viewPatient();
					System.out.println();
					break;
				case 3:
					//view doctors
					doctor.viewDoctor();
					System.out.println();
					break;
				case 4:
					//book appointment
					bookAppointment(patient,doctor,con,sc);
					System.out.println();
					break;
				case 5:
					System.out.println("Exiting From Program:-");
					return;
				default:
					System.out.println("Enter valid choice-");
					
				}
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void bookAppointment(Patient patient, Doctors doctor,Connection con, Scanner sc) {
		System.out.print("Enter Patient Id:- ");
		int patientId = sc.nextInt();
		System.out.print("Enter Doctors Id:- ");
		int docId = sc.nextInt();
		System.out.print("Enter Appointment Date (YYYY-MM-DD):- ");
		String appoDate = sc.next();
		if(patient.getPatientById(patientId) && doctor.getDoctorById(docId)) {
			if(checkDoctorAvailable(docId,appoDate,con)) {
				String query = "INSERT INTO APPOINTMENTS (PATIENT_ID, DOCTOR_ID, APPOINTMENT_DATE) VALUES (?,?,?)";
				try {
					PreparedStatement stm = con.prepareStatement(query);
					stm.setInt(1, patientId);
					stm.setInt(2, docId);
					stm.setString(3, appoDate);
					int ra = stm.executeUpdate();
					if (ra > 0) {
						System.out.println("Appointment Booked Successfully.");
					} else {
						System.out.println("Appointment Booked Successfully.");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("Doctor is not available on this date.");
			}
		} else {
			System.out.println("Either doctor or patient doesn't exist!!!");
		}
	}
	public static boolean checkDoctorAvailable(int docId, String appoDate, Connection con) {
		String query = "SELECT COUNT(*) FROM APPOINTMENTS WHERE DOCTOR_ID = ? AND APPOINTMENT_DATE = ?";
		try {
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, docId);
			pst.setString(2, appoDate);
			ResultSet rst = pst.executeQuery();
			if(rst.next()) {
				int count = rst.getInt(1);
				if(count == 0) {
					return true;
				} else {
					return false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
