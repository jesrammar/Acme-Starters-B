
<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<jstl:choose>
		<jstl:when test="${!draftMode && _command != 'create'}">
		    <acme:form-textbox code="sponsor.sponsorship.form.label.ticker" path="ticker" readonly="true"/>
		    <acme:form-textbox code="sponsor.sponsorship.form.label.name" path="name"/>
		    <acme:form-textarea code="sponsor.sponsorship.form.label.description" path="description" readonly="true"/>
		    <acme:form-moment code="sponsor.sponsorship.form.label.startMoment" path="startMoment" readonly="true"/>
		    <acme:form-moment code="sponsor.sponsorship.form.label.endMoment" path="endMoment" readonly="true"/>
		    <acme:form-url code="sponsor.sponsorship.form.label.moreInfo" path="moreInfo" readonly="true"/>
		</jstl:when>
		<jstl:otherwise>
			<acme:form-textbox code="sponsor.sponsorship.form.label.ticker" path="ticker"/>
    		<acme:form-textbox code="sponsor.sponsorship.form.label.name" path="name"/>
    		<acme:form-textarea code="sponsor.sponsorship.form.label.description" path="description"/>
    		<acme:form-moment code="sponsor.sponsorship.form.label.startMoment" path="startMoment"/>
    		<acme:form-moment code="sponsor.sponsorship.form.label.endMoment" path="endMoment"/>
    		<acme:form-url code="sponsor.sponsorship.form.label.moreInfo" path="moreInfo"/>
        </jstl:otherwise>
    </jstl:choose>
    <jstl:choose>
    	<jstl:when test="${_command == 'create'}">
			<acme:submit code="sponsor.sponsorship.form.button.create" action="/sponsor/sponsorship/create"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish')}">
			<jstl:if test="${draftMode}">
				<acme:submit code="sponsor.sponsorship.form.button.update" action="/sponsor/sponsorship/update"/>
				<acme:submit code="sponsor.sponsorship.form.button.delete" action="/sponsor/sponsorship/delete"/>
				<acme:submit code="sponsor.sponsorship.form.button.publish" action="/sponsor/sponsorship/publish"/>
			</jstl:if>
    		<acme:button code="sponsor.sponsorship.form.button.donations"
    		action="/sponsor/donation/list?sponsorshipId=${id}"/>
    	</jstl:when>
    </jstl:choose>
</acme:form>