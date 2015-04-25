package data_io;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.security.Key;
import java.util.ArrayList;

import security.SecurityData;

/*
 * StreamHandler: 
 * Contains functions for handling all File I/O and Serialization
 * This class could be further generalized by having functions return Objects; code which invokes
 * the method would then be responsible for casting it into an appropriate object.
 */
public class StreamHandler {
	
	public static boolean fileCheck(String importType)
	{
		boolean exists = true;
		
		if(importType == SecurityData.SECURE_IMPORT)
		{
			File passwordFile = new File(SecurityData.PASSWORD_FILE);
			File keyFile = new File(SecurityData.KEY_FILE);
			if(!passwordFile.exists() || !keyFile.exists())
			{
				exists = false;
			}
		}
		else
		{
			File backupFile = new File(SecurityData.TEXT_FILE);
			if(!backupFile.exists())
			{
				exists = false;
			}
		}
		
		return exists;
	}
	
	public static void deleteOldFiles()
	{
		File keyFile = new File(SecurityData.KEY_FILE);
		keyFile.delete();
		File passwordFile = new File(SecurityData.PASSWORD_FILE);
		passwordFile.delete();
	}	
	
	public static Key readKeyFile(String path) 
	throws FileNotFoundException, IOException, ClassNotFoundException
	{
		FileInputStream fiStream = new FileInputStream(path);
		ObjectInputStream oiStream = new ObjectInputStream(fiStream);
		Key theKey = (Key)oiStream.readObject();
		
		oiStream.close();
		return theKey;
	}
	
	public static byte[] readFile(String path) 
	throws IOException, FileNotFoundException
	{
		FileInputStream in = new FileInputStream(path);
		ArrayList<Byte> allBytes = new ArrayList<Byte>();
		
		byte[] singleByteArray = new byte[1];
		while((in.read(singleByteArray)) != -1)
		{
			allBytes.add(singleByteArray[0]);
		}		
		in.close();
		
		byte[] allBytesArray = new byte[allBytes.size()];
		int counter = 0;
		for(Byte b : allBytes)
		{
			allBytesArray[counter] = b.byteValue();
			counter++;
		}
		
		return allBytesArray;
	}
	
	public static void writeKeyFile(String path, Key newKey)
	throws FileNotFoundException, IOException
	{	
		ObjectOutputStream ooStream = new ObjectOutputStream(new FileOutputStream(path));
		ooStream.writeObject(newKey);
		
		ooStream.close();
	}
	
	public static void writeFile(String path, byte[] data) 
	throws IOException, FileNotFoundException
	{
		FileOutputStream out = new FileOutputStream(path);
		out.write(data);
		
		out.close();
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<Block> getBlockList(byte[] data)
	throws IOException, ClassNotFoundException
	{
		ByteArrayInputStream byteInputStream = new ByteArrayInputStream(data);
		ObjectInputStream oiStream = new ObjectInputStream(byteInputStream);
		
		return (ArrayList<Block>)oiStream.readObject();
	}
	
	public static byte[] getBlockByteArray(ArrayList<Block> information)
	throws IOException
	{
		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream ooStream = new ObjectOutputStream(byteOutputStream);
		
		ooStream.writeObject(information);
		return byteOutputStream.toByteArray();
	}
	
	public static void writeTextFile(ArrayList<Block> allInformation) 
	throws IOException
	{
		PrintWriter pwriter = new PrintWriter(new FileWriter(SecurityData.TEXT_FILE));
		
		for(Block b : allInformation)
		{
			pwriter.print((b.getName().charAt(0) == ' ' ? b.getName().substring(1) : b.getName()) + ":");
			pwriter.println();
			
			char[] infoChars = b.getInformation().toCharArray();
			ArrayList<String> individualLines = new ArrayList<String>();
			for(int i = 0, start_i = 0; i < infoChars.length; i++)
			{	
				if(infoChars[i] == '\n')
				{
					String extractedLine = "";
					for(int j = start_i; j < i; j++)
					{
						extractedLine += String.valueOf(infoChars[j]);
					}
					
					individualLines.add(extractedLine);
					start_i = i + 1;
				}
			}
			
			for(String s : individualLines)
			{
				pwriter.print(s);
				pwriter.println();
			}
			pwriter.print(SecurityData.SEPAPRATOR);
			pwriter.println();
		}
		
		pwriter.close();
	}
	
	public static ArrayList<Block> readTextFile() 
	throws IOException
	{
		ArrayList<Block> allInformation = null;
		
		if(fileCheck(SecurityData.TEXT_IMPORT))
		{
			allInformation = new ArrayList<Block>();
			File backupFile = new File(SecurityData.TEXT_FILE);
			BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(backupFile.toPath())));
			
			String theLine = reader.readLine(), tempLine = "";
			do
			{
				Block newBlock = new Block(theLine.substring(0, theLine.length() - 1));	//Get Rid of the leading ':'
				
				theLine = "";
				tempLine = reader.readLine();
				while(tempLine.compareTo(SecurityData.SEPAPRATOR) != 0)
				{
					theLine += tempLine + "\n";
					tempLine = reader.readLine();				
				}
				
				newBlock.setInformation(theLine);
				allInformation.add(newBlock);
				
				theLine = reader.readLine();
			} while(theLine != null && theLine.compareTo("") != 0);
		}
		
		return allInformation;
	}
}
