<% 

	java.sql.Connection connection=null;

	java.sql.PreparedStatement pst = null;
	
	Class.forName("com.mysql.jdbc.Driver");
	
	connection = java.sql.DriverManager.getConnection("jdbc:mysql://localhost/synote?user=synote&password=synote");
	

 
 %>
 
<html>
<head>
<head>
	<title>Create Test</title>
	<meta name="layout" content="main" />
</head>
<title>CREATE TEST</title>
 
<style type="text/css">
<!--
.style7 {
 font-size: 24px;
 color: #330099;
 font-weight: bold;
}
.style8 {
 font-size: 18px;
 color: #990000;
}
.style9 {font-size: 16px}
-->
</style>
<script type="text/javascript">
var c=0
var t
</script>
 
</head>




<body>
<form id="f1" name="form1" method="post">
  <tr bgcolor="#FFFFCA">
    <td height="138"><div align="center" class="style7">
      <p> </p>
      <p>STUDENT TEST </p>
      <p align="left"><span class="style8">Create Test Instructions:</span></p>
      <p align="left"><span class="style8"><span class="style9">Input your Question , you have the option of uploading a diagram , give URL in the last field and For the input choices either enter the choice or leave blank if dont want to use it </span></span></p>
       <p align="left"><span class="style8"><span class="style9">when you done with one question click on "Next"  else click on "Finish" </span></span></p>
	  <p align="left"> </p>
    </div>
      <table border="0" width="500px" cellspacing="2" cellpadding="4">
<%
 
%>
<tr>
 <td> <strong>	Input Question:
         <input type="text" name="q" value= "" /></td>
		 </tr>
          <td> <strong>Input Choice 1:
		  </strong>
            <input type="text" name="a1" value= "" /></td>
           <tr>
		  <td> <strong>Input Choice 2:
		  </strong>
           <input type="text" name="a2" value= "" /></td>
 </tr>       
	   <tr>
          <td> <strong>Input Choice 3:
		  </strong>
            <input type="text" name="a3" value= "" /></td>
        </tr>
        <tr>
          <td> <strong>Input Choice 4:
            </strong>
             <input type="text" name="a4" value= "" /></td>
        </tr>
		<td> <strong>Correct choice:
            </strong>
             <select name="cc" >
			 <option value="1">1</option>
			 <option value="2">2</option>
			 <option value="3">3</option>
			 <option value="4">4</option>
			 </select>
			 </td>
        </tr>
		<tr>
          <td> <strong>Diagram:
            </strong>
             <input type="text" name="d" value= "" /></td>
        </tr>	
		<tr>
          <td> <strong>Diagram Height:
            </strong>
             <input type="text" name="h" value= "" /></td>
        </tr>	
		<tr>
          <td> <strong>Diagram Width:
            </strong>
             <input type="text" name="w" value= "" /></td>
        </tr>
        <tr>
          <td><center>
<%
 
%>
 
              <input type="submit" value="SUBMIT TEST QUESTION" name="submit" onclick="saveTest();"/>
          </center></td>
        </tr>
      </table>
    <p> </p></td>
  </tr>
</table>
 
</form>




</body>
	<script type="text/javascript">
	function saveTest(){
	
	<%
	try{
	String q = request.getParameter("q");
	
	String a1 = request.getParameter("a1");
	String a2 = request.getParameter("a2");
	String a3 = request.getParameter("a3");
	String a4 = request.getParameter("a4");
	
	String cc = request.getParameter("cc");
	int ccInt = Integer.parseInt(cc);
	
	String d = request.getParameter("d");
	
	
	String h = request.getParameter("h");
	int hInt = Integer.parseInt(h);
	
	String w = request.getParameter("w");
	int wInt = Integer.parseInt(w);
	
	String testId = Integer.parseInt(params.id);
	int tId = Integer.parseInt(testId);
	
	
	
	System.out.println("HELLO didigeeg " + testId);

		pst=connection.prepareStatement(
		"insert into newtest " +  
		"values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
	
	pst.setInt(1, tId);
	pst.setString(2, q);
	pst.setString(3, a1);
	pst.setString(4, a2);
	pst.setString(5, a3);
	pst.setString(6, a4);
	pst.setInt(7, ccInt);
	pst.setString(8, d);
	pst.setInt(9, hInt);
	pst.setInt(10, wInt);
    pst.executeUpdate();
	
	}catch(Exception e){
	
		System.out.println("ERROR WHILE ADDING TEST!!!");
	}
	
 connection.close();
 
 %>
	
	
	}
	</script>
</html>