<%@ page import="metier.ProduitFinie" %>
<%@ page import="model.Magasin" %>
<%@ page import="model.Produitfinie" %>
<%@ page import="bdd.Bddobject" %>
<%@ page import="metier.EtatStock" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  ProduitFinie produitFinie = new ProduitFinie();
  String[] prodname = new String[0];
  Magasin magasin = new Magasin();
  EtatStock mouvementStock = new EtatStock();
  EtatStock[] mouvementStocks = new EtatStock[0];
  try {
    mouvementStocks = EtatStock.convert(mouvementStock.getData(Bddobject.toConPostgres(),null));
    magasin = Magasin.convert(new Magasin().getData(Bddobject.toConPostgres(),null))[0];
    prodname = produitFinie.getProductName();
  } catch (Exception e) {
    e.printStackTrace(response.getWriter());
  }
%>
<html>
<head>
    <title>Formulaire</title>
</head>
<body>
  <div class="table-header">
    <form method="post" action="reportInsert.jsp">
      <table>
        <thead>
          <tr>
            <td>Produit</td>
            <td>Quantité</td>
            <td>Date</td>
            <td>Magasin</td>
          </tr>
        </thead>
        <tbody>
          <% for (int i = 0; i < mouvementStocks.length; i++) { %>
          <tr>
            <td><input type="hidden" name="idproduit" value="<%=mouvementStocks[i].getIdproduit()%>"><%=prodname[i]%></td>
            <td><input type="number" name="quantite"></td>
            <td><input type="date" name="date"></td>
            <td><input type="hidden" name="idmagasin" value="<%=mouvementStocks[i].getIdmagasin()%>"><%=mouvementStocks[i].getIdmagasin()%></td>
          </tr>
          <% }
          %>
        </tbody>
      </table>
      <input type="submit" value="Validez">
    </form>
  </div>
  <a href="index.jsp"></a>
</body>
</html>
