import java.sql.*;

public class InsertApplication {

    // The main program that inserts a guest
    public static void main(String[] args) throws SQLException {
        // Connect to the database
        String Userstudent = "mam033";           // Change to your own userstudent
        String mysqlPassword = "test";    // Change to your own mysql Password
        jdbc_db myDB = new jdbc_db();
        myDB.connect(Userstudent, mysqlPassword);
        myDB.initDatabase();				    // reset db for testing phase only

        // For debugging purposes:  Show the database before the insert
        StringBuilder builder = new StringBuilder();
         builder.append("<br><div align=\"center\"> Job successfully added!</div><br>");
        
        String query1 = "SELECT * from Applications";
        builder.append("<br> Table APPLICATIONS before:" + myDB.query(query1));       

        // Parse input string to get guest student and Address
        //String guestNo = "15";
        String student = "";
        String job = "";
        boolean jobFlag = false;

        for(int i=0; i<args.length; i++)
        {
            if (args[i].equals("-a"))
                jobFlag = true;
            else if (jobFlag)
                job += args[i] + " ";
            else
                student += args[i] + " ";
        }

        // Insert the new guest
        student = student.substring(0, student.length()-1);  // remove trailing blank
        job = job.substring(0, job.length()-1);  // remove trailing blank
        String input = student + "," + job;  
//		System.out.println("Input: " + input);
        myDB.insert("Applications", input);    // insert new student

        // For debugging purposes:  Show the database after the insert
        builder.append("<br><br><br> Table APPLICATIONS after:" + myDB.query(query1));
        System.out.println(builder.toString());     

        myDB.disConnect();
    }

}
