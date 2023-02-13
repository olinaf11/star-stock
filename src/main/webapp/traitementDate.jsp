<%@ page import="model.ReportProduit" %>
<%@ page import="bdd.Bddobject" %>
<%@ page import="metier.EtatStockDate" %>
<%@ page import="metier.EtatdateOptimise" %>
<%@ page import="metier.EtatStock" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String date = request.getParameter("date");
  EtatdateOptimise etatdateOptimise =new EtatdateOptimise(date);
  EtatStock[] etatStocks = new EtatStock[0];
  try {
    etatStocks = etatdateOptimise.getEtatStocks();
    request.setAttribute("etatStocks", etatStocks);
    request.setAttribute("lastreport", etatdateOptimise.getReportProduitLast());
    request.setAttribute("date", date);
    out.print(etatdateOptimise.getReportProduitLast()[0].getIdproduit());
    RequestDispatcher dispatcher = request.getRequestDispatcher("etatdateoptimise.jsp");
    dispatcher.forward(request,response);
  } catch (Exception e) {
    e.printStackTrace(response.getWriter());
  }
%>
<html>
<head>
    <title></title>
</head>
<body>

</body>
</html>
