<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Book Form</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
	<h1>Add Book Form</h1>
	
	<a href="<c:url value='/books/' />" class="btn">Back to Book List</a>
	
	<c:if test="${not empty requestScope.error}">
        <div class="error">
            Error: <c:out value="${requestScope.error}"/>
        </div>
    </c:if>
    
    <form method="POST" action="<c:url value='/books/new' />">
        
        <label for="title">Title:</label><br>
        <%-- Use requestScope.title to pre-fill the field if form submission fails --%>
        <input type="text" id="title" name="title" required 
               value="<c:out value="${requestScope.title}" />"><br><br>

        <label for="author">Author:</label><br>
        <input type="text" id="author" name="author" required 
               value="<c:out value="${requestScope.author}" />"><br><br>
        
        <label for="isbn">ISBN:</label><br>
        <input type="text" id="isbn" name="isbn" required 
               value="<c:out value="${requestScope.isbn}" />"><br><br>

        <label for="description">Description:</label><br>
        <textarea id="description" name="description"><c:out value="${requestScope.description}" /></textarea><br><br>

        <label for="categoryId">Category ID:</label><br>
        <%-- Note: In a real app, this should be a <select> using a list of Category DTOs --%>
        <input type="number" id="categoryId" name="categoryId" required min="1"
               value="<c:out value="${requestScope.categoryId}" />"><br><br>

        <label for="totalCopies">Total Copies:</label><br>
        <input type="number" id="totalCopies" name="totalCopies" required min="1"
               value="<c:out value="${requestScope.totalCopies}" />"><br><br>

        <button type="submit" class="btn btn-primary">Add Book</button>
    </form>
</body>
</html>