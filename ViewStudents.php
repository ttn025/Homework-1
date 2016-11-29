<!DOCTYPE html>
<html lang="en">
<head>
<title>Assignment 5 - View Students</title>
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
  <p>Student Directory:</p>
  <?php
    $command = 'java -cp .:mysql-connector-java-5.1.40-bin.jar ViewStudents';
    
    // remove dangerous characters from command to protect web server
    $command = escapeshellcmd($command);

    // run
    system($command);
  ?>
  </div>
  <footer>
  Copyright &copy; 2016 <a href= "mailto:mam033@uark.edu">Mika Moore</a> and <a href= "mailto:ttn025@uark.edu">Thomas Nguyen</a>
  <p> Page Last Updated <script type="text/javascript">document.write(document.lastModified);</script></p>
  </footer>
</div>
</body>
</html>