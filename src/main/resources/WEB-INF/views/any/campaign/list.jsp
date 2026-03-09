<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.campaign.list.label.ticker" path="ticker" width="15%"/>
	<acme:list-column code="any.campaign.list.label.name" path="name" width="25%"/>
	<acme:list-column code="any.campaign.list.label.startMoment" path="startMoment" width="15%"/>
	<acme:list-column code="any.campaign.list.label.endMoment" path="endMoment" width="15%"/>
	<acme:list-column code="any.campaign.list.label.moreInfo" path="moreInfo" width="20%"/>
	<acme:list-column code="any.campaign.list.label.monthsActive" path="monthsActive" width="12%"/>
	<acme:list-column code="any.campaign.list.label.effort" path="effort" width="13%"/>
	<acme:list-hidden path="description"/>
	<acme:list-hidden path="draftMode"/>
</acme:list>
