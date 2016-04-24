/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kij_chat_client;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import javax.xml.bind.DatatypeConverter;
import java.util.Base64;

/**
 *
 * @author santen-suru
 */
public class Write implements Runnable {
    
	private Scanner chat;
        private PrintWriter out;
        boolean keepGoing = true;
        ArrayList<String> log;
	
	public Write(Scanner chat, PrintWriter out, ArrayList<String> log)
	{
		this.chat = chat;
                this.out = out;
                this.log = log;
	}
	
	@Override
	public void run()//INHERIT THE RUN METHOD FROM THE Runnable INTERFACE
	{
		try
		{
			while (keepGoing)//WHILE THE PROGRAM IS RUNNING
			{						
				String input = chat.nextLine();	//SET NEW VARIABLE input TO THE VALUE OF WHAT THE CLIENT TYPED IN
                                if(input.contains("pm"))
                                {
                                    String dest = input.split(" ")[1];
                                    EncryptionUtil encryptText = new EncryptionUtil(dest);
                                    byte[] b = encryptText.encrypt(input.split(" ")[2]);
                                    //String encryptedText = encryptText.encrypt(input.split(" ")[2]).toString();
                                    input = "pm " + dest + " " + (Base64.getEncoder().encodeToString(b));
                                }
				out.println(input);//SEND IT TO THE SERVER
				out.flush();//FLUSH THE STREAM
                                
                                if (input.contains("logout")) {
                                    if (log.contains("true"))
                                        keepGoing = false;
                                    
                                }
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();//MOST LIKELY WONT BE AN ERROR, GOOD PRACTICE TO CATCH THOUGH
		} 
	}

}
