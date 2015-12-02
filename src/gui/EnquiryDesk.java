package gui;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;


public class EnquiryDesk extends JFrame{
	
	session session_obj;
	JTextArea comment1=new JTextArea(50,50);
	JButton btnnewque=new JButton("New Question");
	JButton btnpost=new JButton("Post");
	JButton btnprev=new JButton("Previous");
	JButton btnnext=new JButton("Next");
	JPanel panel=new JPanel();
	JScrollPane sp=new JScrollPane(comment1);
	DBCursor cursor;
	MongoClient mongoClient;
	DB db;
	DBCollection coll1;
	DBObject record;
	
	public EnquiryDesk(session obj)
	{
		session_obj=obj;
		setTitle("Enquiry Desk");
		setSize(650, 650);
		setLayout(null);
		
		comment1.setEditable(false);
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel.setLayout(new BorderLayout());
		panel.setBounds(75, 25, 500, 375);
		panel.add(sp);
		add(panel);
		
		//add(btnprev);
		btnprev.setBounds(150, 450, 150, 25);
		btnprev.addActionListener(new ButtonHandler());
		add(btnnext);
		btnnext.setBounds(250, 450, 150, 25);
		btnnext.addActionListener(new ButtonHandler());
		add(btnnewque);
		btnnewque.setBounds(150, 500, 150, 25);
		btnnewque.addActionListener(new ButtonHandler());
		add(btnpost);
		btnpost.setBounds(350, 500, 150, 25);
		btnpost.addActionListener(new ButtonHandler());

		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		try{
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			// 1.To connect to mongodb server
			mongoClient = new MongoClient( constants.serverip , 27017 );
			

			//2. Now connect to your databases
			db = mongoClient.getDB( "blog" );
			System.out.println("Connect to database successfully");
			
			//4.create collection and get collection
			coll1 = db.createCollection("blog", null);
			System.out.println("Collection created successfully");
			
			cursor = coll1.find();
			setCursor(null);
			}catch(Exception e)
			{
			e.getStackTrace();
			}
		if(cursor.hasNext())
		{	
			record=cursor.next();
			find();
		}
		else
			comment1.setText("No Questions Available");
	}
	class ButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e1) {
				switch(e1.getActionCommand())
				{
				case "Next":
					if(cursor.hasNext()) {
						record=cursor.next();
						find();
					}
					else if(cursor.size()==0)
					{
						comment1.setText("No Questions Available");
					}
					else
					{
						cursor = coll1.find();
						record=cursor.next();
						find();
					}
					break;
					
				case "Previous":
					break;
					
				case "New Question":
					new NewPost(session_obj);
					break;
					
				case "Post":
					new NewPost(session_obj,0);
					break;
				}
		
		}
		
	}
	
	public void find() {
		String res=display();
		comment1.setText(res);
		
	}
	
	public String display()
	{
		DBObject record1;
		int i=0;
		String res="",res1;
		res+="Title : "+record.get("title")+"\n";
		res+="Question : "+record.get("Quetion")+"\n";
		record1=(DBObject) record.get("Comment");
		res1=getRecord(record1);
		res+=res1;
		res+="Additional Comments:\n";
		BasicDBList vc=(BasicDBList) record.get("comment");
		if(!(vc.size()==0))
		{
		while(i<vc.size())
		{
			res1=getRecord((DBObject) vc.get(i));
			res+=res1;
			i++;
		}
		}
		else
		{
			res+="Null";
		}
			return res;
		
	}
	
	public String getRecord(DBObject record)
	{
		String res="";
		res+="Name : "+record.get("Name")+"\n";
		res+="Date : "+record.get("Date")+"\n";
		res+="Status : "+record.get("Status")+"\n";
		res+="Content : "+record.get("Content")+"\n";
		return res;
	}
	
	
}