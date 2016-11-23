<!DOCTYPE html>
<html>
<body>
<h1>Please enter information about the Application that you wish to add to the database:</h1>

<form action="InsertApplication.php" method="post">
    Student ID: <input type="text" name="student"><br>
    Job ID: <input type="text" name="job"><br>
    <input name="submit" type="submit" ><br><br>
	<a href="http://csce.uark.edu/~ttn025/Homework5/HomePage.html">return to home page</a>
</form>
<br><br>

</body>
</html>

<?php
if (isset($_POST['submit'])) 
{
    // run Hello.java just for testing calling java without mysql
    system('java Hello ' . $_POST[student]);    

    $application = $_POST[student] . ' -a ' . $_POST[job];
    $command = 'java -cp .:mysql-connector-java-5.1.40-bin.jar InsertApplication ' . $application;

    
    // remove dangerous characters from command to protect web server
    $command = escapeshellcmd($command);

    // run
    system($command);           
}
?>	