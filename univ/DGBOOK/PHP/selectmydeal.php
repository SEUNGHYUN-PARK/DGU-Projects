<?php

	$con=mysqli_connect("localhost","root","1234","Bookstore");


	if (mysqli_connect_errno($con))
	{
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}
	//$title="data"; 
	$owner = isset($_POST['owner']) ? $_POST['owner']:12;

	$res = mysqli_query($con,"SELECT * FROM Book WHERE owner = '$owner'");
	$result=array();
	while($row=mysqli_fetch_array($res)){
	array_push($result,
	 array('BookID'=>$row[0],'title'=>$row[1],'company'=>$row[2],
	 'author'=>$row[3],'professor'=>$row[4],'course'=>$row[5],
	 'price'=>$row[6],'type'=>$row[7],'url'=>$row[8],'owner'=>$row[11]
	 ));
	}
	echo json_encode(array("result"=>$result));

	mysqli_close($con);

?> 