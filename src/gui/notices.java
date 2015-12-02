package gui;


import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.*;

import org.jdesktop.swingx.JXHyperlink;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;


public class notices extends JFrame{

	gridfsfile fsobj=new gridfsfile();
	DBCursor cursor;
	MongoClient mongoClient;
	DB db;
	DBCollection coll1;
	DBObject record;
	File file;
	JLabel label=new JLabel("Notices");
	JLabel label1=new JLabel("Upload Notices");
	JLabel name=new JLabel("Name:");
	JLabel path=new JLabel("Path:");
	JTextField tfname=new JTextField();
	JTextField tfpath=new JTextField();
	JFileChooser chooser=new JFileChooser();
	JButton choose=new JButton("Choose File");
	JButton upload=new JButton("Upload");
	JButton refresh=new JButton("Refresh");
	JPanel panel=new JPanel();
	Vector<JXHyperlink> vec=new Vector<JXHyperlink>();
	JXHyperlink temp;
	int i=0;
	
	public notices()
	{
		setSize(450,450);

		panel.setSize(400, 300);
		panel.setLocation(25,0);
		add(panel);
		

		init();
		
		refresh.setBounds(125, 350, 100, 25);
		add(refresh);
		refresh.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				dispose();
				new notices();
				
			}
		});
		refresh.setToolTipText("Reload the Page.");

		revalidate();
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public notices(int j)
	{
		setSize(450,500);
		label.setBounds(200, 0, 50, 20);
		add(label);
		panel.setSize(400, 200);
		panel.setLocation(25,25);
		add(panel);
		

		init();
		label1.setBounds(200, 225, 150, 25);
		add(label1);
		name.setBounds(25, 275, 100, 25);
		add(name);
		tfname.setBounds(125, 275, 150, 25);
		add(tfname);
		tfname.setToolTipText("Name of the notice to be uploaded.");
		
		path.setBounds(25, 350, 100, 25);
		add(path);
		tfpath.setBounds(125, 350, 150, 25);
		add(tfpath);
		tfpath.setToolTipText("Path of the pdf file containing the notice.");
		
		choose.setBounds(300, 350, 100, 25);
		add(choose);
		choose.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int retval=chooser.showOpenDialog(rootPane);
				if(retval== JFileChooser.APPROVE_OPTION)
				{
					file=chooser.getSelectedFile();
					tfpath.setText(file.getAbsolutePath());
				}
				else
				{
					tfpath.setText("No file selected!!!");
				}
				
			}
		});
		
	
		upload.setBounds(125, 400, 100, 25);
		add(upload);
		upload.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(file.exists())
				{
					setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					String name=tfname.getText();
					String path=tfpath.getText();
					if(fsobj.putFile(name, path))
					{
						setCursor(null);
						SubmitDialog sd=new SubmitDialog("File Upload done Successfully");
						sd.setVisible(true);
					}
					else
					{
						setCursor(null);
						SubmitDialog sd=new SubmitDialog("File Upload not done Successfully");
						sd.setVisible(true);
					}
				}
			}
		});
		
		refresh.setBounds(250, 400, 100, 25);
		add(refresh);
		refresh.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				dispose();
				new notices(0);
				
			}
		});
		revalidate();
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public void init()
	{
		setTitle("Notices Desk");
		setLayout(null);
		
		
		try{
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			// 1.To connect to mongodb server
			mongoClient = new MongoClient( constants.serverip , 27017 );
			

			//2. Now connect to your databases
			db = mongoClient.getDB( "blog" );
			System.out.println("Connect to database successfully");
			
			//4.create collection and get collection
			coll1 = db.createCollection("files.files", null);
			System.out.println("Collection created successfully");
			
			cursor = coll1.find();
			setCursor(null);
			
			}catch(Exception e)
			{
			e.getStackTrace();
			}
		while(cursor.hasNext())
		{	
			record=cursor.next();
			temp=new JXHyperlink();
			temp.setActionCommand(record.get("filename").toString());
			temp.setText(record.get("filename").toString());
			vec.add(temp);
			vec.elementAt(i).addActionListener(new MouseHandler());
			i++;
		}
		display();
	}
	
	public void display()
	{
		i=0;
		int num=vec.size();
		panel.setLayout(new GridLayout(num, 1));
		while(i<num)
		{
			panel.add(vec.elementAt(i));
			i++;
		}
		
	}
	
	class MouseHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			File file=fsobj.getFile(e.getActionCommand());
			fsobj.openFile1(file);
			setCursor(null);
		}
		
	}
	

}