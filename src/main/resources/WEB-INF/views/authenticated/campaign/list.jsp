<%@page%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<script type="text/javascript">
	clearReturnUrl();
	pushReturnUrl("/authenticated/project/show?id=${param.projectId}");
	sessionStorage.setItem(returnTopKey, getCurrentUrl());
</script>

<acme:list>
	<acme:list-column code="authenticated.campaign.list.label.ticker" path="ticker" width="15%"/>
	<acme:list-column code="authenticated.campaign.list.label.name" path="name" width="25%"/>
	<acme:list-column code="authenticated.campaign.list.label.startMoment" path="startMoment" width="15%"/>
	<acme:list-column code="authenticated.campaign.list.label.endMoment" path="endMoment" width="15%"/>
	<acme:list-column code="authenticated.campaign.list.label.draftMode" path="draftModeVisual" width="10%"/>
	<acme:list-column code="authenticated.campaign.list.label.monthsActive" path="monthsActive" width="10%"/>
	<acme:list-column code="authenticated.campaign.list.label.effort" path="effort" width="10%"/>
	<acme:list-hidden path="projectId"/>
</acme:list>
