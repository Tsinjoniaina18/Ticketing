<%@ page import="java.util.List" %>
<%@ page import="model.Vol" %>
<%@ page import="model.Ville" %>
<%
    List<Vol> vols = (List<Vol>) request.getAttribute("vols");
    List<Ville> villes = (List<Ville>) request.getAttribute("villes");
    String[] etats = {"Annulee", "En attente", "Effectuee"};

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
    <title>Vols</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #1e3c72, #2a5298);
            color: #fff;
        }
        .container {
            margin: 75px auto 0 auto;
            background-color: rgba(255, 255, 255, 0.1);
            padding: 20px;
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
        .search-form {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
            align-items: flex-end;
            margin-bottom: 20px;
        }
        .search-form .form-group {
            flex: 1;
            min-width: 180px;
        }
        .search-form .form-group-date {
            flex: 2;
            min-width: 180px;
        }
        .search-form label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        .search-form select {
            width: 100%;
            padding: 8px;
            border-radius: 5px;
            border: none;
            font-size: 1rem;
        }
        .search-form input[type="datetime-local"]{
            width: 48%;
            padding: 8px;
            border-radius: 5px;
            border: none;
            font-size: 1rem;
        }
        .date-group {
            display: flex;
            align-items: center;
            gap: 5px;
        }
        .date-group .separator {
            font-size: 1.2rem;
        }
        .button-submit {
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            background-color: #2a5298;
            color: #fff;
            font-size: 1.1rem;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        .button-submit:hover {
            background-color: #1e3c72;
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
            .search-form {
                flex-direction: column;
            }
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
    <h1>Vols</h1>
    <form action="/Ticketing/rechercher" method="post">
        <div class="search-form">
            <p>
                <div class="form-group">
                    <label for="depart">Départ</label>
                    <select name="depart" id="depart">
                        <option value="">Départ</option>
                        <%
                            for (int i = 0; i < villes.size(); i++) {
                        %>
                        <option value="<%= villes.get(i).getId() %>"><%= villes.get(i).getNom() %></option>
                        <%
                            }
                        %>
                    </select>
                </div>
                <div class="form-group">
                    <label for="destination">Destination</label>
                    <select name="destination" id="destination">
                        <option value="">Destination</option>
                        <%
                            for (int i = 0; i < villes.size(); i++) {
                        %>
                        <option value="<%= villes.get(i).getId() %>"><%= villes.get(i).getNom() %></option>
                        <%
                            }
                        %>
                    </select>
                </div>
                <div class="form-group-date">
                    <label>Dates</label>
                    <div class="date-group">
                        <input type="datetime-local" name="min">
                        <span class="separator">-</span>
                        <input type="datetime-local" name="max">
                    </div>
                </div>
                <div class="form-group">
                    <label for="etat">Situation</label>
                    <select name="etat" id="etat">
                        <option value="">Etat</option>
                        <%
                            for (int i = 0; i < etats.length; i++) {
                        %>
                        <option value="<%= i %>"><%= etats[i] %></option>
                        <%
                            }
                        %>
                    </select>
                </div>
            </p>
        </div>
            <p>
                <center><button type="submit" class="button-submit">Valider</button></center>
            </p>
    </form>
    <table>
        <thead>
        <tr>
            <th>Avion</th>
            <th>Départ</th>
            <th>Destination</th>
            <th>Décollage</th>
            <th>Situation</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (int i = 0; i < vols.size(); i++) {
                Vol vol = vols.get(i);
        %>
        <tr>
            <td><%= vol.getId_avion() %></td>
            <td><%= vol.getDepart() %></td>
            <td><%= vol.getDestination() %></td>
            <td><%= vol.formatDecollage() %></td>
            <td><%= etats[vol.getEtat()] %></td>
            <td>
                <a href="/Ticketing/update?id=<%= vol.getId() %>"><button>Modifier</button></a>
                <a href="/Ticketing/fiche?id=<%= vol.getId() %>"><button>Réserver</button></a>
            </td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
</div>
</body>
</html>
