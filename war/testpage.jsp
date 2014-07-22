<%@page import="model.*"%>
<%@page import="domein.*"%>
<%@page import="java.util.ArrayList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml"> 
 <head> 
 <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" /> 
 <title>Dynamic Example</title> 
 </head> 
 <body> 
 <% 
 decription myEncryptor= new decription();
 
 String stringToEncrypt="Ruben";
 String encrypted=myEncryptor.encrypt(stringToEncrypt);
 String decrypted=myEncryptor.decrypt(encrypted);


 %>
 </body> 
</html>