<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<jstl:set var="readonly" value="${_command == 'show' || _command == 'delete'}"/>

<jstl:if test="${_command == 'create'}">
	<script type="text/javascript">
		clearReturnUrl();
		pushReturnUrl("/spokesperson/campaign/list");
		sessionStorage.setItem(returnTopKey, getCurrentUrl());
	</script>
</jstl:if>

<jstl:if test="${_command == 'update'}">
	<script type="text/javascript">
		clearReturnUrl();
		pushReturnUrl("/spokesperson/campaign/show?id=${id}");
		sessionStorage.setItem(returnTopKey, getCurrentUrl());
	</script>
</jstl:if>

<jstl:if test="${_command == 'delete'}">
	<script type="text/javascript">
		clearReturnUrl();
		pushReturnUrl("/spokesperson/campaign/list");
		sessionStorage.setItem(returnTopKey, getCurrentUrl());
	</script>
</jstl:if>

<acme:form readonly="${readonly}">
	<acme:form-textbox code="spokesperson.campaign.form.label.ticker" path="ticker"/>
	<acme:form-textbox code="spokesperson.campaign.form.label.name" path="name"/>
	<acme:form-textarea code="spokesperson.campaign.form.label.description" path="description"/>
	<acme:form-moment code="spokesperson.campaign.form.label.startMoment" path="startMoment"/>
	<acme:form-moment code="spokesperson.campaign.form.label.endMoment" path="endMoment"/>
	<acme:form-url code="spokesperson.campaign.form.label.moreInfo" path="moreInfo"/>
	<jstl:if test="${_command == 'show'}">
		<acme:form-checkbox code="spokesperson.campaign.form.label.draftMode" path="draftMode"/>
		<acme:form-double code="spokesperson.campaign.form.label.monthsActive" path="monthsActive"/>
		<acme:form-double code="spokesperson.campaign.form.label.effort" path="effort"/>
	</jstl:if>
	<jstl:if test="${_command == 'show'}">
		<acme:button code="spokesperson.campaign.form.button.milestones" action="/spokesperson/milestone/list?campaignId=${id}"/>
		<jstl:if test="${draftMode == true}">
			<acme:button code="spokesperson.campaign.form.button.create-milestone" action="/spokesperson/milestone/create?campaignId=${id}"/>
		</jstl:if>
	</jstl:if>

	<jstl:if test="${_command == 'create'}">
		<acme:submit code="spokesperson.campaign.form.button.create" action="/spokesperson/campaign/create"/>
	</jstl:if>
	<jstl:if test="${_command == 'update'}">
		<acme:submit code="spokesperson.campaign.form.button.update" action="/spokesperson/campaign/update"/>
	</jstl:if>
	<jstl:if test="${_command == 'publish'}">
		<acme:submit code="spokesperson.campaign.form.button.publish" action="/spokesperson/campaign/publish"/>
	</jstl:if>
	<jstl:if test="${_command == 'delete'}">
		<acme:submit code="spokesperson.campaign.form.button.delete" action="/spokesperson/campaign/delete?id=${param.id}"/>
	</jstl:if>
	<jstl:if test="${_command == 'show' && draftMode == true}">
		<acme:button code="spokesperson.campaign.form.button.update" action="/spokesperson/campaign/update?id=${id}"/>
		<acme:button code="spokesperson.campaign.form.button.delete" action="/spokesperson/campaign/delete?id=${id}"/>
		<acme:button code="spokesperson.campaign.form.button.publish" action="/spokesperson/campaign/publish?id=${id}"/>
	</jstl:if>
</acme:form>
