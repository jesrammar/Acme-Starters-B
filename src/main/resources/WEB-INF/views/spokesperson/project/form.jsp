<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<jstl:if test="${_command == 'create'}">
	<script type="text/javascript">
		clearReturnUrl();
		pushReturnUrl("/authenticated/project/show?id=${projectId}");
		sessionStorage.setItem(returnTopKey, getCurrentUrl());
	</script>
	<p><strong>${projectTitle}</strong></p>
	<acme:form>
		<acme:form-textbox code="spokesperson.project.form.label.campaignTicker" path="campaignTicker"/>
		<acme:submit code="spokesperson.project.form.button.attach" action="/spokesperson/project/create?projectId=${projectId}"/>
	</acme:form>
</jstl:if>

<jstl:if test="${_command == 'delete'}">
	<script type="text/javascript">
		clearReturnUrl();
		pushReturnUrl("/authenticated/campaign/show?projectId=${projectId}&id=${param.campaignId}");
		sessionStorage.setItem(returnTopKey, getCurrentUrl());
	</script>
	<p><strong>${projectTitle}</strong></p>
	<acme:form readonly="true">
		<acme:form-textbox code="spokesperson.project.form.label.campaignTicker" path="campaignTicker"/>
		<acme:submit code="spokesperson.project.form.button.detach-confirm" action="/spokesperson/project/delete?projectId=${projectId}&campaignId=${param.campaignId}"/>
	</acme:form>
</jstl:if>
