<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${pageTitle}</title>
    <style>
        body { font-family: sans-serif; }
        table { border-collapse: collapse; width: 60%; margin-bottom: 1em;} /* Adjusted width */
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        .actions a { margin-right: 5px; text-decoration: none; }
        .add-button { margin-bottom: 1em; }
        nav a { margin-right: 10px; }
        .error-message { color: red; font-weight: bold; margin-bottom: 1em; }
    </style>
</head>
<body>

    <h1>${pageTitle}</h1>

    <nav>
        <a href="<c:url value='/pacientes'/>">Pacientes</a> |
        <a href="<c:url value='/medicos'/>">Médicos</a> |
        <a href="<c:url value='/especialidades'/>">Especialidades</a> |
        <a href="<c:url value='/consultas'/>">Consultas</a> |
        <a href="<c:url value='/'/>">Home</a>
    </nav>
    <hr/>

    <c:if test="${not empty errorMessage}">
        <p class="error-message"><c:out value="${errorMessage}"/></p>
    </c:if>

    <div class="add-button">
        <a href="<c:url value='/especialidades/novo'/>">Adicionar Nova Especialidade</a>
    </div>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Ações</th>
            </tr>
        </thead>
        <tbody>
            <c:choose>
                <c:when test="${not empty especialidades}">
                    <c:forEach items="${especialidades}" var="esp">
                        <tr>
                            <td>${esp.id}</td>
                            <td><c:out value="${esp.nome}"/></td>
                            <td class="actions">
                                <a href="<c:url value='/especialidades/editar/${esp.id}'/>">Editar</a>
                                <a href="<c:url value='/especialidades/excluir/${esp.id}'/>" onclick="return confirm('Tem certeza que deseja excluir esta especialidade? Médicos associados podem impedir a exclusão.');">Excluir</a>
                            </td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td colspan="3">Nenhuma especialidade encontrada.</td>
                    </tr>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>

</body>
</html>