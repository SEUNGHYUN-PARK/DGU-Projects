<?php
	function unistr_to_xnstr($str){
    return preg_replace('/\\\u([a-z0-9]{4})/i', "&#x\\1;", $str);
	} 
	$con=mysqli_connect("localhost","root","1234","Bookstore");

	if (mysqli_connect_errno($con))
	{
	   echo "Failed to connect to MySQL: " . mysqli_connect_error();
	} 
	mysqli_set_charset($con,"utf8");
	$sql ="SELECT BookID FROM Book ORDER BY BookID DESC LIMIT 1";
  
	$res = mysqli_query($con,$sql);
	$id=0;
	while($row = mysqli_fetch_array($res)){
	$id = $row['BookID'];
	}
	$id=$id+1;
  
	$title = isset($_POST['title']) ? $_POST['title']:'';
	$company = isset($_POST['company']) ? $_POST['company']:'';
	$author = isset($_POST['author']) ? $_POST['author']:'';
	$professor = isset($_POST['professor']) ? $_POST['professor']:'';
	$course = isset($_POST['course']) ? $_POST['course']:'';
	$type = isset($_POST['type']) ? $_POST['type']:'';
	$price=isset($_POST['price']) ? $_POST['price']:'';
	$owner=isset($_POST['owner']) ? $_POST['owner']:'';
	//$image=isset($_POST['image']) ? $_POST['image']:'';  
	$image=$_POST['image'];
  
	$path="images/$id.png";
	$actualpath="http://52.78.227.248/$path";
	$other="/var/www/html/images/$id.png";
   
 	$result = mysqli_query($con,"insert into Book (title,company,author,professor,course,price,type,image,o    wner) values ('$title','$company','$author','$professor','$course','$price','$type','$actualpath','$own    er')");   
        
       
    if($result){
      file_put_contents($path,base64_decode($image));
      echo 'success';  
    }
    else{  
      echo 'failure';   
    }  
  
 	mysqli_close($con);
  
 ?>   