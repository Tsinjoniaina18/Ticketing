<%@ page import="java.util.List" %>
<%@ page import="model.Avion" %>
<%@ page import="model.Ville" %>
<%@ page import="model.Vol" %>

<%
    List<Avion> avions = (List<Avion>) request.getAttribute("avions");
    List<Ville> villes = (List<Ville>) request.getAttribute("villes");
    Vol vol = (Vol) request.getAttribute("vol");
    String[] etats = {"Annulee", "En attente", "Effectuee"};
%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Vol</title>
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
    <h1>Vol</h1>
    <form action="/Ticketing/vol" method="post">
        <% if (vol != null) { %>
        <input type="hidden" name="vol.id" value="<%= vol.getId() %>">
        <% } %>
        <p>
            <label>Avion : </label>
            <select name="vol.avion">
                <%
                    for (int i = 0; i < avions.size(); i++) {
                %>
                    <option value="<%= avions.get(i).getId() %>" <%= vol != null ? vol.isSelected(vol.getId_avion(), avions.get(i).getId()) : "" %>><%= avions.get(i).getNom() %></option>
                <%
                    }
                %>
            </select>
        </p>
        <p>
            <label>Date de décollage :</label>
            <input type="datetime-local" name="decollage" value="<%= vol != null ? vol.getDecollageValue() : "" %>" required>
        </p>
        <p>
            <label>Lieu de départ :</label>
            <select name="vol.depart">
                <%
                    for (int i = 0; i < villes.size(); i++) {
                %>
                    <option value="<%= villes.get(i).getId() %>" <%= vol != null ? vol.isSelected(vol.getDepart(), villes.get(i).getId()) : "" %>><%= villes.get(i).getNom() %></option>
                <%
                    }
                %>
            </select>
        </p>
        <p>
            <label>Lieu de déstination :</label>
            <select name="vol.destination">
                <%
                    for (int i = 0; i < villes.size(); i++) {
                %>
                <option value="<%= villes.get(i).getId() %>" <%= vol != null ? vol.isSelected(vol.getDestination(), villes.get(i).getId()) : "" %>><%= villes.get(i).getNom() %></option>
                <%
                    }
                %>
            </select>
        </p>
        <p>
            <label>Prix Business :</label>
            <input type="number" name="vol.business" value="<%= vol != null ? vol.getBusiness() : "" %>" required>
        </p>
        <p>
            <label>Prix Eco :</label>
            <input type="number" name="vol.eco" value="<%= vol != null ? vol.getEco() : "" %>" required>
        </p>
        <% if (vol != null) { %>
        <p>
            <label>État :</label>
            <select name="vol.etat">
                <%
                    for (int i = 0; i < etats.length; i++) {
                %>
                <option value="<%= i %>" <%= vol.isSelected(i, vol.getEtat()) %>><%= etats[i] %></option>
                <%
                    }
                %>
            </select>
        </p>
        <% } %>
        <input type="submit" value="Valider">
    </form>
</div>
</body>
</html>

