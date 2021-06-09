/**
 *
 * @author Ngonidzaishe Erica Chipato
 * 218327315
 */
package za.ac.cput.assignment3project;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Collection {
    
    
    private FileReader fr;
    private BufferedReader br;
    private BufferedWriter bw;
    private FileWriter fw;
    private ObjectInputStream input;
    private Customer temp;
    private ArrayList holders = new ArrayList();
    private ArrayList customers = new ArrayList();
    private ArrayList suppliers = new ArrayList();
    private int[] cusAge = new int[6];
    private int can = 0;
    private int cannotRent = 0;
    
    //Customer Array 
    private String[] IDs = new String[6];
    private String[] name = new String[6];
    private String[] surname = new String[6];
    private String[] address = new String[6];
    private String[] birth = new String[6];
    private Double[] credit = new Double[6];
    private Boolean[] canRent = new Boolean[6];
    
    //Supplier array
    private String[] sIDs = new String[5];
    private String[] sNames = new String[5];
    private String[] sProductTypes = new String[5];
    private String[] sProductDescription = new String[5];
    
    //Open file method
    public void openFile(){
        try{
            input = new ObjectInputStream(new FileInputStream("stakeholder.ser"));
        }
        catch(IOException ioe)
        
        {
            System.out.println("error opening ser file: " + ioe.getMessage());
        }
    }
    
    
    public void closeFiles(){
        try{
         input.close();
        }
        catch(IOException ioe)
        {
            System.out.println(ioe);
        }
    }
    
    //add to arraylist 
    public void readFile() throws ClassNotFoundException{
        try{
            for(int i = 0; i < 11; i++){
                holders.add(input.readObject());
            }
            
        }catch(IOException ioe){
            System.out.println("Error reading from ser file "+ ioe);
        }
    }
    
    
    // ArrayList allocated => customers and suppliers
    public void allocate(){
        for (int i=0; i < holders.size(); i ++){
            if (holders.get(i) instanceof Customer){
                customers.add(holders.get(i));
            }else{
                suppliers.add(holders.get(i));
            }
        }
    }
    
    //sort  objects of customers arrayList
    // comparator used to sort the arraylist
    public void sortId(){
        Collections.sort(customers, Comparator.comparing(Customer::getStHolderId));

    }
    
    //sort  objects of the suppliers arrayList
    // comparator to sort the arrayList
    public void sortName(){
        Collections.sort(suppliers, Comparator.comparing(Supplier::getName));
    }
    
    //determine age of the customer
    public void determineAge(){
        String yr;
        int age = 0;
        int birthYear;
        int month;
        int day;
        
        //loop to access the date of birth of every customer
        for(int i = 0; i < 6; i++){
            Customer c = (Customer) customers.get(i);
            String str = c.getDateOfBirth();
            
            String [] arr = str.split("-");
            //parse string to int
            birthYear = Integer.parseInt(arr[0]);
            month = Integer.parseInt(arr[1]);
            day = Integer.parseInt(arr[2]);
            
            //checks the customer was born on any other date 
            if(day > 8 && month > 6){
                age = ((2021 - birthYear) -1);
                String name = c.getFirstName();
                
                cusAge[i] = age;
            }else{
                age = (2021 - birthYear);
                String name = c.getFirstName();
                
                cusAge[i] = age;
            }
            
            //calling the store objects 
            storeObjects(i);
            //calling method to calculate number of customers that can rent and those that cannot. 
            canRent(i);
        }
    }
    
    // store the objects' data in supplier arrayList
    public void storeStoreSupplierObjects(){
        for (int i = 0; i < 5; i ++){
            // stores suppliers objects' data in different arrays
            createSupplierArrays(i);
        }
    }
    // create arrays that will store the object's data
    public void storeObjects(int i){
        
        Customer c = (Customer) customers.get(i);
            
            
            //getting the user Id's  
            String a = c.getStHolderId();
            IDs[i] = a;
            
            //getting the customer names 
            String b = c.getFirstName();
            name[i] = b;
            
            //getting the customer surnames 
            String d = c.getSurName();
            surname[i] = d;
            
            //getting the customer addresses 
            String e = c.getAddress();
            address[i] = e;
            
            //getting the customer date of births 
            String f = c.getDateOfBirth();
            birth[i] = f;
            
            //getting the customer credit and putting 
            Double g = c.getCredit();
            credit[i] = g;
            
            //getting the customer canRent value 
            Boolean h = c.getCanRent();
            canRent[i] = h;
            
            
    }
    
    // create arrays to store  supplierobject  data
    public void createSupplierArrays(int i){
        Supplier s = (Supplier) suppliers.get(i);
        
            //getting the supplier Id's 
            String a = s.getStHolderId();
            sIDs[i] = a;
            
            //getting the supplier names 
            String b = s.getName();
            sNames[i] = b;
            
            //getting the supplier prod types 
            String c = s.getProductType();
            sProductTypes[i] = c;
            
            //getting the supplier Description 
            String d = s.getProductDescription();
            sProductDescription[i] = d;
            
            
    }
    
    
    public void reFormat(){
        for(int i = 0; i < 6; i++){
           Customer c = (Customer) customers.get(i);
           String str = c.getDateOfBirth();
           
           String [] arr = str.split("-");
           
           String temp = arr[0];
           arr[0] = arr[2];
           arr[2] = temp;
           
           // change the number to the month
           
           int num = Integer.parseInt(arr[1]);
           String[] months = {"Jan", "Feb", "Mar","Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
           arr[1] = months[num-1];
           String joined = String.join(" ", arr);
          
           c.setDateOfBirth(joined);
        }
    }
    
    // determine the customers who can rent and those that cannot rent
    public void canRent(int i){
        Customer c = (Customer) customers.get(i);
        Boolean bool = c.getCanRent();
        
        if(bool == true){
            can++;
        }else{
           cannotRent++;
        }
        
    }
    
    
    // print the value of the objects in Customers ArrayList
    public void customersFile(){    
        
        
        
        String output = "========================== Customers ============================\n" +
                            "Id       Name        Surname        Date of Birth         Age\n"
                          + "================================================================\n" +
                  IDs[0]+"     " + name[0] + "        " + surname[0] + "       " + birth[0] + "            " + cusAge[0] + "\n" +
                  IDs[1]+"     " + name[1] + "        " + surname[1] + "       " + birth[1] + "            " + cusAge[1] + "\n" +
                  IDs[2]+"     " + name[2] + "        " + surname[2] + "       " + birth[2] + "            " + cusAge[2] + "\n" +
                  IDs[3]+"     " + name[3] + "        " + surname[3] + "       " + birth[3] + "            " + cusAge[3] + "\n" +
                  IDs[4]+"     " + name[4] + "        " + surname[4] + "       " + birth[4] + "            " + cusAge[4] + "\n" +
                  IDs[5]+"     " + name[5] + "        " + surname[5] + "       " + birth[5] + "            " + cusAge[5] + "\n" + "\n" +
                  "Number of customers who can rent:  "  + can + " \n" +
                  "Number of customers who cannot rent:  " + cannotRent;
        
        try {
            //creating new file
            File file = new File("CustomerOutFile.txt");
            
            //checking whether our file exists
            if(!file.exists()){
                file.createNewFile(); 
            }
            
            PrintWriter pw = new PrintWriter(file); 
            pw.println(output);
            pw.close();
            System.out.println("Successfully written customer details"); 
        }
        catch (IOException ex) {
                //catch block
                ex.printStackTrace();
            }
            
        
    }
    
    //o print the values of the objects in Suppliers arrayList
    public void suppliersFile(){
        String output = "========================== Customers ============================\n" +
                            "Id       Name        prod Type        Description"  + "\n"    
                          + "================================================================\n" +
                  sIDs[0]+"     " + sNames[0] + "        " + sProductTypes[0] + "       " + sProductDescription[0] + "\n" +
                  sIDs[1]+"     " + sNames[1] + "        " + sProductTypes[1] + "       " + sProductDescription[1] + "\n" +
                  sIDs[2]+"     " + sNames[2] + "        " + sProductTypes[2] + "       " + sProductDescription[2] + "\n" +
                  sIDs[3]+"     " + sNames[3] + "        " + sProductTypes[3] + "       " + sProductDescription[3] + "\n";
        
        try {
            //creating new file
            File file = new File("SupplierOutFile.txt");
            
            //checking if file exists
            if(!file.exists()){
                file.createNewFile(); 
            }
            
            PrintWriter pw = new PrintWriter(file); 
            pw.println(output);
            pw.close();
            System.out.println("Successfully written supplier details"); 
        }
        catch (IOException ex) {
            //catch block
            ex.printStackTrace();
        }
    }
    
    public static void main(String[] args) throws IOException, ClassNotFoundException{
        
Collection  collection = new Collection();
        //open file
        collection.openFile();
        //read file
        collection.readFile();
        //close t file
        collection.closeFiles();
        // Allocate arrayLists customers & suppliers 
        collection.allocate();
        //sorts the array by the stakeholderId's 
        collection.sortId();
        //sorts the suppliers ID by Name
        collection.sortName();
        //determine the age of the customer
        collection.determineAge();
        //re-format the date
        collection.reFormat();
        //store supplier objects
        collection.storeStoreSupplierObjects();
        //write to customer out file text
        collection.customersFile();
        //write to the Supplier out file text
        collection.suppliersFile();
        
        
        
    }
}
  


