package gui;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
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


public class NewPost extends JFrame{
	
	session session_obj;
	boolean type=false;
	JLabel title=new JLabel("Title");
	JLabel que=new JLabel("Question");
	JLabel date=new JLabel("Date");
	JLabel status=new JLabel("Status");
	JLabel name=new JLabel("Name");
	JLabel comment=new JLabel("comment");

	
	JTextField tftitle=new JTextField(50); 
	JTextField tfque=new JTextField(50); 
	JTextField tfdate=new JTextField(50); 
	JTextField tfstatus=new JTextField(50);
	JTextField tfname=new JTextField(50); 
	JTextField tfcomment=new JTextField(50); 
	
	JButton btn=new JButton();
	
	public NewPost(session obj)
	{
		session_obj=obj;
		init();
		add(title);
		title.setBounds(25, 25, 150, 25);
		add(tftitle);
		tftitle.setBounds(200, 25, 200, 25);
		tftitle.setToolTipText("Title or Subject of the Discussion.");
		btn.setText("Insert");
	}
	
	public NewPost(session obj,int i)
	{
		session_obj=obj;
		init();
		type=true;
		btn.setText("Update");
	}
	
	public void init()
	{
		setTitle("Enquiry");
		setSize(450, 400);
		setLayout(null);
	
		add(que);
		que.setBounds(25, 75, 150, 25);
		add(tfque);
		tfque.setBounds(200, 75, 200, 25);
		tfque.setToolTipText("Entire Question of the discussion.");
		
		add(date);
		date.setBounds(25, 125, 150, 25);
		add(tfdate);
		tfdate.setBounds(200, 125, 200, 25);
		tfdate.setText(new Date().toString());
		tfdate.setEditable(false);
		tfdate.setToolTipText("Date and time of post.");
		
		add(status);
		status.setBounds(25, 175, 150, 25);
		add(tfstatus);
		tfstatus.setBounds(200, 175, 200, 25);
		tfstatus.setText(session_obj.getType());
		tfstatus.setEditable(false);
		tfstatus.setToolTipText("Type of User Comment.");
		
		add(name);
		name.setBounds(25, 225, 150, 25);
		add(tfname);
		tfname.setBounds(200, 225, 200, 25);
		tfname.setToolTipText("Name of the User.");
		
		add(comment);
		comment.setBounds(25, 275, 150, 25);
		add(tfcomment);
		tfcomment.setBounds(200, 275, 200, 25);
		tfcomment.setToolTipText("Comment or Solution of the Discussion.");
		
		add(btn);
		btn.setBounds(105, 325, 150, 25);
		btn.addActionListener(new ButtonHandler());
		
		
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	class ButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e1) {
			try{
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				// 1.To connect to mongodb server
				MongoClient mongoClient = new MongoClient( constants.serverip , 27017 );
				Scanner obj=new Scanner(System.in);

				//2. Now connect to your databases
				DB db = mongoClient.getDB( "blog" );
				System.out.println("Connect to database successfully");
				
				//4.create collection and get collection
				DBCollection coll1 = db.createCollection("blog", null);
				System.out.println("Collection created successfully");
				
				switch(e1.getActionCommand())
				{
				case "Insert":
					insert(coll1);
					setCursor(null);
					SubmitDialog sd=new SubmitDialog("New Enquiry Question Inserted Successfully");
					sd.setVisible(true);
					System.out.println("Insert");
					dispose();
					break;
					
				case "Update":
					update(coll1);
					setCursor(null);
					sd=new SubmitDialog("New Post Inserted Successfully");
					sd.setVisible(true);
					System.out.println("Update");
					dispose();
					break;
				}
				}catch(Exception e)
				{
				
				e.printStackTrace();
				}
			
			
		}
		
	}
	
		public void update(DBCollection coll1) {
			String sque=tfque.getText();
			String sdate=tfdate.getText();
			String sstatus=tfstatus.getText();
			String sname=tfname.getText();
			String scontent=tfcomment.getText();
			BasicDBObject query = new BasicDBObject();
			query.put("Quetion", sque);
			
			BasicDBObject newDocument = new BasicDBObject("comment",new BasicDBObject("Name", sname).append("Date", sdate).append("Status", sstatus).append("Content", scontent));
			BasicDBObject updateObj = new BasicDBObject("$push",newDocument);
			coll1.update(query, updateObj);
			
		}
		public void insert(DBCollection coll1) {
			//5.insert document  in collection
			
			String stitle=tftitle.getText();
			String sque=tfque.getText();
			String sdate=tfdate.getText();
			String sstatus=tfstatus.getText();
			String sname=tfname.getText();
			String scontent=tfcomment.getText();
			BasicDBObject doc = new BasicDBObject("title", stitle).
			append("Quetion", sque);
			
			BasicDBObject comment= new BasicDBObject("Name", sname).append("Date", sdate).append("Status", sstatus).append("Content", scontent);
			doc.put("Comment", comment);
			doc.put("comment", new BasicDBList());
			coll1.insert(doc);
			System.out.println("Document inserted successfully");
			
		}
		

}
