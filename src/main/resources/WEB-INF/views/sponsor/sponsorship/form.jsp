
<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form readonly="true">

    <acme:form-textbox code="sponsor.sponsorship.form.label.ticker" path="ticker"/>
    <acme:form-textbox code="sponsor.sponsorship.form.label.name" path="name"/>
    <acme:form-textarea code="sponsor.sponsorship.form.label.description" path="description"/>
    <acme:form-moment code="sponsor.sponsorship.form.label.startMoment" path="startMoment"/>
    <acme:form-moment code="sponsor.sponsorship.form.label.endMoment" path="endMoment"/>
    <acme:form-url code="sponsor.sponsorship.form.label.moreInfo" path="moreInfo"/>
    <acme:form-checkbox code="sponsor.sponsorship.form.label.draftMode" path="draftMode"/>
    <acme:button code="sponsor.sponsorship.form.button.donations"
                 action="/sponsor/donation/list?sponsorshipId=${id}"/>

</acme:form>