import javax.swing.JFrame;

class serverMain{

	public static void main(String args[]){
		server serverObj = new server();
		serverObj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		serverObj.setBounds(1000, 100, 500, 700);
		serverObj.setVisible(true);
		serverObj.startRunning();
		serverObj.pack();
	}

}