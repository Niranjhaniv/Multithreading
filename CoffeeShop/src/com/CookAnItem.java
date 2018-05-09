package com;

import java.util.Queue;

public class CookAnItem implements Runnable {
	Cook currCook;
	int orderNum;
	Food machineFoodType;
	Queue<Food> foodList;
	Machine machine;
	public CookAnItem(Cook currCook, int orderNum,Food machineFoodType,Queue<Food> foodList,Machine machine){
		this.currCook = currCook;
		this.orderNum = orderNum;
		this.machineFoodType= machineFoodType;
		this.foodList = foodList;
		
		this.machine = machine;
		
	}

	public void run() {
		try {
			//YOUR CODE GOES HERE...	
				Simulation.logEvent(SimulationEvent.machineCookingFood(machine, machineFoodType));
				Thread.sleep(machineFoodType.cookTimeMS);
				Simulation.logEvent(SimulationEvent.machineDoneFood(machine, machineFoodType));
				Simulation.logEvent(SimulationEvent.cookFinishedFood(currCook, machineFoodType,orderNum));
				synchronized(foodList){
					foodList.remove();
					foodList.notifyAll();	
				}
				synchronized(currCook.completedFood){
					currCook.completedFood.add(machineFoodType);
					currCook.completedFood.notifyAll();	
				}
			
			
		} catch(InterruptedException e) { }
	}
}

