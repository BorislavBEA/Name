package application;

public class Server {

	public static void main(String[] args) {
		TaskList TL = new TaskList();
		TaskListServer TLS = new TaskListServer(TL, 1234);
		TLS.execute();
	}

}
