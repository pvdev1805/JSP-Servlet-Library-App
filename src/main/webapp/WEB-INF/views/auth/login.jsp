<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>University Library Login</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
	<h1>Login</h1>
	
	<div class="login-container">
		<c:if test="${not empty requestScope.error}">
			<p class="error">
				<c:out value="${requestScope.error}" />
			</p>
		</c:if>
		
		<form method="POST" action="<c:url value="/login" />" class="form">
			<fieldset>
				<div class="form-group">
					<label for="username" class="label"> Username </label> 
					<input
						class="input-field" type="text" id="username" name="username"
						value="<c:out value="${requestScope.username}" />" required />
				</div>


				<div class="form-group">
					<label for="password" class="label"> Password </label> 
					<input
						class="input-field" type="password" id="password" name="password"
						value="<c:out value="${requestScope.password}" />" required />
				</div>

				<button type="submit" class="btn btn-login">Login</button>
			</fieldset>
		</form>
		
		<p>Haven't got an account? <a href="<c:url value='/register' />">Register</a></p>
	</div>
</body>
</html>