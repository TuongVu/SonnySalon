<?php
    $mysql_host = "mysql1.000webhost.com";
    $mysql_database = "a5316362_sonny";
    $mysql_user = "a5316362_nails";
    $mysql_password = "justbboy86";

    $firstname = $_POST["firstname"];
    $lastname = $_POST["lastname"];
    $loginname = $_POST["loginname"];
    $pwdhash = $_POST["pwdhash"];
    $useruid = $_POST["useruid"];
    $phone = "";
    $email = "";
    $notes = "";

    $user = array();

    if(!empty($_POST["loginname"])){
    $con=mysqli_connect($mysql_host,$mysql_user,$mysql_password,$mysql_database);
    if ( !$con ) {
      die( 'connect error: '.mysqli_connect_error() );
      echo 'no database connection';
    }
    $insert = "INSERT INTO `Users` (`UserUID`, `ActiveYN`, `AdminYN`, `LoginName`,`PwdHash`,`FirstName`,`LastName`,`Phone`,`Email`,`Notes`) VALUES (?, 1, 0, ?,?,?,?,?,?,?)";
    $statement = mysqli_prepare($con, $insert);
    mysqli_stmt_bind_param($statement, "ssssssss", $useruid, $loginname, $pwdhash, $firstname,$lastname, $phone,$email,$notes);
    mysqli_stmt_execute($statement);

    $affected_rows = mysqli_stmt_affected_rows($statement);

    if($affected_rows == 1){
        $user["useruid"] = $useruid;
        $user["loginname"] = $loginname;
        $user["firstname"] = $firstname;
        $user["lastname"] = $lastname;
        $user["pwdhash"] = $pwdhash;
    }
    echo json_encode($user);

    mysqli_stmt_close($statement);

    mysqli_close($con);
    }else{
       echo json_encode($user);
    }
?>