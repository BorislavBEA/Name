package application;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class TaskListServer {
	private ServerSocket welcomesocket;
	private TaskList tasklist;
	public TaskListServer(TaskList tasklist,int port) {
		this.tasklist=tasklist;
		try {
			this.welcomesocket= new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public void execute() {
		try {
			for (int i = 0;i<5;i--) {
			Socket Socket= new Socket();
			welcomesocket.accept();
			welcomesocket.wait();
			TaskListCommunicationThreadHandler obj = new TaskListCommunicationThreadHandler(Socket,tasklist);
            Thread serverAccessThread = new Thread(obj);
            serverAccessThread.start();
			}		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	}
