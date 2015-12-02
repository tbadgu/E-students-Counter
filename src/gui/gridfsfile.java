package gui;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.Scanner;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

import java.io.File;


public class gridfsfile
{
	MongoClient mongo;
	DB db;
	DBCollection coll;
	GridFS gfsfilebucket;
	static Scanner sc;
	
	public gridfsfile()
	{
		try {
			mongo=new  MongoClient(constants.serverip, 27017);
			db=mongo.getDB("blog");
			coll=db.getCollection("blog");
			gfsfilebucket=new GridFS(db, "files");
			sc=new Scanner(new InputStreamReader(System.in));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public boolean putFile(String name,String path)
	{
		GridFSInputFile gfsfile;
		try {
			File file=new File(path);
			gfsfile = gfsfilebucket.createFile(file);
			gfsfile.setFilename(name);
			gfsfile.save();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	public File getFile(String name)
	{
		try {
			File file=File.createTempFile("tmp", ".pdf");
			GridFSDBFile fileop=gfsfilebucket.findOne(name);
			fileop.writeTo(file.getAbsolutePath());
			file.deleteOnExit();
			return file;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	public void openFile(File file)
	{
		if(Desktop.isDesktopSupported())
		{
			try {
				Desktop.getDesktop().open(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void openFile1(File file)
	{
		if(file.toString().endsWith(".pdf"))
		{
			try {
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			try {
				Desktop.getDesktop().open(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) 
	{
		gridfsfile blog_obj=new gridfsfile();
		String name,frompath,topath;
		int a=1,b;
		boolean result;
		while(a==1)
		{
			System.out.println("1.Save File to MongoDb from localdisk");
			System.out.println("2.Open File from MongoDB");
			System.out.println("3.Exit");
			b=sc.nextInt();
			switch(b)
			{
			case 1:
				System.out.println("Enter name of File:");
				name=sc.next();
				System.out.println("Enter path:");
				frompath=sc.next();
				result=blog_obj.putFile(name, frompath);
				if(result)
					System.out.println("Save Successfull");
				else 
					System.out.println("Save Unsuccessfull");
				break;
				
			case 2:
				System.out.println("Enter name of File:");
				name=sc.next();
				File myFile=blog_obj.getFile(name);
				if(myFile.exists())
				{
					blog_obj.openFile1(myFile);
				}
				else 
					System.out.println("Open Unsuccessfull");
				break;
				
			case 3:
				a=0;
				break;
			}
		}
		
	}

}
