//Sending mail from Java Code using Socket programming
//Instructor: Dr. Felicia Doswell
//CSC530 Data Communication
//Date: 09/21/2017
//BY : Bigyan KC and Satyanarayan Raju
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class EmailSender
{
   public static void main(String[] args) throws Exception
   {
      
	   //Initialize scanner class to get input from keyboard
	   Scanner sc=new Scanner(System.in);
	   
	   // Establish a TCP connection with the mail server.
       Socket socket = new Socket("mx01.nsu.edu", 25);
       DataOutputStream outToServer = 
          new DataOutputStream(socket.getOutputStream());

      // Create a BufferedReader to read a line at a time.
      InputStream is = socket.getInputStream();
      InputStreamReader isr = new InputStreamReader(is);
      BufferedReader br = new BufferedReader(isr);

      // Read greeting from the server.
      String response = br.readLine();
      System.out.println(response);
      if (!response.startsWith("220")) {
         throw new Exception("220 reply not received from server.");
      }

      // Get a reference to the socket's output stream.
      OutputStream os = socket.getOutputStream();

      // Send HELO command and get server response.
      String command = "HELO gmail.com\r\n";
      System.out.print(command);
      os.write(command.getBytes("US-ASCII"));
      response = br.readLine();
      System.out.println(response);
      if (!response.startsWith("250")) {
    	  socket.close();
    	  throw new Exception("250 reply not received from server.");
      }

      // Send MAIL FROM command.
      
         String from = "bigyan.kc1@gmail.com";
         String mailFromCommand = "MAIL FROM:  <" + from + ">\r\n";
         os.write(mailFromCommand.getBytes("US-ASCII"));
         response = br.readLine();
         System.out.println(response);


      // Send RCPT TO command.
      
         String to = "fdoswell@nsu.edu";
         String fullAddress = "RCPT TO:  <" + to + ">\r\n";
         os.write(fullAddress.getBytes("US-ASCII"));
         response = br.readLine();
         System.out.println(response);

         
      // Send DATA command.
         String dataString=new String();
         dataString="DATA\r\n";
         os.write(dataString.getBytes("US-ASCII"));
         response=br.readLine();
         if(!response.startsWith("354")){
        	 	socket.close();
        	    throw new Exception("354 reply not received from server.\n");
         }
         System.out.println(response);
      // Send message data.
         String input = new String();
         String emailMsg="";
         
         input=sc.nextLine();
         while(input.charAt(0) != '.')
         {
        	 emailMsg=emailMsg+input+"\n";
        	 input = sc.nextLine();
         }
         // End with line with a single period.
         
         emailMsg=emailMsg+"\r\n"+"."+"\r\n";
         os.write(emailMsg.getBytes("US-ASCII"));
         response = br.readLine();
         System.out.println(response);
      

      // Send QUIT command.
         String quitCommand = "QUIT\r\n";
         os.write(quitCommand.getBytes("US-ASCII"));
         response = br.readLine();
         System.out.println(response);
      
   }
}
