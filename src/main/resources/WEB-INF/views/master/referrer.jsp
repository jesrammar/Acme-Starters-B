<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
	<jstl:choose>
		<jstl:when test="${not empty return_url}">
			redirect("${return_url}");
		</jstl:when>
		<jstl:otherwise>
			redirect("##");
		</jstl:otherwise>
	</jstl:choose>
</script>
