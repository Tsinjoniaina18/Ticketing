<%@ page import="java.util.List" %>
<%@ page import="model.reservation.Reservation" %>
<%
    List<Reservation> reservations = (List<Reservation>) request.getAttribute("reservations");
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
    <title>Reservations</title>
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
            margin-bottom: auto;
            margin-top: 75px;
            background-color: rgba(255, 255, 255, 0.1);
            padding: 20px 10px;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
            width: 90%;
            max-width: 1000px;
        }
        h1 {
            text-align: center;
            margin-bottom: 20px;
            font-size: 2.5rem;
        }
        table {
            width: 100%;
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
            font-weight: bold;
        }
        tr:nth-child(even) {
            background-color: rgba(255, 255, 255, 0.05);
        }
        tr:hover {
            background-color: rgba(255, 255, 255, 0.1);
        }
        button {
            background-color: #2a5298;
            color: #fff;
            border: none;
            padding: 8px 12px;
            border-radius: 4px;
            cursor: pointer;
            margin-right: 5px;
            transition: background-color 0.3s ease;
        }
        button:hover {
            background-color: #1e3c72;
        }
        a {
            text-decoration: none;
            color: inherit;
        }
        @media (max-width: 600px) {
            th, td {
                padding: 8px 10px;
            }
            h1 {
                font-size: 2rem;
            }
        }
    </style>
</head>
<body>
<%@ include file="nav.jsp" %>
<div class="container">
    <h1>Vos reservations</h1>
    <table border="1" width="90%">
        <thead>
            <tr>
                <th>Vol</th>
                <th>Passager</th>
                <th>Age</th>
                <th>Date de reservation</th>
                <th>Siege</th>
                <th>Type de siege</th>
                <th>Promotion</th>
                <th>Prix</th>
                <th>Passeport</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <%
                for (int i = 0; i < reservations.size(); i++) {
            %>
                    <tr>
                        <td><%= reservations.get(i).getId_vol() %></td>
                        <td><%= reservations.get(i).getPassager() %></td>
                        <td><%= reservations.get(i).getAge() %></td>
                        <td><%= reservations.get(i).formatDate_reservation() %></td>
                        <td><%= reservations.get(i).getSiege() %></td>
                        <td><%= reservations.get(i).getType_siege() %></td>
                        <td><%= reservations.get(i).getPromotion() %></td>
                        <td><%= reservations.get(i).getPrix() %></td>
                        <td><img src="<%= reservations.get(i).srcToImage() %>" alt="passeport" width="50px"></td>
                        <td><a href="/Ticketing/annulation?id=<%= reservations.get(i).getId() %>"><button>Annuler</button></a></td>
                    </tr>
            <%
                }
            %>
        </tbody>
    </table>
</div>
</body>
</html>
