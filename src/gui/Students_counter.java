package gui;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class Students_counter {

	public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
		

		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
					
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                StartupPage.createAndShowGUI();
            }
        });
    }

}
