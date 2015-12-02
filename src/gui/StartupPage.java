package gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.mongodb.MongoClient;

import db.ConnectionManager;

import java.beans.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.util.List;
import java.util.Random;

public class StartupPage extends JPanel
                             implements PropertyChangeListener {
	
    private JProgressBar progressBar;
    private JLabel label;
    private JTextArea taskOutput;
    private Task task;
    Connection conn;
    MongoClient mongoClient;
    boolean result1=false;
    boolean result2=true;
    boolean result3=false;

    class Task extends SwingWorker<Void, Void> {
        /*
         * Main task. Executed in background thread.
         */
    	public Task()
    	{
    		String conn_string="jdbc:mysql://"+constants.serverip+"/student_counter";
    		ConnectionManager.getinstance().setCredentials(conn_string, constants.mysqluser ,constants.mysqlpasswd);
    		conn=ConnectionManager.getinstance().getConnection();
    		if(conn==null)
    			result1=false;
    		else 
    			result1=true;
    		
    		result2=testInet("www.google.com");
    		result2=true;
    		
    		result3=testMongo();
    		
    		
    	}
    	public boolean testMongo()
    	{
    		try {
				mongoClient = new MongoClient( constants.serverip , 27017 );
				List<String> db=mongoClient.getDatabaseNames();
				return true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return false;
			}
    	}
    	
    	public boolean testInet(String site)
    	{
    		Socket sock=new Socket();
    		InetSocketAddress addr=new InetSocketAddress(site, 80);
    		try {
				sock.connect(addr, 9000);
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				return false;
			}
    		finally{
    			try {
					sock.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
				}
    		}
    	}
        @Override
        public Void doInBackground() {
            Random random = new Random();
            int progress = 0;
            //Initialize progress property.
            setProgress(0);
            while (progress < 100) {
                //Sleep for up to one second.
                try {
                    Thread.sleep(random.nextInt(500));
                } catch (InterruptedException ignore) {}
                //Make random progress.
                progress += random.nextInt(10);
                setProgress(Math.min(progress, 100));
            }
            return null;
        }

        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done() {
            Toolkit.getDefaultToolkit().beep();
            setCursor(null); //turn off the wait cursor
            
            if(result1==false)
            	taskOutput.append("MySql Database not connected!!!");
            if(result2==false)
            	taskOutput.append("Internet not connected!!!");
            if(result3==false)
            	taskOutput.append("MongoDb Database not connected!!!");
            if(result1 && result2 && result3)
            {
            	taskOutput.append("Done!\n");
            	
            	javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                    	HomePage obj=new HomePage();
                    	obj.show();
                    }
                });
            	
            }
        }
    }

    public StartupPage() {
        super(new BorderLayout());

        //Create the demo's UI.
        label=new JLabel();
        label.setText("E-Students Counter");
        
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);

        taskOutput = new JTextArea(5, 20);
        taskOutput.setMargin(new Insets(5,5,5,5));
        taskOutput.setEditable(false);

        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(progressBar);

        add(panel, BorderLayout.PAGE_START);
        add(new JScrollPane(taskOutput), BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.setVisible(true);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        //Instances of javax.swing.SwingWorker are not reusuable, so
        //we create new instances as needed.
        task = new Task();
        task.addPropertyChangeListener(this);
        task.execute();

    }

    /**
     * Invoked when the user presses the start button.
     */
 
    /**
     * Invoked when task's progress property changes.
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            progressBar.setValue(progress);
            taskOutput.append(String.format(
                    "Requirements checking %d%% Completed.\n", task.getProgress()));
        } 
    }


    /**
     * Create the GUI and show it. As with all GUI code, this must run
     * on the event-dispatching thread.
     */
    public static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("E-Students Counter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new StartupPage();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
        
        
    }
	

}

