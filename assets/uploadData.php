<?php
    date_default_timezone_set('Asia/Bangkok');
    // Open connection
    try {
        $pdo=new PDO('mysql:host=localhost;dbname=cs361_asm2','root','');
    }catch (PDOException $e){
        echo 'Error: ' . $e->getMessage(); exit();
    }
    // Run Insert
    $weight = $_REQUEST["sWeight"]; 
    $height = $_REQUEST["sHeight"];
    $bmi = $_REQUEST["sBMI"]; 
    $status = $_REQUEST["sStatus"];
    $sql = "INSERT INTO data(weight,height,bmi,status) VALUES (?,?,?,?)";
    $stm= $pdo->prepare($sql);
    if(!$stm->execute([$weight, $height, $bmi, $status])){
        $arr['StatusID'] = "0"; 
        $arr['Error'] = "Cannot save data!";
    }else{
        $arr['StatusID'] = "1"; 
        $arr['Error'] = "";
    }
    // Close connection
    $pdo = null;
    echo json_encode($arr);
?>
