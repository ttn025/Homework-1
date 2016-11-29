<!DOCTYPE html>
<html lang="en">
<head>
<title>Assignment 5 - Add Job</title>
<meta charset="utf-8">
<link href="stylin.css" rel="stylesheet">
</head>
<body>
<div id="wrapper">
  <header>
      <h1>Assignment 5</h1>
  </header>
  <nav>
  <ul>
    <li><a href="http://csce.uark.edu/~mam033/HomePage.html">Home</a></li>
    <div class="dropdown">
        <button class="dropbtn">Add</button>
        <div class="dropdown-content">
            <a href="http://csce.uark.edu/~mam033/InsertStudent.php">Student</a>
            <a href="http://csce.uark.edu/~mam033/InsertJob.php">Job</a>
            <a href="http://csce.uark.edu/~mam033/InsertApplication.php">Application</a>
        </div>
    </div>
    <div class="dropdown">
        <button class="dropbtn">View</button>
        <div class="dropdown-content">
            <a href="http://csce.uark.edu/~mam033/ViewStudents.php">Student</a>
            <a href="http://csce.uark.edu/~mam033/ViewJobs.php">Job</a>
            <a href="http://csce.uark.edu/~mam033/ViewApplications.php">Application</a>
    </div>
</div>
  </ul>
  </nav>
  <div id="content" align="left">
    <p>Please enter information about the job that you wish to add to the database:</p>
    <form action="InsertJob.php" method="post">
    Company Name: <input type="text" name="name"><br>
    Job Title: <input type="text" name="title"><br>
    Salary: <input type="number" step = "0.01" name="salary"><br>
    <input name="submit" type="submit" >
  </form>

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

  </div>
  <footer>
  Copyright &copy; 2016 <a href= "mailto:mam033@uark.edu">Mika Moore</a> and <a href= "mailto:ttn025@uark.edu">Thomas Nguyen</a>
  <p> Page Last Updated <script type="text/javascript">document.write(document.lastModified);</script></p>
  </footer>
</div>
</body>
</html>

