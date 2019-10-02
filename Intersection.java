package Traffic.RoadNetwork;
import Traffic.RoadNetwork.BlockType;
import Traffic.RoadNetwork.Block;
import Traffic.Auto;
import java.util.Vector;
import java.lang.Math;

public class Intersection extends Block{
	
	public Intersection(){
		super(BlockType.valueOf("BLOCK_INTERSECT"));
	}
	public Intersection(int BlockNo, Auto v, Block prevBlock){
		super(BlockType.valueOf("BLOCK_INTERSECT"), BlockNo);
		super.setAuto(v);
		super.setPrev(prevBlock);
	}
	
	public boolean MoveForward(){
		boolean mF = false;
		if(super.getBlockNo()==0){
			mF = true;
		}
		return mF;
	}
	
	public void setNeighbhors(Block[] neighhors){
		Vector<Block> road = new Vector<Block>();
		for(int i = 0; i<neighhors.length;i++){
			road.add(neighhors[i]);
		}
	}
	
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
}