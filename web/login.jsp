<%@ page import="java.util.ArrayList" %>
<%
    String fail = (String) request.getAttribute("fail");
    if(fail!=null){
%>
<script>
    alert("Info: " + "<%= fail %>");
</script>
<%
    }
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login - Aeroport</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #1e3c72, #2a5298);
            color: #fff;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
        }

        h1 {
            text-align: center;
            margin-bottom: 20px;
            font-size: 2.5rem;
        }

        form {
            background-color: rgba(255, 255, 255, 0.1);
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
            width: 300px;
        }

        form p {
            margin: 15px 0;
        }

        label {
            font-weight: bold;
            margin-bottom: 5px;
            display: block;
        }

        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 10px;
            border: none;
            border-radius: 5px;
            margin-top: 5px;
            font-size: 1rem;
        }

        input[type="text"]:focus,
        input[type="password"]:focus {
            outline: none;
            box-shadow: 0 0 5px rgba(42, 82, 152, 0.8);
        }

        input[type="submit"] {
            width: 100%;
            padding: 10px;
            margin-top: 20px;
            border: none;
            border-radius: 5px;
            background-color: #2a5298;
            color: #fff;
            font-size: 1.1rem;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        input[type="submit"]:hover {
            background-color: #1e3c72;
        }
    </style>
</head>
<body>
    <form action="/Ticketing/check" method="post">
        <h1>Login</h1>
        <% if(request.getAttribute("error")!=null){
            ArrayList<String> erreurs = (ArrayList<String>) request.getAttribute("error");
        %>
        <b>Errors: </b>
        <ul>
            <% for(int i=0; i<erreurs.size(); i++) { %>
            <li><%= erreurs.get(i) %></li>
            <% } %>
        </ul>
        <% } %>
        <p>
            <label for="email">Email :</label>
            <input type="text" id="email" name="auth.email">
        </p>
        <p>
            <label for="password">Password :</label>
            <input type="password" id="password" name="auth.password">
        </p>
        <input type="submit" value="Se connecter">
    </form>
</body>
</html>
