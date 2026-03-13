<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
    <acme:list-column code="auditor.audit-report.label.ticker" path="ticker" width="15%"/>
    <acme:list-column code="auditor.audit-report.label.name" path="name" width="25%"/>
    <acme:list-column code="auditor.audit-report.label.draftMode" path="draftMode" width="10%"/>
    <acme:list-column code="auditor.audit-report.label.startMoment" path="startMoment" width="15%"/>
    <acme:list-column code="auditor.audit-report.label.endMoment" path="endMoment" width="15%"/>
    <acme:list-column code="auditor.audit-report.label.sectionCount" path="sectionCount" width="10%"/>
    <acme:list-column code="auditor.audit-report.label.hours" path="hours" width="10%"/>

    <acme:list-hidden path="description"/>
    <acme:list-hidden path="moreInfo"/>
</acme:list>

<acme:button code="auditor.audit-report.button.create" action="/auditor/audit-report/create"/>