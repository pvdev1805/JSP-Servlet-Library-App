<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Application Error</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
	<div class="error-container">
        <h1>An Unexpected Error Occurred</h1>
        
        <p class="error-message">
            We apologize, but the application encountered an issue while processing your request.
        </p>

        <%-- Display Error Message from Controller --%>
        <c:if test="${not empty requestScope.errorMessage}">
            <p><strong>Details:</strong> <c:out value="${requestScope.errorMessage}" /></p>
        </c:if>
        <c:if test="${empty requestScope.errorMessage}">
            <p>Please try navigating back to the home page or contact support.</p>
        </c:if>
        
        <p>
            <a href="${pageContext.request.contextPath}/books" class="btn btn-primary">Go to Book List</a>
        </p>
    </div>

</body>
</html>