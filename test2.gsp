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
	
	
 %>
 
 

 
<html>


<head>
	<title>Show Test</title>
	<meta name="layout" content="main" />
</head>



<head>

<title>STUDENT TEST</title>
 
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

</head>



<body>
	
<form id="f1" name="form1" method="POST">
  <tr bgcolor="#FFFFCA">
    <td height="138"><div align="center" class="style7">
      <p> </p>
      <p>STUDENT TEST </p>
      <p align="left"><span class="style8">Test Instructions:</span></p>
      <p align="left"><span class="style8"><span class="style9">For each multiple choice question, please choose one answer. </span></span></p>
      <p align="left"> </p>
    </div>
      <table border="0" width="500px" cellspacing="2" cellpadding="4">
<%
 
int i=1;
while(rs.next())
{
 correctAns.add( rs.getInt(7) );
%>
        <tr>
			
          <td width="26%"> <strong>Question:<%=i%></strong>.<%=rs.getString(2) %> 
			<input type="text" name="que<%=i%>" style="visibility:hidden" value= "<%= rs.getString(1) %>" /></td>
        <tr>
		<tr>
          <td> <strong>:
            </strong>
           	<img src = <%=rs.getString(8)%>>
			
        </tr>
		
		<tr>
			<td> <strong>a:</strong>
            <input type="radio" name="a<%=i%>" value= "radio1" />
              <%= rs.getString(3) %></td>
        </tr>
        <tr>
          <td> <strong>b:</strong>
            <input type="radio" name="a<%=i%>"  value="radio2"  />
              <%= rs.getString(4) %></td>
        </tr>
        <tr>
          <td> <strong>c:</strong>
            <input type="radio" name="a<%=i%>" value="radio3"  />
              <%= rs.getString(5) %></td>
        </tr>
        <tr>
          <td> <strong>d:
            </strong>
            <input type="radio" name="a<%=i%>" value="radio4"  />
              <%= rs.getString(6) %> </td>
        </tr>
		
        <tr>
          <td><center>
<%

i++;


}
 
%>

<input type="submit" value="Calculate My Score" name="Score" onclick = "getScore()"/>
			  	
<script type="text/javascript">

	function getScore(){

		var i =<%=i%>
		
	
		
<%
		int correct=0;
		
		boolean selected = false;
		
		for(int j =1;j<i; j++){
			
				String a = "a" + j;
				
				String answer = request.getParameter( a );
			
                if(answer.equals("radio1") && correctAns.get(j-1) == 1) {
					selected = true;
					correct++;
                }
                else if(answer.equals("radio2") && correctAns.get(j-1) == 2) {
					selected = true;
					correct++;
                }
                else if(answer.equals("radio3") && correctAns.get(j-1) == 3) {
					selected = true;
					correct++;
                }
				else if(answer.equals("radio4") && correctAns.get(j-1) == 4) {
					selected = true;
					correct++;
                }
				else if(answer != null){
					selected = true;
				}
		}

		if(selected){
			System.out.println("YOUR SCORE IS: " + correct + " out of "+(i-1));	
			javax.swing.JOptionPane.showMessageDialog(null,
			"YOUR SCORE IS: " + correct + " out of "+(i-1));
		}
		else{
		
			//javax.swing.JOptionPane.showMessageDialog(null,
			//		"PLEASE ANSWER AT LEAST 1 QUESTION.");
		}
	
		
		
		%>
 
 }
</script>


          </center></td>
        </tr>
      </table>
    <p> </p></td>
  </tr>
</table>
 
</form>




</body>

 
</html>
<% 
 rs.close();
 pst.close();
 connection.close();
 

%>