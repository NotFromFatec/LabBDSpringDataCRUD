<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> <%-- Keep fmt for potential future use or other date types --%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${pageTitle}</title>
    <style>
        body { font-family: sans-serif; }
        table { border-collapse: collapse; width: 100%; margin-bottom: 1em;}
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        .actions a { margin-right: 5px; text-decoration: none; }
        .search-form { margin-bottom: 1em; padding: 10px; border: 1px solid #ccc; background-color: #f9f9f9; }
        .search-form label, .search-form input, .search-form button { margin-right: 10px; }
        .add-button { margin-bottom: 1em; }
        nav a { margin-right: 10px; }
        .error-message { color: red; font-weight: bold; margin-bottom: 1em; }
        .count-info { font-style: italic; margin-bottom: 1em; }
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
     <c:if test="${not empty dateError}">
        <p class="error-message"><c:out value="${dateError}"/></p>
    </c:if>

     <%-- Search Form (Requirement d/e) --%>
    <div class="search-form">
        <form action="<c:url value='/consultas'/>" method="get">
            <label for="searchDate">Buscar Consultas por Dia:</label>
            <input type="date" id="searchDate" name="searchDate" value="${searchDate}">
            <button type="submit">Buscar</button>
            <c:if test="${not empty searchDate}">
                 <a href="<c:url value='/consultas'/>" style="margin-left: 15px;">Mostrar Todas</a>
            </c:if>
        </form>
    </div>

    <c:if test="${not empty searchDate}">
        <%-- Formatting the searchDate (LocalDate) correctly here --%>
        <fmt:parseDate value="${searchDate}" pattern="yyyy-MM-dd" var="parsedSearchDate" type="date"/>
        <p class="count-info">
            Encontradas ${countConsultasDia} consulta(s) para o dia <fmt:formatDate value="${parsedSearchDate}" pattern="dd/MM/yyyy" type="date"/>.
        </p>
    </c:if>

     <div class="add-button">
        <a href="<c:url value='/consultas/novo'/>">Adicionar Nova Consulta</a>
    </div>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Paciente</th>
                <th>Médico</th>
                <th>Data e Hora</th>
                <th>Ações</th>
            </tr>
        </thead>
        <tbody>
            <c:choose>
                <c:when test="${not empty consultas}">
                    <c:forEach items="${consultas}" var="consulta">
                        <tr>
                            <td>${consulta.id}</td>
                            <td><c:out value="${consulta.paciente.nome}"/></td>
                            <td><c:out value="${consulta.medico.nome}"/> (<c:out value="${consulta.medico.especialidade.nome}"/>)</td>
                            <td>
                                <%-- FIXED LINE: Manually format LocalDateTime using EL --%>
                                <c:if test="${not empty consulta.dataHora}">
                                    <c:out value="${String.format('%02d/%02d/%d %02d:%02d',
                                                    consulta.dataHora.dayOfMonth,
                                                    consulta.dataHora.monthValue,
                                                    consulta.dataHora.year,
                                                    consulta.dataHora.hour,
                                                    consulta.dataHora.minute)}"/>
                                </c:if>
                            </td>
                            <td class="actions">
                                <a href="<c:url value='/consultas/editar/${consulta.id}'/>">Editar</a>
                                <a href="<c:url value='/consultas/excluir/${consulta.id}'/>" onclick="return confirm('Tem certeza que deseja excluir esta consulta?');">Excluir</a>
                            </td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td colspan="5">Nenhuma consulta encontrada<c:if test="${not empty searchDate}"> para o dia selecionado</c:if>.</td>
                    </tr>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>

</body>
</html>