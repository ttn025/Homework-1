import java.sql.*;

public class ViewJobs {

    // The main program that inserts a guest
    public static void main(String[] args) throws SQLException {
        // Connect to the database
        String Username = "ttn025";           // Change to your own username
        String mysqlPassword = "mysqlmypassword25";    // Change to your own mysql Password
        jdbc_db myDB = new jdbc_db();
        myDB.connect(Username, mysqlPassword);
        myDB.initDatabase();				    // reset db for testing phase only

        // For debugging purposes:  Show the database before the insert
        StringBuilder builder = new StringBuilder();
        String query1 = "SELECT * from Jobs";
		builder.append("<br>" + myDB.query(query1));      

        System.out.println(builder.toString());     

        myDB.disConnect();
    }
}
