package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import com.google.gson.Gson;

public class TaskListClient {
private Scanner input;
private BufferedReader in;
private PrintWriter out;
private Socket socket;

public TaskListClient(int host,int port) {
	this.socket = new Socket();
	this.input = new Scanner(System.in);
	
	try {
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	} catch (IOException e) {
		e.printStackTrace();
	}
	try {
		out = new PrintWriter(socket.getOutputStream(), true);
	} catch (IOException e) {	
		e.printStackTrace();
	}
}
public void execute() {
	while(true) {
		System.out.println("Chooce a task:ADD, GET, SIZE, EXIT");
		String clientText= input.nextLine();
		if (clientText == "ADD") {
			System.out.println("Input your task");
			String s = input.nextLine();
			System.out.println("You have chosen "+s +" Now enter estimated time");
			long i = input.nextLong();
			System.out.println("Your estimated time is"+ i);
			
			Package p = new Package(s);
			Gson g = new Gson();
			String replyJson= g.toJson(p);
			out.println(replyJson);
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				String s2= in.readLine();
				Gson g2 = new Gson();
				Package p2 = g2.fromJson(s2, Package.class);
				System.out.println(p2);
				if (p2.getText()=="EXIT") {
					
					socket.close();	
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		break;

		
	}
	
}
}
