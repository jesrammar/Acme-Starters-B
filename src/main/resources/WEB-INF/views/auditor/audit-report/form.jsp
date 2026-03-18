<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form> 
	<jstl:choose>
		<jstl:when test="${!draftMode && _command != 'create'}">
			<acme:form-textbox code="auditor.audit-report.label.ticker" path="ticker" readonly="true"/>
			<acme:form-textbox code="auditor.audit-report.label.name" path="name" readonly="true"/>
			<acme:form-moment code="auditor.audit-report.label.startMoment" path="startMoment" readonly="true"/>
			<acme:form-moment code="auditor.audit-report.label.endMoment" path="endMoment" readonly="true"/>
			<acme:form-url code="auditor.audit-report.label.moreInfo" path="moreInfo" readonly="true"/>
			<acme:form-textarea code="auditor.audit-report.label.description" path="description" readonly="true"/>
		</jstl:when>
		<jstl:otherwise>
			<acme:form-textbox code="auditor.audit-report.label.ticker" path="ticker"/>
			<acme:form-textbox code="auditor.audit-report.label.name" path="name"/>
			<acme:form-moment code="auditor.audit-report.label.startMoment" path="startMoment"/>
			<acme:form-moment code="auditor.audit-report.label.endMoment" path="endMoment"/>
			<acme:form-url code="auditor.audit-report.label.moreInfo" path="moreInfo"/>
			<acme:form-textarea code="auditor.audit-report.label.description" path="description"/>
		</jstl:otherwise>
	</jstl:choose>

	<jstl:choose>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="auditor.audit-report.button.create" action="/auditor/audit-report/create"/>
		</jstl:when>
		<jstl:when test="${_command == 'show' || _command == 'update' || _command == 'publish' || _command == 'delete' }">
			<jstl:if test="${draftMode}">
				<acme:submit code="auditor.audit-report.button.update" action="/auditor/audit-report/update"/>
				<acme:submit code="auditor.audit-report.button.delete" action="/auditor/audit-report/delete"/>
				<acme:submit code="auditor.audit-report.button.publish" action="/auditor/audit-report/publish"/>
			</jstl:if>
			<acme:button code="auditor.audit-report.button.sections" action="/auditor/audit-section/list?reportId=${id}"/>			
		</jstl:when>
	</jstl:choose>
</acme:form>