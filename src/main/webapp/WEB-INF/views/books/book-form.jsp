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
	<c:set var="isEditing" value="${not empty requestScope.book}" />

	<h1>
		<c:if test="${isEditing}">
			Edit Book
		</c:if>
		
		<c:if test="${!isEditing}">
			Add New Book
		</c:if>
	</h1>
	
	<a href="<c:url value='/books/' />" class="btn">Back to Book List</a>
	
	<c:if test="${not empty requestScope.error}">
        <div class="error">
            Error: <c:out value="${requestScope.error}"/>
        </div>
    </c:if>
    
    <form method="POST" action="<c:url value='/books/${isEditing ? "edit" : "new" }' />">
    
    	<c:if test="${isEditing}">
    		<input type="hidden" name="id" class="input-field"
               value="<c:out value="${requestScope.book.id}" />">
    	</c:if>
        
        <label for="title" class="label">Title:</label>
        <%-- Use requestScope.title to pre-fill the field if form submission fails --%>
        <input type="text" id="title" name="title" required class="input-field"
               value="<c:out value="${isEditing ? book.id : requestScope.title}" />">

        <label for="author" class="label">Author:</label>
        <input type="text" id="author" name="author" required class="input-field" 
               value="<c:out value="${isEditing ? book.author : requestScope.author}" />">
        
        <label for="isbn" class="label">ISBN:</label>
        <input type="text" id="isbn" name="isbn" required class="input-field" 
               value="<c:out value="${isEditing ? book.isbn : requestScope.isbn}" />">

        <label for="description" class="label">Description:</label>
        <textarea id="description" name="description" class="input-field">
        	<c:out value="${isEditing ? book.description : requestScope.description}" />
        </textarea>

        <label for="categoryId" class="label">Category ID:</label>
        <%-- Note: In a real app, this should be a <select> using a list of Category DTOs --%>
        <input type="number" id="categoryId" name="categoryId" required min="1" class="input-field"
               value="<c:out value="${requestScope.categoryId}" />">

        <label for="totalCopies" class="label">Total Copies:</label>
        <input type="number" id="totalCopies" name="totalCopies" required min="1" class="input-field"
               value="<c:out value="${requestScope.totalCopies}" />">

        <button type="submit" class="btn btn-primary">Add Book</button>
    </form>
</body>
</html>