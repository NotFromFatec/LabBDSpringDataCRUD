<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
        .search-form label, .search-form select, .search-form button { margin-right: 10px; }
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

     <%-- Search Form --%>
    <div class="search-form">
        <form action="<c:url value='/medicos'/>" method="get">
            <label for="searchEspecialidade">Filtrar por Especialidade:</label>
            <select id="searchEspecialidade" name="searchEspecialidade">
                <option value="">-- Todas --</option>
                <c:forEach items="${especialidades}" var="esp">
                    <option value="${esp.nome}" ${esp.nome == searchTerm ? 'selected' : ''}>
                        <c:out value="${esp.nome}"/>
                    </option>
                </c:forEach>
            </select>
            <button type="submit">Filtrar</button>
            <c:if test="${not empty searchTerm}">
                 <a href="<c:url value='/medicos'/>" style="margin-left: 15px;">Limpar Filtro</a>
            </c:if>
        </form>
    </div>

     <div class="add-button">
        <a href="<c:url value='/medicos/novo'/>">Adicionar Novo Médico</a>
    </div>

    <table>
        <thead>
            <tr>
                <th>Código</th>
                <th>Nome</th>
                <th>Contato</th>
                <th>Especialidade</th>
                <th>Endereço</th>
                <th>Ações</th>
            </tr>
        </thead>
        <tbody>
            <c:choose>
                <c:when test="${not empty medicos}">
                    <c:forEach items="${medicos}" var="medico">
                        <tr>
                            <td>${medico.codigo}</td>
                            <td><c:out value="${medico.nome}"/></td>
                            <td><c:out value="${medico.contato}"/></td>
                            <td><c:out value="${medico.especialidade.nome}"/></td>
                            <td>
                                 <c:out value="${medico.endereco.rua}, ${medico.endereco.numero} - CEP: ${medico.endereco.cep} ${not empty medico.endereco.complemento ? '- '.concat(medico.endereco.complemento) : ''}"/>
                            </td>
                            <td class="actions">
                                <a href="<c:url value='/medicos/editar/${medico.codigo}'/>">Editar</a>
                                <a href="<c:url value='/medicos/excluir/${medico.codigo}'/>" onclick="return confirm('Tem certeza que deseja excluir este médico?');">Excluir</a>
                            </td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td colspan="6">Nenhum médico encontrado.</td>
                    </tr>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>

</body>
</html>