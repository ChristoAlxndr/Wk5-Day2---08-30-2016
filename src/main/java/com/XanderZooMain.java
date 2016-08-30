package com;

import java.util.Scanner;

public class XanderZooMain {

	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		
		String userInput = null;
		boolean menuCorrect = false;
		
		System.out.println("Welcome to the Xander Zoo");
		
		do {
			System.out.println("Press 1 to read from the animal list from the database \n" + 
					"Press 2 to add an animal to the database \n" + 
					"Press 3 to delete an animal from the database with the animal's full info \n" +
					"Press 4 to delete by the animal's ID # \n" +
					"Press 5 to update the animal's information.");
			userInput = scan.nextLine();
			
			switch (userInput) {
			case "1":
				ZooMethods.readFromDB();
				break;

			case "2":
//				ZooMethods.writeToDB();
				break;
				
			case "3":
				ZooMethods.deleteFullInfo();
				break;
				
			case "4":
				ZooMethods.deleteByID();
				break;
				
			case "5":
				ZooMethods.updateByID();
				break;

			default:
				System.out.println("You have entered an invalid option.");
				menuCorrect = true;

				break;
			}
			
		} while (menuCorrect);
		
	}

}
