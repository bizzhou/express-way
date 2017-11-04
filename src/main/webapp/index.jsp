<%-- <% response.sendRedirect("home/home"); %> --%>

<html>
   <head>
      <title>Spring Redirection Test</title>
   </head>

   <body>
      <h2>Redirection Test</h2>
      <p>Click below button to redirect</p>
      
      <form:form method = "GET" action = "/expressway/redirect">
         <table>
            <tr>
               <td>
                  <input type = "submit" value = "Redirect Page"/>
               </td>
            </tr>
         </table>  
      </form:form>
   </body>
   
</html>