
<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<jstl:choose>
		<%-- Si está publicado y no estas creando no puedes editar:--%>
		<jstl:when test="${!draftMode && _command != 'create'}">
			<acme:form-textbox code="fundraiser.strategy.form.label.ticker" path="ticker" readonly="true" />
			<acme:form-textbox code="fundraiser.strategy.form.label.name" path="name" readonly="true" />
			<acme:form-textarea code="fundraiser.strategy.form.label.description" path="description" readonly="true" />
			<acme:form-moment code="fundraiser.strategy.form.label.startMoment" path="startMoment" readonly="true" />
			<acme:form-moment code="fundraiser.strategy.form.label.endMoment" path="endMoment" readonly="true" />
			<acme:form-url code="fundraiser.strategy.form.label.moreInfo" path="moreInfo" readonly="true" />
		</jstl:when>
		<%-- Si esta en borrador o lo estas creando SI puedes editar:--%>
		<jstl:otherwise>
		<acme:form-textbox code="fundraiser.strategy.form.label.ticker"	path="ticker" />
		<acme:form-textbox code="fundraiser.strategy.form.label.name" path="name" />
		<acme:form-textarea code="fundraiser.strategy.form.label.description" path="description" />
		<acme:form-moment code="fundraiser.strategy.form.label.startMoment" path="startMoment" />
		<acme:form-moment code="fundraiser.strategy.form.label.endMoment" path="endMoment" />
		<acme:form-url code="fundraiser.strategy.form.label.moreInfo" path="moreInfo" />
		</jstl:otherwise>
	</jstl:choose>

	<jstl:choose>
		<jstl:when test="${_command == 'create'}">
		<acme:submit code="fundraiser.strategy.form.button.create" action="/fundraiser/strategy/create"/>
		</jstl:when>
			<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish')}">			
			<jstl:if test="${draftMode}">
				<acme:submit code="fundraiser.strategy.form.button.update" action="/fundraiser/strategy/update"/>
				<acme:submit code="fundraiser.strategy.form.button.delete" action="/fundraiser/strategy/delete"/>
				<acme:submit code="fundraiser.strategy.form.button.publish" action="/fundraiser/strategy/publish"/>
			</jstl:if>
			
			<acme:button code="fundraiser.strategy.form.button.tactics" action="/fundraiser/tactic/list?strategyId=${id}" />
		</jstl:when>
	</jstl:choose>	
	

</acme:form>