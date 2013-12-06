package com.meteor.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;

import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.ssl.SslConfigurationFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.SaltedPasswordEncryptor;

import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;

import com.meteor.module.Ftp_UserManager;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FtpServerFactory serverFactory = new FtpServerFactory();

		/**/						
		SslConfigurationFactory ssl = new SslConfigurationFactory();
		ssl.setKeystoreFile(new File("c://stone//stone.txt"));
		//ssl.setKeystoreFile(new File("src/test/resources/ftpserver.jks"));
		ssl.setKeystorePassword("password");
		
		ListenerFactory factory = new ListenerFactory();
		//Port º¯°æ
		factory.setPort(2221);
		/*
		factory.setSslConfiguration(ssl.createSslConfiguration());
		factory.setImplicitSsl(true);
		*/
		
		serverFactory.addListener("default", factory.createListener());
		
		
		
		Ftp_UserManager um = new Ftp_UserManager();
		//um.create_user("lee", "11", true);
		um.delete_user("lee");
		
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
