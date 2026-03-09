
<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
    <acme:list-column code="auditor.audit-report.list.label.ticker" path="ticker" width="15%"/>
    <acme:list-column code="auditor.audit-report.list.label.name" path="name" width="25%"/>
    <acme:list-column code="auditor.audit-report.list.label.draftMode" path="draftMode" width="10%"/>
    <acme:list-column code="auditor.audit-report.list.label.startMoment" path="startMoment" width="15%"/>
    <acme:list-column code="auditor.audit-report.list.label.endMoment" path="endMoment" width="15%"/>
    <acme:list-column code="auditor.audit-report.list.label.sectionCount" path="sectionCount" width="10%"/>
    <acme:list-column code="auditor.audit-report.list.label.hours" path="hours" width="10%"/>
    <acme:list-hidden path="description"/>
    <acme:list-hidden path="moreInfo"/>
</acme:list>