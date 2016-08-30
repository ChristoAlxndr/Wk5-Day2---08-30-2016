package com;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ZooMethods {

	Scanner scan = new Scanner(System.in);
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/?user=root&autoReconnect=true&useSSL=false";
	static final String USER = "root";
	static final String PASSWORD = "root";

	static Connection CONN = null;
	static Statement STMT = null;
	static PreparedStatement PREP_STMT = null;
	static ResultSet RES_SET = null;

	public static void connectToDB() {

		try {
			
			Class.forName(JDBC_DRIVER);
			
			System.out.println("Attempting connection with Database...");
			CONN = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			System.out.println("Connected to Database.");

		} catch (Exception e) {
			System.out.println("Connection Failed. YOU LOSE.");
		}
	}

	public static void readFromDB() {

		connectToDB();

		ArrayList<Animals> ourAnimals = new ArrayList<>();

		try {
			STMT = CONN.createStatement();
			RES_SET = STMT.executeQuery("SELECT * FROM xanderzoo.zooanimals;");

			while (RES_SET.next()) {
				Animals animalInDB = new Animals();

				animalInDB.setAnimalID(RES_SET.getString("animal_ID"));
				animalInDB.setSpecies(RES_SET.getString("species"));
				animalInDB.setAnimalName(RES_SET.getString("name"));
				animalInDB.setHabitat(RES_SET.getString("habitat"));
				animalInDB.setDiet(RES_SET.getString("diet"));
				animalInDB.setWeight(RES_SET.getString("weight"));
				animalInDB.setAge(RES_SET.getString("age"));

				ourAnimals.add(animalInDB);
			}

			for (Animals animal : ourAnimals) {
				System.out.println(animal.toString());
			}

		} catch (Exception e) {
		}
	}

	public static void writeToDB(Animals animal) {

		Animals animalToAdd = new Animals();

		animalToAdd = animal;

		connectToDB();

		try {
			PREP_STMT = CONN.prepareStatement(insertToDB);

			PREP_STMT.setString(1, animalToAdd.getSpecies());
			PREP_STMT.setString(2, animalToAdd.getAnimalName());
			PREP_STMT.setString(3, animalToAdd.getHabitat());
			PREP_STMT.setString(4, animalToAdd.getDiet());
			PREP_STMT.setString(5, animalToAdd.getWeight());
			PREP_STMT.setString(6, animalToAdd.getAge());

			PREP_STMT.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("\n\n");
		readFromDB();
	}

	public static String insertToDB = "INSERT INTO `xanderzoo`.`zooanimals`"
			+ "(species, name, habitat, diet, weight, age)" + " VALUES " + "(?, ?, ?, ?, ?, ?)";

	public static void deleteByID() {

		Animals animalToDelete = new Animals();
		animalToDelete = aboutTheAnimalID();

		connectToDB();

		try {
			PREP_STMT = CONN.prepareStatement(deleteByID);

			PREP_STMT.setInt(1, Integer.parseInt(animalToDelete.getAnimalID()));

			PREP_STMT.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("\n\n");
		readFromDB();
	}
	
	private static Animals aboutTheAnimalID() {
		
		Scanner scan = new Scanner(System.in);
		
		Animals animalID = new Animals();
		
		System.out.println("What is the animal's ID #?");
		animalID.setAnimalID(scan.nextLine());
		
		return animalID;
	}

	private static String deleteByID = "DELETE FROM `xanderzoo`.`zooanimals`" + "WHERE animal_ID=?";

	public static void deleteFullInfo() {

		Animals animalToDelete = new Animals();

		animalToDelete = aboutTheAnimal();

		connectToDB();

		try {
			PREP_STMT = CONN.prepareStatement(deleteFullInfo);

			PREP_STMT.setString(1, animalToDelete.getSpecies());
			PREP_STMT.setString(2, animalToDelete.getAnimalName());
			PREP_STMT.setString(3, animalToDelete.getHabitat());
			PREP_STMT.setString(4, animalToDelete.getDiet());
			PREP_STMT.setString(5, animalToDelete.getWeight());
			PREP_STMT.setString(6, animalToDelete.getAge());

			PREP_STMT.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("\n\n");
		readFromDB();
	}

	private static String deleteFullInfo = "DELETE FROM `xanderzoo`.`zooanimals`"
			+ "WHERE species=? AND name=? AND habitat=? AND diet=? AND weight=? AND age=?";

	private static Animals aboutTheAnimal() {

		Scanner scan = new Scanner(System.in);

		Animals animalToAdd = new Animals();

		System.out.println("What is the animal's species?");
		animalToAdd.setSpecies(scan.nextLine());

		System.out.println("What is the animal's name?");
		animalToAdd.setAnimalName(scan.nextLine());

		System.out.println("What is the animal's habitat?");
		animalToAdd.setHabitat(scan.nextLine());

		System.out.println("What is the animal's diet?");
		animalToAdd.setDiet(scan.nextLine());

		System.out.println("What is the animal's weight?");
		animalToAdd.setWeight(scan.nextLine());

		System.out.println("What is the animal's age?");
		animalToAdd.setAge(scan.nextLine());

		return animalToAdd;

	}
	
	
	
	public static void updateByID() {
		
		Animals animalToUpdate = new Animals();
		
		animalToUpdate = aboutTheAnimalUpdate();
		
		connectToDB();
		
		try {
			PREP_STMT = CONN.prepareStatement(updateByID);
			
			PREP_STMT.setString(1, animalToUpdate.getSpecies());
			PREP_STMT.setString(2, animalToUpdate.getAnimalName());
			PREP_STMT.setString(3, animalToUpdate.getHabitat());
			PREP_STMT.setString(4, animalToUpdate.getDiet());
			PREP_STMT.setString(5, animalToUpdate.getWeight());
			PREP_STMT.setString(6, animalToUpdate.getAge());
			PREP_STMT.setInt(7, Integer.parseInt(animalToUpdate.getAnimalID()));

			PREP_STMT.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("\n\n");
		readFromDB();
	}
	
	
	private static String updateByID = "UPDATE `xanderzoo`.`zooanimals`" + 
			"SET species=?, name=?, habitat=?, diet=?, weight=?, age=? WHERE animal_ID=?";
	
	
	private static Animals aboutTheAnimalUpdate() {

		Scanner scan = new Scanner(System.in);

		Animals animalToUpdate = new Animals();
		
		System.out.println("What is the animal's ID to update?");
		animalToUpdate.setAnimalID(scan.nextLine());

		System.out.println("What is the animal's species?");
		animalToUpdate.setSpecies(scan.nextLine());

		System.out.println("What is the animal's name?");
		animalToUpdate.setAnimalName(scan.nextLine());

		System.out.println("What is the animal's habitat?");
		animalToUpdate.setHabitat(scan.nextLine());

		System.out.println("What is the animal's diet?");
		animalToUpdate.setDiet(scan.nextLine());

		System.out.println("What is the animal's weight?");
		animalToUpdate.setWeight(scan.nextLine());

		System.out.println("What is the animal's age?");
		animalToUpdate.setAge(scan.nextLine());

		return animalToUpdate;

	}
	
}