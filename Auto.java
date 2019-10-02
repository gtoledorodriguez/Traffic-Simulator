package Traffic;

public class Auto{
	private int count;
	private int ID;
	
	public Auto(){
		String auto = "Vehicle";
		this.count++;
		this.ID = count;
	}
	
	public void setID(int count){
		this.ID = count;
	}
	public int getID(){
		return this.ID;
	}
}