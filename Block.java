package Traffic.RoadNetwork;//change as needed
import Traffic.RoadNetwork.BlockType;
import Traffic.Auto;

public abstract class Block extends Auto{		
		private BlockType type; //0:normal,1:intersection,2:traffic-light
		private int BlockNo;
		private Auto vehicle; // object of the vehicle occupying the block
		private Block Next; // the next block on the lane
		private Block Prev; // previous block on the lane
		
		public Block(BlockType t){
			this.setType(t);
		}
		
		public Block(BlockType t, int no){
			this.setType(t);
			this.setBlockNo(no);
		}
		
		public BlockType getType(){
			return this.type;
		}
		public void setType(BlockType t){
			this.type = t;
		}
		
		public int getBlockNo(){
			return this.BlockNo;
		}
		public void setBlockNo(int posNo){
			this.BlockNo=posNo;
		}
		
		public Auto getAuto(){
			return this.vehicle;
		}
		public void setAuto(Auto v){
			this.vehicle=v;
		}
		
		public Block getNext(){
			return this.Next;
		}
		public void setNext(Block nextBlock){
			this.Next=nextBlock;			
		}
		
		public Block getPrev(){
			return this.Prev;
		}
		public void setPrev(Block prevBlock){
			this.Prev=prevBlock;			
		}
		
		public abstract void setNeighbhors(Block[] neighhors);//Initialization to set the road network
		public abstract boolean MoveForward();//method to move the vehicle to the next place in the road
}