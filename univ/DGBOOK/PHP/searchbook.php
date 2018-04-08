<?php
	function unistr_to_xnstr($str){
		return preg_replace('/\\\u([a-z0-9]{4})/i', "&#x\\1;", $str);
	}

	$con=mysqli_connect("localhost","root","1234","Bookstore");

	mysqli_set_charset($con,"utf8");

	if (mysqli_connect_errno($con))
	{
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}

	$title = isset($_POST['title']) ? $_POST['title']:'';
	$company = isset($_POST['company']) ? $_POST['company']:'';
	$author = isset($_POST['author']) ? $_POST['author']:'';
	$professor = isset($_POST['professor']) ? $_POST['professor']:'';
	$course = isset($_POST['course']) ? $_POST['course']:'';
	$type = isset($_POST['type']) ? $_POST['type']:'';
	$res = mysqli_query($con,"SELECT * FROM Book 
	WHERE title LIKE '$title%'
	AND company LIKE '$company%'  
	AND author LIKE '$author%'  
	AND professor LIKE '$professor%'  
	AND course LIKE '$course%'  
	AND type LIKE '$type%'");
	$result=array();
	while($row=mysqli_fetch_array($res)){
	array_push($result,
	 array('BookID'=>$row[0],'title'=>$row[1],'company'=>$row[2],
	 'author'=>$row[3],'professor'=>$row[4],'course'=>$row[5],
	 'price'=>$row[6],'type'=>$row[7],'url'=>$row[8]
	 ));
	}
	$json = json_encode(array("result"=>$result));
	echo unistr_to_xnstr($json);
	   
	mysqli_close($con);

?>   