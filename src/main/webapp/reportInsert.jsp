<%@ page import="model.Report" %>
<%@ page import="model.ReportProduit" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String[] idproduit = request.getParameterValues("idproduit");
  String[] quantite = request.getParameterValues("quantite");
  String[] date = request.getParameterValues("date");
  String[] idmagasin = request.getParameterValues("idmagasin");
  try {
    for (int i = 0; i < idproduit.length; i++) {
      Report report = new Report(date[i], idmagasin[i]);
      report.insertObj(null);
      ReportProduit reportProduit = new ReportProduit(quantite[i], idproduit[i], report.getId());
      reportProduit.insertObj(null);
    }
    response.sendRedirect("ReportForm.jsp");
  } catch (Exception e) {
    e.printStackTrace(response.getWriter());
  }

%>
<html>
<head>
    <title>Traitement</title>
</head>
<body>

</body>
</html>
