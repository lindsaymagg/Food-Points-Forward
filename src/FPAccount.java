import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FPAccount {
	
	    //instance variables
		private String myNetid;
		private double myFpCount;
		private String myFoodPlan;
		private boolean myAccess;
		
		//setters and getters
		public String getMyNetid() {
			return myNetid;
		}
		
		public double getMyFpCount() {
			return myFpCount;
		}
		
		public void setMyFpCount(double myFpCount) {
			this.myFpCount = myFpCount;
		}
		
		public String getMyFoodPlan() {
			return myFoodPlan;
		}
		

		public boolean isMyAccess() {
			return myAccess;
		}
		
		public void setMyAccess(boolean myAccess) {
			this.myAccess = myAccess;
		}
		
	/**
	 * Construct a Food Point Account using student information
	 * @param input netid, food point count, food plan type, and whether or not they can make
	 * requests for food points
	 */		
	public FPAccount(String netid, double fpCount, String foodPlan, boolean hasAccess) {
			myNetid = netid;
			myFpCount = fpCount;
			myFoodPlan = foodPlan;
			myAccess = hasAccess;
		}
		
	/**
	 * Move food points from one account (donor) to another account (recipient)
	 * @param input netid, food point count, food plan type, and whether or not they can make
	 * requests for food points. Edit donor file, recipient file, and requests file with updated info.
	 */	
	public void donateTo(FPAccount b, double amount) {
		this.setMyFpCount(myFpCount - amount);
		b.setMyFpCount(b.getMyFpCount() + amount);
		String[] both = this.makePTLine(this, b, amount);
		String transLineDon = "\n"+both[0];
		String transLineRec = "\n"+both[1];
		String currStatsDon = "\n"+this.myNetid +" " + this.myFpCount +" " + this.myFoodPlan +" " + this.myAccess;
		String currStatsRec = "\n"+ b.myNetid +" " + b.myFpCount +" " + b.myFoodPlan +" " + b.myAccess;	
		try {
    	    Files.write(Paths.get("/Users/lindsaymagg/eclipse/cs201/FoodPointAccount/src/Students/" + 
    	    		this.myNetid), transLineDon.getBytes(), StandardOpenOption.APPEND);
    	}catch (IOException e) {
    	    System.out.println("fail");
    	}
		try {
    	    Files.write(Paths.get("/Users/lindsaymagg/eclipse/cs201/FoodPointAccount/src/Students/" + 
    	    		this.myNetid), currStatsDon.getBytes(), StandardOpenOption.APPEND);
    	}catch (IOException e) {
    	    System.out.println("fail");
    	}	
		try {
    	    Files.write(Paths.get("/Users/lindsaymagg/eclipse/cs201/FoodPointAccount/src/Students/" + 
    	    		b.myNetid), transLineRec.getBytes(), StandardOpenOption.APPEND);
    	}catch (IOException e) {
    	    System.out.println("fail");
    	}
		try {
    	    Files.write(Paths.get("/Users/lindsaymagg/eclipse/cs201/FoodPointAccount/src/Students/" + 
    	    		b.myNetid), currStatsRec.getBytes(), StandardOpenOption.APPEND);
    	}catch (IOException e) {
    	    System.out.println("fail");
    	}
	}
	/**
	 * Creates a line of text for the donor and the recipient that will be written into their files.
	 * @param donor, recipient, amount donated
	 * @return an array where the first element is the post-transaction string for the donor
	 * and the second element is the post-transaction string for the recipient
	 */	
	private String[] makePTLine(FPAccount donor, FPAccount recipient, double amount) {
		String donorLine = ("Donated " + amount + " to " + recipient.getMyNetid());
		String recipientLine = ("Received " + amount + " from " + donor.getMyNetid());
		String[] retArray = {donorLine, recipientLine};
		return retArray;
	}		
}
