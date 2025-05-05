<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${pageTitle}</title>
     <style>
        body { font-family: sans-serif; }
        .form-group { margin-bottom: 1em; }
        .form-group label { display: block; margin-bottom: 0.3em; }
        .form-group input[type=datetime-local], .form-group select { width: 300px; padding: 5px; }
        .error { color: red; font-size: 0.9em; }
        button { padding: 8px 15px; cursor: pointer; }
        .back-link { margin-top: 1em; display: inline-block;}
        nav a { margin-right: 10px; }
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

    <form:form action="${formAction}" method="post" modelAttribute="consulta">

        <form:hidden path="id"/>

        <div class="form-group">
            <form:label path="paciente.id">Paciente:</form:label>
            <form:select path="paciente.id" required="true">
                <form:option value="" label="-- Selecione um Paciente --"/>
                <form:options items="${pacientes}" itemValue="id" itemLabel="nome"/>
            </form:select>
            <form:errors path="paciente" cssClass="error"/>
        </div>

        <div class="form-group">
            <form:label path="medico.codigo">Médico:</form:label>
             <form:select path="medico.codigo" required="true">
                <form:option value="" label="-- Selecione um Médico --"/>
                 <c:forEach items="${medicos}" var="med"> <%-- Manual loop for better label --%>
                    <form:option value="${med.codigo}" label="${med.nome} (${med.especialidade.nome})"/>
                 </c:forEach>
            </form:select>
            <form:errors path="medico" cssClass="error"/>
        </div>

        <div class="form-group">
            <form:label path="dataHora">Data e Hora da Consulta:</form:label>
            <%-- Input type datetime-local expects 'yyyy-MM-ddTHH:mm' --%>
            <form:input path="dataHora" type="datetime-local" required="true"/>
            <form:errors path="dataHora" cssClass="error"/>
        </div>

        <button type="submit">Salvar</button>
        <a href="<c:url value='/consultas'/>" class="back-link">Cancelar</a>

    </form:form>

</body>
</html>