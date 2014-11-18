package com.meteor.module;

import java.io.File;
import java.io.IOException;
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
	private UserManager user_manager;
	private String store_home = "C://FTP_STORE";
	private String Configuration_File_Name = "c://stone/stone.properties";
	
	public Ftp_UserManager(){
		
		userManagerFactory = new PropertiesUserManagerFactory();
		File configFile = new File( Configuration_File_Name );
		
		if(!configFile.exists()){//if Not Exist File, Create New File
			try {
				configFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		userManagerFactory.setFile( configFile );
		userManagerFactory.setPasswordEncryptor(new SaltedPasswordEncryptor());
		
		user_manager = userManagerFactory.createUserManager();
	}
	/**
	 * Create Ftp_User
	 * @param id : User Name
	 * @param pwd : PassWord
	 * @param doWriter : Write Permission
	 */
	public void create_user(String id, String pwd, boolean doWriter){
		
		try {
			if(!user_manager.doesExist(id)){
			BaseUser user = new BaseUser();
			
			user.setName(id);
			user.setPassword(pwd);
			user.setHomeDirectory(store_home);
			
				if(doWriter){
					List<Authority> auth = new ArrayList<Authority>();
					auth.add(new WritePermission());
					user.setAuthorities(auth);
				}
			
				user_manager.save(user);
				logger.info("OK User Create");
			}//
		} catch (FtpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Delete User
	 * @param id : User Name
	 */
	public boolean delete_user(String id){
		
		
		try {
			if(user_manager.doesExist(id)){
				user_manager.delete(id);
				
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
		return user_manager;
	}
	/**
	 * Setting Permit to User for Write
	 * @param id : User Name
	 * @param doWriter : Write Permission
	 */
	public boolean set_user_WritePermission(String id, boolean doWriter){
		try {
			if(user_manager.doesExist(id)){
				BaseUser user = (BaseUser) user_manager.getUserByName(id); 
				List<Authority> auth = new ArrayList<Authority>();
				
				
				if(doWriter){
					auth.add(new WritePermission());	
				}
				user.setAuthorities(auth);
				user_manager.save(user);
				return true;
				
			}
		} catch (FtpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				return false;
	}

	/**
	 * Setting Home Dir
	 * @param id
	 * @param dir
	 * @return
	 */
	public boolean set_user_Dir(String id, String dir){
		try {
			if(user_manager.doesExist(id)){
				BaseUser user = (BaseUser) user_manager.getUserByName(id); 
				user.setHomeDirectory(dir);
				
				user_manager.save(user);
				return true;
			}
		} catch (FtpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				return false;
	}
	/**
	 * Setting User Pwd
	 * @param id
	 * @param pwd
	 * @return
	 */
	public boolean set_user_Pwd(String id, String pwd){
		try {
			if(user_manager.doesExist(id)){
				BaseUser user = (BaseUser) user_manager.getUserByName(id); 
				user.setPassword(pwd);
				user_manager.save(user);
				return true;
			}
		} catch (FtpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				return false;
	}
}

