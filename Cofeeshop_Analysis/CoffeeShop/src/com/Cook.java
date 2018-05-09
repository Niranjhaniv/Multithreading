package com;

import java.util.LinkedList;

/**
 * Cooks are simulation actors that have at least one field, a name.
 * When running, a cook attempts to retrieve outstanding orders placed
 * by Eaters and process them.
 */

/**
 * 
 * @author niran
 * 
 * @Invarient - 1) a Cook can handle only one order at a time
 * 				2) Gold customer are given higher priority than the other.
 * 				3) Cook name should be unique
 * 
 *
 */
public class Cook implements Runnable {
	private final String name;

	/**
	 * You can feel free modify this constructor.  It must
	 * take at least the name, but may take other parameters
	 * if you would find adding them useful. 
	 *
	 * @param: the name of the cook
	 */
	public Cook(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}
	/**
	 * @PostCondition - Completes the customers order
	 * @exception- InterruptedException if the thread is interrupted arises while cooking
	 */

	/**
	 * This method executes as follows.  The cook tries to retrieve
	 * orders placed by Customers.  For each order, a List<Food>, the
	 * cook submits each Food item in the List to an appropriate
	 * Machine, by calling makeFood().  Once all machines have
	 * produced the desired Food, the order is complete, and the Customer
	 * is notified.  The cook can then go to process the next order.
	 * If during its execution the cook is interrupted (i.e., some
	 * other thread calls the interrupt() method on it, which could
	 * raise InterruptedException if the cook is blocking), then it
	 * terminates.
	 */
	public void run() {

		Simulation.logEvent(SimulationEvent.cookStarting(this));
		try {
			while(true) {
				
				/*
				 * Synchronized on listofOrders
				 * get the customer order
				 * notifyAll the threads that is waiting for listofOrders.
				 * 
				 * get all the orders by the customer
				 * Synchronized on machine (grill,coffee,fries)
				 * wait if the capacity of the machine is full
				 * once the capacity is available 
				 * makefood()
				 * notify all the threads waiting for the machine
				 * 
				 * Synchornized on finishedfood
				 * wait till the food is completed
				 * notify all the cook once the food is complete
				 */
				
		}
		catch(InterruptedException e) {
			// This code assumes the provided code in the Simulation class
			// that interrupts each cook thread when all customers are done.
			// You might need to change this if you change how things are
			// done in the Simulation class.
			Simulation.logEvent(SimulationEvent.cookEnding(this));
		}
	}
}