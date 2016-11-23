<!DOCTYPE html>
<html>
<body>
<h1>Please enter information about the Job that you wish to add to the database:</h1>

<form action="InsertJob.php" method="post">
    Company Name: <input type="text" name="name"><br>
    Job Title: <input type="text" name="title"><br>
	Salary: <input type="text" name="salary"><br>
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
    system('java Hello ' . $_POST[name]);    

    $job = $_POST[name] . ' -a ' . $_POST[title] . ' -a ' . $_POST[salary];
    $command = 'java -cp .:mysql-connector-java-5.1.40-bin.jar InsertJob ' . $job;

    
    // remove dangerous characters from command to protect web server
    $command = escapeshellcmd($command);

    // run
    system($command);           
}
?>	