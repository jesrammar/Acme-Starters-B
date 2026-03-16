<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form readonly="true">
    <acme:form-textbox code="any.donation.label.name" path="name"/>
    <acme:form-textarea code="any.donation.label.notes" path="notes"/>
    <acme:form-double code="any.donation.label.money" path="hours"/>
    <acme:form-textbox code="any.donation.label.kind" path="kind"/>
</acme:form>