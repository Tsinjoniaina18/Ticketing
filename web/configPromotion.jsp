<%@ page import="model.Vol" %>
<%@ page import="model.TypeSiege" %>
<%@ page import="java.util.List" %>
<%
    List<Vol> vols = (List<Vol>) request.getAttribute("vols");
    List<TypeSiege> typeSieges = (List<TypeSiege>) request.getAttribute("type");
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

    <h1>Configuration Promotion</h1>
    <form action="/Ticketing/config_promotion" method="post">
        <p>
            <label>Vol: </label>
            <select name="config.vol">
                <%
                    for (int i = 0; i < vols.size(); i++) {
                %>
                        <option value="<%= vols.get(i).getId() %>">De <%= vols.get(i).getDepart() %> vers <%= vols.get(i).getDestination() %> le <%= vols.get(i).formatDecollage() %></option>
                <%
                    }
                %>
            </select>
        </p>
        <p>
            <label>Siege: </label>
            <input type="number" name="config.siege" required>
        </p>
        <p>
            <label>Type de siege: </label>
            <select name="config.type">
                <%
                    for (int i = 0; i < typeSieges.size(); i++) {
                %>
                    <option value="<%= typeSieges.get(i).getId() %>"><%= typeSieges.get(i).getNom() %></option>
                <%
                    }
                %>
            </select>
        </p>
        <p>
            <label>Remise: </label>
            <input type="number" step="any" name="config.remise">
        </p>
        <input type="submit" value="Valider">
    </form>
</div>
</body>
</html>
