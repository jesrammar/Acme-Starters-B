<%@page%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form readonly="true">
    <acme:form-textbox code="any.inventor.form.label.bio" path="bio"/>
    <acme:form-textarea code="any.inventor.form.label.keyWords" path="keyWords"/>
    <acme:form-checkbox code="any.inventor.form.label.licensed" path="licensed"/>
</acme:form>