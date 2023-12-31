<?php
    // Open connection
    try {
        $pdo=new PDO('mysql:host=localhost;dbname=cs361_asm2','root','');
    }catch (PDOException $e){
        echo 'Error: ' . $e->getMessage(); exit();
        }
        // Run Query
        $sql = 'SELECT * FROM data ORDER BY id DESC';
        $stm = $pdo->prepare($sql); // Prevent MySQL injection.
        $stm->execute();
        while ($row = $stm->fetch(PDO::FETCH_ASSOC)){
        $output[] = $row;
    }
    // Close connection
    $pdo = null;
    echo json_encode($output);
?>
