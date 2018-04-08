<?php
	$con=mysqli_connect("localhost","root","1234","Bookstore");
	if (mysqli_connect_errno($con))
	{
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}
	//$title="data"; 
	$bookid = isset($_POST['bookid']) ? $_POST['bookid']:12;

	$res = mysqli_query($con,"SELECT * FROM Comment WHERE BookID = '$bookid'");
	$result=array();
	while($row=mysqli_fetch_array($res))
	{
		array_push($result,
		array('commentid'=>$row[0],'author'=>$row[1],'owner'=>$row[2],'content'=>$row[3],'time'=>$row[4],'bookid'=>$row[5],));
	}
	echo json_encode(array("result"=>$result));

	mysqli_close($con);
?>
