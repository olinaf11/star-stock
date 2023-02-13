<%@ page import="model.MouvementStock" %>
<%@ page import="java.io.PrintWriter" %>
<%--
  Created by IntelliJ IDEA.
  User: Fanilo
  Date: 24/01/2023
  Time: 15:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    PrintWriter sout = response.getWriter();
    String idproduit = request.getParameter("idproduit");
    String date = request.getParameter("date");
    String entree = request.getParameter("entree");
    String sortie = request.getParameter("sortie");
    String idmagasin = request.getParameter("idmagasin");
    try {
        MouvementStock etat = new MouvementStock(idproduit,date,entree,sortie,idmagasin);
        etat.insertObj(null);
        response.sendRedirect("form.jsp");
    } catch (Exception e) {
        e.printStackTrace(new PrintWriter(sout));
    }
%>
<html>
<head>
    <title>insertion</title>
</head>
<body>

</body>
</html>
