<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.invention.list.label.ticker" path="ticker"/>
	<acme:list-column code="any.invention.list.label.name" path="name"/>
	<acme:list-column code="any.invention.list.label.startMoment" path="startMoment"/>
	<acme:list-column code="any.invention.list.label.endMoment" path="endMoment"/>
	<acme:list-column code="any.invention.list.label.cost" path="cost"/>
	<acme:list-column code="any.invention.list.label.partsCount" path="partsCount"/>
	<acme:list-hidden path="monthsActive"/>
	<acme:list-hidden path="description"/>
	<acme:list-hidden path="moreInfo"/>
</acme:list>