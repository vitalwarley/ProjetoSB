<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.List"%>
<%
	List<nucleo.model.negocios.Blog> list = (List<nucleo.model.negocios.Blog>) request.getAttribute("blogs");
%>
<jsp:useBean id="usuario" class="nucleo.model.negocios.Usuario" />

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>P�gina inicial</title>
</head>
<body>
	<header>
		<c:choose>
			<c:when test="${ empty usuario }">
				<form action="login.jsp" method="post">
					<fieldset>
						Login: <input type="text" name="login"> Senha: <input
							type="text" name="pass"> <input type="submit"
							value="Logar">
					</fieldset>
				</form>
				<fieldset>
					N�o tem uma conta? <a href="web/registrar.jsp">Registre-se!</a>
				</fieldset>
			</c:when>

			<c:otherwise>
				<c:import url="/WEB-INF/_consultar-blogs.jsp" />
			</c:otherwise>
		</c:choose>


	</header>
	<hr>
	<section>

		<h1>Blogs vivos</h1>
		<br>

		<c:forEach items="${blogs}" var="blog">
			<a href="web/mostrar_blog.jsp?id=${blog.codigo}">${blog.titulo }</a>
		</c:forEach>
	</section>
	<hr>
</body>
</html>