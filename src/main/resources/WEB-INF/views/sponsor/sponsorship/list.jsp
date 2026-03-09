
<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
    <acme:list-column code="sponsor.sponsorship.list.label.ticker" path="ticker" width="15%"/>
    <acme:list-column code="sponsor.sponsorship.list.label.name" path="name" width="25%"/>
    <acme:list-column code="sponsor.sponsorship.list.label.draftMode" path="draftMode" width="10%"/>
    <acme:list-column code="sponsor.sponsorship.list.label.startMoment" path="startMoment" width="15%"/>
    <acme:list-column code="sponsor.sponsorship.list.label.endMoment" path="endMoment" width="15%"/>
    <acme:list-column code="sponsor.sponsorship.list.label.donationCount" path="donationCount" width="10%"/>
    <acme:list-column code="sponsor.sponsorship.list.label.money" path="money" width="10%"/>
    <acme:list-hidden path="description"/>
    <acme:list-hidden path="moreInfo"/>
</acme:list>