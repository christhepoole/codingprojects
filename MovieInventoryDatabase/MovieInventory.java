package edu.cpt187.poole.program6;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class MovieInventory
{

	private final int ARRAYSIZE = 50;
	private final int ARRAYSIZE_COUNTER = 2;
	private final int RESETVALUE = 0;
	private final int NOTFOUNDVALUE = -1;

	private int[] movieID;
	private String[] movieTitles;
	private double[] moviePrices;
	private int[] movieHowManys;
	private double[] movieTotals;
	private int itemIndex = 0;
	private int howMany = 0;
	private double orderTotals = 0.0;

	private int recordCount = 0;
	private int[] searchCount = new int [ARRAYSIZE_COUNTER];
	private int[] fileCount = new int [ARRAYSIZE_COUNTER];

	public void setLoadArrays (String borrowedFileName)
	{
		try
		{
			recordCount = RESETVALUE;

			//instantiate object to read .dat files
			Scanner infile = new Scanner(new FileInputStream(borrowedFileName));
			
			movieID = new int [ARRAYSIZE];
			movieTitles = new String [ARRAYSIZE];
			moviePrices = new double [ARRAYSIZE];

			while (infile.hasNext() == true && recordCount < ARRAYSIZE )
			{
				movieID[recordCount] = infile.nextInt();
				movieTitles[recordCount] = infile.next();
				moviePrices[recordCount] = infile.nextDouble();

				recordCount++;
			}
			
			setBubbleSort ();

			//close the file
			infile.close();
			fileCount[0]++;
		}

		catch (IOException ex)
		{

			//if file cannot be imported, send error flag
			recordCount = NOTFOUNDVALUE;
			fileCount[1]++;

		}
		
	}
	
	public void setLoadArrays (String borrowedFileName, int borrowedArraySize)
	{
		try
		{
			recordCount = RESETVALUE;
			
			Scanner infile = new Scanner(new FileInputStream(borrowedFileName));
			
			movieID = new int [borrowedArraySize];
			movieTitles = new String [borrowedArraySize];
			moviePrices = new double [borrowedArraySize];
			movieHowManys = new int [borrowedArraySize];
			movieTotals = new double [borrowedArraySize];
			
			while (infile.hasNext() == true && recordCount < borrowedArraySize)
			{
				movieID[recordCount] = infile.nextInt();
				movieTitles[recordCount] = infile.next();
				moviePrices[recordCount] = infile.nextDouble();
				movieHowManys[recordCount] = infile.nextInt();
				movieTotals[recordCount] = infile.nextDouble();
				
				recordCount++;
			}
			
			setOrderTotals ();
			infile.close();
		}
		
		catch (IOException ex)
		{
			recordCount = NOTFOUNDVALUE;
		}	
		
	}
	
	public void setBubbleSort ()
	{
		boolean swap = false;
		int last = RESETVALUE;
		int index = RESETVALUE;
		
		last = movieID.length + NOTFOUNDVALUE;
		
		while (last > 0)
		{
			index = RESETVALUE;
			swap = false;
			
			while (index < last)
			{
				if (movieID[index] > movieID[index + 1])
				{
					setSwapArrayElements (index);
					swap = true;
				}
				else
				{
					index++;
				}
			}
			if (swap == false)
			{
				last = RESETVALUE;
			}
			else 
			{
				last = last + NOTFOUNDVALUE;
			}
		}
	}
	
	public void setSwapArrayElements (int borrowedIndex)
	{
		int tempMovieID = RESETVALUE;
		String tempMovieTitles = "";
		double tempMoviePrices = 0.0;
		
		tempMovieID = movieID[borrowedIndex];
		movieID[borrowedIndex] = movieID[borrowedIndex +1];
		movieID[borrowedIndex + 1] = tempMovieID;
		
		tempMovieTitles = movieTitles[borrowedIndex];
		movieTitles[borrowedIndex] = movieTitles[borrowedIndex + 1];
		movieTitles[borrowedIndex + 1] = tempMovieTitles;
		
		tempMoviePrices = moviePrices[borrowedIndex];
		moviePrices[borrowedIndex] = moviePrices[borrowedIndex + 1];
		moviePrices[borrowedIndex + 1] = tempMoviePrices;
	}
	
	public void setItemIndex (int borrowedSearchValue)
	{
		int first = RESETVALUE;
		int last = RESETVALUE;
		boolean found = false;
		
		last = movieID.length + NOTFOUNDVALUE;
		
		while (first <= last && found == false)
		{
			itemIndex = (first + last)/2;
			
			if (movieID[itemIndex] == borrowedSearchValue)
			{
				found = true;
				searchCount[0]++;
			}
			else
			{
				if (movieID[itemIndex] < borrowedSearchValue)
				{
					first = itemIndex + 1;
				}
				else
				{
					last = itemIndex - 1;
				}
			}
		}
		
		if (found == false)
		{
			itemIndex = NOTFOUNDVALUE;
			searchCount[1]++;
		}
	}

	public void setResetSearchCounters ()
	{
		searchCount[0] = RESETVALUE;
		searchCount[1] = RESETVALUE;
	}
	
	public void setHowMany (String borrowedHowMany)
	{
		howMany = Integer.parseInt(borrowedHowMany);
	}
	
	public void setOrderTotals ()
	{
		int localIndex = 0;
		
		while(localIndex < movieTotals.length)
		{
			orderTotals = movieTotals[localIndex] + orderTotals;
			localIndex++;			
		}
	}
	
	public double getOrderTotal ()
	{
		return moviePrices[itemIndex] * howMany;
	}
	
	public int getMovieCount ()
	{
		return recordCount;
	}

	public int[] getMovieID ()
	{
		return movieID;
	}

	public String[] getMovieTitles ()
	{
		return movieTitles;
	}

	public double[] getMoviePrices ()
	{
		return moviePrices;
	}
	
	public int[] getMovieHowManys ()
	{
		return movieHowManys;
	}
	
	public double[] getMovieTotals ()
	{
		return movieTotals;
	}
	
	public double getOrderTotals ()
	{
		return orderTotals;
	}
	
	public int getSearchResults ()
	{
		return itemIndex;
	}

	public int getFileCount ()
	{
		return fileCount[0] + fileCount[1];
	}

	public int getFoundFileCount ()
	{
		return fileCount[0];
	}

	public int getNotFoundFileCount ()
	{
		return fileCount[1];
	}

	public int getSearchCount ()
	{
		return searchCount[0] + searchCount[1];
	}

	public int getFoundSearchCount ()
	{
		return searchCount[0];
	}

	public int getNotFoundSearchCount ()
	{
		return searchCount[1];
	}
	
	public int getHowMany ()
	{
	return howMany;
	}

}
