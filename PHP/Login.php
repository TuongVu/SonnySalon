<?php
    $mysql_host = "mysql1.000webhost.com";
    $mysql_database = "a5316362_sonny";
    $mysql_user = "a5316362_nails";
    $mysql_password = "justbboy86";


    $loginname = $_POST["loginname"];
    $pwdhash = $_POST["pwdhash"];

    $user = array();

    if(!empty($_POST["loginname"]) && !empty($_POST["pwdhash"])){
    $con=mysqli_connect($mysql_host,$mysql_user,$mysql_password,$mysql_database);
    if ( !$con ) {
      die( 'connect error: '.mysqli_connect_error() );
      echo 'no database connection';
    }
    $sql = "SELECT `UserUID`,`LoginName`,`FirstName`,`LastName`,`PwdHash` FROM `Users` WHERE `LoginName` = ? AND `PwdHash` = ?";
    $statement = mysqli_prepare($con, $sql);
    mysqli_stmt_bind_param($statement, "ss", $loginname, $pwdhash);
    mysqli_stmt_execute($statement);
    mysqli_stmt_bind_result($statement, $UserUID, $LoginName, $FirstName, $LastName, $PwdHash);
    while(mysqli_stmt_fetch($statement)){
        $user["useruid"] = $UserUID;
        $user["loginname"] = $LoginName;
        $user["firstname"] = $FirstName;
        $user["lastname"] = $LastName;
        $user["pwdhash"] = $PwdHash;
    }
    echo json_encode($user);

    mysqli_stmt_close($statement);

    mysqli_close($con);
    }else{
       echo json_encode($user);
    }
?>