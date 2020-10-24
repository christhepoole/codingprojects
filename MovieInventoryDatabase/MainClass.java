/* AUTHOR: Chris Poole
 * PURPOSE: Allow users to import inventory files and search inventory to purchase
 * copies of DVDs and track said purchases.
 * CREATEDATE: 12/06/2018
 */

package edu.cpt187.poole.program6;

import java.util.Scanner;

public class MainClass 
{

	final static String MAINMENUOPTIONA = "[A] - Load An Inventory File";
	final static String MAINMENUOPTIONQ = "[Q] - Quit the Program";
	final static String SEARCHMENUOPTIONA = "[A] - Search using DVD ID";
	final static String SEARCHMENUOPTIONB = "[B] - Return to Main Menu";
	final static String PURCHASEMENUOPTIONA = "[A] - Purchase copies of this DVD";
	final static String PURCHASEMENUOPTIONB = "[B] - Return to Search Menu";

	public static void main(String[] args) 
	{		

		String userName = "";

		Scanner input = new Scanner (System.in);
		MovieInventory myMovie = new MovieInventory ();
		WritePurchaseOrders movieWriter = new WritePurchaseOrders ();


		displayWelcomeBanner ();

		userName = getUserName (input);

		//Load Main Menu
		while (validateMainMenu (input) != 'Q')
		{

			//import inventory .dat file
			myMovie.setLoadArrays(validateFileName (input));

			//test to see if file contents were loaded
			if (myMovie.getMovieCount() > 0)
			{ 

				displayFileFound (myMovie.getMovieCount());

				//Load Search Menu
				while (validateSearchMenu (input) != 'B')
				{

					//enter DVD ID for search
					myMovie.setItemIndex(Integer.parseInt(validateSearch(input)));

					//test to see if matching index found
					if (myMovie.getSearchResults() >= 0)
					{
						displaySearchResults (myMovie.getMovieID(), myMovie.getSearchResults(), 
								myMovie.getMovieTitles(), myMovie.getMoviePrices());

						//Load Purchase Menu
						if (validatePurchaseMenu (input) != 'B')
						{
							//take input for how many copies of DVD to purchase
							myMovie.setHowMany (validateHowMany(input));

							//write details of purchase order to masterFile
							movieWriter.setWriteOrder(myMovie.getSearchResults(), myMovie.getMovieID(), 
									myMovie.getMovieTitles(), myMovie.getMoviePrices(), myMovie.getHowMany(), 
									myMovie.getOrderTotal());

							//if movieWriter encounters an error
							if (movieWriter.getRecordCount() == -1)
							{
								displayMovieWriterError ();
								break;
							}

							displayPurchaseNotice (myMovie.getSearchResults(), myMovie.getMovieID(), 
									myMovie.getHowMany(), myMovie.getOrderTotal());							
						}

					}

					else
					{
						displaySearchResults ();
					}
				}

				//test to see if any searches made
				if (myMovie.getSearchCount() > 0 && movieWriter.getRecordCount() != -1)
				{
					displaySearchReport (myMovie.getSearchCount(), myMovie.getFoundSearchCount(), 
							myMovie.getNotFoundSearchCount());

					myMovie.setResetSearchCounters ();
				}

			} 

			else
			{
				displayFileNotFound ();
			}
		}

		//test to see if any file loading attempts made
		if (myMovie.getFileCount() > 0)
		{
			displayFileReport (myMovie.getFileCount(), myMovie.getFoundFileCount(), 
					myMovie.getNotFoundFileCount());

			//test to see if any DVD purchases made
			if (movieWriter.getRecordCount() > 0)
			{
				//read file written when DVDs purchased
				myMovie.setLoadArrays(movieWriter.getMasterFile(), movieWriter.getRecordCount());

				if (myMovie.getMovieCount() == -1)
				{
					displayFileNotFound ();
				}

				else
				{
					//display contents of file
					displayPurchaseReport (myMovie.getMovieID(), myMovie.getMovieTitles(),
							myMovie.getMoviePrices(), myMovie.getMovieHowManys(), myMovie.getMovieTotals(),
							movieWriter.getRecordCount(), myMovie.getOrderTotals());
				}
			}
		}


		displayFarewellMessage (userName);		

		input.close();

	} 

	public static void displayWelcomeBanner ()
	{
		System.out.println("************************************************************");
		System.out.println("Welcome to the Buy DVDs Inventory Purchase Program! This ");
		System.out.println("program will allow you to import your DVD inventory, and ");
		System.out.println("then search for titles by DVD ID and purchase copies. The ");
		System.out.println("program will also store information regarding your purchases ");
		System.out.println("and display it in a report when you choose to quit.");
		System.out.println("************************************************************");		
	}

	public static String getUserName (Scanner borrowedInput)
	{
		String localUserName = "";

		System.out.println("\nBefore we begin, please enter your desired User Name:");

		localUserName = borrowedInput.next();

		System.out.println("Thanks " + localUserName + "! Welcome to our program!\n");

		return localUserName;
	}

	public static char validateMainMenu (Scanner borrowedInput)
	{
		char localMenuSelection = ' ';

		displayMainMenu ();

		localMenuSelection = borrowedInput.next().toUpperCase().charAt(0);

		while (localMenuSelection != 'A' && localMenuSelection != 'Q')
		{
			System.out.println("\nOption not available - ");
			System.out.println("Please enter a valid menu selection:\n");

			displayMainMenu ();

			localMenuSelection = borrowedInput.next().toUpperCase().charAt(0);
		}

		return localMenuSelection;
	}

	public static void displayMainMenu ()
	{
		System.out.println("************************************************************");
		System.out.println("BUY DVDS PLEASE FILE IMPORT MAIN MENU");
		System.out.println("Please choose from one of the following options:");
		System.out.printf("%s\n", MAINMENUOPTIONA);
		System.out.printf("%s\n", MAINMENUOPTIONQ);
		System.out.println("************************************************************");
	}

	public static String validateFileName (Scanner borrowedInput)
	{
		String localFileName = "";

		displayFileNameMenu ();

		localFileName = borrowedInput.next();

		return localFileName;
	}

	public static void displayFileNameMenu ()
	{
		System.out.println("\nPlease enter the name of the file you would like to import:");
	}

	public static void displayFileFound (int borrowedRecordCount)
	{
		System.out.println("\nFile loaded successfully!\n");
		System.out.println(borrowedRecordCount + " DVD records loaded from file.\n");
	}

	public static void displayFileNotFound ()
	{
		System.out.println("\nFile not found or unable to be loaded");
		System.out.println("Please try again\n");
	}

	public static char validateSearchMenu (Scanner borrowedInput)
	{
		char localSearchSelection = ' ';

		displaySearchMenu ();

		localSearchSelection = borrowedInput.next().toUpperCase().charAt(0);

		while (localSearchSelection != 'A' && localSearchSelection != 'B')
		{
			System.out.println("\nOption not available - ");
			System.out.println("Please enter a valid menu selection:\n");

			displaySearchMenu ();

			localSearchSelection = borrowedInput.next().toUpperCase().charAt(0);
		}

		return localSearchSelection;
	}

	public static void displaySearchMenu ()
	{
		System.out.println("************************************************************");
		System.out.println("BUY DVDS PLEASE INVENTORY SEARCH MENU");
		System.out.println("Please choose from one of the following options:");
		System.out.printf("%s\n", SEARCHMENUOPTIONA);
		System.out.printf("%s\n", SEARCHMENUOPTIONB);
		System.out.println("************************************************************");
	}

	public static String validateSearch (Scanner borrowedInput)
	{
		String localSearchID = "";

		displaySearch ();

		localSearchID = borrowedInput.next();

		while (!Character.isDigit(localSearchID.charAt(0)) || 
				Integer.parseInt(localSearchID) < 0)
		{
			System.out.println("\nPlease enter a positive number DVD ID to search for:\n");

			displaySearch ();

			localSearchID = borrowedInput.next();
		}

		return localSearchID;
	}

	public static void displaySearch ()
	{
		System.out.println("Please enter a DVD ID to search for:");
	}

	public static void displaySearchResults (int[] borrowedMovieID, int borrowedIndexResult, 
			String[] borrowedMovieTitles, double[] borrowedMoviePrices)
	{
		System.out.println("\nSEARCH RESULTS:");
		System.out.println("************************************************************");
		System.out.printf("%-44s%-7s%s\n", "DVD TITLE", "ID", "PRICE");
		System.out.println("------------------------------------------------------------");
		System.out.printf("%-44s%-7s%s%.2f\n\n", borrowedMovieTitles[borrowedIndexResult], borrowedMovieID[borrowedIndexResult], 
				"$", borrowedMoviePrices[borrowedIndexResult]);	
	}

	public static void displaySearchResults ()
	{
		System.out.println("\nSEARCH RESULTS:");
		System.out.println("************************************************************\n");
		System.out.println("------------------------------------------------------------");
		System.out.printf("%38s\n\n", "MOVIE NOT FOUND");
	}

	public static void displayMovieWriterError ()
	{
		System.out.println("************************************************************");
		System.out.println("THERE IS AN ERROR WITH THE DVD PURCHASE ORDER WRITER");
		System.out.println("PLEASE CONTACT CUSTOMER SUPPORT");
		System.out.println("************************************************************\n");
	}
	public static char validatePurchaseMenu (Scanner borrowedInput)
	{
		char localPurchaseSelection = ' ';

		displayPurchaseMenu ();

		localPurchaseSelection = borrowedInput.next().toUpperCase().charAt(0);

		while (localPurchaseSelection != 'A' && localPurchaseSelection != 'B')
		{
			System.out.println("\nOption not available - ");
			System.out.println("Please enter a valid menu selection:\n");

			displayPurchaseMenu ();

			localPurchaseSelection = borrowedInput.next().toUpperCase().charAt(0);
		}

		return localPurchaseSelection;
	}

	public static void displayPurchaseMenu ()
	{
		System.out.println("************************************************************");
		System.out.println("BUY DVDS PLEASE PURCHASE MENU");
		System.out.println("Please choose from one of the following options:");
		System.out.printf("%s\n", PURCHASEMENUOPTIONA);
		System.out.printf("%s\n", PURCHASEMENUOPTIONB);
		System.out.println("************************************************************");
	}

	public static String validateHowMany (Scanner borrowedInput)
	{
		String localHowMany = "";

		displayHowManyPrompt ();

		localHowMany = borrowedInput.next();

		while (!Character.isDigit(localHowMany.charAt(0)) || 
				Integer.parseInt(localHowMany) < 0)
		{
			System.out.println("\nPlease enter a number greater than 0:\n");

			displayHowManyPrompt ();

			localHowMany = borrowedInput.next();
		}

		return localHowMany;
	}

	public static void displayHowManyPrompt ()
	{
		System.out.println("\nPlease enter how many copies of this DVD to purchase:");
	}

	public static void displayPurchaseNotice (int borrowedItemIndex, int[] borrowedMovieID, 
			int borrowedHowMany, double borrowedOrderTotal)
	{
		System.out.println("\nPURCHASE NOTICE:");
		System.out.println("************************************************************");
		System.out.printf("%-13s%-19s%s\n", "DVD ID", "QTY PURCHASED", "ORDER TOTAL");
		System.out.println("------------------------------------------------------------");
		System.out.printf("%-19d%-18d%s%.2f\n\n", borrowedMovieID[borrowedItemIndex], 
				borrowedHowMany, "$", borrowedOrderTotal);	
	}

	public static void displayPurchaseReport (int[] borrowedMovieID, String[] borrowedMovieTitles, 
			double[] borrowedMoviePrices, int[] borrowedHowManys, double[] borrowedMovieTotals, 
			int borrowedRecordCount, double borrowedOrderTotals)
	{
		int count = 0;

		System.out.println("\nBUY DVDS PURCHASE ORDER REPORT");
		while(count < borrowedMovieID.length)
		{
			System.out.println("************************************************************");
			System.out.printf("%-10s%-22s%-10s%-8s%s\n", "DVD ID", "TITLE", "PRICE", "QTY", "TOTAL");
			System.out.printf("%-10d%-22.18s%s%-10.2f%-7d%s%.2f\n", borrowedMovieID[count], 
					borrowedMovieTitles[count], "$", borrowedMoviePrices[count], borrowedHowManys[count], 
					"$", borrowedMovieTotals[count]);
			count++;
		}
		System.out.println("************************************************************");
		System.out.printf("%-32s%s\n", "DVD COUNT", "ORDERS TOTAL");
		System.out.printf("%4d%29s%.2f\n\n", borrowedRecordCount, "$", borrowedOrderTotals);
		System.out.println("************************************************************\n");

	}

	public static void displaySearchReport (int borrowedSearchCount, int borrowedFoundSearchCount,
			int borrowedNotFoundSearchCount)
	{
		System.out.println("\nBUY DVDS PLEASE SEARCH REPORT");
		System.out.println("************************************************************");
		System.out.printf("%16s%15s%20s\n", "SEARCH COUNT", "IDs FOUND", "IDs NOT FOUND");
		System.out.println("------------------------------------------------------------");
		System.out.printf("%10d%17d%18d\n\n", borrowedSearchCount, borrowedFoundSearchCount, 
				borrowedNotFoundSearchCount);
	}

	public static void displayFileReport (int borrowedFileCount, int borrowedFoundFileCount,
			int borrowedNotFoundFileCount)
	{
		System.out.println("\nBUY DVDS PLEASE FILE PROCESSING REPORT");
		System.out.println("************************************************************");
		System.out.printf("%15s%17s%22s\n", "FILE COUNT", "FILEs FOUND", "FILEs NOT FOUND");
		System.out.println("------------------------------------------------------------");
		System.out.printf("%10d%17d%18d\n\n", borrowedFileCount, borrowedFoundFileCount, 
				borrowedNotFoundFileCount);
		System.out.println("************************************************************\n");
	}

	public static void displayFarewellMessage (String borrowedUserName)
	{
		System.out.println("Thank you for using this program " + borrowedUserName + ", goodbye!");
	}
}
