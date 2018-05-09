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
		private final String priority;
		
		public String getPriority() {
			return priority;
		}

		private static int runningCounter = 0;

		/**
		 * You can feel free modify this constructor.  It must take at
		 * least the name and order but may take other parameters if you
		 * would find adding them useful.
		 */
		public Customer(String name, List<Food> order,String priority) {
			this.name = name;
			this.order = order;
			this.orderNum = runningCounter++;
			this.priority=priority;
		}

		public String toString() {
			return name;
		}
		public List<Food> getOrder(){
			return this.order;
		}
		public int getOrderNum(){
			return this.orderNum;
		}
		/** 
		 * This method defines what an Customer does: The customer attempts to
		 * enter the coffee shop (only successful when the coffee shop has a
		 * free table), place its order, and then leave the coffee shop
		 * when the order is complete.
		 */
		public void run() {
			//YOUR CODE GOES HERE...
			Simulation.logEvent(SimulationEvent.customerStarting(this));
			
			entryCondition();
			
			placeAnOrder();
			
			addOrder();
		
			completeOrder();
			
			leaveRes();
		}

		private void addOrder() {
			synchronized(Simulation.finishedOrder){
				Simulation.finishedOrder.put(this, false);
			}
			
		}

		private void leaveRes() {
			synchronized(Simulation.currentCapacity){
				Simulation.currentCapacity.remove(this);
				Simulation.logEvent(SimulationEvent.customerLeavingCoffeeShop(this));
				Simulation.currentCapacity.notifyAll();
			}
			
		}

		private void completeOrder() {
			//initialize the persons order as not completed
			
			
			synchronized(Simulation.finishedOrder){
				while(!(Simulation.finishedOrder.get(this))){
					try {
						Simulation.finishedOrder.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				Simulation.logEvent(SimulationEvent.customerReceivedOrder(this, this.order, this.orderNum));
				Simulation.finishedOrder.notifyAll();
			}
			
		}

		private void placeAnOrder() {
			
			synchronized(Simulation.listOfOrders){
				Simulation.listOfOrders.add(this);
				Simulation.logEvent(SimulationEvent.customerPlacedOrder(this, this.order, this.orderNum));
				Simulation.listOfOrders.notifyAll();
			}
			
		}

		private void entryCondition() {
			synchronized(Simulation.currentCapacity){
				while(!(Simulation.currentCapacity.size() < Simulation.events.get(0).simParams[2])){
					try {
						Simulation.currentCapacity.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				Simulation.currentCapacity.add(this);
				Simulation.logEvent(SimulationEvent.customerEnteredCoffeeShop(this));
				Simulation.currentCapacity.notifyAll();
			}
			
		}
}