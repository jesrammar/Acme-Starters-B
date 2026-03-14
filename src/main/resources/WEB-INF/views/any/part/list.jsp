<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.part.list.label.name" path="name" width="35%"/>
	<acme:list-column code="any.part.list.label.description" path="description" width="35%"/>
	<acme:list-column code="any.part.list.label.cost" path="cost" width="15%"/>
	<acme:list-column code="any.part.list.label.kind" path="kind" width="15%"/>
</acme:list>