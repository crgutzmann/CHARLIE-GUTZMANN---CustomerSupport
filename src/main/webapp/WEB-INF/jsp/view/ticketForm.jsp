<html>
<head>
    <title>Create a new Ticket</title>
</head>
<body>
<a href="<c:url value='/login'>
        <c:param name='logout'/>
    </c:url>">Logout</a>
<h2>Create a Ticket</h2>
<form method="POST" action="ticket" enctype="multipart/form-data">
    <input type="hidden" name="action" value="create">
    Name:<br>
    <input type="text" name="name"><br><br>
    Subject:<br>
    <input type="text" name="subject"><br><br>
    Body:<br>
    <textarea name="body" rows="25" cols="100"></textarea><br><br>
    <b>Attachment</b><br>
    <input type="file" name="file1"><br><br>
    <input type="submit" value="Submit">
</form>
</body>
</html>
