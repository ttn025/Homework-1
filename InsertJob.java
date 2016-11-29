import java.sql.*;

public class InsertJob {

    // The main program that inserts a guest
    public static void main(String[] args) throws SQLException {
        // Connect to the database
        String Username = "mam033";           // Change to your own username
        String mysqlPassword = "test";    // Change to your own mysql Password
        jdbc_db myDB = new jdbc_db();
        myDB.connect(Username, mysqlPassword);
        myDB.initDatabase();				    // reset db for testing phase only

        //For debugging purposes:  Show the database before the insert
        StringBuilder builder = new StringBuilder();
        builder.append("<br><div align=\"center\"> Job successfully added!</div><br>");


        //DUBG
        String query1 = "SELECT * from Jobs";
        builder.append("<br> Table JOBS before:" + myDB.query(query1));       

        // Parse input string to get guest Name and Address
        String name = "";
        String title = "";
		String salary = "";
        boolean titleFlag = false;
		boolean salaryFlag = false;

        for(int i=0; i<args.length; i++)
        {
			if (!args[i].equals("-a") && !titleFlag && !salaryFlag)
				name += args[i] + " ";
            else if (args[i].equals("-a") && !titleFlag)
                titleFlag = true;
			else if (args[i].equals("-a") && titleFlag)
				salaryFlag = true;
            else if (titleFlag && !salaryFlag)
                title += args[i] + " ";
			else if (titleFlag && salaryFlag)
				salary += args[i] + " ";
        }

        // Insert the new guest
        name = name.substring(0, name.length()-1);  // remove trailing blank
        title = title.substring(0, title.length()-1);  // remove trailing blank
		salary = salary.substring(0, salary.length()-1);  // remove trailing blank
        String input = "0" + ",'" + name + "','" + title + "'," + salary;
        myDB.insert("Jobs", input);    // insert new student

        // For debugging purposes:  Show the database after the insert
        builder.append("<br><br><br> Table JOBS after:" + myDB.query(query1));
//        builder.append("</div>");
        System.out.println(builder.toString());     

        myDB.disConnect();
    }
}
