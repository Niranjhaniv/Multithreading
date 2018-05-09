package com;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
	private Customer currCustomer;
	public List<Food> completedFood = new LinkedList<Food>();
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
				//YOUR CODE GOES HERE...
				
				
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
				

				//get the customer currently up next
				synchronized(Simulation.listOfOrders){

					while(Simulation.listOfOrders.isEmpty()){
						Simulation.listOfOrders.wait();
					}
					
					currCustomer = checkPriorityCustomer(Simulation.listOfOrders);
					//Simulation.orderList.remove();
					Simulation.logEvent(SimulationEvent.cookReceivedOrder(this, currCustomer.getOrder(), currCustomer.getOrderNum()));
					Simulation.listOfOrders.notifyAll();
				}
				//sends food to specific machine
				for(int index = 0; index < currCustomer.getOrder().size(); index++){
					Food currFood = currCustomer.getOrder().get(index);
					if(currFood.equals(FoodType.burger)){
						synchronized(Simulation.grill.foodList){
							while(!(Simulation.grill.foodList.size() < Simulation.grill.capacity)){
								Simulation.grill.foodList.wait();
							}
							Simulation.logEvent(SimulationEvent.cookStartedFood(this, FoodType.burger , currCustomer.getOrderNum()));
							Simulation.grill.makeFood(this, currCustomer.getOrderNum());
							Simulation.grill.foodList.notifyAll();

						}
						
					}else if(currFood.equals(FoodType.fries)){
						synchronized(Simulation.burner.foodList){
							while(!(Simulation.burner.foodList.size() < Simulation.burner.capacity)){
								Simulation.burner.foodList.wait();
							}
							Simulation.logEvent(SimulationEvent.cookStartedFood(this, FoodType.fries , currCustomer.getOrderNum()));
							Simulation.burner.makeFood(this,currCustomer.getOrderNum());
							Simulation.burner.foodList.notifyAll();
							
						}
						
					}else{
						synchronized(Simulation.braun.foodList){
							while(!(Simulation.braun.foodList.size() < Simulation.braun.capacity)){
								Simulation.braun.foodList.wait();
							}
							Simulation.logEvent(SimulationEvent.cookStartedFood(this, FoodType.coffee , currCustomer.getOrderNum()));
							Simulation.braun.makeFood(this,currCustomer.getOrderNum());
							Simulation.braun.foodList.notifyAll();
							
						}
					}
				}
				synchronized(completedFood){
					while(!(completedFood.size() == currCustomer.getOrder().size())){
						completedFood.wait();
						completedFood.notifyAll();
					}
				}
				Simulation.logEvent(SimulationEvent.cookCompletedOrder(this, currCustomer.getOrderNum()));
				
				synchronized(Simulation.finishedOrder){
					Simulation.finishedOrder.put(currCustomer, true);
					Simulation.finishedOrder.notifyAll();
				}
				completedFood = new LinkedList<Food>();
			
			}
		}
		catch(InterruptedException e) {
			// This code assumes the provided code in the Simulation class
			// that interrupts each cook thread when all customers are done.
			// You might need to change this if you change how things are
			// done in the Simulation class.
			Simulation.logEvent(SimulationEvent.cookEnding(this));
		}
	}

	private Customer checkPriorityCustomer(Queue<Customer> customerList) {
		boolean goldPresent= false;
		for(Customer cust:customerList)
		{
			if(cust.getPriority().equals("Gold"))
			{
				Customer goldCustomer = customerList.remove();
				return goldCustomer;
			}
			
		}
		if(!goldPresent)
		{
			Customer regCustomer = customerList.remove();
			return regCustomer;
		}
		return null;
	}	
}