package operator;

import java.util.ArrayList;

import data_io.*;
import security.*;

public class LogicDriver {
	
	private CryptWorker cryptWorker;
	
	public LogicDriver()
	{
		try 
		{
			cryptWorker = new CryptWorker();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public ArrayList<Block> getInformation()
	{
		ArrayList<Block> information = null;
		try {
			if(StreamHandler.fileCheck(SecurityData.SECURE_IMPORT) == true)
			{
				information = StreamHandler.getBlockList(cryptWorker.decryptData(
				StreamHandler.readFile(SecurityData.PASSWORD_FILE), StreamHandler.readKeyFile(SecurityData.KEY_FILE)));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return information;
	}
	
	public void saveInformation(ArrayList<Block> information)
	{
		try {
			StreamHandler.deleteOldFiles();
			StreamHandler.writeKeyFile(SecurityData.KEY_FILE, cryptWorker.getCurrentKey());
			StreamHandler.writeFile(SecurityData.PASSWORD_FILE, cryptWorker.encryptData(StreamHandler.getBlockByteArray(information)));
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void backupInformation(ArrayList<Block> allInformation)
	{
		try {
			StreamHandler.writeTextFile(allInformation);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public ArrayList<Block> importInformation()
	{
		ArrayList<Block> allInformation = null;
		
		try {
			allInformation = StreamHandler.readTextFile();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return allInformation;
	}
}
