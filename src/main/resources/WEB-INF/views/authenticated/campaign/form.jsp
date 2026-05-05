<%@page%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<script type="text/javascript">
	clearReturnUrl();
	pushReturnUrl("/authenticated/campaign/list?projectId=${projectId}");
	sessionStorage.setItem(returnTopKey, getCurrentUrl());
</script>

<acme:form readonly="true">
	<acme:form-textbox code="authenticated.campaign.form.label.ticker" path="ticker"/>
	<acme:form-textbox code="authenticated.campaign.form.label.name" path="name"/>
	<acme:form-textarea code="authenticated.campaign.form.label.description" path="description"/>
	<acme:form-moment code="authenticated.campaign.form.label.startMoment" path="startMoment"/>
	<acme:form-moment code="authenticated.campaign.form.label.endMoment" path="endMoment"/>
	<acme:form-url code="authenticated.campaign.form.label.moreInfo" path="moreInfo"/>
	<acme:form-checkbox code="authenticated.campaign.form.label.draftMode" path="draftMode"/>
	<acme:form-double code="authenticated.campaign.form.label.monthsActive" path="monthsActive"/>
	<acme:form-double code="authenticated.campaign.form.label.effort" path="effort"/>
	<acme:button code="authenticated.campaign.form.button.milestones" action="/authenticated/milestone/list?projectId=${projectId}&campaignId=${id}"/>
</acme:form>
