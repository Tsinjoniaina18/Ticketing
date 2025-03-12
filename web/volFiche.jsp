<%@ page import="model.Vol" %>
<%@ page import="model.reservation.ReservationPlaceInfo" %>
<%@ page import="java.util.List" %>
<%
    Vol vol = (Vol) request.getAttribute("vol");
    List<ReservationPlaceInfo> infos = (List<ReservationPlaceInfo>) request.getAttribute("infos");
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Fiche Vol</title>
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
        h1, h2 {
            text-align: center;
            margin-bottom: 20px;
        }
        h1 {
            font-size: 2.5rem;
        }
        h2 {
            font-size: 2rem;
        }
        form p {
            margin: 15px 0;
        }
        label {
            margin-left: 50px;
            font-weight: bold;
            display: block;
            margin-bottom: 5px;
        }
        input[type="datetime-local"],
        input[type="number"],
        input[type="text"],
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
        input[type="text"]:focus,
        select:focus {
            outline: none;
            box-shadow: 0 0 5px rgba(42, 82, 152, 0.8);
        }
        input[type="file"] {
            margin-left: 50px;
            width: 90%;
            padding: 8px;
            border: 1px solid rgba(255, 255, 255, 0.4);
            border-radius: 5px;
            background-color: rgba(255, 255, 255, 0.2);
            color: #fff;
            font-size: 1rem;
        }
        input[type="file"]:hover {
            background-color: rgba(255, 255, 255, 0.3);
        }
        button {
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            background-color: #2a5298;
            color: #fff;
            font-size: 1.1rem;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        input[type="submit"]:hover,
        button:hover {
            background-color: #1e3c72;
        }
        table {
            width: 90%;
            margin: 20px auto;
            border-collapse: collapse;
            background-color: rgba(255, 255, 255, 0.1);
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
            border-radius: 8px;
            overflow: hidden;
        }
        th, td {
            padding: 12px 15px;
            text-align: center;
            border-bottom: 1px solid rgba(255, 255, 255, 0.2);
        }
        th {
            background-color: rgba(42, 82, 152, 0.8);
        }
        tr:nth-child(even) {
            background-color: rgba(255, 255, 255, 0.05);
        }
        tr:hover {
            background-color: rgba(255, 255, 255, 0.1);
        }
        a {
            text-decoration: none;
            color: inherit;
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
    </style>
</head>
<body>
<%@ include file="nav.jsp" %>
<div class="container">
    <h1>Fiche Vol</h1>
    <p>
        <b>Avion: </b><%= vol.getId_avion() %>
    </p>
    <p>
        <b>Décollage: </b><%= vol.formatDecollage() %>
    </p>
    <p>
        <b>Départ: </b><%= vol.getDepart() %>
    </p>
    <p>
        <b>Destination: </b><%= vol.getDestination() %>
    </p>
    <p>
        <b>Business: </b><%= vol.getBusiness() %>
    </p>
    <p>
        <b>Eco: </b><%= vol.getEco() %>
    </p>
    <center>
        <a href="/Ticketing/info?id=<%= vol.getId() %>">
            <button>Réserver</button>
        </a>
    </center>
    <%
        if(infos != null){
    %>
    <div>
        <table>
            <thead>
            <tr>
                <th>Info</th>
                <th>Business</th>
                <th>Eco</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>Disponible</td>
                <td><%= infos.get(0).getDisponible() %></td>
                <td><%= infos.get(1).getDisponible() %></td>
            </tr>
            <tr>
                <td>Promotion</td>
                <td><%= infos.get(0).getPromotion().getSiege() %></td>
                <td><%= infos.get(1).getPromotion().getSiege() %></td>
            </tr>
            <tr>
                <td>Remise</td>
                <td><%= infos.get(0).getPromotion().getRemise() %> %</td>
                <td><%= infos.get(1).getPromotion().getRemise() %> %</td>
            </tr>
            </tbody>
        </table>
        <center><h2>Réservation</h2></center>
        <form action="/Ticketing/reservation" method="post" enctype="multipart/form-data">
            <input type="hidden" name="res.id_vol" value="<%= vol.getId() %>">
            <p>
                <label>Passager:</label>
                <input type="text" name="res.passager" required>
            </p>
            <p>
                <label>Age:</label>
                <input type="number" name="res.age" required>
            </p>
            <p>
                <label>Date:</label>
                <input type="datetime-local" name="date" required>
            </p>
            <p>
                <label>Place:</label>
                <input type="number" name="res.siege" value="1" readonly>
            </p>
            <p>
                <label>Type:</label>
                <div
                    style="width: 90%; margin-left: 50px;"
                >
                    <input type="radio" name="res.type_siege" value="TSG00001"> Business
                    <input type="radio" name="res.type_siege" style="margin-left: 50px" value="TSG00002"> Eco
                </div>
            </p>
            <p>
                <label>Passeport:</label>
                <input type="file" name="res.passeport" required>
            </p>
            <input type="submit" value="Valider">
        </form>
    </div>
    <%
        }
    %>
</div>
</body>
</html>
