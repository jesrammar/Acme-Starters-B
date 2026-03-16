
<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="inventor.invention.list.label.ticker" path="ticker"/>
	<acme:list-column code="inventor.invention.list.label.name" path="name"/>
	<acme:list-column code="inventor.invention.list.label.description" path="description"/>
	<acme:list-column code="inventor.invention.list.label.startMoment" path="startMoment"/>
	<acme:list-column code="inventor.invention.list.label.endMoment" path="endMoment"/>
	<acme:list-column code="inventor.invention.list.label.moreInfo" path="moreInfo"/>
	<acme:list-column code="inventor.invention.list.label.draftMode" path="draftMode"/>
	<acme:list-column code="inventor.invention.list.label.monthsActive" path="monthsActive"/>
	<acme:list-column code="inventor.invention.list.label.cost" path="cost"/>
</acme:list>

<acme:button code="inventor.invention.form.button.create" action="/inventor/invention/create"/>