import java.sql.*;

public class InsertStudent {

    // The main program that inserts a guest
    public static void main(String[] args) throws SQLException {
        // Connect to the database
        String Username = "mam033";           // Change to your own username
        String mysqlPassword = "test";    // Change to your own mysql Password
        jdbc_db myDB = new jdbc_db();
        myDB.connect(Username, mysqlPassword);
        myDB.initDatabase();				    // reset db for testing phase only

        // For debugging purposes:  Show the database before the insert
        StringBuilder builder = new StringBuilder();
        builder.append("<br><div align=\"center\"> Job successfully added!</div><br>");
        
        //DEBUG
        String query1 = "SELECT * from Students";
        builder.append("<br> Table STUDENTS before:" + myDB.query(query1));       

        // Parse input string to get guest Name and Address
        //String guestNo = "15";
        String name = "";
        String major = "";
        boolean majorFlag = false;

        for(int i=0; i<args.length; i++)
        {
            if (args[i].equals("-a"))
                majorFlag = true;
            else if (majorFlag)
                major += args[i] + " ";
            else
                name += args[i] + " ";
        }

        // Insert the new guest
        name = name.substring(0, name.length()-1);  // remove trailing blank
        major = major.substring(0, major.length()-1);  // remove trailing blank
        String input = "0" + ",'" + name + "','" + major + "'";               
        myDB.insert("Students", input);    // insert new student

 //       For debugging purposes:  Show the database after the insert
        builder.append("<br><br><br> Table STUDENTS after:" + myDB.query(query1));
        System.out.println(builder.toString());     

        myDB.disConnect();
    }

}
