<?php 
 $conn=mysql_connect("localhost", "root", "") or die("can't connect database");
mysql_select_db("hotel",$conn);
mysql_query("SET character_set_results=utf8", $conn);
/* mb_language('uni'); */
/* mb_internal_encoding('UTF-8'); */
if( !(empty($_GET['loaiphong'])))
{
$lp= $_GET['loaiphong'];
$sql="select * from rooms where room_type=$lp";
$query=mysql_query($sql);

if(mysql_num_rows($query) == 0)
   {
      echo "Khong tim thay ket qua";
    }
else
    {
      while($e=mysql_fetch_assoc($query))
      {
            $output[]=$e;
      }
      
      print(json_encode($output));

    }
}
else echo "Chua nhap tu khoa";
/* mysql_close($conn); */
 ?>