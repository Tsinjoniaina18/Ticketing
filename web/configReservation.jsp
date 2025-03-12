<%@ page import="model.ConfigReservation" %><%
    ConfigReservation configReservation = (ConfigReservation) request.getAttribute("config");
    String sucess = (String) request.getAttribute("sucess");
%>
<%
    if(sucess!=null){
%>
    <script>
        alert("Info: " + "<%= sucess %>");
    </script>
<%
    }
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Configuration</title>
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
            min-height: 100vh;
        }
        .container {
            background-color: rgba(255, 255, 255, 0.1);
            padding: 20px;
            margin-top: 75px;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
            width: 800px;
            max-width: 95%;
        }
        h1 {
            text-align: center;
            margin-bottom: 20px;
            font-size: 2.5rem;
        }
        form p {
            margin: 15px 0;
        }
        label {
            font-weight: bold;
            display: block;
            margin-bottom: 5px;
            margin-left: 50px;
        }
        input[type="datetime-local"],
        input[type="number"],
        select {
            margin-left: 50px;
            width: 90%;
            padding: 10px;
            border: none;
            border-radius: 5px;
            font-size: 1rem;
            margin-top: 5px;
        }
        input[type="datetime-local"]:focus,
        input[type="number"]:focus,
        select:focus {
            outline: none;
            box-shadow: 0 0 5px rgba(42, 82, 152, 0.8);
        }
        input[type="submit"] {
            margin-left: 50px;
            width: 90%;
            padding: 10px;
            border: none;
            border-radius: 5px;
            background-color: #2a5298;
            color: #fff;
            font-size: 1.1rem;
            cursor: pointer;
            transition: background-color 0.3s ease;
            margin-top: 20px;
        }
        input[type="submit"]:hover {
            background-color: #1e3c72;
        }
        a {
            text-decoration: none;
        }
    </style>
</head>
<body>
<%@ include file="nav.jsp" %>
<div class="container">
    <h1>Configuration Reservation</h1>
    <form action="/Ticketing/config_reservation" method="post">
        <input type="hidden" name="config.id" value="<%= configReservation.getId() %>">
        <p>
            <label>Heure avant Vol: </label>
            <input type="number" name="config.heureReservation" value="<%= configReservation.getHeure_reservation() %>">
        </p>
        <p>
            <label>Heure avant Annulation: </label>
            <input type="number" name="config.heureAnnulation" value="<%= configReservation.getHeure_annulation() %>">
        </p>
        <input type="submit" value="valider">
    </form>
</div>
</body>
</html>
