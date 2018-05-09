package com;

import java.util.List;

/**
 * Customers are simulation actors that have two fields: a name, and a list
 * of Food items that constitute the Customer's order.  When running, an
 * customer attempts to enter the coffee shop (only successful if the
 * coffee shop has a free table), place its order, and then leave the 
 * coffee shop when the order is complete.
 */

/**
 * @Invarient - 1) Customer can place 1 order and it should have a unique order number
 * 				2) Customer should have unique name
 */
public class Customer implements Runnable {
	//JUST ONE SET OF IDEAS ON HOW TO SET THINGS UP...
	private final String name;
	private final List<Food> order;
	private final int orderNum;  
	private final boolean priority;
	
	private static int runningCounter = 0;

	/**
	 * You can feel free modify this constructor.  It must take at
	 * least the name and order but may take other parameters if you
	 * would find adding them useful.
	 */
	
	/**
	 * 
	 * @PreCondition - name,order and priority cannot be null
	 *
	 */
	public Customer(String name, List<Food> order,boolean priority) {
		this.name = name;
		this.order = order;
		this.orderNum = ++runningCounter;
		this.priority = priority;
	}

	public String toString() {
		return name;
	}

	/** 
	 * This method defines what an Customer does: The customer attempts to
	 * enter the coffee shop (only successful when the coffee shop has a
	 * free table), place its order, and then leave the coffee shop
	 * when the order is complete.
	 */
	public void run() {
		//YOUR CODE GOES HERE...
		/*
		 *  Created four methods depending on the sequence of actions by the customer
		 *   
		 *   entryCondition();
		 *
		 *   placeAnOrder();
		 *
		 *   addOrder();
		 *
		 *	 completeOrder();
		 *	
		 *   leaveRes();
		 * 
		 */
		
		
	}
	
	private void addOrder() {
		/* 
		 * Synchronized on finishedOrder
		 * put the customer in the list
		 * release the lock
		 */
		
	}
	
	/**
	 *
	 * @PostCondition - notify all the items in the currentCapacity
	 * @exception - nullPointer exception 
	 */

	private void leaveRes() {
		/*
		 * Synchronized on currentCapacity
		 * remove the customer from the list
		 * notifyAll the items currentCapacity
		 */
		
	}

	/**
	 
	 * @PostCondition - notify All the other threads once the order is complete
	 * @exception - Throws InterruptedException if the thread is interrupted
	 */
	private void completeOrder() {
		//initialize the persons order as not completed
		
		/*
		 * Synchronized on finishedOrder
		 * if the order for the current customer is not complete
		 * wait till the order is complete
		 * @exception - Throws InterruptedException if the thread is interrupted
		 * notifyAll once it is complete
		 */
		
		
	}

	private void placeAnOrder() {
		 /*
		  * Synchronized on listOfOrders
		  * add the customer to the list
		  * once added notifyAll the other threads in the orderList
		  * release lock
		  * 
		  */

		
	}
/**
 * @PreCondition-  current Capacity is less than or equal to the total number of tables
 * @PostConditionn- added customer to the table
 * @exception - throws InterruptedException if the thread is interrupted
 * 
 */
	private void entryCondition() {
		/*
		 * Synchronized on currentCapacity
		 * if currentCapacity is more than number of tables wait for the table
		 * 
		 * if not add the customer to the table
		 * notifyall to wake up other thread which are waiting
		 */
		
	
		
	}
}