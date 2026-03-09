
<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form readonly="true">

    <acme:form-textbox code="sponsor.donation.form.label.title" path="name"/>
    <acme:form-textarea code="sponsor.donation.form.label.notes" path="notes"/>
    <acme:form-double code="sponsor.donation.form.label.money" path="money"/>
    <acme:form-textbox code="sponsor.donation.form.label.kind" path="kind"/>

</acme:form>