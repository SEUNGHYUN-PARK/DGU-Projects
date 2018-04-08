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
	$bookid = isset($_POST['bookid']) ? $_POST['bookid']:87;

	$res = mysqli_query($con,"SELECT * FROM Book WHERE BookID = '$bookid'");
	$result=array();
	while($row=mysqli_fetch_array($res)){
	array_push($result,
	 array('BookID'=>$row[0],'title'=>$row[1],'company'=>$row[2],
	 'author'=>$row[3],'professor'=>$row[4],'course'=>$row[5],
	 'price'=>$row[6],'type'=>$row[7],'url'=>$row[8],'owner'=>$row[11]
	 ));
	}
	$json = json_encode(array("result"=>$result));
	echo unistr_to_xnstr($json);
	mysqli_close($con);
?>