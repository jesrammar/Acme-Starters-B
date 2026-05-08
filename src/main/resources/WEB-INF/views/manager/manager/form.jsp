<%@page%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form readonly="true">
	<acme:form-textbox code="manager.dashboard.form.label.totalProjects" path="totalProjects"/>
	<acme:form-double code="manager.dashboard.form.label.projectsDeviation" path="projectsDeviation"/>
	<acme:form-double code="manager.dashboard.form.label.effortMinimum" path="effortMinimum"/>
	<acme:form-double code="manager.dashboard.form.label.effortMaximum" path="effortMaximum"/>
	<acme:form-double code="manager.dashboard.form.label.effortAverage" path="effortAverage"/>
	<acme:form-double code="manager.dashboard.form.label.effortDeviation" path="effortDeviation"/>
</acme:form>
