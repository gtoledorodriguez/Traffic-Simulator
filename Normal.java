package Traffic.RoadNetwork;
import Traffic.RoadNetwork.BlockType;
import Traffic.RoadNetwork.Block;
import Traffic.Auto;
import java.util.Vector;

public class Normal extends Block{
	
	public Normal(){
		super(BlockType.valueOf("BLOCK_NORMAL"));
	}
	public Normal(int BlockNo, Auto v, Block nextBlock, Block prevBlock){
		super(BlockType.valueOf("BLOCK_NORMAL"), BlockNo);
		super.setAuto(v);
		super.setNext(nextBlock);
		super.setPrev(prevBlock);
	}
	
	public boolean MoveForward(){
		boolean mF = false;
		if(super.getNext().getAuto() == null){
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
}