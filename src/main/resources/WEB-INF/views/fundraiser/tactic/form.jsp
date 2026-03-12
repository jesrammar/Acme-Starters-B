<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form readonly="true">
    <acme:form-textbox code="fundraiser.tactic.form.label.name" path="name"/>
    <acme:form-textarea code="fundraiser.tactic.form.label.notes" path="notes"/>
    <acme:form-double code="fundraiser.tactic.form.label.expectedPercentage" path="expectedPercentage"/>
    <acme:form-textbox code="fundraiser.tactic.form.label.kind" path="kind"/>
    
</acme:form>