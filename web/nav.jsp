<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
  header {
    background-color: #2a5298;
    padding: 15px 20px;
    box-shadow: 0 4px 10px rgba(0,0,0,0.3);
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    z-index: 1000;
  }
  nav ul {
    list-style-type: none;
    margin: 0;
    padding: 0;
    display: flex;
    justify-content: center;
  }
  nav li {
    margin: 0 15px;
  }
  nav a {
    color: #fff;
    text-decoration: none;
    font-size: 1.2rem;
    transition: color 0.3s ease;
  }
  nav a:hover {
    color: #1e3c72;
  }
</style>

<header>
  <nav>
    <ul>
      <li><a href="/Ticketing/home">Accueil</a></li>
      <li><a href="/Ticketing/vols">Vols</a></li>
      <li><a href="/Ticketing/vol">Vol</a></li>
      <li><a href="/Ticketing/reservations">Réservations</a></li>
      <li><a href="/Ticketing/config_reservation">Config reservation</a></li>
      <li><a href="/Ticketing/config_promotion">Config promotion</a></li>
      <li><a href="/Ticketing/logout">Déconnexion</a></li>
    </ul>
  </nav>
</header>
