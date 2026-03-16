<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<jstl:set var="readonly" value="${_command == 'show' || _command == 'delete'}"/>

<jstl:if test="${_command == 'create'}">
	<script type="text/javascript">
		clearReturnUrl();
		pushReturnUrl("/spokesperson/milestone/list?campaignId=${campaignId}");
		sessionStorage.setItem(returnTopKey, getCurrentUrl());
	</script>
</jstl:if>

<jstl:if test="${_command == 'update'}">
	<script type="text/javascript">
		clearReturnUrl();
		pushReturnUrl("/spokesperson/milestone/show?id=${id}");
		sessionStorage.setItem(returnTopKey, getCurrentUrl());
	</script>
</jstl:if>

<jstl:if test="${_command == 'delete'}">
	<script type="text/javascript">
		clearReturnUrl();
		pushReturnUrl("/spokesperson/milestone/list?campaignId=${campaignId}");
		sessionStorage.setItem(returnTopKey, getCurrentUrl());
	</script>
</jstl:if>

<acme:form readonly="${readonly}">
	<acme:form-textbox code="spokesperson.milestone.form.label.title" path="title"/>
	<acme:form-textarea code="spokesperson.milestone.form.label.achievements" path="achievements"/>
	<acme:form-double code="spokesperson.milestone.form.label.effort" path="effort"/>
	<acme:form-select code="spokesperson.milestone.form.label.kind" path="kind" choices="${kinds}"/>

	<jstl:if test="${_command == 'create'}">
		<acme:submit code="spokesperson.milestone.form.button.create" action="/spokesperson/milestone/create?campaignId=${campaignId}"/>
	</jstl:if>
	<jstl:if test="${_command == 'update'}">
		<acme:submit code="spokesperson.milestone.form.button.update" action="/spokesperson/milestone/update"/>
	</jstl:if>
	<jstl:if test="${_command == 'delete'}">
		<acme:submit code="spokesperson.milestone.form.button.delete" action="/spokesperson/milestone/delete?id=${param.id}"/>
	</jstl:if>

	<jstl:if test="${_command == 'show' && draftMode == true}">
		<acme:button code="spokesperson.milestone.form.button.update" action="/spokesperson/milestone/update?id=${id}"/>
		<acme:button code="spokesperson.milestone.form.button.delete" action="/spokesperson/milestone/delete?id=${id}"/>
	</jstl:if>
</acme:form>
