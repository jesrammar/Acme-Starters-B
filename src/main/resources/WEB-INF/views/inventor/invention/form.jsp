
<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<jstl:choose>
		<jstl:when test="${ !draftMode && _comand != 'create' }">
			<acme:form-textbox code="inventor.invention.form.label.ticker" path="ticker" readonly="true"/>
			<acme:form-textbox code="inventor.invention.form.label.name" path="name" readonly="true"/>
			<acme:form-textarea code="inventor.invention.form.label.description" path="description" readonly="true"/>
			<acme:form-moment code="inventor.invention.form.label.startMoment" path="startMoment" readonly="true"/>
			<acme:form-moment code="inventor.invention.form.label.endMoment" path="endMoment" readonly="true"/>
			<acme:form-url code="inventor.invention.form.label.moreInfo" path="moreInfo" readonly="true"/>
			<acme:form-checkbox code="inventor.invention.form.label.draftMode" path="draftMode" readonly="true"/>
			<acme:form-double code="inventor.invention.form.label.monthsActive" path="monthsActive" readonly="true"/>
			<acme:form-money code="inventor.invention.form.label.cost" path="cost" readonly="true"/>
		</jstl:when>
		<jstl:otherwise>
			<acme:form-textbox code="inventor.invention.form.label.ticker" path="ticker"/>
			<acme:form-textbox code="inventor.invention.form.label.name" path="name"/>
			<acme:form-textarea code="inventor.invention.form.label.description" path="description"/>
			<acme:form-moment code="inventor.invention.form.label.startMoment" path="startMoment"/>
			<acme:form-moment code="inventor.invention.form.label.endMoment" path="endMoment"/>
			<acme:form-url code="inventor.invention.form.label.moreInfo" path="moreInfo"/>
			<acme:form-checkbox code="inventor.invention.form.label.draftMode" path="draftMode"/>		
		</jstl:otherwise>
	</jstl:choose>
	
	<jstl:choose>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="inventor.invention.button.create" action="/inventor/invention/create"/>
		</jstl:when>
		<jstl:when test="${ _command == 'show' || _command == 'update' }">
			<jstl:if test="${draftMode}">
				<acme:submit code="inventor.invention.button.update" action="/inventor/invention/update"/>
				<acme:submit code="inventor.invention.button.delete" action="/inventor/invention/delete"/>
			</jstl:if>
			
			<acme:button code="inventor.invention.button.parts" action="/inventor/part/list?inventionId=${id}"/>
		</jstl:when>
	</jstl:choose>
</acme:form>