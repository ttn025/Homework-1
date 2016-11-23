<!DOCTYPE html>
<html>
<body>
<h1>Please enter information about the student that you wish to add to the database:</h1>

<form action="InsertStudent.php" method="post">
    Student Name: <input type="text" name="name"><br>
    Major: <input type="text" name="major"><br>
    <input name="submit" type="submit" ><br><br>
	<a href="http://csce.uark.edu/~ttn025/Homework5/HomePage.html">return to home page</a>
</form>
<br><br>

</body>
</html>

<?php
if (isset($_POST['submit'])) 
{
    $student = $_POST[name] . ' -a ' . $_POST[major];
    $command = 'java -cp .:mysql-connector-java-5.1.40-bin.jar InsertStudent ' . $student;
    
    // remove dangerous characters from command to protect web server
    $command = escapeshellcmd($command);

    // run
    system($command);           
}
?>	