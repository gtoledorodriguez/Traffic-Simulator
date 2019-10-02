package Traffic;
import Traffic.RoadNetwork.BlockType;
import Traffic.RoadNetwork.Block;
import Traffic.RoadNetwork.Normal;
import Traffic.RoadNetwork.Intersection;
import Traffic.RoadNetwork.Traffic;
import Traffic.RoadNetwork.Network;
import Traffic.Auto;
import java.util.Vector;
import java.lang.Math;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;

public class Simulator{
	public static void main(String[] args){
		Network sim = new Network();
		
		Vector<Block> inNS = new Vector<Block>();
		Vector<Block> inSN = new Vector<Block>();
		Vector<Block> inWE = new Vector<Block>();
		Vector<Block> inEW = new Vector<Block>();
		
		/*Originally used to store vehicles that left the lane
		Vector<Block> outNS = new Vector<Block>();
		Vector<Block> outSN = new Vector<Block>();
		Vector<Block> outWE = new Vector<Block>();
		Vector<Block> outEW = new Vector<Block>();
		*/
		
		//Intersection Block
		Intersection iBlock1 = new Intersection();
		Intersection iBlock2 = new Intersection();
		Intersection iBlock3 = new Intersection();
		Intersection iBlock4 = new Intersection();
		
		inNS.add(iBlock1);
		inSN.add(iBlock2);
		inWE.add(iBlock3);
		inEW.add(iBlock4);
		
		//Traffic Block
		Traffic tBlock1 = new Traffic();
		Traffic tBlock2 = new Traffic();
		Traffic tBlock3 = new Traffic();
		Traffic tBlock4 = new Traffic();
		
		inNS.add(tBlock1);
		inSN.add(tBlock2);
		inWE.add(tBlock3);
		inEW.add(tBlock4);
		
		//Normal Block
		Normal[] nArr1 = new Normal[5];
		Normal[] nArr2 = new Normal[5];
		Normal[] nArr3 = new Normal[5];
		Normal[] nArr4 = new Normal[5];
		
		
		for(int i =0; i<nArr1.length;i++){
			Normal nBlock1 = new Normal();
			Normal nBlock2 = new Normal();
			Normal nBlock3 = new Normal();
			Normal nBlock4 = new Normal();
			
			nArr1[i] = nBlock1;
			nArr2[i] = nBlock2;
			nArr3[i] = nBlock3;
			nArr4[i] = nBlock4;
			
			inNS.add(nArr1[i]);
			inSN.add(nArr2[i]);
			inWE.add(nArr3[i]);
			inEW.add(nArr4[i]);
		}
		
		//sets the values of each block
			//currently sets Auto to null
		sim.setBlocks(inNS);
		sim.setBlocks(inSN);
		sim.setBlocks(inWE);
		sim.setBlocks(inEW);
		
		/*Check that they all start at null
		System.out.println("\nNorth to South\n");
		sim.checkAuto(inNS);
		System.out.println("\nSouth to North\n");
		sim.checkAuto(inSN);
		System.out.println("\nWest to East\n");
		sim.checkAuto(inWE);
		System.out.println("\nEast to West\n");
		sim.checkAuto(inEW);
		*/
		
		
		
		
		//Things needed for the out.txt
		Vector<Integer> entryTime = new Vector<Integer>(); //Entry Time
		Vector<String> entryLane = new Vector<String>(); //Entry Lane
		Vector<String> exitLane = new Vector<String>(); //Exit Lane
		Vector<Integer> IDs = new Vector<Integer>(); //IDs
		
		int rotation = 4;
		
		//Write out.txt
		try
		{
			FileOutputStream fos = new FileOutputStream( "out.txt", false );
			// false means we will be writing to out.txt,
			// rather than appending to it
			PrintWriter pw = new PrintWriter( fos );
			// write data to the file
			System.out.println("\nIf no update, assume cars are moving to the next block.");
			for(int i = 0; i<rotation; i++){ //Simulates traffic for a certain number of rotations
				System.out.println("\nNorth and South Lanes the light is GREEN\nWest and East Lanes the light is RED");
				sim.simulate(inNS, inSN,0);
				
				System.out.println("\nWest and East  Lanes the light is GREEN\nNorth and South Lanes the light is RED");
				sim.simulate(inWE, inEW,1);
			}
			
			IDs = sim.getIDs();
			entryTime = sim.getEntryTime();
			entryLane = sim.getEntryLane();
			exitLane = sim.getExitLane();
			int loop = -1;
			
			//Loop to find the smallest vector size
			if(IDs.size()>entryTime.size()){
				loop = entryTime.size();
				if(entryTime.size()>entryLane.size()){
					loop = entryLane.size();
					if(entryLane.size()>exitLane.size()){
						loop = exitLane.size();
					}
				}
				else if(entryTime.size()>exitLane.size()){
					loop = exitLane.size();
				}
			}
			else{
				loop = IDs.size();
				if(IDs.size()>entryLane.size()){
					loop = entryLane.size();
					if(entryLane.size()>exitLane.size()){
						loop = exitLane.size();
					}
				}
				else if(IDs.size()>exitLane.size()){
					loop = exitLane.size();
				}
			}
				
			for(int i = 0; i<loop;i++){
				pw.print("ID: ");
				pw.println(IDs.get(i));
				
				pw.print("Entry Time: ");
				pw.println(entryTime.get(i));
				
				pw.print("Entry Lane: ");
				pw.println(entryLane.get(i));
				
				pw.print("Exit Lane: ");
				pw.println(exitLane.get(i));
			}
			pw.close( );
		}
		catch(FileNotFoundException fnfe )
		{
			System.out.println( "Unable to find out.txt" );
		}
		
	}
}