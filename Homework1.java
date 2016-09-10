import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;

public class Homework1 
{
    static int RECORD_SIZE = 71;
    static int NUM_RECORDS = 4110;
    //static String FILENAME = "input.txt";
    static String FILENAME = "";
    static boolean FILEISOPEN = false;
    static RandomAccessFile DIN;
    
    public static void PrintMenu()
    {
    	System.out.println("Please input which operation you would like to perform.");
    	System.out.println("Menu of Operations:");
    	System.out.println("1) Create New Database");
    	System.out.println("2) Open Database");
    	System.out.println("3) Close Database");
    	System.out.println("4) Display record");
    	System.out.println("5) Update record");
    	System.out.println("6) Create report");
    	System.out.println("7) Add a record");
    	System.out.println("8) Delete a record");
    	System.out.println("9) Quit");
    }
    
    public static void CreateNewDatabase(String filename) throws IOException
    {
    	PrintWriter writer = new PrintWriter(filename + ".txt", "UTF-8");
    	writer.println("0");
    	writer.close();
    }
    
    public static void OpenDatabase(String filename) throws FileNotFoundException
    {
    	if (FILEISOPEN)
    	{
    		System.out.println("Please close the current database before opening another one.");
    	}
    	else
    	{
    		FILENAME = filename;
    		DIN = new RandomAccessFile(FILENAME, "rw");    		
    		FILEISOPEN = true;
    	}
    	
    }
    
    public static void CloseCurrentDatabase() throws IOException
    {
    	FILEISOPEN = false;
    	DIN.close();
    }
    
    public static void DisplayRecord(String id) throws IOException
    {
    	if (FILENAME == "")
    	{
    		System.out.println("Please open a database.");
    	}
    	else
    	{
    		System.out.println(binarySearch(DIN, id));
    	}
    }
    
    public static void UpdateRecord(String id) throws IOException
    {
    	if (FILENAME == "")
    	{
    		System.out.println("Please open a database.");
    	}
    	else
    	{    		
    		int Low = 0;
    	    int High = NUM_RECORDS;
    	    int Middle;
    	    String MiddleId;
    	    String record = "NOT_FOUND";
    	    boolean Found = false;

            while (!Found && (High >= Low)) 
            {
                Middle = (Low + High) / 2;
                record = getRecord(DIN, Middle);
                MiddleId = record.substring(0,5);
         
                int result = MiddleId.compareTo(id);
                if (result == 0)   // ids match
                {
                	Found = true;
                	DIN.seek(0); // return to the top of the file
                    DIN.skipBytes(Middle * RECORD_SIZE);
                    System.out.print(record);
                    String recordId = record.substring(0, 6);
                    String experience = record.substring(6, 17);
                    String married = record.substring(17, 25);
                    String wage = record.substring(25, 38);
                    String industry = record.substring(38, 66);
                    PrintUpdateMenu();
                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                	String input;
                	while((input = br.readLine()) != "0")
                	{
                		String temp;
                		switch (input)
                		{
	                		case "1":
	                			System.out.print("Please enter a new value for experience: ");
	                			temp = br.readLine();	                			
	                			if (temp.matches("[0-9]+") && temp.length() <= 10)
	                			{
	                				experience = temp;
	                				if (experience.length() < 10)
	                				{
	                					for ( ; experience.length() <10 ; )
	                					{
	                						experience += " ";
	                					}
	                				}
	                			}
	                			else
	                			{
	                				System.out.println("Invalid value for experience.");
	                			}
	                			break;
	                		case "2":
	                			System.out.print("Please enter 'y' for yes or 'n' for no: ");
	                			temp = br.readLine();	                			
	                			if (temp == "y")
	                			{
	                				married = "yes    ";
	                			}
	                			else if (temp == "n")
	                			{
	                				married = "no     ";
	                			}
	                			else
	                			{
	                				System.out.println("Invalid value for married.");
	                			}
	                			break;
	                		case "3":
	                			System.out.print("Please enter a new value for wage: ");
	                			temp = br.readLine();	                			
	                			if (temp.matches("^[0-9]*\\.?[0-9]*$") && temp.length() <= 12)
	                			{
	                				wage = temp;
	                				if (wage.length() < 12)
	                				{
	                					for ( ; wage.length() <12 ; )
	                					{
	                						wage += " ";
	                					}
	                				}
	                			}
	                			else
	                			{
	                				System.out.println("Invalid value for wage.");
	                			}
	                			break;
	                		case "4":
	                			System.out.print("Please enter a new value for industry. Please separate each word with an underscore: ");
	                			temp = br.readLine();	                			
	                			if (temp.matches("[ $s =~ ^[A-Za-z_]+$ ]") && temp.length() <= 27)
	                			{
	                				industry = temp;
	                				if (industry.length() < 27)
	                				{
	                					for ( ; industry.length() <27 ; )
	                					{
	                						industry += " ";
	                					}
	                				}
	                			}
	                			else
	                			{
	                				System.out.print("Invalid value for married.");
	                			}
	                			break;
                			default:
                				System.out.println("Please enter a valid input.");
                				break;
                		}
                		System.out.println("Updated record: " + recordId + experience + married + wage + industry);
                		PrintUpdateMenu();
                	}
                	DIN.writeChars(recordId + experience + married + wage + industry);                	
                }                	
                else if (result < 0)
                    Low = Middle + 1;
                else
                    High = Middle -1;
            }    		
    	}
    }
    
    public static void PrintUpdateMenu()
    {
    	System.out.println("Please input which field you would like to update.");    	
    	System.out.println("1) Experience");
    	System.out.println("2) Married");
    	System.out.println("3) Wage");
    	System.out.println("4) Industry");
    	System.out.println("0) Done");
    }
    
    public static void CreateReport() throws IOException
    {
    	BufferedReader br = new BufferedReader(new FileReader(FILENAME + ".txt"));
    	PrintWriter writer = new PrintWriter(FILENAME + "Report" + ".txt", "UTF-8");
    	
    	for (int i = 0; i < 11; i++)
    	{
    		writer.println(br.readLine());
    	}
    	
    	br.close();
    	writer.close();    	
    }
    
    public static void AddRecord(String record)
    {
    	
    }
    
    public static void DeleteRecord(String id) throws IOException
    {
		int Low = 0;
	    int High = NUM_RECORDS;
	    int Middle;
	    String MiddleId;
	    String record = "NOT_FOUND";
	    boolean Found = false;

        while (!Found && (High >= Low)) 
        {
            Middle = (Low + High) / 2;
            record = getRecord(DIN, Middle);
            MiddleId = record.substring(0,5);
     
            int result = MiddleId.compareTo(id);
            if (result == 0)   // ids match
            {
            	Found = true;
            	DIN.seek(0); // return to the top of the file
                DIN.skipBytes(Middle * RECORD_SIZE);
                String deletedString = "-1";
                for (int i = 0; i < 69; i++)
                {
                	deletedString += " ";
                }
                DIN.writeChars(deletedString); 
            }                    
            else if (result < 0)
                Low = Middle + 1;
            else
                High = Middle -1;
        }
        if (!Found)
        {
        	System.out.println("ID does not exist.");
        }
	
    }

    public static void main(String[] args) throws IOException 
    {    	
    	PrintMenu();
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	String input;
    	while ((input = br.readLine()) != "9")
    	{
    		int operation = Integer.parseInt(input);
    		
    		switch (operation)
    		{
	    		case 1:
	    			System.out.print("Please enter a name for the file to be created: ");
	    			CreateNewDatabase(br.readLine());
	    			break;
	    		case 2:
	    			//Check to see if a file is open
	    			System.out.print("Please enter the name of the file you would like to open: ");
	    			OpenDatabase(br.readLine());
	    			break;
	    		case 3:
	    			CloseCurrentDatabase();
	    			break;
	    		case 4:
	    			System.out.print("Please enter the ID of the record you would like to display: ");
	    			DisplayRecord(br.readLine());
	    			break;
	    		case 5:
	    			System.out.print("Please enter the ID of the record you would like to update: ");
	    			UpdateRecord(br.readLine());
	    			break;
	    		case 6:
	    			CreateReport();
	    			break;
	    		case 7:
	    			System.out.println("Please enter a value for each of the following fields: ");	    			
	    			
	    			//find what id to assign to new record
	    			String id = new String();
	    			
	    			boolean validExperience = false;
	    			System.out.print("Experience: ");
	    			String experience = new String();
	    			while (!validExperience)
	    			{
	    				experience = br.readLine();
	    				if (experience.matches("[0-9]+") && experience.length() <= 10)
            			{
            				if (experience.length() < 10)
            				{
            					for ( ; experience.length() <10 ; )
            					{
            						experience += " ";
            					}
            				}
            				validExperience = true;
            			}
            			else
            			{
            				System.out.println("Invalid value for experience.");
            			}
	    			}
	    			
	    			boolean validMarried = false;
	    			String married = new String();
	    			System.out.print("Married (Please enter 'y' for yes or 'n' for no): ");
	    			while (!validMarried)
	    			{
	    				married = br.readLine();	                			
            			if (married == "y")
            			{
            				married = "yes    ";
            				validMarried = true;
            			}
            			else if (married == "n")
            			{
            				married = "no     ";
            				validMarried = true;
            			}
            			else
            			{
            				System.out.println("Invalid value for married.");
            			}
	    			}

	    			boolean validWage = false;	
	    			String wage = new String();   			
	    			System.out.print("Wage: ");
	    			while (!validWage)
	    			{              
	    				wage = br.readLine();
            			if (wage.matches("^[0-9]*\\.?[0-9]*$") && wage.length() <= 12)
            			{
            				if (wage.length() < 12)
            				{
            					for ( ; wage.length() <12 ; )
            					{
            						wage += " ";
            					}
            				}
            				validWage = true;
            			}
            			else
            			{
            				System.out.println("Invalid value for wage.");
            			}
	    			}
	    			
	    			boolean validIndustry = false;
	    			String industry = new String();
	    			System.out.print("Industry: ");
	    			while (!validIndustry)
	    			{
	    				industry = br.readLine();
	    				if (industry.matches("[ $s =~ ^[A-Za-z_]+$ ]") && industry.length() <= 27)
            			{
            				if (industry.length() < 27)
            				{
            					for ( ; industry.length() <27 ; )
            					{
            						industry += " ";
            					}
            				}
            				validIndustry = true;
            			}
            			else
            			{
            				System.out.print("Invalid value for married.");
            			}
	    			}
	    			
	    			AddRecord(id + experience + married + wage + industry);
	    			break;
	    		case 8:
	    			System.out.print("Please enter the ID of the record you would like to delete: ");
	    			DeleteRecord(br.readLine());
	    			break;
	    		default:
	    			System.out.println("Invalid input. Please try another input.");
	    			break;
    		}
    	}
    }

    /*Get record number n-th (from 1 to 4360) */
    //public static String getRecord(RandomAccessFile Din, int recordNum) throws IOException 
    public static String getRecord(RandomAccessFile Din, int recordNum) throws IOException 
    {
    	String record = "NOT_FOUND";
        if ((recordNum >=1) && (recordNum <= NUM_RECORDS))
        {
            Din.seek(0); // return to the top fo the file
            Din.skipBytes(recordNum * RECORD_SIZE);
            record = Din.readLine();
        }
        return record;
    }

    /*Binary Search record id */
    public static String binarySearch(RandomAccessFile Din, String id) throws IOException 
    {
	    int Low = 0;
	    int High = NUM_RECORDS;
	    int Middle;
	    String MiddleId;
	    String record = "NOT_FOUND";
	    boolean Found = false;

        while (!Found && (High >= Low)) 
        {
            Middle = (Low + High) / 2;
            record = getRecord(Din, Middle);
            MiddleId = record.substring(0,5);
     
            int result = MiddleId.compareTo(id);
            if (result == 0)   // ids match
                Found = true;
            else if (result < 0)
                Low = Middle + 1;
            else
                High = Middle -1;
        }
        return record;
    }
}
