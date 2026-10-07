package parkinglot.messy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//enum VehicleType{
//	CAR,
//	BIKE
//}
//
//enum ParkingSpotType{
//	CAR,
//	BIKE
//}

class Vehicle{
	
	String vehicleNumber;
	String vehicleType;
	public Vehicle(String vehicleNumber, String vehicleType) {
		
		this.vehicleNumber = vehicleNumber;
		this.vehicleType = vehicleType;
	}
	public String getVehicleNumber() {
		return vehicleNumber;
	}
	public String getVehicleType() {
		return vehicleType;
	}
		
	
}

class ParkingSpot{
	String spotType;
	String spotNumber;
	boolean isOccupied;
	Vehicle vehicle;
	
	
	public ParkingSpot(String spotType, String spotNumber) {
		super();
		this.spotType = spotType;
		this.spotNumber = spotNumber;
	    this.isOccupied = false;
	}
	
	// can park
	
	boolean canPark(Vehicle vehicle) {
		
		if(isOccupied) {
			return false;
		}
		
		return this.spotType.equals(vehicle.vehicleType);
		
		
	}
		
	
	// park Vehicle
	
	void park(Vehicle vehicle) {
		this.vehicle = vehicle;
		this.isOccupied = true;
	}
	
	// unpark vehicle 
	void unPark() {
		this.vehicle = null;
		this.isOccupied = false;
	}
}

class ParkingFloor{
	
	int floorNumber;
	
	List<ParkingSpot> parkingSpots = new ArrayList<>();

	public ParkingFloor(int floorNumber) {
	
		this.floorNumber = floorNumber;
		//this.parkingSpots = new ArrayList<ParkingSpot>();
		
	}
	
	// jab bhi list use ho add and all ka functionality use karna chahiye
	public void addSpot(ParkingSpot sopt) {
		
		parkingSpots.add(sopt);
	}
	
	
	ParkingSpot findSpot(Vehicle vehicle) {
		
		for(ParkingSpot spot : parkingSpots) {
			if(spot.canPark(vehicle)) {
				return spot;
			}
		}
		
		return null;
	}
	
	
	
	
	// add spots
	//and find spot int the floor 
}

class ParkingLot{
	
	String name;
	int ticketCounter = 1;
	HashMap<Integer, Ticket> tickets = new HashMap<Integer, Ticket>();
	List<ParkingFloor> parkingFloors = new ArrayList<>();
	// Parking Lot Parks Vehicles
	
	
	// Add Floor
	
	void addFloor(ParkingFloor floor) {
		
		parkingFloors.add(floor);
		
	}
	
	ParkingLot(String name){
		
		this.name = name;
		
	}
	
	
	/*
	 * 1. Vehicle Enter
	 * 2. Find Spot and Park
	 * 3. Generate Ticket 
	 * 4. */
	
	
	
	
	
    public String getName() {
		return name;
	}

	

	public Ticket parkVehicle(Vehicle vehicle, int entryTime) {
    	
    	//Vehicle Enters  and i will get vehicle type.
    	//find the parking spot based on the vehocle type if available
    	//generate ticket 
    	System.out.println("A " + vehicle.getVehicleType() + "  with : " +  vehicle.getVehicleNumber() 
    	 + " is entreing in " + this.getName());
    	
    	// find Spot
    	
    	ParkingSpot spot = null;
    	
    	for(ParkingFloor floor : parkingFloors) {
    		
    		spot =  floor.findSpot(vehicle);
    		
    	    if(spot != null) {
    	    	break;
    	    }
    	}
    	
    	if(spot ==  null) {
    		System.out.println("Sorry No spots available for "+ vehicle.getVehicleType());
    		return null;
    	}
    	
    	spot.park(vehicle);
    	
    	// generate ticket
    	Ticket ticket = new Ticket(
    			ticketCounter,
    			vehicle,
    			spot,
    			entryTime
    			);
    	    	
    	tickets.put(ticketCounter, ticket);
    	System.out.println("Ticket Generated "  + ticket.getTicketNumber() + " for " +  vehicle.vehicleNumber);
    	ticketCounter++;

    	return ticket;
    }
	
	double calculateFee(Vehicle vehicle, int hours) {
		
		double pricePerHour = 0;
		
		String type = vehicle.getVehicleType().toLowerCase();
		
		switch(type) {
		case "car" :
			pricePerHour = 50;
			break;
		case "bike" :
			pricePerHour = 20;
			break;
		default : 
			System.out.println( "Invalid Vehicle type" );
		}
		
		return hours * pricePerHour;
		
		
	}
	
	void makePayment(double amount) {
		
		System.out.println("Payment successfull of " + amount );
		
	}
	
	
	
	// Exit
	
	void exit(int ticketId, int exitTime) {
		
		System.out.println("Vahicle is exiting !! \n");
		System.out.println("Ticket Id is : " + ticketId );
		Ticket ticket = tickets.get(ticketId);
		
		int eTime = ticket.exit(exitTime);
		// calculate hours 
		
	
		
		int hours = eTime - ticket.getEntryTime();
		
		// calculate Fee
		
		double fee = calculateFee(ticket.getVehicle(), hours);
		
		// makePyment
		makePayment(fee);
		
		ticket.parkingSpot.unPark();
		
		tickets.remove(ticketId);
		
		 System.out.println(
	                "Payment successful."
	        );

	        System.out.println(
	                "Parking spot "
	                        + ticket.parkingSpot.spotNumber
	                        + " is now free."
	        );

	        System.out.println(
	                "Vehicle "
	                        + ticket.vehicle.getVehicleNumber()
	                        + " exited successfully."
	        );
	    }
	
	
	
	
	
	//calculate Fee 
	// unpark
	
}

class Ticket{
	int ticketNumber;
	Vehicle vehicle;
	ParkingSpot parkingSpot;
	int entryTime;
	int exitTime;
	
	
	
	public Ticket(int ticketNumber, Vehicle vehicle, ParkingSpot parkingSpot, int entryTime) {
		super();
		this.ticketNumber = ticketNumber;
		this.vehicle = vehicle;
		this.parkingSpot = parkingSpot;
		this.entryTime = entryTime;
	}



	public int getTicketNumber() {
		return ticketNumber;
	}



	public Vehicle getVehicle() {
		return vehicle;
	}



	public ParkingSpot getParkingSpot() {
		return parkingSpot;
	}



	public int getEntryTime() {
		return entryTime;
	}



	int exit(int time) {
		//this.exitTime = time;
		return time;
	}
	
}



//

public class ParkingService {
	
	
	 
	 public static void main(String[] args) {
		 
		 ParkingLot parkingLot =
	             new ParkingLot("My Parking Lot");
		 
		  ParkingFloor floor1 =
		             new ParkingFloor(1);
		  
		  floor1.addSpot(
				  new ParkingSpot("CAR", "1")
				  );
		  
		  floor1.addSpot(
				  new ParkingSpot("CAR", "2")
				  );
		  
		  floor1.addSpot(
				  new ParkingSpot("BIKE", "3")
				  );
		  
		  floor1.addSpot(
				  new ParkingSpot("BIKE", "4")
				  );
		  
		  floor1.addSpot(
				  new ParkingSpot("BIKE", "5")
				  );
		  
		  
		  ParkingFloor floor2 =
		             new ParkingFloor(2);
		  
		  floor2.addSpot(
				  new ParkingSpot("BIKE", "1")
				  );
		  
		  floor2.addSpot(
				  new ParkingSpot("BIKE", "2")
				  );
		  
		  floor2.addSpot(
				  new ParkingSpot("CAR", "3")
				  );
		  
		  floor2.addSpot(
				  new ParkingSpot("CAR", "4")
				  );
		  
		  floor2.addSpot(
				  new ParkingSpot("CAR", "5")
				  );
		
		  
		  parkingLot.addFloor(floor1);
		  parkingLot.addFloor(floor2);
		  
		  Vehicle car1 = new Vehicle("1234", "CAR");
		  Vehicle car2 = new Vehicle("1235", "CAR");
		  
		  Vehicle bike1 = new Vehicle("0120", "BIKE");
		  //Vehicle bike = new Vehicle("0120", "BIKE");
		  
		  Ticket car1Ticket = parkingLot.parkVehicle(car1, 5);
		  Ticket car2Ticket =  parkingLot.parkVehicle(car2, 6);
		  
		  
		 
		  parkingLot.exit(car1Ticket.getTicketNumber(), 8);
		  parkingLot.exit(car2Ticket.getTicketNumber(), 8);
	}


     
	

}
