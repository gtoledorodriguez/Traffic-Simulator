package Traffic.RoadNetwork;
import Traffic.RoadNetwork.BlockType;
import Traffic.RoadNetwork.Block;
import Traffic.RoadNetwork.Normal;
import Traffic.RoadNetwork.Intersection;
import Traffic.RoadNetwork.Traffic;
import Traffic.Auto;
import java.util.Vector;
import java.lang.Math;

public class Network{
	
	private int green = 5;
	private int yellow = 7;
	private int countCarExit = 0;
	private int Tick;
	private Vector<Integer> entryTime = new Vector<Integer>(); //Entry Time
	private Vector<Integer> exitTime = new Vector<Integer>(); // Exit Time
	private Vector<String> entryLane = new Vector<String>(); //Entry Lane
	private Vector<String> exitLane = new Vector<String>(); //Exit Lane
	private int count = 0;
	private Vector<Integer> IDs = new Vector<Integer>();
	
	public Network(){
		boolean ignore = true;
		this.Tick = 0;
	}
	
	public int getTick(){
		return this.Tick;
	}
	public void setTick(int tick){
		this.Tick=tick;
	}
	//Entry Time
	public Vector<Integer> getEntryTime(){
		return this.entryTime;
	}
	public void setEntryTime(int tick){
		this.entryTime.add(tick);
	}
	//Exit Time
	public Vector<Integer> getExitTime(){
		return this.exitTime;
	}
	public void setExitTime(int tick){
		this.exitTime.add(tick);
	}
	//Entry Lane
	public Vector<String> getEntryLane(){
		return this.entryLane;
	}
	public void setEntryLane(String lane){
		this.entryLane.add(lane);
	}
	//Exit Lane
	public Vector<String> getExitLane(){
		return this.exitLane;
	}
	public void setExitLane(String lane){
		this.exitLane.add(lane);
	}
	//IDs
	public Vector<Integer> getIDs(){
		return this.IDs;
	}
	public void setIDs(int count){
		this.IDs.add(count);
	}
	
	public void setBlocks(Vector<Block> b){
		for(int i = 0; i<b.size();i++){
			if(b.get(i).getType() == BlockType.valueOf("BLOCK_INTERSECT")){
				b.get(i).setBlockNo(i);
				b.get(i).setAuto(null);
				if((i+1)<b.size()){
					b.get(i).setPrev(b.get(i+1));
				}
				else{
					b.get(i).setPrev(null);
				}
			}
			else{
				b.get(i).setBlockNo(i);
				b.get(i).setAuto(null);
				if((i+1)<b.size()){
					b.get(i).setPrev(b.get(i+1));
				}
				else{
					b.get(i).setPrev(null);
				}
				b.get(i).setNext(b.get(i-1));
			}
		}
	}
	
	public boolean checkNull(Auto a){
		boolean check = false;
		if(a==null){
			check = true;
		}
		return check;
	}
	
	//Had to put turnRate and turnLeft methods here instead of Intersection.java
	public boolean turnRate(){ //If true do a turn otherwise moveForward
		int rndVal = (int)(Math.random()*(11));
		boolean t = false;
		if(rndVal>5){
			t = true;
		}
		return t;
	}
	
	public boolean turnLeft(){//If true do a left turn, if false due right turn
		int value = (int)(Math.random()*(11));
		boolean lt = false;
		if(value>5){
			lt = true;
		}
		else{
			lt = false;
		}
		return lt;
	}
	
	public Auto generateVehicle(){
		Auto a = null;
		double rndValue = 0;
		rndValue = Math.random();
		if(rndValue>0.5){
			a = new Auto();
			//a = b;
			this.count++;
			this.setIDs(this.count);
			System.out.println("New Car enters the lane.");
		}
		return a;
	}
	
	public void intersectRules(Vector<Block> b, int i, int e){
		String exitLane = "";
		String rightExit = "";
		String leftExit = "";
		if(e == 1){
			exitLane = "\nNorth to South";
			rightExit = "\nEast to West";
			leftExit = "\nWest to East";
		}
		if(e == 2){
			exitLane = "\nSouth to North";
			rightExit = "\nWest to East";
			leftExit = "\nEast to West";
		}
		if(e == 3){
			exitLane = "\nWest to East";
			rightExit = "\nNorth to South";
			leftExit = "\nSouth to North";
		}
		if(e == 4){
			exitLane = "\nEast to West";
			rightExit = "\nSouth to North";
			leftExit = "\nNorth to South";
		}
		
		System.out.println("There is a car at intersection.");
		boolean tRate = this.turnRate();
		if(tRate == true){
			boolean lTurn = this.turnLeft();
			if(lTurn == true){
				System.out.println("Car turned left.");
				this.setExitLane(rightExit);
			}
			else{
				System.out.println("Car turned right.");
				this.setExitLane(leftExit);
			}
		}
		else{
			System.out.println("Car moved forward.");
			this.setExitLane(exitLane);
		}
		b.get(i).setAuto(null);//Removes Auto in intersection block
		this.countCarExit++;
		this.setExitTime(this.Tick);
	}
	
	
	public void entryRate(Vector<Block> b, int e){
		Auto temp = null;
		Auto pre = null;
		for(int i = 0; i<b.size();i++){
			
			if(b.get(i).getType() == BlockType.valueOf("BLOCK_INTERSECT")){
				if(b.get(i).getAuto()==null){
					//Do nothing
				}
				else{
					this.intersectRules(b,i,e);
				}
			}
			else if(b.get(i).MoveForward() == true){
					temp = b.get(i).getAuto();
					if((i+1)<b.size()){
						pre = b.get(i+1).getAuto();
						System.out.println("Car moves to next block.");
					}
					b.get(i-1).setAuto(temp);
					b.get(i).setAuto(pre);
					
					if((i-1)==0 && b.get(i-1).getAuto()!=null){
						this.intersectRules(b,i-1,e);
					}
			}
			
		}
	}
	
	public int waitTime(){
		int num = -1;
		int time = 0;
		if(this.entryTime.size()>this.exitTime.size()){
			num = this.exitTime.size();
		}
		else if(this.entryTime.size()<this.exitTime.size()){
			num = this.entryTime.size();
		}
		else if(this.entryTime.size()==this.exitTime.size()){
			num = this.entryTime.size();
		}
		
		for(int i = 0;i<num;i++){
			time = time + this.entryTime.get(i)+this.exitTime.get(i);
		}
		time = time/num;
		return time;
	}
	
	public String toString(){
		String str = "";
		str = "Num of Cars that Exit the Lanes: " + this.countCarExit;
		str = str + "\nAverage wait time: " + this.waitTime() + " Ticks";
		return str;
	}
	
	public void simulate(Vector<Block> b, Vector<Block> c, int s){
		int lastBlock = b.size()-1;
		int lastBlock2 = c.size()-1;
		this.countCarExit = 0;
		Auto temp = null;
		String lane1 = "\nNorth to South";
		String lane2 = "\nSouth to North";
		int eL1 = 1;
		int eL2 = 2;
		if(s == 1){
			lane1 = "\nWest to East";
			lane2 = "\nEast to West";
			eL1 = 3;
			eL2 = 4;
		}
		for(int i = 0; i<green; i++){//Green Light
			if(i ==0){
				System.out.println("\nGreen Light.");
			}
			System.out.println("\nTick " + i);
			
			//First Lane
			System.out.println(lane1);
			//System.out.println(b.get(lastBlock).getAuto());
			if(b.get(lastBlock).getAuto()==null){
				if(this.checkNull(b.get(i).getAuto())==true){
					temp = this.generateVehicle();
					b.get(i).setAuto(temp);
					if(b.get(i).getAuto()!=null){
						this.setEntryTime(this.Tick);
						this.setEntryLane(lane1);
						this.entryRate(b,eL1);
					}
				}
			}
			else{
				this.entryRate(b,eL1);
			}
			
			//Second Lane
			System.out.println(lane2);
			//System.out.println(c.get(lastBlock2).getAuto());
			if(c.get(lastBlock2).getAuto()==null){
				if(this.checkNull(c.get(i).getAuto())==true){
					temp = this.generateVehicle();
					c.get(i).setAuto(temp);
					if(c.get(i).getAuto()!=null){
						this.setEntryTime(this.Tick);
						this.setEntryLane(lane2);
						this.entryRate(c,eL2);
					}
				}
			}
			else{
				this.entryRate(c,eL2);
			}
			this.Tick++;
		}
		for(int i = green; i<yellow; i++){//Yellow Light
			if(i == green){
				System.out.println("\nYellow Light");
			}
			System.out.println("\nTick " + i);
			
			//First Lane
			System.out.println(lane1);
			if(b.get(lastBlock).getAuto()==null){
				if(this.checkNull(b.get(i).getAuto())==true){
					temp = this.generateVehicle();
					b.get(i).setAuto(temp);
					if(b.get(i).getAuto()!=null){
						this.setEntryTime(this.Tick);
						this.setEntryLane(lane1);
						this.entryRate(b,eL1);
					}
				}
			}
			else{
				this.entryRate(b,eL1);
			}
			
			//Second Lane
			System.out.println(lane2);
			if(c.get(lastBlock2).getAuto()==null){
				if(this.checkNull(c.get(i).getAuto())==true){
					temp = this.generateVehicle();
					c.get(i).setAuto(temp);
					if(c.get(i).getAuto()!=null){
						this.setEntryTime(this.Tick);
						this.setEntryLane(lane2);
						this.entryRate(c,eL2);
					}
				}
			}
			else{
				this.entryRate(c,eL2);
			}
			this.Tick++;
			if(i ==(yellow-1)){
				System.out.println("\nRed Light");
				System.out.println(this.toString());
			}
		}
	}
	
	
	
}