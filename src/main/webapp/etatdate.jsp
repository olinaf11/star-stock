<%@ page import="metier.EtatStockDate" %>
<%@ page import="metier.EtatStock" %><%--
  Created by IntelliJ IDEA.
  User: Fanilo
  Date: 24/01/2023
  Time: 14:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String date = request.getParameter("date");
    EtatStockDate etatStockDate = new EtatStockDate(date);
    etatStockDate.setDate(date);
    EtatStock[] mouvementStocks = new EtatStock[0];
    try {
        mouvementStocks = etatStockDate.getEtatStocks();
    } catch (Exception e) {
        e.printStackTrace(response.getWriter());
    }
%>
<html>
<head>
    <title>Etat de stock datée</title>
</head>
<body>
<div>
    <table border="1">
        <thead>
        <tr>
            <td>Produit</td>
            <td>Entree</td>
            <td>Sortie</td>
            <td>Restes</td>
            <td>Magasin</td>
            <td>Date</td>
        </tr>
        </thead>
        <tbody>
        <% for (int i = 0; i < mouvementStocks.length; i++) { %>
        <tr>
            <td><%=mouvementStocks[i].getIdproduit()%></td>
            <td><%=mouvementStocks[i].getEntree()%></td>
            <td><%=mouvementStocks[i].getSortie()%></td>
            <td><%=mouvementStocks[i].getReste()%></td>
            <td><%=mouvementStocks[i].getIdmagasin()%></td>
            <td><%=etatStockDate.getDate()%></td>
        </tr>
        <% }
        %>
        </tbody>
    </table>
</div>
</body>
</html>
