<%@page%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<script type="text/javascript">
	clearReturnUrl();
	pushReturnUrl("/authenticated/milestone/list?projectId=${projectId}&campaignId=${campaignId}");
	sessionStorage.setItem(returnTopKey, getCurrentUrl());
</script>

<acme:form readonly="true">
	<acme:form-textbox code="authenticated.project-milestone.form.label.title" path="title"/>
	<acme:form-textarea code="authenticated.project-milestone.form.label.achievements" path="achievements"/>
	<acme:form-double code="authenticated.project-milestone.form.label.effort" path="effort"/>
	<acme:form-textbox code="authenticated.project-milestone.form.label.kind" path="kind"/>
</acme:form>
