
<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form readonly="true">

    <acme:form-textbox code="auditor.audit-report.form.label.ticker" path="ticker"/>
    <acme:form-textbox code="auditor.audit-report.form.label.name" path="name"/>
    <acme:form-textarea code="auditor.audit-report.form.label.description" path="description"/>
    <acme:form-moment code="auditor.audit-report.form.label.startMoment" path="startMoment"/>
    <acme:form-moment code="auditor.audit-report.form.label.endMoment" path="endMoment"/>
    <acme:form-url code="auditor.audit-report.form.label.moreInfo" path="moreInfo"/>
    <acme:button code="auditor.audit-report.form.button.sections"
                 action="/any/audit-section/list?auditReportId=${id}"/>

</acme:form>