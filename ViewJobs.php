<!DOCTYPE html>
<html>
<h1>Job Listings:</h1>	
<body>

<?php
    $command = 'java -cp .:mysql-connector-java-5.1.40-bin.jar ViewJobs';
    
    // remove dangerous characters from command to protect web server
    $command = escapeshellcmd($command);

    // run
    system($command);
?>

<br><br>
<a href="http://csce.uark.edu/~ttn025/Homework5/HomePage.html">return to home page</a>
</body>
</html>