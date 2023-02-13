<%@ page import="metier.ProduitFinie" %>
<%@ page import="model.Magasin" %>
<%@ page import="bdd.Bddobject" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ProduitFinie produitFinie = new ProduitFinie();
    Magasin[] magasin = Magasin.convert(new Magasin().getData(Bddobject.toConPostgres(),null));
%>
<html>
<head>
    <title>Formulaire</title>
</head>
<body>
    <div class="">
        <form method="post" action="insertion.jsp">
            <input type="hidden">
            <select name="idproduit">
                <% for (int i=0; i<produitFinie.getProduitFinies().length;i++) { %>
                    <option value="<%=produitFinie.getProduitFinies()[i].getIdproduit()%>"><%=produitFinie.getProductName()[i]%></option>
                <% } %>
            </select>
            <input type="date" name="date">
            <input type="number" name="entree">
            <input type="number" name="sortie">
            <select name="idmagasin">
                <% for (int i = 0; i < magasin.length; i++) { %>
                    <option value="<%=magasin[i].getId()%>"><%=magasin[i].getName()%></option>
                <% } %>
            </select>
            <input type="submit" value="submit">
        </form>
    </div>
    <a href="index.jsp">Retour</a>
</body>
</html>
