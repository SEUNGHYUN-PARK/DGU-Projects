<?php

	$con=mysqli_connect("localhost","root","1234","Bookstore");

	function unistr_to_xnstr($str){
	 return preg_replace('/\\\u([a-z0-9]{4})/i', "&#x\\1;", $str);
	}

	if (mysqli_connect_errno($con))
	{
	echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}
	mysqli_set_charset($con,"utf8");
	$author = isset($_POST['author']) ? $_POST['author']:'';

	$res = mysqli_query($con,"SELECT bookid FROM Comment WHERE author = '$author'");
	$result=array();
	while($row=mysqli_fetch_array($res)){
	array_push($result,
	 array('bookid'=>$row[0]
	 ));
	}
	$json = json_encode(array("result"=>$result));
	echo unistr_to_xnstr($json);

	mysqli_close($con);

?>
