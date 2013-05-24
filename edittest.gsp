<% 

	java.sql.Connection connection=null;

	
	java.sql.PreparedStatement pst = null;
	java.sql.ResultSet rs=null;
	
	Class.forName("com.mysql.jdbc.Driver");
	
	connection = java.sql.DriverManager.getConnection("jdbc:mysql://localhost/synote?user=synote&password=synote");
	
	
	pst=connection.prepareStatement("select * from newtest where multid = ?");
	
	
	pst.setString(1, params.id);
    rs=pst.executeQuery();
	
	java.util.ArrayList correctAns = new java.util.ArrayList();
	java.util.ArrayList allquestions = new java.util.ArrayList();
	
	
 
 %>
 
<html>
<head>
<head>
	<title>Edit Test</title>
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
      <p align="left"><span class="style8">Edit Test Instructions:</span></p>
      <p align="left"><span class="style8"><span class="style9">Select the Question you want to remove from your Test</span></span></p>
       <p align="left"><span class="style8"><span class="style9">We have the Option of Modifying the Question by selecting one Question and then writing its modified version below </span></span></p>
	  <p align="left"> </p>
    </div>
      <table border="0" width="500px" cellspacing="2" cellpadding="4">
<%
 
%>

<%
 
int i=1;
while(rs.next())
{
 correctAns.add( rs.getInt(7) );
allquestions.add( rs.getString(2) );
	
%>

		<tr>
	
			<td> <strong>Delete Me:</strong>
            <input type="radio" name="Delete<%=i%>" value= "radiodelete" />
               </td>
        </tr>

        <tr>
	
			<td> <strong>Edit Me:</strong>
            <input type="radio" name="Edit123" value= "radioedit<%=i%>" />
               </td>
        </tr>
		
		
		
		
		<tr>	
          <td width="26%"> <strong>Question:<%=i%></strong>.<%=rs.getString(2) %> 
			
        <tr>
		<tr>
          <td> 
			<strong>diagram URL:</strong><%=rs.getString(8)%>
        </tr>
		<tr>
          <td> <strong>diagram:
            </strong>
           	<img src = <%=rs.getString(8)%>>
			
        </tr>
		
		
		
		
		
		<tr>
			<td> <strong>a:</strong>
              <%= rs.getString(3) %></td>
        </tr>
        <tr>
          <td> <strong>b:</strong>
				<%= rs.getString(4) %></td>
        </tr>
        <tr>
          <td> <strong>c:</strong>
              <%= rs.getString(5) %></td>
        </tr>
        <tr>
          <td> <strong>d:</strong>
              <%= rs.getString(6) %> </td>
        </tr>
		
        <tr>
          <td><center>
<%

i++;


}
 
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
 
              <input type="submit" value="Delete All Seclected" name="submit" onclick="DeleteTest();"/>
			  <input type="submit" value="Edit The Seclected Question" name="submit" onclick="EditTest();"/>
          </center></td>
        </tr>
      </table>
    <p> </p></td>
  </tr>
</table>
 
</form>




</body>
	<script type="text/javascript">
	function EditTest(){
	
	<%
	try{
	
	
	
	
	
	
	String ques = request.getParameter("q");
	String a1 = request.getParameter("a1");
	
	
	String testId = Integer.parseInt(params.id);
	int tId = Integer.parseInt(testId);
	
	
	
	System.out.println("HELLO didigeeg " + testId);

				
		String editchoice = request.getParameter( "Edit123" );
		
		System.out.println("this is edit test value"+ editchoice);
		
		int u = 0;
		
         for(int j =1;j<=i; j++){
		 System.out.println("inside for loop i=" + i +" and j= " + j);
		 String hullo = "radioedit"+j;
		 
		 if(editchoice.equals(hullo)){
					u =j;
					
					System.out.println("radioedit"+j);
					System.out.println("Question no" + u + "and question is :" + allquestions.get(u-1));
				}
                
		}
		
		
		pst=connection.prepareStatement(
		 "update newtest set question = ? where answer2 = ?");
	
	
	pst.setString(1, ques);
	pst.setString(2, a1);
	
    int x = pst.executeUpdate();
	
	System.out.println(x+ " total records changed");
	
	
	}catch(Exception e){
	
		System.out.println("ERROR WHILE Editing Test TEST!!!");
	}
	
 connection.close();
 
 %>
	
	
	}
	</script>
</html>