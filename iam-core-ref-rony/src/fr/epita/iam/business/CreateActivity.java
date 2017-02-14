/**
 * 
 */
package fr.epita.iam.business;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.services.FileIdentityDAO;
import fr.epita.iam.services.IdentityJDBCDAO;

/**
 * @author tbrou
 *
 */
public class CreateActivity {
	
	/**
	 * create a user
	 * @param scanner
	 */
	public static void execute(Scanner scanner){	//add the information of the new user
		System.out.println("Identity Creation");
		System.out.println("please input the displayName");
		String displayName = scanner.nextLine();
		System.out.println("please input the email address");
		String email = scanner.nextLine();
		System.out.println("please input the birthdate with this format: yyyy-MM-dd");
		String date = scanner.nextLine();
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		Identity identity = null;
		try {
			calendar.setTime(sdf.parse(date));
			identity = new Identity("", displayName, email, date);
		} catch (ParseException e) {
			System.out.println("Icorrect date format");
		}
		
		if(identity != null){
			IdentityJDBCDAO identityInsert = new IdentityJDBCDAO();
			try {
				//insert user into database
				identityInsert.write(identity);
				//persist the identity somewhere
				System.out.println("this is the identity you created");
				FileIdentityDAO identityWriter = new FileIdentityDAO("tests.txt");
				identityWriter.write(identity);
				System.out.println("creation Done");
			} catch (SQLException e) {
				System.out.println("wrong information");
			}
		}

	}
	
	/**
	 * checks uid existence and return the object identity
	 * @param identityJDBCDAO
	 * @param scanner
	 * @return
	 */
	private static Identity checkUser(IdentityJDBCDAO identityJDBCDAO,Scanner scanner){
        int id = 0;
        Identity ident = null;
		try {	//checks if there are users in the database
			List<Identity> identities = identityJDBCDAO.readAllIdentities(); //prints all the users
	        for(Identity identity : identities) {
	            System.out.println(identity.toString());
	        }
			System.out.println("Enter the user id:");
			try{	//checks if the id entered is an integer
				id = scanner.nextInt();
				int exists = 0;
				while(exists == 0){	//checks if the id corresponds to a user
				    for(Identity identity : identities) {
				       	int dbId = Integer.parseInt(identity.getUid());
				       	if(dbId == id){
				       		exists = 1;
				       		ident = identity; 
				       		break;
				       	}
				    }
				    if(exists == 0){
				      	System.out.println("Enter a correct id");
				       	id = scanner.nextInt();
				    }
				}
			}catch(InputMismatchException exception){
				System.out.println("This is not an integer");
			}
			
		} catch (SQLException e) {
			System.out.println("No users found");
		}
		return ident;
	}
	
	/**
	 * modify a user
	 * @param scanner
	 */
	public static void modifyUser(Scanner scanner){
		IdentityJDBCDAO identityUpdate = new IdentityJDBCDAO();
		Identity identity = checkUser(identityUpdate,scanner);
		if(identity != null){
			try {
				//persist the identity somewhere
				System.out.println("this is the identity you're about to update:");
				FileIdentityDAO identityWriter = new FileIdentityDAO("tests.txt");
				identityWriter.write(identity);
				//update menu
				System.out.println("Please select an action :");
				System.out.println("a. change name");
				System.out.println("b. change email");
				System.out.println("c. change birthdate");
				System.out.println("d. quit");
				Scanner scan = new Scanner(System.in);

				String choice = scan.nextLine();
				
				while(!choice.equals("d")){
					switch (choice) {
					case "a":
						System.out.println("enter new name:");
						String displayName = scan.nextLine();
						identity.setDisplayName(displayName);
						break;
					case "b":
						System.out.println("enter new email:");
						String email = scan.nextLine();
						identity.setEmail(email);
						break;
						
					case "c":
						System.out.println("enter new birthdate with this format yyyy-MM-dd:");
						String date = scan.nextLine();
						Calendar calendar = Calendar.getInstance();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
						try {
							calendar.setTime(sdf.parse(date));
							identity.setDate(date);
						} catch (ParseException e) {
							System.out.println("Icorrect date format");
						}
						break;
						
					case "d":
						//Quit
						break;
						
					default:
						System.out.println("Your choice is not recognized");
						break;
					}
					System.out.println("Please select an action :");
					choice = scan.nextLine();
				}
				//insert user into database
				identityUpdate.update(identity);
			} catch (SQLException e) {
				System.out.println("wrong information");
			}
		}
	}
	
	/**
	 * delete a user
	 * @param scanner
	 */
	public static void deleteUser(Scanner scanner){
		IdentityJDBCDAO identityDelete = new IdentityJDBCDAO();
		Identity identity = checkUser(identityDelete,scanner);
		
		//persist the identity somewhere
		System.out.println("this is the identity you're about to delete:");
		FileIdentityDAO identityWriter = new FileIdentityDAO("tests.txt");
		identityWriter.write(identity);
		
		try {
			identityDelete.delete(identity);
		} catch (SQLException e) {
			System.out.println("there has been a problem please try again later");
		}
	}
}
