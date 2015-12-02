package gui;

import org.apache.commons.lang3.*;
public class password_generator {
	
	public String getRandomPassword() {
	    StringBuffer password = new StringBuffer(20);
	    int next = RandomUtils.nextInt(0, 13) + 8; //Minimum password length will be 8
	    password.append(RandomStringUtils.randomAlphanumeric(next));
	    return password.toString();
	}
	
}
