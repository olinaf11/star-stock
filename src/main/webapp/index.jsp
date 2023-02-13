<%@ page import="metier.ProduitFinie" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    ProduitFinie produitFinie = new ProduitFinie();
%>
<!DOCTYPE html>
<html>
<head>
    <title>Index</title>
</head>
<body>
    <ul>
        <%
            try {
                for (String name:
                     produitFinie.getProductName()) {%>
                <li><%=name%></li>
                <%}
            } catch (Exception e) {
                    e.printStackTrace(response.getWriter());
            }
        %>
    </ul>
    <a href="form.jsp">Insertion du mouvement de stock</a><br>
    <a href="detail.jsp">L'etat de stock simple</a><br>
    <a href="DateForm.jsp">L'etat de stock daté</a><br>
    <a href="ReportForm.jsp">Report a inserer</a>
    <a href="etatdateoptimise.jsp">L'etat de stock optimiser</a>
</body>
</html>