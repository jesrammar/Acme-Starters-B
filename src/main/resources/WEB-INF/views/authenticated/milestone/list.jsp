<%@page%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<script type="text/javascript">
	clearReturnUrl();
	pushReturnUrl("/authenticated/campaign/show?projectId=${param.projectId}&id=${param.campaignId}");
	sessionStorage.setItem(returnTopKey, getCurrentUrl());
</script>

<acme:list>
	<acme:list-column code="authenticated.milestone.list.label.title" path="title" width="25%"/>
	<acme:list-column code="authenticated.milestone.list.label.achievements" path="achievements" width="45%"/>
	<acme:list-column code="authenticated.milestone.list.label.effort" path="effort" width="15%"/>
	<acme:list-column code="authenticated.milestone.list.label.kind" path="kind" width="15%"/>
</acme:list>
