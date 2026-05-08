<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<jstl:if test="${_command == 'show'}">
	<script type="text/javascript">
		clearReturnUrl();
		pushReturnUrl("/manager/user-account/list?projectId=${projectId}");
		sessionStorage.setItem(returnTopKey, getCurrentUrl());
	</script>

	<p><strong>${projectTitle}</strong></p>
	<acme:form readonly="true">
		<acme:form-textbox code="manager.project-member.form.label.username" path="username"/>
		<acme:form-textbox code="manager.project-member.form.label.name" path="name"/>
		<acme:form-textbox code="manager.project-member.form.label.surname" path="surname"/>
		<acme:form-email code="manager.project-member.form.label.email" path="email"/>

		<jstl:if test="${draftMode == true && isManagerAccount != true}">
			<acme:button code="manager.project-member.form.button.remove" action="/manager/user-account/delete?projectId=${projectId}&memberId=${id}"/>
		</jstl:if>
	</acme:form>
</jstl:if>

<jstl:if test="${_command == 'create'}">
	<script type="text/javascript">
		clearReturnUrl();
		pushReturnUrl("/manager/user-account/list?projectId=${projectId}");
		sessionStorage.setItem(returnTopKey, getCurrentUrl());
	</script>

	<p><strong>${projectTitle}</strong></p>
	<acme:form>
		<acme:form-textbox code="manager.project-member.form.label.memberUsername" path="username"/>
		<acme:submit code="manager.project-member.form.button.add-confirm" action="/manager/user-account/create?projectId=${projectId}"/>
	</acme:form>
</jstl:if>

<jstl:if test="${_command == 'delete'}">
	<script type="text/javascript">
		clearReturnUrl();
		pushReturnUrl("/manager/user-account/list?projectId=${projectId}");
		sessionStorage.setItem(returnTopKey, getCurrentUrl());
	</script>

	<p><strong>${projectTitle}</strong></p>
	<acme:form readonly="true">
		<acme:form-textbox code="manager.project-member.form.label.memberUsername" path="username"/>
		<acme:submit code="manager.project-member.form.button.remove-confirm" action="/manager/user-account/delete?projectId=${projectId}&memberId=${param.memberId}"/>
	</acme:form>
</jstl:if>
