
<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form readonly="true">

    <acme:form-textbox code="fundraiser.strategy.form.label.ticker" path="ticker"/>
    <acme:form-textbox code="fundraiser.strategy.form.label.name" path="name"/>
    <acme:form-textarea code="fundraiser.strategy.form.label.description" path="description"/>
    <acme:form-moment code="fundraiser.strategy.form.label.startMoment" path="startMoment"/>
    <acme:form-moment code="fundraiser.strategy.form.label.endMoment" path="endMoment"/>
    <acme:form-url code="fundraiser.strategy.form.label.moreInfo" path="moreInfo"/>
    <acme:form-checkbox code="fundraiser.strategy.form.label.draftMode" path="draftMode"/>
    <acme:button code="fundraiser.strategy.fom.label.tactics"
                 action="/fundraiser/tactic/list?strategyId=${id}"/>

</acme:form>