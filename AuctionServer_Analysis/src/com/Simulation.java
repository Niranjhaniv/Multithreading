package com;

/**
 * Class provided for ease of test. This will not be used in the project 
 * evaluation, so feel free to modify it as you like.
 */ 
public class Simulation
{
    public static void main(String[] args)
    {                
        int nrSellers = 50;
        int nrBidders = 20;
        
        Thread[] sellerThreads = new Thread[nrSellers];
        Thread[] bidderThreads = new Thread[nrBidders];
        Seller[] sellers = new Seller[nrSellers];
        Bidder[] bidders = new Bidder[nrBidders];
        
        // Start the sellers
        for (int i=0; i<nrSellers; ++i)
        {
            sellers[i] = new Seller(
            		AuctionServer.getInstance(), 
            		"Seller"+i, 
            		100, 50, i
            );
            sellerThreads[i] = new Thread(sellers[i]);
            sellerThreads[i].start();
        }
        
        // Start the buyers
        for (int i=0; i<nrBidders; ++i)
        {
            bidders[i] = new Bidder(
            		AuctionServer.getInstance(), 
            		"Buyer"+i, 
            		1000, 20, 150, i
            );
            bidderThreads[i] = new Thread(bidders[i]);
            bidderThreads[i].start();
        }
        
        // Join on the sellers
        for (int i=0; i<nrSellers; ++i)
        {
            try
            {
                sellerThreads[i].join();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        
        // Join on the bidders
        for (int i=0; i<nrBidders; ++i)
        {
            try
            {
                sellerThreads[i].join();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        
        // TODO: Add code as needed to debug
        
       
        /* calculate the itemsSold
		 * *******************************
		 * PSEUDOCODE
		 * *******************************
		 * initialize itemsSold to zero 
		 * 
		 * BEGIN LOOP  loop through ItemAndID's
		 * 
		 * 	IF bidding is not open AND highestBids contains the item
		 * 
		 * 			Increment the itemsSold
		 * 
		 * 	END IF
		 * 
		 * END LOOP 
		 * 
		 * 
		 * calculate revenue
		 * 
		 * 
		 * 
		 * * BEGIN LOOP  loop through ItemAndID's
		 * 
		 * 	IF bidding is not open AND highestBids contains the item
		 * 
		 * 			Add the highestBid value to revenue
		 * END IF
		 * 
		 * END LOOP
		 * 
		 * 
		 * 
		 * 
		 * 
		 * LOOP Bidders
		 * 		
		 * 			Calculate the cash spent by the bidders 
		 * 
		 *	END LOOP
		 *
		 *	Check whether the total cash spent is equal to revenue
		 * 
		 * 
		 */
        
    
        
    }
}