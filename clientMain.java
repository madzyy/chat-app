import javax.swing.JFrame;

class clientMain{

	public static void main(String args[]){
		client clientObj = new client("127.0.0.1");
		clientObj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		clientObj.setBounds(500, 100, 500, 700);
		clientObj.setVisible(true);
		clientObj.startRunning();
		//clientObj.pack();
	}

}