<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Bigdata Web API Home</title>
</head>
<body>

<form action="/service/upload/file" method="post" enctype="multipart/form-data">
    <p>
        Select a file : <input type="file" name="uploadedFile" size="50" />
    </p>
    <!--
    <p>
        The Next file : <input type="file" name="uploadedFile2" size="50" />
    </p>
    -->
    <input type="submit" value="Upload It" />
</form>

</body>
</html>