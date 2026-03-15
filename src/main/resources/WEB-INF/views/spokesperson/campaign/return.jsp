<%@page%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Returning</title>
</head>
<body>
	<script type="text/javascript">
		window.location.replace("<%= request.getContextPath() %>/spokesperson/campaign/list");
	</script>
</body>
</html>
