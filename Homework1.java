import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;

public class Homework1 
{
    static int RECORD_SIZE = 72;
    static int NUM_RECORDS = 0;
    static int NUM_OVERFLOW = 0;
    //static String FILENAME = "input.txt";
    static String FILENAME = "";
    static boolean FILEISOPEN = false;
    static RandomAccessFile DIN;
    static RandomAccessFile DIN_OVERFLOW;
    static RandomAccessFile DIN_CONFIG;
    
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
    	writer = new PrintWriter(filename + "Overflow.txt", "UTF-8");
    	writer = new PrintWriter(filename + "Config.txt", "UTF-8");
    	writer.println("0 0");
    	System.out.println("Created new database '" + filename + "'.\n");
    	writer.close();
    }
    
    public static void OpenDatabase(String filename) throws IOException
    {
		if (FILEISOPEN)
    	{
    		System.out.println("Please close the current database before opening another one.");
    	}
    	else
    	{
    		File f = new File(filename + ".txt");
    		if (f.isFile())
    		{
    			FILENAME = filename;
    			DIN = new RandomAccessFile(FILENAME + ".txt", "rw");
    			DIN_OVERFLOW = new RandomAccessFile(FILENAME + "Overflow.txt", "rw");
    			DIN_CONFIG = new RandomAccessFile(FILENAME + "Config.txt", "rw");
    			BufferedReader br = new BufferedReader(new FileReader(FILENAME + "Config.txt"));
    			String[] line = br.readLine().split(" ");
    			NUM_RECORDS = Integer.parseInt(line[0]);
    			NUM_OVERFLOW = Integer.parseInt(line[1]);
    			FILEISOPEN = true;
    			System.out.println("Database '" + FILENAME + "' is open.\n");
    		}
    		else
    		{
    			System.out.println("File does not exist.\n");
    		}        	
    	}    	
    }
    
    public static void CloseCurrentDatabase() throws IOException
    {
    	FILEISOPEN = false;
    	DIN.close();
    	DIN_CONFIG.close();
    	DIN_OVERFLOW.close();
    	System.out.println("Database '" + FILENAME + "' is closed.\n");
    }
    
    public static void DisplayRecord(String id) throws IOException
    {
    	if (FILENAME == "")
    	{
    		System.out.println("Please open a database.");
    	}
    	else
    	{
    		for (; id.length() < 5; )
    		{
    			id = "0" + id;
    		}
    		String sRecord = binarySearch(DIN, id);
    		String lRecord = linearSearch(DIN_OVERFLOW, id);
    		if (sRecord.charAt(6) == ' ')
    		{
    			System.out.println("NOT_FOUND");
    		}
    		else if (!sRecord.equals("NOT_FOUND"))
    		{
    			System.out.println(sRecord + "\n");
    		}
    		else if (!lRecord.equals("NOT_FOUND"))
    		{
    			System.out.println(lRecord + "\n");
    		}
    		/*else
    		{
    			System.out.println("NOT_FOUND");
    		}*/    		
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
    		for (; id.length() < 5; )
    		{
    			id = "0" + id;
    		}
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
                    System.out.println(record + "\n");
                    String recordId = record.substring(0, 6);
                    String experience = record.substring(6, 17);
                    String married = record.substring(17, 25);
                    String wage = record.substring(25, 38);
                    String industry = record.substring(38, 66);
                    PrintUpdateMenu();
                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                	String input;
                	while(!(input = br.readLine()).equals("0"))
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
	                			if (temp.equals("y"))
	                			{
	                				married = "yes    ";
	                			}
	                			else if (temp.equals("n"))
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
	                			if (temp.matches("^[a-zA-Z0-9_]*$") && temp.length() <= 27)
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
	                				System.out.print("Invalid value for industry\n.");
	                			}
	                			break;
                			default:
                				System.out.println("Please enter a valid input.\n");
                				break;
                		}
                		System.out.println("Updated record: \n" + recordId + experience + married + wage + industry + "\n");
                		PrintUpdateMenu();
                	}
                	DIN.writeBytes(recordId + experience + married + wage + industry);                	
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
    	
    	System.out.println("Report has been saved to " + FILENAME + "Report.");
    	br.close();
    	writer.close();    	
    }
    
    public static void AddRecord(boolean inSorted, String id, String experience, String married, String wage, String industry) throws IOException
    {
    	if (inSorted)
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
                    String tempRecord = id + " " + experience + " " + married + " " + wage + " " + industry;
                    //Take out -2?
                    for (; tempRecord.length() < RECORD_SIZE - 2; )
                    {
                    	tempRecord += " ";
                    }
                    DIN.writeBytes(tempRecord + "\r\n");              	
                }                	
                else if (result < 0)
                    Low = Middle + 1;
                else
                    High = Middle -1;
            }
            NUM_RECORDS++;
    	}
    	else
    	{    		
    		DIN_OVERFLOW.seek(0); // return to the top of the file
            DIN_OVERFLOW.skipBytes(NUM_OVERFLOW * RECORD_SIZE);
            String tempRecord = id + " " + experience + " " + married + " " + wage + " " + industry;
            //Take out -2?
            for (; tempRecord.length() < RECORD_SIZE - 2; )
            {
            	tempRecord += " ";
            }
            DIN_OVERFLOW.writeBytes(tempRecord + "\r\n");  
            NUM_OVERFLOW++;
            
            
            //Check if num_overflow == 4
    	}
    }
    
    public static void DeleteRecord(String id) throws IOException
    {
		int Low = 0;
	    int High = NUM_RECORDS;
	    int Middle;
	    String MiddleId;
	    String record = "NOT_FOUND";
	    boolean Found = false;
	    for (; id.length() < 5; )
		{
			id = "0" + id;
		}
	    
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
                //String deletedString = "-1";
                for (int i = 0; i < RECORD_SIZE - 7; i++)
                {
                	//deletedString += " ";
                	id += " ";
                }
                //DIN.writeBytes(deletedString); 
                DIN.writeBytes(id);
                System.out.println("Record " + id.trim() + "has been deleted.\n");
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
    
    public static String linearSearch(RandomAccessFile Din, String id) throws IOException 
    {
    	String record;
    	Din.seek(0);
    	for(int i = 0; i < NUM_OVERFLOW; i++)
    	{
    		record = Din.readLine();
    		if(record.substring(0, 5).equals(id))
    			return record;
    	}
    	return "NOT_FOUND";
    }
    
    public static void main(String[] args) throws IOException 
    {    	
    	PrintMenu();
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	String input;
    	while (!(input = br.readLine()).equals("9"))
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
	    			boolean validId = false;
	    			boolean freeId = false;
	    			boolean inSorted = false;
	    			System.out.print("ID: ");
	    			String id = new String();
	    			while (!validId && !freeId)
	    			{
	    				id = br.readLine();
	    				if (id.matches("[0-9]+") && id.length() <= 5)
            			{
            				if (id.length() < 5)
            				{
            					for ( ; id.length() < 5 ; )
            					{
            						id = "0" + id;
            					}
            				}
            				validId = true;
            				
            				String sRecord = binarySearch(DIN, id);
            				String lRecord = linearSearch(DIN_OVERFLOW, id);
            				if ((sRecord.equals("NOT_FOUND") && lRecord.equals("NOT_FOUND")))
            				{
            					freeId = true;
            				}
            				else if (sRecord.charAt(6) == (' '))
            				{
            					freeId = true;
            					inSorted = true;
            				}
            			}
            			else
            			{
            				System.out.println("Invalid value for ID. Please enter a valid value: ");
            			}
	    			}
	    			
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
            				System.out.println("Invalid value for experience. Please enter a valid value: ");
            			}
	    			}
	    			
	    			boolean validMarried = false;
	    			String married = new String();
	    			System.out.print("Married (Please enter 'y' for yes or 'n' for no): ");
	    			while (!validMarried)
	    			{
	    				married = br.readLine();	                			
            			if (married.equals("y"))
            			{
            				married = "yes    ";
            				validMarried = true;
            			}
            			else if (married.equals("n"))
            			{
            				married = "no     ";
            				validMarried = true;
            			}
            			else
            			{
            				System.out.println("Invalid value for married. Please enter a valid value: ");
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
            				System.out.println("Invalid value for wage. Please enter a valid value: ");
            			}
	    			}
	    			
	    			boolean validIndustry = false;
	    			String industry = new String();
	    			System.out.print("Industry: ");
	    			while (!validIndustry)
	    			{
	    				industry = br.readLine();
	    				if (industry.matches("^[a-zA-Z0-9_]*$") && industry.length() <= 27)
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
            				System.out.print("Invalid value for industry. Please enter a valid value: ");
            			}
	    			}
	    			
	    			AddRecord(inSorted, id, experience, married, wage, industry);
	    			break;
	    		case 8:
	    			System.out.print("Please enter the ID of the record you would like to delete: ");
	    			DeleteRecord(br.readLine());
	    			break;
	    		default:
	    			System.out.println("Invalid input. Please try another input.");
	    			break;
    		}
    		PrintMenu();
    	}
    }    
}
