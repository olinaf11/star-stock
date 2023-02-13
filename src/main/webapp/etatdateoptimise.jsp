<%@ page import="metier.EtatStock" %>
<%@ page import="metier.EtatStockDate" %>
<%@ page import="model.ReportProduit" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    EtatStockDate etatStockDate = new EtatStockDate("");
    etatStockDate.setDate("");
    EtatStock[] mouvementStocks = null;
    ReportProduit[] reportQuantite = null;
    try {
        mouvementStocks = etatStockDate.getEtatStocks();
    } catch (Exception e) {
        e.printStackTrace(response.getWriter());
    }
    if (request.getAttribute("etatStocks")!=null && request.getAttribute("lastreport")!=null && request.getAttribute("date")!=null){
        mouvementStocks =  (EtatStock[]) request.getAttribute("etatStocks");
        reportQuantite = (ReportProduit[]) request.getAttribute("lastreport");
        etatStockDate.setDate(request.getAttribute("date").toString());
    }
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <div>
        <form method="post" action="traitementDate.jsp">
            <input type="date" name="date">
            <input type="submit" value="Date">
        </form>
    </div>
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
        <% for (int i = 0; i < mouvementStocks.length; i++) {
            if (reportQuantite!=null){%>
        <tr>
            <td><%=mouvementStocks[i].getIdproduit()%></td>
            <td><%=mouvementStocks[i].getReportEntree(reportQuantite[i].getQuantite())%></td>
            <td><%=mouvementStocks[i].getSortie()%></td>
            <td><%=mouvementStocks[i].getReportReste(reportQuantite[i].getQuantite())%></td>
            <td><%=mouvementStocks[i].getIdmagasin()%></td>
            <td><%=etatStockDate.getDate()%></td>
        </tr>
        <% }else { %>
            <tr>
                <td><%=mouvementStocks[i].getIdproduit()%></td>
                <td><%=mouvementStocks[i].getEntree()%></td>
                <td><%=mouvementStocks[i].getSortie()%></td>
                <td><%=mouvementStocks[i].getReste()%></td>
                <td><%=mouvementStocks[i].getIdmagasin()%></td>
                <td><%=etatStockDate.getDate()%></td>
            </tr>
        <%  }
        }%>
        </tbody>
    </table>
    </div>
</body>
</html>
