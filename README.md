# E-students-Counter
Students Counter application for PICT college.  
Mini-Project to be submitted in third year.  

Database Analysis : 
Set of databases = students_counter
Where,
students_counter = {register_db, login_db, scholarship_db, document_db, bonafide_db,examform_db, wifiregister_db, blog}

register_db = {username(PRIMARY KEY), roll_no, name,enroll_no,addmission_date,gender, dob, religion, nationality, fee_catagory, phone_number, address}  
username : username of person : [A – Z] [0 – 9]   
roll_no : student roll number : [0 – 9]  
name : name of person : [a – z][A – z]  
 enroll_no : enrollment number of students : [A – Z] [a – z] [0 – 9]  
addmission_date : date of addminssion : yyyy/mm/dd : [0 – 9]  
Gender : gender of person : {M , F}  M:Male   F:Female  
Dob : date of birth : yyyy/mm/dd : [0 – 9]  
Religion : religion of student : {hindu, muslim, sikh, parasi, jain, christian}  
Nationality : nationality of students : {INDIAN , NRI}    
fee_catagory : fee catagory of students : {OPEN, OBC, SBC, NT, SC/ST, TFWS, VJTI}  
phone_no : phone number of student : [0 – 9]  
Address : address of person :  [A – Z] [a – z] [0 – 9]
  
  
login_db : {username(PRIMARY KEY), passwd, registered, type, mail, branch, class}  
username : username of person : [A – Z][0 – 9]  
Passwd : password of students : [A – Z] [a – z] [0 – 9]  
Registered : boolean value : {true , false}  
Type : type of account : {student, admin, sub-admin}  
Mail : emailID of student : [A – Z] U [a – z] U [0 – 9]  
Branch : branch of students : {computer, information technology, EnTC, Staff}  
Class : year of studying : {FE, SE, TE, BE, ME}
  
scholarship_db : {username(REFERENCE KEY), s_username, s_passwd, s_type, s_registered, s_documents}  
username : username of person : [A – Z][0 – 9] 	  
s_passwd : password of students : [A – Z] [a – z] [0 – 9]  
s_type : type of account : {scholarship,freeship}  
s_registered : boolean value : {true , false}  
s_documents : boolean value : {true , false} i.e. {submitted , not submitted}  

document_db : {username(REFERENCE KEY), LC,form16,income,cast_certf , cast_val,other}  
username : username of person : [A – Z][0 – 9] 	  
LC : boolean value : {true , false}  
Form16 : boolean value : {true , false}  
income : boolean value : {true , false}  
cast_certf : boolean value : {true , false}  
cast_val : boolean value : {true , false}  
other : boolean value : {true , false}  

bonafied_db : {username(REFERENCE KEY), applied_on,reason , issued_on}  
username : username of person : [A – Z][0 – 9] 	  
applied_on : date of application : yyyy/mm/dd : [0 – 9]  
reason : reason for bonafied : [A – Z] [a – z]   
applied_on : date of application : yyyy/mm/dd : [0 – 9]  

examform_db : {roll_no, type, seat_no, uni_uname, uni_passwd, year, dept, semister, dd_no}  
roll_no : student roll number : [0 – 9]  
type : type of students : {repeater , fresher}  
seat_no : student seat number : [0 – 9] U [A – Z]  
uni_uname : univercsity username of person : [A – Z][0 – 9]   
uni_passwd : password of students university exam account : [A – Z][a – z] [0 – 9]  
year : year of students : {FE, SE , TE ,BE ,ME}  
dept : department of students : {comp , it ,entc}  
semister : semister of studend : {1st, 2nd }  
dd_no : dd number : [A – Z][a – z] [0 – 9]  

wifiregister_db : {username(REFERENCE KEY), class, dept, reg_dt, device_id, mac_add, forward_on}
username : username of person : [A – Z][0 – 9] 	  
class : calss of student : {FE, SE , TE ,BE ,ME}  
dept : department of students : {comp , it ,entc}  
reg_dt : date of application : yyyy/mm/dd : [0 – 9]  
device_id : device number : [A – Z][a – z] [0 – 9]  
mac_add : mac address of device : [A – Z][a – z] [0 – 9]   
forward_on :date on which application is forwarded: yyyy/mm/dd E[0 – 9]  

blog : {title, doc,question, comment }  
title : title of the blog : [A – Z][a – z] [0 – 9]   
question : question of students : [A - Z] [a - z]  
comment : {name, date, status, content}  
name : name of students : [A - Z] [a - z]    
date : date of question : yyyy/mm/dd : [0 – 9]  
status : status of person : {admin, subadmin, student}  

C : Connectivity of databases
C : {C1 , C2}  
where,  
C1 : set of connectivity to MySQL server    
C1 : {driver , ipadd , database1 , database2 , port1 , port2}  
driver : MySQL driver for java (jar file)  
ipadd : ip address of database server  
port1 : 3306 for MySQL databases  
database1 : name of MySQL database  
port2 : 27017 for MongoDB databases  
database2 : name of MongoDB database  

D : Required drivers  
D : {commons-jar-3.3.2.jar, jCalender-1.4.jar, mysql-connector-java-5.1.24-bin.jar, seaglass-demo.jar, log4j-1.2.16.jar, mail.jar, swingX.jar}  
