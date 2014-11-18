package com.meteor.main;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.ListenerFactory;

import com.meteor.module.Ftp_UserManager;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FtpServerFactory serverFactory = new FtpServerFactory();
		ListenerFactory factory = new ListenerFactory();

		//Port Setting
		factory.setPort(2221);
		
		/**
		 * Create Connection Account
		 */
		Ftp_UserManager um = new Ftp_UserManager();
		um.create_user("lee", "11", true);
		//um.delete_user("lee");
		
		//serverFactory.setUserManager(userManagerFactory.createUserManager());
		serverFactory.setUserManager(um.get_UserManager());
		serverFactory.addListener("default", factory.createListener());

		
		FtpServer server = serverFactory.createServer();
		
		
		
		try {
			server.start();
		} catch (FtpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
