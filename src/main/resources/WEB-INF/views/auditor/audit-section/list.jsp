<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="auditor.audit-section.label.name" path="name" width="25%"/>
	<acme:list-column code="auditor.audit-section.label.kind" path="kind" width="10%"/>
	<acme:list-column code="auditor.audit-section.label.hours" path="hours" width="10%"/>
	<acme:list-column code="auditor.audit-section.label.notes" path="notes" width="40%"/>
</acme:list>

<jstl:if test="${auditReportDraftMode}">
	<acme:button code="auditor.audit-section.button.create" action="/auditor/audit-section/create?reportId=${reportId}"/>
</jstl:if>