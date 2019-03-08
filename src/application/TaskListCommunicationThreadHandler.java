package application;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class TaskListCommunicationThreadHandler implements Runnable {
	String ip;
	BufferedReader in;
	PrintWriter out;
	Socket socket;
	TaskList tasklist;
	public TaskListCommunicationThreadHandler(Socket socket,TaskList tasklist) {
		this.socket=socket;
		this.tasklist=tasklist;
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
	@Override
	public void run() {
		for (int i = 0;i<tasklist.size();i++) {
			try {
				// read line from client.
				String clientText= in.readLine();
				System.out.println("Client> " + clientText);
				// convert from JSon
				Gson gson= new Gson();
				Package p = gson.fromJson(clientText, Package.class);
				System.out.println("Student: " + p);
			
				// creating reply
				Package reply =operation(p);
				

				System.out.println("Reply: " + reply);
				// convert reply to Json
				String replyJson= gson.toJson(reply);
				// Send reply to client.
				System.out.println("Server> " + replyJson);
				out.println(replyJson);}
			catch (Exception e){ e.printStackTrace(); } }
			}
		
		private Package operation(Package request) {
			Package reply;
			switch (request.getText()) {
			case "ADD": reply = new Package(Package.ADD);
			case "GET": reply = new Package(Package.GET, tasklist.getAndRemoveNextTask());
			case "SIZE": reply = new Package(Package.SIZE+ "=" + tasklist.size());
			case "EXIT": reply = new Package(Package.EXIT);
			default : reply = new Package("Something's WRONG");
			
			}
			return reply;
		}
		
	}


