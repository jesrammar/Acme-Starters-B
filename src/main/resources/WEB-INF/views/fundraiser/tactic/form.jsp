<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
    <jstl:choose>
        <%-- Si la estrategia NO está en borrador no se puede editar --%>
        <jstl:when test="${!strategyDraftMode && _command != 'create'}">
            <acme:form-textbox code="fundraiser.tactic.form.label.name" path="name" readonly="true"/>
            <acme:form-textarea code="fundraiser.tactic.form.label.notes" path="notes" readonly="true"/>
            <acme:form-double code="fundraiser.tactic.form.label.expectedPercentage" path="expectedPercentage" readonly="true"/>
            <acme:form-select code="fundraiser.tactic.form.label.kind" path="kind" choices="${kind}" readonly="true"/>
        </jstl:when>
        <%-- Si está en borrador o estás creando SI puedes editar --%>
        <jstl:otherwise>
            <acme:form-textbox code="fundraiser.tactic.form.label.name" path="name"/>
            <acme:form-textarea code="fundraiser.tactic.form.label.notes" path="notes"/>
            <acme:form-double code="fundraiser.tactic.form.label.expectedPercentage" path="expectedPercentage"/>
            <acme:form-select code="fundraiser.tactic.form.label.kind" path="kind" choices="${kind}"/>
        </jstl:otherwise>
    </jstl:choose>
    <jstl:choose>
        <jstl:when test="${acme:anyOf(_command, 'show|update|delete') && strategyDraftMode}">
            <acme:submit code="fundraiser.tactic.form.button.update" action="/fundraiser/tactic/update"/>
            <acme:submit code="fundraiser.tactic.form.button.delete" action="/fundraiser/tactic/delete"/>
        </jstl:when>
        <jstl:when test="${_command == 'create'}">
            <acme:submit code="fundraiser.tactic.form.button.create" action="/fundraiser/tactic/create?strategyId=${strategyId}"/>
        </jstl:when>
    </jstl:choose>
</acme:form>