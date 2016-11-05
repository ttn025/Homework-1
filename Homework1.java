import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Homework4 
{
	private static Connection connection;
    private static Statement statement;
    private static String tempQuery;
    
    private static String allIntsRegex = "[0-9]+";

    // The constructor for the class
    public Homework4()
    {
        connection = null;
        statement = null;
    }
    
    // Connect to the database
    public void connect(String Username, String mysqlPassword) throws SQLException 
    {
        try 
        {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/" + Username + "?" +
                    "user=" + Username + "&password=" + mysqlPassword);
            //connection = DriverManager.getConnection("jdbc:mysql://localhost/" + Username +
             //       "?user=" + Username + "&password=" + mysqlPassword);
        }
        catch (Exception e) 
        {
            throw e;
        }
    }
    
    // Remove all records and fill them with values for testing
    // Assumes that the tables are already created
    public void initDatabase(String Username, String Password, String SchemaName) throws SQLException 
    {
        statement = connection.createStatement();
        statement.executeUpdate("DELETE from Booking");
        statement.executeUpdate("DELETE from Room");
        statement.executeUpdate("DELETE from Hotel");
        statement.executeUpdate("DELETE from Guest");

        insert("Guest", "1, 'Cam Newton', '800 S Mint St., Charlotte, NC'");
        insert("Guest", "4, 'Dark Prescott', '1 ATT Way, Arlington, TX'");
        insert("Guest", "11, 'Alex Smith', '1 Arrowhead Dr., Kansas City, MO'");
        insert("Guest", "0, 'Johnny Manziel', 'Texas A&M'");

        insert("Hotel", "0, 'Fleabag', 'Detroit'");
        insert("Hotel", "8, 'Lap of Luxury', 'Las Vegas'");
        insert("Hotel", "3, 'Razorback Heave', 'Fayetteville'");

        insert("Room", "13, 0, 'tp', 75");
        insert("Room", "222, 0, 'db', 60");
        insert("Room", "107, 0, 'sg', 50");
        insert("Room", "107, 3, 'sg', 80");
        insert("Room", "109, 3, 'db', 100");
        insert("Room", "207, 3, 'tp', 125");
        insert("Room", "307, 3, 'db', 110");
        insert("Room", "101, 8, 'db', 400");
        insert("Room", "107, 8, 'tp', 600");
        insert("Room", "201, 8, 'db', 400");
        insert("Room", "301, 8, 'db', 400");

        insert("Booking", "0, 101, 8, 1, STR_TO_DATE('2017-06-20', '%Y-%m-%d'), STR_TO_DATE('2017-06-24', '%Y-%m-%d')");
        insert("Booking", "1, 101, 8, 1, STR_TO_DATE('2017-07-01', '%Y-%m-%d'), STR_TO_DATE('2017-07-03', '%Y-%m-%d')");
        insert("Booking", "2, 201, 8, 1, STR_TO_DATE('2017-07-01', '%Y-%m-%d'), STR_TO_DATE('2017-07-03', '%Y-%m-%d')");
        insert("Booking", "3, 301, 8, 11, STR_TO_DATE('2017-07-01', '%Y-%m-%d'), STR_TO_DATE('2017-07-03', '%Y-%m-%d')");
        insert("Booking", "4, 107, 3, 4, STR_TO_DATE('2017-07-01', '%Y-%m-%d'), STR_TO_DATE('2017-07-03', '%Y-%m-%d')");
        insert("Booking", "5, 107, 0, 0, STR_TO_DATE('2017-07-01', '%Y-%m-%d'), STR_TO_DATE('2017-07-03', '%Y-%m-%d')");
        insert("Booking", "6, 101, 8, 4, STR_TO_DATE('2017-09-28', '%Y-%m-%d'), STR_TO_DATE('2017-10-03', '%Y-%m-%d')");
    }
    
    // Insert into any table, any values from data passed in as String parameters
    public static void insert(String table, String values) 
    {
        String query = "INSERT into " + table + " values (" + values + ")" ;
        try 
        {
            statement.executeUpdate(query);
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }
    
    // Disconnect from the database
    public static void disConnect() throws SQLException 
    {
        connection.close();
        statement.close();
    }

    // Execute an SQL query passed in as a String parameter
    // and print the resulting relation
    public void query(String q) 
    {
        try 
        {
            ResultSet resultSet = statement.executeQuery(q);
            System.out.println("\n---------------------------------");
            System.out.println("Query: \n" + q + "\n\nResult: ");
            print(resultSet);
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }
    
    public static int GetMaxHotelID()
    {
    	try 
        {
            ResultSet resultSet = statement.executeQuery("SELECT MAX(hotelID) from Hotel");
            
            return resultSet.getInt(1);
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
            
            return -1;
        }
    }
    
    public static int GetMaxBookingNo()
    {
    	try 
        {
            ResultSet resultSet = statement.executeQuery("SELECT MAX(bookingNo) from Booking");
            
            return resultSet.getInt(1);
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
            
            return -1;
        }
    }
    
    public static int GetMaxGetNum()
    {
    	try 
        {
            ResultSet resultSet = statement.executeQuery("SELECT MAX(guestNo) from Guest");
            
            return resultSet.getInt(1);
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
            
            return -1;
        }
    }
    
    public static int CheckNameExists(String name)
    {
    	try 
        {
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(guestName) FROM Guest where guestName = " + name);
            //maybe this is wrong
            return resultSet.getInt(1);
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
            
            return -1;
        }
    }
    
    public static int CheckGuestNoExists(String num)
    {
    	try 
        {
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(guestNo) FROM Guest where guestNo = " + num);
            //maybe this is wrong
            return resultSet.getInt(1);
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
            
            return -1;
        }
    }
    
    public static int GetHotelIDFromName(String hotelName)
    {
    	try 
        {
            ResultSet resultSet = statement.executeQuery("SELECT hotelID FROM Hotel WHERE hotelName = " + hotelName);
            //maybe this is wrong
            return resultSet.getInt(1);
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
            
            return -1;
        }
    }

    // Print the results of a query with attribute names on the first line
    // Followed by the tuples, one per line
    public void print(ResultSet resultSet) throws SQLException 
    {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int numColumns = metaData.getColumnCount();

        printHeader(metaData, numColumns);
        printRecords(resultSet, numColumns);
    }

    // Print the attribute names
    public void printHeader(ResultSetMetaData metaData, int numColumns) throws SQLException 
    {
        for (int i = 1; i <= numColumns; i++) 
        {
            if (i > 1)
                System.out.print(",  ");
            System.out.print(metaData.getColumnName(i));
        }
        System.out.println();
    }

    // Print the attribute values for all tuples in the result
    public void printRecords(ResultSet resultSet, int numColumns) throws SQLException 
    {
        String columnValue;
        while (resultSet.next()) 
        {
            for (int i = 1; i <= numColumns; i++) 
            {
                if (i > 1)
                    System.out.print(",  ");
                columnValue = resultSet.getString(i);
                System.out.print(columnValue);
            }
            System.out.println("");
        }
    }
	
	public static void PrintMenu()
    {
    	System.out.println("Please input which operation you would like to perform.");
    	System.out.println("Menu of Operations:");
    	System.out.println("1) Find a hotel room");
    	System.out.println("2) Book a hotel room");
    	System.out.println("3) Find a booking ");
    	System.out.println("4) Cancel a booking");
    	System.out.println("5) List all guests");
    	System.out.println("6) Add a hotel");
    	System.out.println("7) Quit");
    }
	
	public static void main(String[] args) throws IOException, SQLException
	{
		String month;
		String day;
		String year;
		String checkInDate;
		String guestNo;
		boolean guestNoExists;
		
		//TODO: Change username and password
		String username = "MYUSERNAME";
        String mysqlPassword = "MYMYSQLPASSWORD";

        Homework4 obj = new Homework4();
        obj.connect(username, mysqlPassword);
        obj.initDatabase(username, mysqlPassword, username);
		
		PrintMenu();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	String input;
    	while (!(input = br.readLine()).equals("7"))
    	{
    		int operation = 0;
			try
			{
				operation = Integer.parseInt(input);
				if (operation < 1 || operation > 7)
				{
					System.out.println("Invalid input.");
					PrintMenu();
					input = br.readLine();
					continue;
				}
			}
			catch (Exception e)
			{
				System.out.println("Invalid input.");
				PrintMenu();
			}
    		
    		
    		switch (operation)
    		{
	    		case 1: //find a hotel room
	    			//Find and list all rooms available for the duration of their stay at all hotels. 
	    			//List the hotel name, room type, and price for each available room.
	    			
	    			System.out.println("Please enter values for the check in date:");
	    			System.out.println("Month (mm):");
	    			month = br.readLine();
	    			while (!month.matches("[0-9]+") || month.length() != 2)
	    			{
	    				System.out.println("Invalid input. Please re-enter the month (mm): ");
	    				month = br.readLine();
	    			}
	    			
	    			System.out.println("Day (dd):");
	    			day = br.readLine();
	    			while (!day.matches("[0-9]+") || month.length() != 2)
	    			{
	    				System.out.println("Invalid input. Please re-enter the day (dd): ");
	    				day = br.readLine();
	    			}
	    			
	    			System.out.println("Year (yyyy):");
	    			year = br.readLine();
	    			while (!year.matches("[0-9]+") || month.length() != 4)
	    			{
	    				System.out.println("Invalid input. Please re-enter the year (yyyy): ");
	    				year = br.readLine();
	    			}
	    			
	    			checkInDate = year + "-" + month + "-" + day;
	    			
	    			System.out.println("Please enter the number of nights you would like to stay: ");
	    			String numNights = br.readLine();
	    			while (!numNights.matches("[0-9]+"))
	    			{
	    				System.out.println("Invalid input. Please re-enter the number of nights you would like to stay: ");
	    				numNights = br.readLine();
	    			}
	    			
	    			//TODO query to find and list all available rooms (hotelName, roomType, price) for numNights from all hotels
	    			
	    			break;
	    		case 2: //book a hotel room
	    			System.out.println("Please enter your name: ");
	    			String bookName = br.readLine();
	    			int bookHotelName = CheckNameExists(bookName);
	    			boolean isBookFound;
	    			if (bookHotelName >= 1)
	    			{
	    				isBookFound = true;
	    			}
	    			else
	    			{
	    				isBookFound = false;
	    			}
	    			
	    			if (isBookFound)
	    			{
	    				obj.query("SELECT guestNo, guestAddress FROM Guest WHERE guestName = " + bookName);
	    			}
	    			else
	    			{
	    				System.out.println("The name '" + bookName + "' does not exist.");
	    				System.out.println("Please enter your address: ");
	    				String address = br.readLine();
	    				int newGuestNum = GetMaxGetNum() + 1;
	    				tempQuery = Integer.toString(newGuestNum) + ", " + bookName + ", " + address;
	    				insert("Guest", tempQuery);
	    				
	    				obj.query("SELECT guestNo FROM Guest WHERE guestName = " + bookName);
	    			}
	    			
	    			System.out.println("Please enter your guest number: ");
    				guestNo = br.readLine();
    				while (true)
	    			{
    					if (!guestNo.matches("[0-9]+"))
    					{
    						System.out.println("Invalid input. Please enter a valid guest number: ");
    						guestNo = br.readLine();
    					}	    				
    					else if (CheckGuestNoExists(guestNo) == 0)
	    				{
    						System.out.println("Guest number does not exist. Please enter a valid guest number: ");
    						guestNo = br.readLine();
	    				}
    					else
    					{
    						break;
    					}	
	    			}
    				
    				
    				
    				System.out.println("Please enter the hotel name: ");
    				String hotelName = br.readLine();
    				int hotelNum = GetHotelIDFromName(hotelName);
    				while (hotelNum == 0)
    				{
    					System.out.println("The hotel name that you have entered does not exist. \nPlease re-enter the hotel name: ");
    					hotelName = br.readLine();
    					hotelNum = GetHotelIDFromName(hotelName);
    				}
    				
    				System.out.println("Please enter the room number: ");
    				String roomNo = br.readLine();
    				while (!roomNo.matches("[0-9]+"))
	    			{
	    				System.out.println("Invalid input. Please enter a valid room number: ");
	    				roomNo = br.readLine();
	    			}
    				
    				System.out.println("Please enter values for the check in date:");
	    			System.out.println("Month (mm):");
	    			month = br.readLine();
	    			while (!month.matches("[0-9]+") || month.length() != 2)
	    			{
	    				System.out.println("Invalid input. Please re-enter the month (mm): ");
	    				month = br.readLine();
	    			}
	    			
	    			System.out.println("Day (dd):");
	    			day = br.readLine();
	    			while (!day.matches("[0-9]+") || month.length() != 2)
	    			{
	    				System.out.println("Invalid input. Please re-enter the day (dd): ");
	    				day = br.readLine();
	    			}
	    			
	    			System.out.println("Year (yyyy):");
	    			year = br.readLine();
	    			while (!year.matches("[0-9]+") || month.length() != 4)
	    			{
	    				System.out.println("Invalid input. Please re-enter the year (yyyy): ");
	    				year = br.readLine();
	    			}
	    			
	    			checkInDate = year + "-" + month + "-" + day;
    				
    				System.out.println("Please enter the length of stay: ");
    				String lengthOfStay = br.readLine();
    				while (!lengthOfStay.matches("[0-9]+"))
	    			{
	    				System.out.println("Invalid input. Please enter a valid value for length of stay: ");
	    				lengthOfStay = br.readLine();
	    			}
    				
    				int newBookingNum = GetMaxBookingNo() + 1;
    				
    				tempQuery = Integer.toString(newBookingNum) + ", " + roomNo + ", " + Integer.toString(hotelNum) + ", " + guestNo + ", " + checkInDate + ", DATE_ADD(" + checkInDate + ", INTERVAL " + lengthOfStay + " DAY)";
    				insert("Booking", tempQuery);
    				
    				System.out.println("You have successfully booked a room. Your booking number is " + newBookingNum);
	    			break;
	    		case 3: //find a booking
	    			System.out.println("Please enter your name: ");
	    			String findName = br.readLine();
	    			int findBookingName = CheckNameExists(findName);
	    			boolean isFindFound;
	    			if (findBookingName >= 1)
	    			{
	    				isFindFound = true;
	    			}
	    			else
	    			{
	    				isFindFound = false;
	    			}
	    			
	    			if (isFindFound)
	    			{
	    				obj.query("SELECT guestNo, guestAddress FROM Guest WHERE guestName = " + findName);
	    				System.out.println("Please enter your guest number: ");
	    				guestNo = br.readLine();
	    				while (true)
		    			{
	    					if (!guestNo.matches("[0-9]+"))
	    					{
	    						System.out.println("Invalid input. Please enter a valid guest number: ");
	    						guestNo = br.readLine();
	    					}	    				
	    					else if (CheckGuestNoExists(guestNo) == 0)
		    				{
	    						System.out.println("Guest number does not exist. Please enter a valid guest number: ");
	    						guestNo = br.readLine();
		    				}
	    					else
	    					{
	    						break;
	    					}	
		    			}
	    				
	    				obj.query("Select b.bookingNo, h.hotelName, b.dateFrom, r.type FROM Booking b, Hotel h, Room r WHERE b.guestNo = " + guestNo + " AND h.hotelID = b.hotelNo AND r.roomNo = b.roomNo AND b.hotelNo = r.hotelNo");
	    			}
	    			else
	    			{
	    				System.out.println("The name '" + findName + "' does not exist.");
	    			}
	    			
	    			break;
	    		case 4: //cancel a booking
	    			System.out.println("Please enter the booking number for the booking that you would like to cancel: ");
	    			String bookingNo = br.readLine();
	    			while (!bookingNo.matches("[0-9]+")) 
	    			{
	    				System.out.println("Invalid input. Please enter a valid booking number: ");
	    				bookingNo = br.readLine();
	    			}

	    			String queryString = "DELETE FROM BOOKING WHERE bookingNo = " + bookingNo;
	    			statement.executeUpdate(queryString);
	    			
	    			System.out.println("Booking number " + bookingNo + " has been deleted.");
	    			break;
	    		case 5: //list all guests
	    			obj.query("SELECT hotelName, hotelID from Hotel");	    			
	    			
	    			System.out.println("Please enter a hotel ID: ");
	    			String hotelID = br.readLine();
	    			while (!hotelID.matches("[0-9]+")) 
	    			{
	    				System.out.println("Invalid input. Please enter a valid hotel ID: ");
	    				hotelID = br.readLine();
	    			}
	    			
	    			System.out.println("Please enter values for the date:");
	    			System.out.println("Month (mm):");
	    			month = br.readLine();
	    			while (!month.matches("[0-9]+") || month.length() != 2)
	    			{
	    				System.out.println("Invalid input. Please re-enter the month (mm): ");
	    				month = br.readLine();
	    			}
	    			
	    			System.out.println("Day (dd):");
	    			day = br.readLine();
	    			while (!day.matches("[0-9]+") || month.length() != 2)
	    			{
	    				System.out.println("Invalid input. Please re-enter the day (dd): ");
	    				day = br.readLine();
	    			}
	    			
	    			System.out.println("Year (yyyy):");
	    			year = br.readLine();
	    			while (!year.matches("[0-9]+") || month.length() != 4)
	    			{
	    				System.out.println("Invalid input. Please re-enter the year (yyyy): ");
	    				year = br.readLine();
	    			}
	    			
	    			checkInDate = year + "-" + month + "-" + day;
	    			
	    			tempQuery = "SELECT g.guestName FROM Booking b, Guest g WHERE b.hotelNo = " + hotelID + " AND (date(b.dateFrom) <= " + checkInDate + " AND date(b.dateTo) >= " + checkInDate + ") AND g.guestNo = b.guestNo";
	    			obj.query(tempQuery);
	    			break;
	    		case 6: //add a hotel
	    			System.out.println("Please enter new hotel name: ");
	    			String addHotelName = br.readLine();
	    			System.out.println("Please enter the city of the new hotel: ");
	    			String city = br.readLine();
	    			
	    			int newHotelID = GetMaxHotelID() + 1;
	    			tempQuery = newHotelID + ", " + addHotelName + ", " + city;
	    			insert("Hotel", tempQuery);
	    			
	    			System.out.println(addHotelName + " has been added. The hotel ID for " + addHotelName + " is " + newHotelID);
	    			
	    			while (true)
	    			{
	    				System.out.println("Would you like to add room information for the new hotel? (y/n): ");
		    			String response = br.readLine();
		    			if (response.equals("y"))
		    			{
		    				System.out.println("Please enter new room number: ");
		    				String addRoomNo = br.readLine();
		    				while (!addRoomNo.matches("[0-9]+"))
			    			{
			    				System.out.println("Invalid input. Please re-enter the year (yyyy): ");
			    				addRoomNo = br.readLine();
			    			}
		    				System.out.println("Please enter new room type (db, sg, tp): ");
		    				String type = br.readLine();
		    				while (type != "db" || type != "sg" || type != "tp")
		    				{
		    					System.out.println("Invalid input. Please re-enter the new room type (db, sg, tp): ");
		    				}
		    				System.out.println("Please enter new room price between 50 and 500: ");
		    				String priceString = br.readLine();
		    				double price = 0;
		    				while (true)
    						{
		    					if (!priceString.matches("[0-9]+([.][0-9]{1,2})?"))
		    					{
		    						System.out.println("Invalid input. Please re-enter a new room price between 50 and 500: ");
		    						priceString = br.readLine();
		    					}
		    					else 
		    					{
		    						price = Double.parseDouble(br.readLine());
		    						if (price < 50 || price > 500)
		    						{
		    							System.out.println("Invalid input. Please re-enter a new room price between 50 and 500: ");
		    							priceString = br.readLine();
		    						}
		    						else
		    						{
		    							break;
		    						}
		    					}
		    					
    						}
		    				
		    				tempQuery = addRoomNo + ", " + newHotelID + ", " + type + ", " + price;
		    				insert("Room", tempQuery);
		    			}
		    			else if (!response.equals("y") || !response.equals("n"))
		    			{
		    				System.out.println("Invalid input.");
		    			}
		    			else
		    			{
		    				break;
		    			}
	    			}	    			
	    			break;
    			default:
    				System.out.println("Invalid input. Please try another input.");
	    			break;
    		}
    		PrintMenu();
    	}
    	obj.disConnect();
	}
}
