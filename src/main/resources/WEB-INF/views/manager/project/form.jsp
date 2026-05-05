<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<jstl:set var="readonly" value="${_command == 'show' || _command == 'delete' || _command == 'publish'}"/>

<acme:form readonly="${readonly}">
	<acme:form-textbox code="manager.project.form.label.title" path="title"/>
	<acme:form-textbox code="manager.project.form.label.keywords" path="keywords"/>
	<acme:form-textarea code="manager.project.form.label.description" path="description"/>
	<acme:form-moment code="manager.project.form.label.kickOffMoment" path="kickOffMoment"/>
	<acme:form-moment code="manager.project.form.label.closeOutMoment" path="closeOutMoment"/>
	<acme:form-textarea code="manager.project.form.label.memberUsernames" path="memberUsernames"/>

	<jstl:if test="${_command == 'show'}">
		<acme:form-checkbox code="manager.project.form.label.draftMode" path="draftMode"/>
		<acme:form-textbox code="manager.project.form.label.membersCount" path="membersCount" readonly="true"/>
		<acme:form-textbox code="manager.project.form.label.campaignsCount" path="campaignsCount" readonly="true"/>
		<acme:button code="manager.project.form.button.campaigns" action="/authenticated/campaign/list?projectId=${id}"/>
	</jstl:if>

	<jstl:if test="${_command == 'create'}">
		<acme:submit code="manager.project.form.button.create" action="/manager/project/create"/>
	</jstl:if>
	<jstl:if test="${_command == 'update'}">
		<acme:submit code="manager.project.form.button.update" action="/manager/project/update"/>
	</jstl:if>
	<jstl:if test="${_command == 'delete'}">
		<acme:submit code="manager.project.form.button.delete" action="/manager/project/delete?id=${id}"/>
	</jstl:if>
	<jstl:if test="${_command == 'publish'}">
		<acme:submit code="manager.project.form.button.publish-confirm" action="/manager/project/publish?id=${id}"/>
	</jstl:if>
	<jstl:if test="${_command == 'show' && draftMode == true}">
		<acme:button code="manager.project.form.button.update" action="/manager/project/update?id=${id}"/>
		<acme:button code="manager.project.form.button.delete" action="/manager/project/delete?id=${id}"/>
		<acme:button code="manager.project.form.button.publish" action="/manager/project/publish?id=${id}"/>
	</jstl:if>
</acme:form>
