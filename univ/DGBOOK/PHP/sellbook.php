<?php
	$con=mysqli_connect("localhost","root","1234","Bookstore");
	if (mysqli_connect_errno($con))
	{
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}
	$bookid = $_POST['bookid'];
	$progress=$_POST['progress'];

	$result = mysqli_query($con,"UPDATE Book SET progress='$progress' WHERE BookID='$bookid'");

	if($result){
	 echo 'success';
	}
	else{
	 echo 'failure';
	}

	mysqli_close($con);
?>

