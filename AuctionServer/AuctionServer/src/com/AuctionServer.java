package com;

/**
 *  @author Niranjhani
 */


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;



public class AuctionServer
{
	/**
	 * Singleton: the following code makes the server a Singleton. You should
	 * not edit the code in the following noted section.
	 * 
	 * For test purposes, we made the constructor protected. 
	 */

	/* Singleton: Begin code that you SHOULD NOT CHANGE! */
	 Timer timer = new Timer();
	 int i = 100;
	 int count =0;
	 int previousCount=0;
	
	protected AuctionServer()
	{
		
		 TimerTask task = new TimerTask() {
		      @Override
		      public void run() {
		    	  
		    	  previousCount = count;
		    	  count =0;
		    	  
		        // task to run goes here
		        synchronized (instanceLock) {
					
				
		        	for(int i : itemsAndIDs.keySet()){
		    			if(itemsAndIDs.get(i).biddingOpen() == false && itemUnbid(i) == false ){
		    			
		    				count++;
		    			
		    			}
		    		}
		    		
		        
		        System.out.println("Item Sold in  millsecond " + i +" is " + (count-previousCount));
		        }
		        
		        i=i+100;
		        
		      }
		    };
		    
		   
		    long delay = 0;
		    long intevalPeriod = 1 * 100; 
		    
		    // schedules the task to be run in an interval 
		    timer.scheduleAtFixedRate(task, delay,
		                                intevalPeriod);
		  	
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
	

	public int soldItemsCount()
	{
		return this.soldItemsCount;
	}

	public int revenue()
	{
		 int revenue = 0;
		for(int i : itemsAndIDs.keySet()){
			if(itemsAndIDs.get(i).biddingOpen() == false && highestBids.containsKey(i)){
				revenue = revenue + highestBids.get(i);
			}
		}
		
		return revenue;
	}



	/**
	 * Server restriction constants:
	 */
	public static final int maxBidCount = 10; // The maximum number of bids at any given time for a buyer.
	public static final int maxSellerItems = 20; // The maximum number of items that a seller can submit at any given time.
	public static final int serverCapacity = 80; // The maximum number of active items at a given time.

	public HashMap<String, Integer> blackListedSellerMap = new HashMap<String, Integer>(); 
	/* Statistic variables and server constants: End code you should likely leave alone. */

	private Map<String, Integer> sellerItemExpirationMap = new HashMap<String, Integer>();

	/**
	 * Some variables we think will be of potential use as you implement the server...
	 */

	// List of items currently up for bidding (will eventually remove things that have expired).
	private List<Item> itemsUpForBidding = new ArrayList<Item>();
	// SoldItemList
	
	
	private List<String> sellerBlackListed = new ArrayList<String>();
	private List<String> blackListedBidder = new ArrayList<String>();
	
	private List<Item> soldItemCount = new ArrayList<Item>();

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
	 private Object instanceLock = new Object(); 


	/*
	 *  The code from this point forward can and should be changed to correctly and safely 
	 *  implement the methods as needed to create a working multi-threaded server for the 
	 *  system.  If you need to add Object instances here to use for locking, place a comment
	 *  with them saying what they represent.  Note that if they just represent one structure
	 *  then you should probably be using that structure's intrinsic lock.
	 */

	
	/**
	 * Attempt to submit an <code>Item</code> to the auction
	 * @param sellerName Name of the <code>Seller</code>
	 * @param itemName Name of the <code>Item</code>
	 * @param lowestBiddingPrice Opening price
	 * @param biddingDurationMs Bidding duration in milliseconds
	 * @return A positive, unique listing ID if the <code>Item</code> listed successfully, otherwise -1
	 */
	public  int submitItem(String sellerName, String itemName, int lowestBiddingPrice, int biddingDurationMs)
	{
		
	
		synchronized (instanceLock) {
			
		
		
		if(itemsUpForBidding.size() < serverCapacity && blackListValidate(sellerName, lowestBiddingPrice) && (!itemsPerSeller.containsKey(sellerName) || itemsPerSeller.get(sellerName) < maxSellerItems) ) 
		{
			//Blacklist code
			
			lastListingID ++;
			Item item = new Item(sellerName,itemName,lastListingID,lowestBiddingPrice,biddingDurationMs);
			itemsAndIDs.put(lastListingID,item);
			itemsUpForBidding.add(item);
			if(itemsPerSeller.containsKey(sellerName))
			{
				if (checkUnBidItems(sellerName))
				{
				
					itemsPerSeller.put(sellerName,itemsPerSeller.get(sellerName)+1);
				}
				else
				{
					blackListSeller(sellerName);
				}
				
			}
			else
			{
				itemsPerSeller.put(sellerName, 1);
			}
			
			
			return lastListingID;
		}
		
		
		}

				return -1;
	}


	


	private boolean blackListValidate(String sellerName, int lowestBiddingPrice) {
		

		if (!sellerBlackListed.contains(sellerName)) {
			if (lowestBiddingPrice < 10) {
				if (blackListedSellerMap.containsKey(sellerName)) {
					int count = blackListedSellerMap.get(sellerName);
					if (count == 2) {
						List<Item> toBeRemoved = new ArrayList<Item>();
						for (Item i : itemsUpForBidding) {
							if (i.seller().equals(sellerName)) {
								toBeRemoved.add(i);
								itemsAndIDs.remove(i.listingID());
							}
						}
						for (Item i : toBeRemoved) {
							itemsUpForBidding.remove(i);
						}
						itemsPerSeller.remove(sellerName);
						sellerBlackListed.add(sellerName);
						System.out.println( sellerName + " added to blacklist.");
						return false;
					} else {
						blackListedSellerMap.put(sellerName, count + 1);
						return true;
					}
				} else {
					blackListedSellerMap.put(sellerName, 1);
					return true;
				}
			} else {
				blackListedSellerMap.put(sellerName, 0);
				return true;
			}
		}
		return false;
	}

	/**
	 * Get all <code>Items</code> active in the auction
	 * @return A copy of the <code>List</code> of <code>Items</code>
	 */
	public List<Item> getItems()
	{

	
	synchronized(instanceLock)
	{
		if(itemsUpForBidding.size() > 0)
		{
			List<Item> listItem = new ArrayList<Item>();
			
			for(Item item : itemsUpForBidding)
			{
				if(item.biddingOpen())
				{
					listItem.add(item);
				}
			}
			
			return listItem;
			
		}
	}
		
			return null;
		
	}
	


	/**
	 * Attempt to submit a bid for an <code>Item</code>
	 * @param bidderName Name of the <code>Bidder</code>
	 * @param listingID Unique ID of the <code>Item</code>
	 * @param biddingAmount Total amount to bid
	 * @return True if successfully bid, false otherwise
	 */
	public  boolean submitBid(String bidderName, int listingID, int biddingAmount)
	{
		
		synchronized (instanceLock) {

		Item item = itemsAndIDs.get(listingID);
		if(!itemsPerBuyer.containsKey(bidderName) || itemsPerBuyer.get(bidderName) < maxBidCount)
		{
		if(item != null && itemsUpForBidding.contains(item) && item.biddingOpen()
				&&  validateBidder(bidderName)  && (!highestBidders.containsKey(listingID) || biddingAmount > highestBids.get(listingID)))
			
		{
			String previousBidder = highestBidders.get(listingID);

			if (previousBidder != null && itemsPerBuyer.containsKey(previousBidder)) {
				itemsPerBuyer.put(previousBidder, itemsPerBuyer.get(previousBidder) - 1);
			}
			
			highestBidders.put(listingID, bidderName);
			highestBids.put(listingID, biddingAmount);
			
			if(itemsPerBuyer.containsKey(bidderName))
			{
				itemsPerBuyer.put(bidderName, itemsPerBuyer.get(bidderName)+1);
			}
			else
			{
				itemsPerBuyer.put(bidderName, 1);
			}
			
			return true;

		}
		else
		{
			if(!validateBidder(bidderName))
			{
				System.out.println(bidderName +" is Blacklisted");
			}
		}
		
	
		}
		else
		{
			if(!blackListedBidder.contains(bidderName))
			{
				blackListedBidder.add(bidderName);
			}
			return false;
		}
		
		return false;
		}
		
	}


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
	
	synchronized(instanceLock)
	{
		
		int bidStatus = -1;
		Item item =itemsAndIDs.get(listingID);
		String sellerName = item.seller();
		if(item!= null)
		{
			if(!item.biddingOpen())
			{
				itemsUpForBidding.remove(item);
				if(highestBidders.containsKey(listingID))
				{
					if(highestBidders.get(listingID).equals(bidderName))
					{
						bidStatus =1;
					}
					else
					{
						bidStatus =3;
					}
				}
				else
				{
					bidStatus =3;
				}
				
			}
			else
			{
				
				if (checkUnBidItems(sellerName))
				{
					bidStatus =2;
				}
				else
				{
					blackListSeller(sellerName);
				}
				
			}
		}
		else
		{
			bidStatus =3;
		}
		
		return bidStatus;
	}
		
	}
	


	/**
	 * Check the current bid for an <code>Item</code>
	 * @param listingID Unique ID of the <code>Item</code>
	 * @return The highest bid so far or the opening price if no bid has been made,
	 * -1 if no <code>Item</code> exists
	 */
	public int itemPrice(int listingID)
	{
		if(highestBids.containsKey(listingID)){
			return highestBids.get(listingID);
		} else {

	

			for(Item i : getItems()){

				if(i.listingID() == listingID){
					return i.lowestBiddingPrice();
				}
				

			}
		}

			return -1;	
	
	}

	/**
	 * Check whether an <code>Item</code> has been bid upon yet
	 * @param listingID Unique ID of the <code>Item</code>
	 * @return True if there is no bid or the <code>Item</code> does not exist, false otherwise
	 */
	public Boolean itemUnbid(int listingID)
	{
		synchronized (instanceLock) {
			
			Item item=itemsAndIDs.get(listingID);
			if(item != null && highestBids.containsKey(listingID))
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		
		
	}


	/**
	 * Get all active in the auction
	 * @return the count of sold items
	 */

	public int getSoldItemCount()
	{
		int count = 0;
		for(int i : itemsAndIDs.keySet()){
			if(itemsAndIDs.get(i).biddingOpen() == false && itemUnbid(i) == false ){
			
			count++;
			
			}
		}
		
		return count;
	}
	
	/**
	 * Get all submitted items in the auction
	 * @return count of submitted items
	 */
	
	public int getItemsSubmittedForAuction()
	{
		return lastListingID+1;
	}
	
	/**
	 * Get all unsold items in teh auction
	 * @return count of unsold items
	 */
	
	public int getUnSoldItemCount()
	{
		int count = 0;
		for(int i : itemsAndIDs.keySet()){
			if(itemsAndIDs.get(i).biddingOpen() == false && itemUnbid(i) == true ){
			
			count++;
			
			}
		}
		
		return count;
	}
	
	/**
	 * Check the UnBid items 
	 * @param sellerName
	 * @return true if the four items bidding item expires for the given sellerName and false if not 
	 */
	
	private boolean checkUnBidItems(String sellerName) {
		for (Item i : itemsAndIDs.values()) {
			if (i.seller().equals(sellerName)) {
				if (!i.biddingOpen() && itemUnbid(i.listingID())) {
					if (sellerItemExpirationMap.containsKey(sellerName)) {
						int count = sellerItemExpirationMap.get(sellerName);
						if (count == 3) {
							blackListSeller(sellerName);
							return false;
						} else {
							sellerItemExpirationMap.put(sellerName, count++);
							return true;
						}
					} else {
						sellerItemExpirationMap.put(sellerName, 1);
						return true;
					}
				} else {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Blacklist the given seller
	 * @param sellerName
	 * @return void
	 */
	
	private void blackListSeller(String sellerName) {
		List<Item> toBeRemoved = new ArrayList<Item>();
		for (Item i : itemsUpForBidding) {
			if (i.seller().equals(sellerName) && i.biddingOpen()) {
				toBeRemoved.add(i);
			}
		}
		for (Item i : toBeRemoved) {
			itemsUpForBidding.remove(i);
			itemsAndIDs.remove(i.listingID());
		}
		itemsPerSeller.remove(sellerName);
		sellerBlackListed.add(sellerName);
		System.out.println("Seller " + sellerName + " added to blacklist.");
	}

	/**
	 * Check the bidderName is blacklisted or not 
	 * @return false if blacklisted else true
	 */
	

	private boolean validateBidder(String bidderName) {
		if(blackListedBidder.contains(bidderName))
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	/**
	 * Get the blackListedBidder
	 * @return blackListedBidder
	 */
	
	public List getBlackListedBidder()
	{
		
		return blackListedBidder;
	}
	
	/**
	 * Get the money spent by bidder
	 * @param name
	 * @return amount spent by bidders
	 **/

	public int moneySpentByBidders(String name){
		int amount = 0;
		for(int i : highestBidders.keySet()){
			if(highestBidders.get(i).equalsIgnoreCase(name)){
				amount = amount + highestBids.get(i);
				
			}
		}
		return amount;
	}
	
	/**
	 *  
	 * @return highest Bid for each items
	 */
	
	 public void highestBidEachItem(){
		 for(int i : highestBids.keySet()){
			 System.out.println("Bidding amount of Item " + i + " is $ " + highestBids.get(i) );
			 
		 }
		 
	 }
	
	
}
 