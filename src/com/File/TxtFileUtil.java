package com.File;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;

import foundation.dataService.util.DateService;

/**
 * 
 * @author nyc
 * 
 */
public class TxtFileUtil {
	private static File fv = new File("/sdcard/SpeedServiceLog.txt"/**文件路径名**/);
	public static File loginFlag = new File("/sdcard/loginLog.txt"/**文件路径名**/);
	public static File totalSportData = new File("/sdcard/totalSportData.txt"/**文件路径名**/);
	/**
	 * @param txtFile
	 * @return
	 */
	public static boolean createFile(File txtFile) throws Exception {
		boolean flag = false;
		try {
			if (!txtFile.exists()) {
				txtFile.createNewFile();
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * @param txtFile
	 * @return
	 */
	public static String readTxtFile(File txtFile) throws Exception {
		String result = "";
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		try {
			fileReader = new FileReader(txtFile);
			bufferedReader = new BufferedReader(fileReader);
			try {
				String read = null;
				while ((read = bufferedReader.readLine()) != null) {
					if(!read.equals("\r\n")){
					result = result + read + "\r\n";
					}
					else{
						result=result+read;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				bufferedReader.close();
			}
			if (fileReader != null) {
				fileReader.close();
			}
		}
		return result;
	}

	public static boolean compareFiles(File srcFile, File desFile) {

		return getFileMD5(srcFile).equals(getFileMD5(desFile));
		
	}
	
	private static String getFileMD5(File file) {
	    if (!file.isFile()){
	      return null;
	    }
	    MessageDigest digest = null;
	    FileInputStream in=null;
	    byte buffer[] = new byte[1024];
	    int len;
	    try {
	      digest = MessageDigest.getInstance("MD5");
	      in = new FileInputStream(file);
	      while ((len = in.read(buffer, 0, 1024)) != -1) {
	        digest.update(buffer, 0, len);
	      }
	      in.close();
	    } catch (Exception e) {
	      e.printStackTrace();
	      return null;
	    }
	    BigInteger bigInt = new BigInteger(1, digest.digest());
	    return bigInt.toString(16);
	  }
	
	/**
	 * @param txtFile
	 */

	public static boolean appendToFile(String content, File txtFile) {
		boolean append = false;
		boolean result = false;

		try {
			if (txtFile.exists())
				append = true;
			FileWriter fw = new FileWriter(txtFile, append);
			BufferedWriter bf = new BufferedWriter(fw);
			bf.append(content);
			result = true;
			bf.flush();
			bf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static boolean writeTxtFile(String content, File txtFile)
			throws Exception {
		// RandomAccessFile mm = null;
		boolean flag = false;
		FileOutputStream outStream = null;
		try {

			outStream = new FileOutputStream(txtFile);
			if (txtFile.exists()) {
				txtFile.delete();
			}

			outStream.write((new String("")).getBytes());
			outStream.flush();
			outStream.write(content.getBytes("utf8"));
			outStream.close();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static void copyFile(File frmFile, File toFile) {

		String content = "";
		try {
			content = readTxtFile(frmFile);
			if (toFile.exists()) {
				toFile.delete();
			}
			writeTxtFile(content, toFile);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public static void writeSpeed(String content) {
		// TODO 
		appendToFile(DateService.getMin()+"：\t"+content, fv);
	}
	public static void writeLoginFlag(String content) {
		// TODO 
		deleteLoginFlagFile();
		appendToFile(content, loginFlag);
	}
	public static void writeSportData(String content) {
		// TODO 
		boolean flag = false;
		FileOutputStream outStream = null;
		try {

			outStream = new FileOutputStream(totalSportData);
			if (totalSportData.exists()) {
				totalSportData.delete();
			}
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		appendToFile(content, totalSportData);
	}
	
	public static boolean deleteLoginFlagFile(){
		boolean flag = false;
		FileOutputStream outStream = null;
		try {

			outStream = new FileOutputStream(loginFlag);
			if (loginFlag.exists()) {
				loginFlag.delete();
			}
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
}
