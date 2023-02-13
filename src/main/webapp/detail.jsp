<%@ page import="model.MouvementStock" %>
<%@ page import="metier.EtatStock" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="bdd.Bddobject" %><%--
  Created by IntelliJ IDEA.
  User: Fanilo
  Date: 24/01/2023
  Time: 14:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    PrintWriter sout = response.getWriter();
    EtatStock mouvementStock = new EtatStock();
    EtatStock[] mouvementStocks = new EtatStock[0];
    try {
        mouvementStocks = EtatStock.convert(mouvementStock.getData(Bddobject.toConPostgres(),null));
    } catch (Exception e) {
        e.printStackTrace(sout);
    }
%>
<html>
<head>
    <title>Detail stock</title>
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
                        </tr>
                    <% }
                %>
            </tbody>
        </table>
    </div>
</body>
</html>
