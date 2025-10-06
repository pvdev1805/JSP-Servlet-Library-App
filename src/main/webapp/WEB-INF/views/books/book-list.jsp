<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Book List</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
	<h1>Book List - University Library</h1>
	
	<a href="<c:url value='/books/new' />" class="btn">+ Add Book</a>
	
	<c:choose>
        <c:when test="${not empty requestScope.books}">
            
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Title</th>
                        <th>Author</th>
                        <th>ISBN</th>
                        <th>Category</th>
                        <th>Total Copies</th>
                        <th>Available Copies</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                
                <tbody>
                    <c:forEach var="book" items="${requestScope.books}">
                        <tr>
                            <td><c:out value="${book.id}" /></td>
                            <td><c:out value="${book.title}" /></td>
                            <td><c:out value="${book.author}" /></td>
                            <td><c:out value="${book.isbn}" /></td>
                            <td><c:out value="${book.categoryName}" /></td>
                            <td><c:out value="${book.totalCopies}" /></td>
                            <td><c:out value="${book.availableCopies}" /></td>
                            <td>
                            	<c:choose>
                            		<c:when test="${book.available }">
                            			<span>Available</span>
                            		</c:when>
                            		<c:otherwise>
                            			<span>Unavailable</span>
                            		</c:otherwise>
                            	</c:choose>
                            </td>
                            <td>
                                <a href="<c:url value='/books/edit'>
                                            <c:param name='id' value='${book.id}'/>
                                        </c:url>" 
                                   class="btn btn-edit"
                                >
                                        Edit
                                </a>
                                
                                <a href="<c:url value='/books/delete'>
                                            <c:param name='id' value='${book.id}'/>
                                        </c:url>" 
                                   class="btn btn-delete"
                                >
                                        Delete
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>

        <c:otherwise>
            <p>No books available.</p>
        </c:otherwise>
	</c:choose>
</body>
</html>