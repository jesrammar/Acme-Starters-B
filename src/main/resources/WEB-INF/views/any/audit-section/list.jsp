
<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list readonly="true">

    <acme:list-column code="auditor.audit-section.label.title" path="name" width="25%"/>
    <acme:list-column code="auditor.audit-section.label.notes" path="notes" width="45%"/>
    <acme:list-column code="auditor.audit-section.label.hours" path="hours" width="10%"/>
    <acme:list-column code="auditor.audit-section.label.kind" path="kind" width="10%"/>
    <acme:list-hidden path="auditReportId"/>

</acme:list>