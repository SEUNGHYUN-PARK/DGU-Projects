<?php

	$con=mysqli_connect("localhost","root","1234","Bookstore");

	function unistr_to_xnstr($str){
	 return preg_replace('/\\\u([a-z0-9]{4})/i', "&#x\\1;", $str);
	}

	mysqli_set_charset($con,"utf8");

	if (mysqli_connect_errno($con))
	{
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}
	//$title="data"; 
	$bookid  = isset($_POST['bookid']) ? $_POST['bookid']:12;

	$res = mysqli_query($con,"SELECT * FROM Comment WHERE bookid = '$bookid'");
	$result=array();
	while($row=mysqli_fetch_array($res)){
	array_push($result,
	 array('commentid'=>$row[0],'author'=>$row[1],'owner'=>$row[2],
	 'content'=>$row[3],'time'=>$row[4],'bookid'=>$row[5],
	 ));
	}
	$json=json_encode(array("result"=>$result));
	echo unistr_to_xnstr($json); 
	mysqli_close($con);

?>