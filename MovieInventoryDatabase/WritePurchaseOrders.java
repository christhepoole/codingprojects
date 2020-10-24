package edu.cpt187.poole.program6;

import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WritePurchaseOrders 
{
	private final String MASTER_FILE = "masterFile.dat";
	private final int NOTFOUNDVALUE = -1;
	private int recordCount = 0;
	
	
	public void setWriteOrder (int borrowedItemIndex, int[] borrowedMovieID, String[] borrowedMovieTitles,
			double[] borrowedMoviePrices, int borrowedHowMany, double borrowedOrderTotal)
	{
		
		try 
		{
			//create file writing object
			PrintWriter writeOrder = new PrintWriter (new FileWriter(MASTER_FILE, true));
			
			//write to MASTER_FILE
			writeOrder.printf("%d\t%s\t%.2f\t%d\t%.2f\r\n", borrowedMovieID[borrowedItemIndex], 
					borrowedMovieTitles[borrowedItemIndex], borrowedMoviePrices[borrowedItemIndex],
					borrowedHowMany, borrowedOrderTotal);
			
			recordCount++;
			writeOrder.close();
		}
		
		catch (IOException ex) 
		{
			recordCount = NOTFOUNDVALUE;
		}
	}
	
	public int getRecordCount ()
	{
		return recordCount;
	}
	
	public String getMasterFile ()
	{
		return MASTER_FILE;
	}

}