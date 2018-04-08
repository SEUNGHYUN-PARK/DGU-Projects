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
 
	$author = isset($_POST['author']) ? $_POST['author']:'';
	$owner = isset($_POST['owner']) ? $_POST['owner']:'';
	$content = isset($_POST['content']) ? $_POST['content']:'';
	$bookid = isset($_POST['bookid']) ? $_POST['bookid']:''; 
	$result = mysqli_query($con,"insert into Comment (author,owner,content,bookid) values ('$author','$owne    r','$content','$bookid')");
 
	if($result)
	{
		echo 'success';
	}
	else
	{
		echo 'failure';
	}
?> 