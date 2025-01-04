<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Greeting Page</title>
</head>
<body>
    <h1>MyTVPSS Test</h1>
    <p>Message: <span th:text="${message}">Default Message</span></p>
    
    <br><a href="login">Login</a><br>
    <a href="register">Register</a><br>
    <a href="logout">Logout</a><br>
    <br>
    <h3>By IpMan:</h3>
    <p>ABDUL AZIZ BIN MABENI</p>
    <p>AHMAD FAIZ BIN ALLAUDDIN</p>
    <p>MUHAMMAD HAFIZ BIN KHAIRUL KAMAL</p>
</body>
</html>