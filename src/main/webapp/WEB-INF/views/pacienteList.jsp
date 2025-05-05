<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %> <%-- Optional for forms --%>

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
    </style>
</head>
<body>

    <h1>${pageTitle}</h1>

    <%-- Navigation (Simple) --%>
    <nav>
        <a href="<c:url value='/pacientes'/>">Pacientes</a> |
        <a href="<c:url value='/medicos'/>">Médicos</a> |
        <a href="<c:url value='/especialidades'/>">Especialidades</a> |
        <a href="<c:url value='/consultas'/>">Consultas</a> |
        <a href="<c:url value='/'/>">Home</a>
    </nav>
    <hr/>

    <%-- Search Forms --%>
    <div class="search-form">
        <form action="<c:url value='/pacientes'/>" method="get" style="display: inline-block; margin-right: 20px;">
            <label for="searchNome">Buscar por Nome:</label>
            <input type="text" id="searchNome" name="searchNome" value="${searchType == 'nome' ? searchTerm : ''}">
            <button type="submit">Buscar</button>
        </form>

        <form action="<c:url value='/pacientes'/>" method="get" style="display: inline-block;">
            <label for="searchTelefone">Buscar por Telefone:</label>
            <input type="text" id="searchTelefone" name="searchTelefone" value="${searchType == 'telefone' ? searchTerm : ''}">
            <button type="submit">Buscar</button>
        </form>
        <c:if test="${not empty searchTerm}">
             <a href="<c:url value='/pacientes'/>" style="margin-left: 15px;">Limpar Busca</a>
        </c:if>
    </div>


    <div class="add-button">
        <a href="<c:url value='/pacientes/novo'/>">Adicionar Novo Paciente</a>
    </div>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Telefone</th>
                <th>Nº Beneficiário</th>
                <th>Endereço</th>
                <th>Ações</th>
            </tr>
        </thead>
        <tbody>
            <c:choose>
                <c:when test="${not empty pacientes}">
                    <c:forEach items="${pacientes}" var="paciente">
                        <tr>
                            <td>${paciente.id}</td>
                            <td><c:out value="${paciente.nome}"/></td>
                            <td><c:out value="${paciente.telefone}"/></td>
                            <td><c:out value="${paciente.numeroBeneficiario}"/></td>
                            <td>
                                <c:out value="${paciente.endereco.rua}, ${paciente.endereco.numero} - CEP: ${paciente.endereco.cep} ${not empty paciente.endereco.complemento ? '- '.concat(paciente.endereco.complemento) : ''}"/>
                            </td>
                            <td class="actions">
                                <a href="<c:url value='/pacientes/editar/${paciente.id}'/>">Editar</a>
                                <a href="<c:url value='/pacientes/excluir/${paciente.id}'/>" onclick="return confirm('Tem certeza que deseja excluir este paciente?');">Excluir</a>
                            </td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td colspan="6">Nenhum paciente encontrado.</td>
                    </tr>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>

</body>
</html>