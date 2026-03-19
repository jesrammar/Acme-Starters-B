<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form readonly="true">
    <acme:form-textbox code="any.audit-section.label.name" path="name"/>
    <acme:form-textarea code="any.audit-section.label.notes" path="notes"/>
    <acme:form-double code="any.audit-section.label.hours" path="hours"/>
    <acme:form-textbox code="any.audit-section.label.kind" path="kind"/>
</acme:form>