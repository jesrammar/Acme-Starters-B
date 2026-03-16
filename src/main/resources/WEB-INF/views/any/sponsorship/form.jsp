<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="any.sponsorship.form.label.ticker" path="ticker"/>
	<acme:form-textbox code="any.sponsorship.form.label.name" path="name"/>
	<acme:form-textarea code="any.sponsorship.form.label.description" path="description"/>
	<acme:form-moment code="any.sponsorship.form.label.startMoment" path="startMoment"/>
	<acme:form-moment code="any.sponsorship.form.label.endMoment" path="endMoment"/>
	<acme:form-double code="any.sponsorship.form.label.activeMonths" path="activeMonths"/>
	<acme:form-money code="any.sponsorship.form.label.money" path="money"/>
	<acme:form-integer code="any.sponsorship.form.label.donationCount" path="donationCount"/>
	<acme:form-url code="any.sponsorship.form.label.moreInfo" path="moreInfo"/>	
	
	<acme:button code="any.sponsorship.button.donations" action="/any/donation/list?sponsorshipId=${id}"/>
	<acme:button code="any.sponsorship.button.sponsor-profile" action="/any/sponsor/show?id=${sponsorId}"/>
</acme:form>