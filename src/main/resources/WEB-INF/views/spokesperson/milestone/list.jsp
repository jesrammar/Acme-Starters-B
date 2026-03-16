<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<script type="text/javascript">
	clearReturnUrl();
	pushReturnUrl("/spokesperson/campaign/show?id=${param.campaignId}");
	sessionStorage.setItem(returnTopKey, getCurrentUrl());
</script>

<acme:list>
	<acme:list-column code="spokesperson.milestone.list.label.title" path="title" width="25%"/>
	<acme:list-column code="spokesperson.milestone.list.label.achievements" path="achievements" width="45%"/>
	<acme:list-column code="spokesperson.milestone.list.label.effort" path="effort" width="10%"/>
	<acme:list-column code="spokesperson.milestone.list.label.kind" path="kind" width="10%"/>
	<acme:list-hidden path="campaignId"/>
</acme:list>
