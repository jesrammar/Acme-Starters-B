<%@page%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form readonly="true">
	<acme:form-textbox code="authenticated.project.form.label.title" path="title"/>
	<acme:form-textbox code="authenticated.project.form.label.keywords" path="keywords"/>
	<acme:form-textarea code="authenticated.project.form.label.description" path="description"/>
	<acme:form-moment code="authenticated.project.form.label.kickOffMoment" path="kickOffMoment"/>
	<acme:form-moment code="authenticated.project.form.label.closeOutMoment" path="closeOutMoment"/>
	<acme:form-checkbox code="authenticated.project.form.label.draftMode" path="draftMode"/>
	<acme:form-textarea code="authenticated.project.form.label.memberUsernames" path="memberUsernames"/>
	<acme:form-textbox code="authenticated.project.form.label.campaignsCount" path="campaignsCount" readonly="true"/>
	<acme:button code="authenticated.project.form.button.campaigns" action="/authenticated/campaign/list?projectId=${id}"/>
</acme:form>
