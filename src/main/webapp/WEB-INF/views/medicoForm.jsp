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
        .form-group input[type=text], .form-group input[type=tel], .form-group select { width: 300px; padding: 5px; }
        .form-group .address-field { display: inline-block; margin-right: 10px; }
        .error { color: red; font-size: 0.9em; }
        button { padding: 8px 15px; cursor: pointer; }
         .back-link { margin-top: 1em; display: inline-block;}
    </style>
</head>
<body>

    <h1>${pageTitle}</h1>

    <form:form action="${formAction}" method="post" modelAttribute="medico">

        <%-- Hidden field for ID (codigo) needed for updates --%>
        <form:hidden path="codigo"/>

        <div class="form-group">
            <form:label path="nome">Nome Completo:</form:label>
            <form:input path="nome" required="true"/>
            <form:errors path="nome" cssClass="error"/>
        </div>

         <div class="form-group">
            <form:label path="contato">Contato (Telefone/Celular):</form:label>
            <form:input path="contato" type="tel"/>
            <form:errors path="contato" cssClass="error"/>
        </div>

        <div class="form-group">
            <form:label path="especialidade.id">Especialidade:</form:label> <%-- Bind to ID --%>
            <form:select path="especialidade.id" required="true">
                <form:option value="" label="-- Selecione --"/>
                <form:options items="${especialidades}" itemValue="id" itemLabel="nome"/>
            </form:select>
            <form:errors path="especialidade" cssClass="error"/> <%-- Check errors for the whole object or path="especialidade.id" --%>
        </div>


        <fieldset>
            <legend>Endereço</legend>
             <div class="form-group">
                <form:label path="endereco.rua">Rua:</form:label>
                <form:input path="endereco.rua" required="true"/>
                <form:errors path="endereco.rua" cssClass="error"/>
            </div>
             <div class="form-group address-field">
                <form:label path="endereco.numero">Número:</form:label>
                <form:input path="endereco.numero" required="true" size="10"/>
                <form:errors path="endereco.numero" cssClass="error"/>
            </div>
             <div class="form-group address-field">
                <form:label path="endereco.cep">CEP (XXXXX-XXX):</form:label>
                <form:input path="endereco.cep" required="true" size="10"/>
                <form:errors path="endereco.cep" cssClass="error"/>
            </div>
             <div class="form-group">
                <form:label path="endereco.complemento">Complemento:</form:label>
                <form:input path="endereco.complemento"/>
                <form:errors path="endereco.complemento" cssClass="error"/>
            </div>
        </fieldset>

        <button type="submit">Salvar</button>
        <a href="<c:url value='/medicos'/>" class="back-link">Cancelar</a>

    </form:form>

</body>
</html>