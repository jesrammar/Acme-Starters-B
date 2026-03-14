<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="auditor.audit-section.label.name" path="name"/>
	<acme:form-select code="auditor.audit-section.label.kind" path="kind" choices="${kind}"/>
	<acme:form-textbox code="auditor.audit-section.label.hours" path="hours"/>
	<acme:form-textarea code="auditor.audit-section.label.notes" path="notes"/>
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && auditReportDraftMode == true}">
			<acme:submit code="auditor.audit-section.button.update" action="/auditor/audit-section/update"/>
			<acme:submit code="auditor.audit-section.button.delete" action="/auditor/audit-section/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="auditor.audit-section.button.create" action="/auditor/audit-section/create?reportId=${reportId}"/>
		</jstl:when>		
	</jstl:choose>		
</acme:form>