package com.ttl.common.utilities;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/*
 * Change List:
 * Task #: TTL-HK-ATYH-01052 - Anthony Tsang 20130423 - First Created (version 1.0)
 */
/**
 * A collection of utility methods to compress / decompress physical files.<br/>
 * <br/>
 * Supported Foramts (version 1.0)
 * <ul>
 * <li>ZIP format</li>
 * </ul>
 * 
 * @version 1.0
 */
public class ZipUtil
{
	/**
	 * Compress a collection of physical files into a Zip archive. Any directory in source file list will be skipped.
	 * @param pSourceFileList List of files to be compressed.
	 * @param pEntryNameList List of entry names representing each source file in the Zip archive. File under a directory could be added by
	 * having Entry Name in the format "&lt;folder&gt;/&lt;file&gt;".
	 * @param pTargetFile The Zip archive to be created.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void compressFiles(File [] pSourceFileList, String [] pEntryNameList, File pTargetFile) throws FileNotFoundException, IOException
	{
		ZipOutputStream lvOutputStream = null;
		FileInputStream lvInputStream = null;
		
		try
		{
			lvOutputStream = new ZipOutputStream(new FileOutputStream(pTargetFile));
			for (int i = 0; i < pSourceFileList.length; ++i)
			{
				File lvFile = pSourceFileList[i];
				if (lvFile.isDirectory())
				{
					continue;
				}
				
				lvInputStream = new FileInputStream(lvFile);
				
				lvOutputStream.putNextEntry(new ZipEntry(pEntryNameList[i]));
				
				int size;
	            byte[] buffer = new byte[1024];
	            
	            while ((size = lvInputStream.read(buffer, 0, buffer.length)) != -1)
	            {
	            	lvOutputStream.write(buffer, 0, size);
	            }
	            
	            lvOutputStream.closeEntry();
	            lvInputStream.close();
			}
			
			lvOutputStream.close();
		}
        finally
        {
        	try
        	{
        		lvOutputStream.closeEntry();
        	}
        	catch (IOException e)
        	{
        		// do nothing
        	}
        	
        	try
        	{
        		lvInputStream.close();
        	}
        	catch (IOException e)
        	{
        		// do nothing
        	}
			
        	try
        	{
        		lvOutputStream.close();
        	}
        	catch (IOException e)
        	{
        		// do nothing
        	}
        }
	}
	
	public static byte[] compressData(byte [][] pSourceByteArrayList, String [] pEntryNameList) throws FileNotFoundException, IOException
	{
		ZipOutputStream lvOutputStream = null;
		ByteArrayInputStream lvInputStream = null;
		
		try
		{
			ByteArrayOutputStream lvByteArrayOutputStream = new ByteArrayOutputStream(); 
			lvOutputStream = new ZipOutputStream(lvByteArrayOutputStream);
			for (int i = 0; i < pSourceByteArrayList.length; ++i)
			{
				lvInputStream = new ByteArrayInputStream(pSourceByteArrayList[i]);				
				lvOutputStream.putNextEntry(new ZipEntry(pEntryNameList[i]));
				
				int size;
	            byte[] buffer = new byte[1024];	            
	            while ((size = lvInputStream.read(buffer, 0, buffer.length)) != -1)
	            {
	            	lvOutputStream.write(buffer, 0, size);
	            }
	            
	            lvOutputStream.closeEntry();
	            lvInputStream.close();
			}			
			lvOutputStream.close();
			return lvByteArrayOutputStream.toByteArray();
		}
        finally
        {
        	try
        	{
        		lvOutputStream.closeEntry();
        	}
        	catch (IOException e)
        	{
        		// do nothing
        	}
        	
        	try
        	{
        		lvInputStream.close();
        	}
        	catch (IOException e)
        	{
        		// do nothing
        	}
			
        	try
        	{
        		lvOutputStream.close();
        	}
        	catch (IOException e)
        	{
        		// do nothing
        	}
        }
	}
	
	/**
	 * Decompress a Zip archive to a specific directory.
	 * @param pSourceFile The Zip archive to be decompressed.
	 * @param pTargetDir Target directory where decompressed files are to be saved.
	 * @return List of files decompressed.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static File [] decompressFile(File pSourceFile, File pTargetDir) throws FileNotFoundException, IOException
	{
		ZipInputStream lvZipInputStream = null;
		ZipEntry lvZipEntry;
		BufferedOutputStream lvOutputStream = null;
		
		try
		{
			Vector<File> lvDecompressedFileList = new Vector<File>();
			
			lvZipInputStream = new ZipInputStream(new BufferedInputStream(new FileInputStream(pSourceFile)));
			while ((lvZipEntry = lvZipInputStream.getNextEntry()) != null)
			{
				int lvSize;
				byte [] lvBuffer = new byte[2048];
				
				String lvEntryName = lvZipEntry.getName();
				File lvFile = new File(pTargetDir, lvEntryName);
				lvOutputStream = new BufferedOutputStream(new FileOutputStream(lvFile));
				
				while ((lvSize = lvZipInputStream.read(lvBuffer, 0, lvBuffer.length)) != -1)
				{
					lvOutputStream.write(lvBuffer, 0, lvSize);
				}
				
				lvOutputStream.flush();
				lvOutputStream.close();
				
				lvDecompressedFileList.add(lvFile);
			}
			
			return lvDecompressedFileList.toArray(new File[] {});
		}
		finally
		{
			if (lvOutputStream != null)
			{
				try
				{
					lvOutputStream.flush();
					lvOutputStream.close();
				}
				catch (IOException e)
				{
					// do nothing
				}
			}
			
			if (lvZipInputStream != null)
			{
				try
				{
					lvZipInputStream.close();
				}
				catch (IOException e)
				{
					// do nothing
				}
			}
		}
	}
	
	public static HashMap<String, byte []> decompressByteArray(byte [] pSourceByteArray) throws FileNotFoundException, IOException
	{
		ZipInputStream lvZipInputStream = null;
		ZipEntry lvZipEntry;
		BufferedOutputStream lvOutputStream = null;
		ByteArrayOutputStream lvByteArrayOutputStream = null;
		
		try
		{
			HashMap<String, byte []> lvDecompressedList = new HashMap<String, byte []>();
			
			lvZipInputStream = new ZipInputStream(new BufferedInputStream(new ByteArrayInputStream(pSourceByteArray)));
			while ((lvZipEntry = lvZipInputStream.getNextEntry()) != null)
			{
				int lvSize;
				byte [] lvBuffer = new byte[2048];
				
				lvByteArrayOutputStream = new ByteArrayOutputStream();  
				lvOutputStream = new BufferedOutputStream(lvByteArrayOutputStream);
				
				while ((lvSize = lvZipInputStream.read(lvBuffer, 0, lvBuffer.length)) != -1)
				{
					lvOutputStream.write(lvBuffer, 0, lvSize);
				}
				
				lvOutputStream.flush();
				lvOutputStream.close();
				
				String lvEntryName = lvZipEntry.getName();
				lvDecompressedList.put(lvEntryName, lvByteArrayOutputStream.toByteArray());
				lvByteArrayOutputStream.close();
			}
			
			return lvDecompressedList;
		}
		finally
		{
			if (lvOutputStream != null)
			{
				try
				{
					lvOutputStream.flush();
					lvOutputStream.close();
				}
				catch (IOException e)
				{
					// do nothing
				}
			}
			
			if (lvZipInputStream != null)
			{
				try
				{
					lvZipInputStream.close();
				}
				catch (IOException e)
				{
					// do nothing
				}
			}
			
			if (lvByteArrayOutputStream != null)
			{
				try
				{
					lvByteArrayOutputStream.close();
				}
				catch (IOException e)
				{
					// do nothing
				}			
			}
		}
	}
}