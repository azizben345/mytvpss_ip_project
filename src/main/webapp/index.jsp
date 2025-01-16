<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Greeting Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100vh;
            color: #333;
        }

        h1 {
            color: #2a5298;
            font-size: 36px;
            margin-bottom: 20px;
        }

        p {
            font-size: 18px;
            margin: 10px 0;
        }

        .message {
            font-weight: bold;
            color: #e67e22;
        }

        a {
            font-size: 18px;
            color: #3498db;
            text-decoration: none;
            margin-top: 20px;
        }

        a:hover {
            text-decoration: underline;
        }

        h3 {
            margin-top: 30px;
            color: #2a5298;
        }

        .team-members {
            font-size: 16px;
            color: #555;
        }
    </style>
</head>
<body>
    <h1>MyTVPSS</h1>
    <p>Message: <span th:text="${message}">Default Message</span></p>
    
    <br><a href="login">Login</a><br>
    
    <br>
    <h3>By IpMan:</h3>
    <p>ABDUL AZIZ BIN MABENI</p>
    <p>AHMAD FAIZ BIN ALLAUDDIN</p>
    <p>MUHAMMAD HAFIZ BIN KHAIRUL KAMAL</p>
</body>
</html>