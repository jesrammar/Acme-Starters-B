
<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
    <acme:list-column code="sponsor.donation.list.label.title" path="name" width="25%"/>
    <acme:list-column code="sponsor.donation.list.label.notes" path="notes" width="45%"/>
    <acme:list-column code="sponsor.donation.list.label.money" path="money" width="10%"/>
    <acme:list-column code="sponsor.donation.list.label.kind" path="kind" width="10%"/>
    <acme:list-hidden path="sponsorshipId"/>
</acme:list>

<jstl:if test="${showCreate}">
	<acme:button code="sponsor.donation.list.button.create" action="/sponsor/donation/create?sponsorshipId=${sponsorshipId}"/>
</jstl:if>