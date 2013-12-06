package com.meteor.module;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.UserManager;

import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.SaltedPasswordEncryptor;

import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class Ftp_UserManager {

	Logger logger = LoggerFactory.getLogger("UserManager");

	
	private PropertiesUserManagerFactory userManagerFactory;
	private UserManager um;
	private String store_home = "C://FTP_STORE";
	private String user_File_name = "c://stone/stone.properties";
	
	public Ftp_UserManager(){
		
		userManagerFactory = new PropertiesUserManagerFactory();
		userManagerFactory.setFile(new File(user_File_name) );
		userManagerFactory.setPasswordEncryptor(new SaltedPasswordEncryptor());
		
		um = userManagerFactory.createUserManager();
	}
	/**
	 * Create Ftp_User
	 * @param id : User Name
	 * @param pwd : PassWord
	 * @param doWriter : Write Permission
	 */
	public void create_user(String id, String pwd, boolean doWriter){
		
		try {
			if(!um.doesExist(id)){
			BaseUser user = new BaseUser();
			
			user.setName(id);
			user.setPassword(pwd);
			user.setHomeDirectory(store_home);
			
				if(doWriter){
					List<Authority> auth = new ArrayList<Authority>();
					auth.add(new WritePermission());
					user.setAuthorities(auth);
				}
			
				um.save(user);
				logger.info("OK User Create");
			}//
		} catch (FtpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param id : User Name
	 */
	public boolean delete_user(String id){
		
		
		try {
			if(um.doesExist(id)){
				um.delete(id);
				
				logger.info("OK User Delete");
				return true;
			}
		} catch (FtpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		logger.info("No User Delete");
		return false;
	
	
	}
	public UserManager get_UserManager(){
		return um;
	}
	/**
	 * 
	 * @param id : User Name
	 * @param doWriter : Write Permission
	 */
	public boolean set_user_WritePermission(String id, boolean doWriter){
		try {
			if(um.doesExist(id)){
				BaseUser user = (BaseUser) um.getUserByName(id); 
				List<Authority> auth = new ArrayList<Authority>();
				
				
				if(doWriter){
					auth.add(new WritePermission());	
				}
				user.setAuthorities(auth);
				um.save(user);
				return true;
				
			}
		} catch (FtpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				return false;
	}
	//
	public boolean set_user_Dir(String id, String dir){
		try {
			if(um.doesExist(id)){
				BaseUser user = (BaseUser) um.getUserByName(id); 
				user.setHomeDirectory(dir);
				
				um.save(user);
				return true;
			}
		} catch (FtpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				return false;
	}
	public boolean set_user_Pwd(String id, String pwd){
		try {
			if(um.doesExist(id)){
				BaseUser user = (BaseUser) um.getUserByName(id); 
				user.setPassword(pwd);
				um.save(user);
				return true;
			}
		} catch (FtpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				return false;
	}
}

