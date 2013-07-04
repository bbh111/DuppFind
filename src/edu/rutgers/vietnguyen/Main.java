package edu.rutgers.vietnguyen;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		
		//TestDuppFind.test4();
		EventQueue.invokeLater(new Runnable()
			{
				public void run()
				{
					JFrame dpFrame = new DuppFindFrame();
					dpFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					dpFrame.setVisible(true);
				}
			});
		
	}

}
