<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<script type="text/javascript">
	clearReturnUrl();
	pushReturnUrl("/manager/project/show?id=${param.projectId}");
	sessionStorage.setItem(returnTopKey, getCurrentUrl());
</script>

<p><strong>${projectTitle}</strong></p>

<acme:list>
	<acme:list-column code="manager.project-member.list.label.username" path="username" width="25%"/>
	<acme:list-column code="manager.project-member.list.label.name" path="name" width="25%"/>
	<acme:list-column code="manager.project-member.list.label.surname" path="surname" width="25%"/>
	<acme:list-column code="manager.project-member.list.label.email" path="email" width="25%"/>
	<acme:list-hidden path="projectId"/>
</acme:list>

<jstl:if test="${draftMode == true}">
	<acme:button code="manager.project-member.list.button.add" action="/manager/user-account/create?projectId=${projectId}"/>
</jstl:if>
