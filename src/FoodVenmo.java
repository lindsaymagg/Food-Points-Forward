import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner; 
import java.time.format.DateTimeFormatter;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class FoodVenmo {
	public static void createTexts() throws IOException {
		String filepathMaster = "/Users/lindsaymagg/eclipse/cs201/FoodPointAccount/src/MasterList"; //line changes based on whose computer it’s on
		  File file = new File (filepathMaster);
		  Scanner sc = new Scanner(file);
		  
		  String filepathStudents = "/Users/lindsaymagg/eclipse/cs201/FoodPointAccount/src/Students/";
		  while (sc.hasNextLine()) {
			String line = sc.nextLine();

			String[] lineArr = line.split(" ");
			String filepathIndiv = filepathStudents + lineArr[0];

			if (new File(filepathIndiv).isFile()) {
				continue;
			}
			else {
				File fileNow = new File(filepathIndiv);
				Writer fileWriter = new FileWriter(filepathIndiv, true);
				PrintWriter printWriter = new PrintWriter(fileWriter);
				printWriter.write(line);
				printWriter.close();
			}
		  } 

	}
	
	public static void main(String[] args) throws IOException{
		createTexts();

		 Scanner scan = new Scanner(System.in); //Creates a new scanner
	     System.out.println("Please enter your net ID:"); //Asks question
	     String netid = scan.nextLine(); //Waits for input
	     
	     //We would need to create for loop to loop through netid's array
	     //If netid matches netid in array, then we get all info 
	     //for that net id 
	     
	     //write in exception if netID does not exist
	     
	     String fileName = netid;
	     String filepath = "/Users/lindsaymagg/eclipse/cs201/FoodPointAccount/src/Students/" + fileName; //line changes based on whose computer it’s on
	     File file = new File (filepath);
	     while (! file.exists()) { // It's not there!
	         System.out.println("Try again. That student does not exist.");
	         System.out.println("Please enter your net ID:"); //Asks question
		     netid = scan.nextLine(); 
		     fileName = netid;
		     filepath = "/Users/lindsaymagg/eclipse/cs201/FoodPointAccount/src/Students/" + fileName; //line changes based on whose computer it’s on
		     file = new File (filepath);
	       }
	     Scanner sc = new Scanner(file);
	     
	     String last = "";
	     while (sc.hasNextLine()) {
	    		last = sc.nextLine();  //gets the last line (which holds current balance)
	    }
	    String[] lastArr= last.split(" ");
	    double currentBalance = Double.valueOf(lastArr[1]);	//gets current balance
	    boolean access = Boolean.valueOf(lastArr[3]); //string to boolean
	    String plan = lastArr[2];
	    FPAccount currentAcc = new FPAccount(netid, currentBalance, plan, access);
	    
	    int condition = 0;
	    while (condition == 0) {
	    
	    //Choose option 1, 2, or 3 for methods to run. 4 quits.
	    System.out.println("Option 1 - Check Food Point Balance" 
	    		 + "\n" + "Option 2 - Request Food Points" 
	    		 + "\n" + "Option 3 - Donate Food Points"
	    		 + "\n" + "Please choose option 1, 2, or 3"
	    		 + "\n" + "Type 4 to quit.");
	     String op = scan.nextLine();
	     while(!op.equals("1") &&!op.equals("2") && !op.equals("3") && !op.equals("4")) {
	    	 System.out.println("Please choose option 1, 2, or 3");
	    	 op = scan.nextLine();
	     }
	     int option = Integer.valueOf(op); 
	     if(option == 1) {
	    	 System.out.println(currentAcc.getMyFpCount());
	    	 
	    	 LocalDate d1 = LocalDate.parse("2018-08-20", DateTimeFormatter.ISO_LOCAL_DATE);
	    	 LocalDate d2 = LocalDate.now();
	    	  Duration diff = Duration.between(d1.atStartOfDay(), d2.atStartOfDay());
	    	  long diffDays = diff.toDays();
	    	  //find plan
	    	  float limit = (float) (2421.98 - (22.22 * diffDays));
	    	  
	    	  if (currentAcc.getMyFpCount() == limit) {
	    		  System.out.println("You are right on track!\n");
	    	  }
	    	  else if(currentAcc.getMyFpCount() > limit) {
	    		  System.out.println("You are " + String.valueOf(currentAcc.getMyFpCount() - limit) + " food points over the recommended amount for this point in the semester.\n");
	    	  }
	    	  else {
	    		  System.out.println("You are " + String.valueOf(limit - currentAcc.getMyFpCount()) + " food points under the recommended amount for this point in the semester.\n");
	    	  }

	     }
	     
	     else if (option == 2) {
	    	 System.out.println("Request amount: ");
	    	 while (!scan.hasNextInt()) {
	    		 System.out.println("Please enter a whole number.");
	    		 String temp = scan.nextLine();
	    	 }
	    	 int amountReq = Integer.valueOf(scan.nextLine());
		      
		     System.out.println("Create a blurb: ");
		     String blurb = scan.nextLine(); 
		     System.out.println("What is your financial need (1 is not needy, 10 is great need): ");
		     String need = scan.nextLine(); 
		     String reqToWrite = netid + ":" + amountReq + ":"+ blurb + ":" + need + "\n";
		     
		     try {
		    	    Files.write(Paths.get("/Users/lindsaymagg/eclipse/cs201/FoodPointAccount/src/Requests.txt"), reqToWrite.getBytes(), StandardOpenOption.APPEND);
		    	}catch (IOException e) {
		    	    System.out.println("fail");
		    	}
		     List <String[]>mList = new ArrayList<String[]>();
	    	 
		     String reqFile = "/Users/lindsaymagg/eclipse/cs201/FoodPointAccount/src/Requests.txt"; //line changes based on whose computer it’s on
		     File file4 = new File (reqFile);
		     Scanner sc4 = new Scanner(file4);
		     
		     while (sc4.hasNextLine()) {
		    		String last3 = sc4.nextLine();  //gets the last line (which holds current balance)
		    		mList.add(last3.split(":"));
		    }
		    
		    int contains = 0;
		    for(int i = 0; i < mList.size()-1;i++) {
		    	if(mList.get(i)[0].equals(netid)) {
		    		contains = 1;
		    	}
		    }
		    
		    if(contains ==1) {
		    	System.out.println("You already have a pending request.");
		    	mList.remove(mList.size()-1);
		    }
		     
		    mList = sortList(mList);
		    try {
		        File requests = new File("/Users/lindsaymagg/eclipse/cs201/FoodPointAccount/src/Requests.txt");

		        FileWriter fw = new FileWriter(requests.getAbsoluteFile());
		        BufferedWriter bw = new BufferedWriter(fw);
		        
		        if(mList.size()!=0) {
		        	for(String[] reqs : mList)
			        {
			                for(int i=0; i < reqs.length; i++)
			                {
			                    bw.write(reqs[i]+":");
			                }
			            bw.write("\n");
			        }
			        bw.close();
		        }      
		    }catch(IOException e){
		        e.printStackTrace();
		    }
	     }
	     
	     else if (option == 3) {	    	 
	    	 List <String[]>myList = new ArrayList<String[]>();
	    	 
		     String reqFile = "/Users/lindsaymagg/eclipse/cs201/FoodPointAccount/src/Requests.txt"; //line changes based on whose computer it’s on
		     File file3 = new File (reqFile);
		     Scanner sc3 = new Scanner(file3);
		     
		     while (sc3.hasNextLine()) {
		    		String last3 = sc3.nextLine();  //gets the last line (which holds current balance)
		    		myList.add(last3.split(":"));
		    }		     
		     if (myList.size()==0) {
		    	 System.out.println("There are no requests at this moment.");
		     }
		     else {
		    	 System.out.println("Pending requests: ");		     
		     for(int i = 0; i < myList.size(); ++i) {
		    	  System.out.println(myList.get(i)[0] + " " + myList.get(i)[1] + " " + myList.get(i)[2]);
		     }    	  
		     System.out.println("Donation amount: ");
		     while (!scan.hasNextDouble()) {
	    		 System.out.println("You must enter a number.");
	    		 String temp = scan.nextLine();
	    	 }
		     Double amountDon = Double.valueOf(scan.nextLine());
		     while(amountDon > currentAcc.getMyFpCount()) {
		    	System.out.println("You do not have sufficient funds to donate.");
		    	System.out.println("Donation amount: ");
			    amountDon = Double.valueOf(scan.nextLine());	    	
		     }
		     System.out.println("Donate to (netID): ");
		     String recipient = scan.nextLine(); 
		     
		     String fileName2 = recipient;
		     String filepath2 = "/Users/lindsaymagg/eclipse/cs201/FoodPointAccount/src/Students/" + fileName2; //line changes based on whose computer it’s on
		     File file2 = new File (filepath2);
		     Scanner sc2 = new Scanner(file2);
		     
		     String last2 = "";
		     while (sc2.hasNextLine()) {
		    		last2 = sc2.nextLine();  //gets the last line (which holds current balance)
		    }
		    String[] lastArr2= last2.split(" ");
		    double currentBalance2 = Double.valueOf(lastArr2[1]);	//gets current balance
		    boolean access2 = Boolean.valueOf(lastArr2[3]); //string to boolean?
		    String plan2 = lastArr2[2];
		    
		    FPAccount recAccount = new FPAccount(recipient, currentBalance2, plan2, access2);
		    currentAcc.donateTo(recAccount, amountDon);
		    
		    for(int i = 0; i < myList.size(); ++i) {
		    	 if (myList.get(i)[0].equals(recipient)) {
		    		 double OG = Double.valueOf(myList.get(i)[1]);
		    		 if ((OG - amountDon) <= 0) {
		    			 myList.remove(i);
		    			 break;
		    		 }
		    		 myList.get(i)[1] = String.valueOf(OG - amountDon);
		    	 }
		    }	
		    
		    try {
		        File requests = new File("/Users/lindsaymagg/eclipse/cs201/FoodPointAccount/src/Requests.txt");

		        FileWriter fw = new FileWriter(requests.getAbsoluteFile());
		        BufferedWriter bw = new BufferedWriter(fw);
		        
		        if(myList.size()!=0) {
		        	for(String[] reqs : myList)
			        {
			                for(int i=0; i < reqs.length; i++)
			                {
			                    bw.write(reqs[i]+":");
			                }
			            bw.write("\n");
			        }
			        bw.close();
		        }      
		    }catch(IOException e){
		        e.printStackTrace();
		    }
	     }
	     }
	     
	     else if (option == 4) {
	    	 System.exit(0);
	     }
	     }
	}
	public static List <String[]> sortList(List <String[]> initial) {
		if(initial.size()!=0) {
			for(int j=0;j<initial.size();j++) {
        	for(int i=0; i < initial.size() -1; i++) {
	             String[]first = initial.get(i);
	             String[]second = initial.get(i+1);
	             double contribution1 = Double.valueOf(initial.get(i)[3]);
	             double contribution2 = Double.valueOf(initial.get(i+1)[3]);
			     if (contribution2 > contribution1) {
			     initial.set(i, second);
			     initial.set(i+1, first);
	                }
	        }
        }   
		}
		return initial;
	}

}
