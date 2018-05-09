package com;

/**
 *  @author Niranjhani
 */


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class AuctionServer
{
	/**
	 * Singleton: the following code makes the server a Singleton. You should
	 * not edit the code in the following noted section.
	 * 
	 * For test purposes, we made the constructor protected. 
	 */

	/* Singleton: Begin code that you SHOULD NOT CHANGE! */
	protected AuctionServer()
	{
	}

	private static AuctionServer instance = new AuctionServer();

	public static AuctionServer getInstance()
	{
		return instance;
	}

	/* Singleton: End code that you SHOULD NOT CHANGE! */





	/* Statistic variables and server constants: Begin code you should likely leave alone. */


	/**
	 * Server statistic variables and access methods:
	 */
	private int soldItemsCount = 0;
	private int revenue = 0;

	public int soldItemsCount()
	{
		return this.soldItemsCount;
	}

	public int revenue()
	{
		
		return this.revenue;
	}



	/**
	 * Server restriction constants:
	 */
	public static final int maxBidCount = 10; // The maximum number of bids at any given time for a buyer.
	public static final int maxSellerItems = 20; // The maximum number of items that a seller can submit at any given time.
	public static final int serverCapacity = 80; // The maximum number of active items at a given time.


	/* Statistic variables and server constants: End code you should likely leave alone. */



	/**
	 * Some variables we think will be of potential use as you implement the server...
	 */

	// List of items currently up for bidding (will eventually remove things that have expired).
	private List<Item> itemsUpForBidding = new ArrayList<Item>();


	// The last value used as a listing ID.  We'll assume the first thing added gets a listing ID of 0.
	private int lastListingID = -1; 

	// List of item IDs and actual items.  This is a running list with everything ever added to the auction.
	private HashMap<Integer, Item> itemsAndIDs = new HashMap<Integer, Item>();

	// List of itemIDs and the highest bid for each item.  This is a running list with everything ever added to the auction.
	private HashMap<Integer, Integer> highestBids = new HashMap<Integer, Integer>();

	// List of itemIDs and the person who made the highest bid for each item.   This is a running list with everything ever bid upon.
	private HashMap<Integer, String> highestBidders = new HashMap<Integer, String>(); 




	// List of sellers and how many items they have currently up for bidding.
	private HashMap<String, Integer> itemsPerSeller = new HashMap<String, Integer>();

	// List of buyers and how many items on which they are currently bidding.
	private HashMap<String, Integer> itemsPerBuyer = new HashMap<String, Integer>();



	// Object used for instance synchronization if you need to do it at some point 
	// since as a good practice we don't use synchronized (this) if we are doing internal
	// synchronization.
	//
	// private Object instanceLock = new Object(); 


	/*
	 *  The code from this point forward can and should be changed to correctly and safely 
	 *  implement the methods as needed to create a working multi-threaded server for the 
	 *  system.  If you need to add Object instances here to use for locking, place a comment
	 *  with them saying what they represent.  Note that if they just represent one structure
	 *  then you should probably be using that structure's intrinsic lock.
	 */
	
	
	/* INVARIENTS  : 	 1) Item price should not be less then $10
	 * 				 	 2) Items should not be expired
	 *  
	 * PRE -CONDITION :  1) itemsUpForBidding should be less then serverCapacity 
	 * 					 2) sellerItems should be less then max sellers
	 * 					 3) sellectName should not be in blacklist
	 * 
	 * POST -CONDITION : 1) The item is submitted successfully and returns unique listingID
	 * 
	 * EXCEPTION :       1) It throws IllegalArgumentException for the Pre-condition 1) and 2)
	 * 					
	 */
	
	/**
	 * Attempt to submit an <code>Item</code> to the auction
	 * @param sellerName Name of the <code>Seller</code>
	 * @param itemName Name of the <code>Item</code>
	 * @param lowestBiddingPrice Opening price
	 * @param biddingDurationMs Bidding duration in milliseconds
	 * @return A positive, unique listing ID if the <code>Item</code> listed successfully, otherwise -1
	 */
	public synchronized int submitItem(String sellerName, String itemName, int lowestBiddingPrice, int biddingDurationMs)
	{
		// TODO: IMPLEMENT CODE HERE
		// Some reminders:
		//   Make sure there's room in the auction site.
		//   If the seller is a new one, add them to the list of sellers.
		//   If the seller has too many items up for bidding, don't let them add this one.
		//   Don't forget to increment the number of things the seller has currently listed.
		
		/* *******************************
		 * PSEUDOCODE
		 * ********************************
		 *   using synchronized method
		 *   
		 *     IF 'itemsUpForBidding.size' is less than 'serverCapacity' (AND) 
		 *     
		 *     		 (IF itemsPerSeller  don't have sellerName (OR) itemsPerSeller less than maxSellerItems)
		 *						
		 *						IF lowestBiddingPrice between $ 1 to $ 10
		 *							
		 *							check the blacklistMap for seller name
		 *									
		 *									IF present,
		 *										if count <2
		 *
		 *										Get the count and increment it.
		 *
		 *										ELSE
		 *											Remove the sellerName from blackListMaP
		 *
		 *											Add the sellerNamer in BlackListedSellers
		 *
		 *											Remove respective items from itemsUpForbidding
		 *
		 *									IF not,
		 *										
		 *										add the sellerName and count as 1
		 *						ELSE
		 *								
		 *								remove the seller if the name is in blacklist 
		 *									
		 *						END IF	
		 *		
		 * 					    IF sellerName not in BlackListedSellers
		 * 
		 * 							Increment unique listingID 
		 *
		 * 							Create a new Item object with given sellerName,itemName,lowestBidingPrice,biddingDurationMs
		 
		 * 							Add the Item created and its listingID to itemsAndIDs list
		 * 
		 * 							Add the Item to itemsUpForBidding list
		 * 
		 * 							IF sellerName is in itemsPerSeller
		 * 
		 * 								Get the value with itemsPerSeller as key and increment the count
		 * 
		 * 							ELSE
		 * 								Create a new sellerName in itemsPerSeller as key and '1' as value
		 * 
		 * 							END IF
	
		 * 
		 * 							RETURN listingID
		 * 
		 * 					END IF
		 * 
		 * 				 END IF
		 * 
		 * 
		 * 		RETURN -1
		 * 
		 *
		 * 	
		 */

				return -1;
	}


	
	/* INVARIENTS  : 	 1) Check whether item is active
	 * 				 	 
	 * PRE -CONDITION :  1) itemsUpForBidding should not be NULL
	 * 					 
	 * 
	 * POST -CONDITION : 1) The items in the list is returned
	 * 
	 * EXCEPTION :       1) It throws NullPointerException for the Pre-condition 1) 
	 * 					
	 */


	/**
	 * Get all <code>Items</code> active in the auction
	 * @return A copy of the <code>List</code> of <code>Items</code>
	 */
	public List<Item> getItems()
	{
		// TODO: IMPLEMENT CODE HERE
		// Some reminders:
		//    Don't forget that whatever you return is now outside of your control.
		
		/* ****************************************************
		 * PSEUDOCODE
		 * ***************************************************
		 * synchronized lock 
		 * 
		 * 	IF 	itemsUpForBidding.size is greater than zero
		 * 		Create a new List<Items>
		 * 
		 * 			BEGIN LOOP
		 * 
		 * 				get the items itemsUpForBidding 
		 * 
		 * 				IF biddingOpen
		 * 
		 * 					 add the items to the list
		 * 
		 * 			    END IF
		 * 
		 * 			END LOOP
		 * 
		 * 			RETURN list
		 * 
		 *  END IF
		 * 
		 */
		
			return null;
		
	}
	
	/* INVARIENTS  : 	 1) BidAmount must be positive
	 *
	 * POST -CONDITION : 1) The item is successfully bided
	 *
	 * 					
	 */

	/**
	 * Attempt to submit a bid for an <code>Item</code>
	 * @param bidderName Name of the <code>Bidder</code>
	 * @param listingID Unique ID of the <code>Item</code>
	 * @param biddingAmount Total amount to bid
	 * @return True if successfully bid, false otherwise
	 */
	public synchronized boolean submitBid(String bidderName, int listingID, int biddingAmount)
	{
		// TODO: IMPLEMENT CODE HERE
		// Some reminders:
		//   See if the item exists.
		//   See if it can be bid upon.
		//   See if this bidder has too many items in their bidding list.
		//   Get current bidding info.
		//   See if they already hold the highest bid.
		//   See if the new bid isn't better than the existing/opening bid floor.
		//   Decrement the former winning bidder's count
		//   Put your bid in place
		
		/* ****************************************************
		 * PSEUDOCODE
		 * ***************************************************
		 *	using synchronized method
			
		 * 	Get the item from itemsAndIDs by listingID
		 * 
		 * 	IF item is not null AND itemsUpForBidding contains item AND item is open for bidding 
		 * 			AND (itemsPerBuyer do not contain bidderName  OR Check the maxBidCount is greater than items count in itemsPerBuyer )
		 * 			AND (highestBidders don't have listingID OR bidderAmount is greater than the highestBids)
		 * 
		 * 				Get the previous bid value
		 * 
		 * 				IF previousBid is not null and itemsPerBuyer has perviousBidder name
		 * 
		 * 					Decrement the item count from the previous Bidder
		 * 
		 * 				END IF
		 * 
		 * 				Update the highestBidders with current bidderName
		 * 
		 * 				Update the highestBids with biddingAmount
		 * 
		 * 				IF itemsPerBuyer has the bidderName
		 * 
		 * 					Increment the item count for the bidder
		 * 
		 * 				ELSE
		 * 
		 * 					Add the bidderName to the itemsPerBuyer
		 * 
		 * 				END IF
		 * 					
		 * 				RETURN true
		 * END IF
		 * 
		 * 		RETURN false
		 * 
		 */
		
		
		
		
		return false;
	}
	
	/* INVARIENTS  : 	 1) Item must be present in the file
	 * 				 	 
	 * PRE -CONDITION :  1) listingID should be valid
	 * 					 
	 * 
	 * POST -CONDITION : 1) Checks the status and returns 1 (success),2 (open), 3(failure)
	 * 
	 * EXCEPTION :       1) It throws IllegalArgumantException for the Pre-condition 1) 
	 * 					
	 */

	/**
	 * Check the status of a <code>Bidder</code>'s bid on an <code>Item</code>
	 * @param bidderName Name of <code>Bidder</code>
	 * @param listingID Unique ID of the <code>Item</code>
	 * @return 1 (success) if bid is over and this <code>Bidder</code> has won<br>
	 * 2 (open) if this <code>Item</code> is still up for auction<br>
	 * 3 (failed) If this <code>Bidder</code> did not win or the <code>Item</code> does not exist
	 */
	public int checkBidStatus(String bidderName, int listingID)
	{
		// TODO: IMPLEMENT CODE HERE
		// Some reminders:
		//   If the bidding is closed, clean up for that item.
		//     Remove item from the list of things up for bidding.
		//     Decrease the count of items being bid on by the winning bidder if there was any...
		//     Update the number of open bids for this seller
		/* ****************************************************
		 * PSEUDOCODE
		 * ***************************************************
		 * synchronized lock 
		 *  	Get the item using listingID from itemsAndIDs
		 *  
		 *  	IF item not equal to null
		 *  
		 *  		IF bidding is not open 
		 *  
		 *  			Remove item from itemsUpForBidding if present
		 *  				
		 *  				Get the seller name from item
		 *  
		 *  				Decrement the item count value from itemsPerSeller
		 *  
		 *  					IF highestBidders contains listingIDs
		 *  
		 *  						Get the listingIDs
		 *  
		 *  						IF highestBidders is equal to bidderName
		 *  
		 *  								bidStatus = 1
		 *    						ELSE
		 *    								bidStatus = 3
		 *    					ELSE
		 *    						bidStatus =3
		 *    		ELSE
		 *    			bidStatus = 2
		 *     ELSE
		 *     	 bidStatus = 3
		 *     
		 *     RETURN bidStatus		
		 *     
		 *     End of lock	
		 *  
		 *  
		 */
		
		
		
		return -1;
	}
	
	/* INVARIENTS  : 	 1) Item must be present in the file
	 * 				 	 
	 * PRE -CONDITION :  1) listingID should be valid
	 * 					 
	 * 
	 * POST -CONDITION : 1) Returns item price
	 * 
	 * EXCEPTION :       1) It throws IllegalArgumantException for the Pre-condition 1) 
	 * 					
	 */

	/**
	 * Check the current bid for an <code>Item</code>
	 * @param listingID Unique ID of the <code>Item</code>
	 * @return The highest bid so far or the opening price if no bid has been made,
	 * -1 if no <code>Item</code> exists
	 */
	public int itemPrice(int listingID)
	{
		// TODO: IMPLEMENT CODE HERE
		/* ****************************************************
		 * PSEUDOCODE
		 * ***************************************************
		 * 	synchronized lock 
		 * 
		 * 		Get the item using listingID from itemsAndIDs
		 * 
		 * 		IF item not equal to null
		 * 
		 * 				RETURN the highestBids of the item using listingID
		 * 
		 * 		ELSE
		 * 				RETURN -1
		 */
		
		return -1;
	}

	/**
	 * Check whether an <code>Item</code> has been bid upon yet
	 * @param listingID Unique ID of the <code>Item</code>
	 * @return True if there is no bid or the <code>Item</code> does not exist, false otherwise
	 */
	public Boolean itemUnbid(int listingID)
	{
		// TODO: IMPLEMENT CODE HERE
		
		/* ****************************************************
		 * PSEUDOCODE
		 * ***************************************************
		 * 
		 * 		synchronized lock 
		 * 
		 * 				Get the item from the itemAndIDs
		 * 
		 * 				IF item is not null AND highestBid contains listingID
		 * 
		 * 						RETURN false
		 * 
		 * 				ELSE
		 * 
		 * 						RETURN true
		 * 
		 * 		End of lock
		 */
		
		return false;
	}


}
 