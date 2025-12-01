package com.com.epattern.common.utilities;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;


public class CoreUtils
{

	public static final int NUMERIC = 1;
	public static final int STRING = 2;
	public static final String VCCLIENTLIST = "VCCLIENTLIST";
	public static final String DECIMALFORMAT6 = "#,##0.000000";
	public static final String DECIMALFORMAT5 = "#,##0.00000";
	public static final String DECIMALFORMAT4 = "#,##0.0000";
	public static final String DECIMALFORMAT3 = "#,##0.000";
	public static final String DECIMALFORMAT2 = "#,##0.00";
	public static final String DECIMALFORMAT1 = "#,##0.0";
	public static final String DECIMALFORMAT0 = "#,##0";
	public static final String CHINESEDATEFORMAT = "yyyy年MM月dd日";
	public static final String SERVERDATEFORMAT = "yyyy-MM-dd";
	public static final String SERVERTIMEFORMAT = "HH:mm:ss";
	public static final String SERVERDATETIMEFORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String CLIENTDATEFORMAT = "dd-MM-yyyy";
	public static final String CLIENTTIMEFORMAT = "HH:mm:ss";
	public static final String CLIENTDATETIMEFORMAT = "yyyy-MM-dd.HH:mm:ss";
	public static final String[] svMonthShortDesc = new String[]{"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC" };
	public static final String[] svChineseNumber = new String[]{"零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二", "十三", "十四", "十五", "十六", "十七", "十八", "十九", "二十", "二十一", "二十二", "二十三", "二十四", "二十五", "二十六", "二十七", "二十八", "二十九", "三十", "三十一"};
	public static final String EXCHANGEDIRECTION_DIRECT = "D";
	public static final String EXCHANGEDIRECTION_INDIRECT = "I";
	
	public static String formatServerDate(Date pvDate)
	{
		return new SimpleDateFormat(SERVERDATEFORMAT).format(pvDate);
	}

	public static String formatServerDateTime(Date pvDate)
	{
		return new SimpleDateFormat(SERVERDATETIMEFORMAT).format(pvDate);
	}
	
	public static Timestamp parseServerTimestamp(String pvDate) throws ParseException
	{
		return new Timestamp(new SimpleDateFormat(SERVERDATEFORMAT).parse(pvDate).getTime());
	}
	
	public static Timestamp parseServerDateTimeTimestamp(String pvDateTime) throws ParseException
	{
		return new Timestamp(new SimpleDateFormat(SERVERDATETIMEFORMAT).parse(pvDateTime).getTime());
	}

	
	public static Map<String, Integer> mvInstrumentFormatMap;
	
	public static void initInstrumentFormatMap(Map<String, Integer> pInstrumentFormatMap)
	{
		mvInstrumentFormatMap = pInstrumentFormatMap;
	}
	
	public static String getLocalHostAddress()
	{
		String lvLocalHostAddress = null;
		try
		{
			InetAddress addr = InetAddress.getLocalHost();
			lvLocalHostAddress = addr.getHostName();
		}
		catch (UnknownHostException e)
		{
		}
		return CoreUtils.trim(lvLocalHostAddress).toUpperCase();
	}
	
	public static double parseDouble(String pValue)
	{
		return CoreUtils.parseDouble(pValue, false);
	}

	public static double parseDouble(String pValue, boolean pShowException)
	{
		double lvResult;
		try
		{
			lvResult = Double.parseDouble(CoreUtils.trim(pValue, CoreUtils.NUMERIC));
		}
		catch (Exception ex)
		{
			lvResult = 0;
			if (pShowException)
				ex.printStackTrace();
		}
		return lvResult;
	}

	public static int parseInt(String pValue)
	{
		return CoreUtils.parseInt(pValue, false);
	}

	public static int parseInt(String pValue, boolean pShowException)
	{
		int lvResult;

		try
		{
			lvResult = Double.valueOf(CoreUtils.trim(pValue, CoreUtils.NUMERIC)).intValue();
		}
		catch (Exception ex)
		{
			lvResult = 0;
			if (pShowException)
				ex.printStackTrace();
		}
		return lvResult;
	}

	public static long parseLong(String pValue)
	{
		return CoreUtils.parseLong(pValue, false);
	}

	public static long parseLong(String pValue, boolean pShowException)
	{
		long lvResult;

		try
		{
			lvResult = Double.valueOf(CoreUtils.trim(pValue, CoreUtils.NUMERIC)).longValue();
		}
		catch (Exception ex)
		{
			lvResult = 0;
			if (pShowException)
				ex.printStackTrace();
		}
		return lvResult;
	}

	public static boolean isZero(Object pValue)
	{
		return isZero(CoreUtils.parseBigDecimal(pValue));
	}

	public static boolean isZero(BigDecimal pValue)
	{
		return pValue.signum() == 0;
	}

	public static boolean isPositive(Object pValue)
	{
		return isPositive(CoreUtils.parseBigDecimal(pValue));
	}

	public static boolean isPositive(BigDecimal pValue)
	{
		return pValue.signum() > 0;
	}

	public static boolean isNegative(Object pValue)
	{
		return isNegative(CoreUtils.parseBigDecimal(pValue));
	}

	public static boolean isNegative(BigDecimal pValue)
	{
		return pValue.signum() < 0;
	}

	public static boolean isNonNegative(Object pValue)
	{
		return isNonNegative(CoreUtils.parseBigDecimal(pValue));
	}

	public static boolean isNonNegative(BigDecimal pValue)
	{
		return pValue.signum() >= 0;
	}

	public static boolean isNonPositive(Object pValue)
	{
		return isNonPositive(CoreUtils.parseBigDecimal(pValue));
	}

	public static boolean isNonPositive(BigDecimal pValue)
	{
		return pValue.signum() <= 0;
	}
	
	public static <T extends Comparable<T>> boolean isBetween(T value, T start, T end) {
	    return value.compareTo(start) >= 0 && value.compareTo(end) <= 0;
	}
	
	public static BigDecimal parseBigDecimal(Object pValue)
	{
		return CoreUtils.parseBigDecimal(pValue==null?null:pValue.toString());
	}
	
	public static BigDecimal parseBigDecimal(String pValue)
	{
		return CoreUtils.parseBigDecimal(pValue, false);
	}

	public static BigDecimal parseBigDecimal(String pValue, boolean pShowException)
	{
		BigDecimal lvResult;
		try
		{
			lvResult = new BigDecimal(CoreUtils.trim(pValue, CoreUtils.NUMERIC));
		}
		catch (Exception ex)
		{
			lvResult = new BigDecimal(0);
			if (pShowException)
				ex.printStackTrace();
		}
		return lvResult;
	}

	public static boolean isInteger(double pvValue)
	{
		double lvCompare = 0;
		boolean lvReturn = false;

		lvCompare = pvValue - new Double(pvValue).intValue();
		if (lvCompare == 0)
		{
			lvReturn = true;
		}
		else
		{
			lvReturn = false;
		}
		return (lvReturn);
	}

	public static boolean isNullStr(Object pStr)
	{
		return CoreUtils.isNullStr(pStr == null ? null : pStr.toString());
	}
	
	public static boolean isNullStr(String pStr)
	{
		if (pStr == null)
			return true;
		else
		{
			String pTrimStr = pStr.trim();
			return pTrimStr.length() == 0 || pTrimStr.equalsIgnoreCase("null");
		}
	}

	public static String coalesce(String pValue, String pSubstituteValue)
	{
		return !isNullStr(pValue) ? pValue : pSubstituteValue;
	}

	public static BigDecimal coalesce(BigDecimal pValue, BigDecimal pSubstituteValue)
	{
		return pValue != null ? pValue : pSubstituteValue;
	}

	public static java.sql.Date coalesce(java.sql.Date pValue, java.sql.Date pSubstituteValue)
	{
		return pValue != null ? pValue : pSubstituteValue;
	}

	public static java.sql.Timestamp coalesce(java.sql.Timestamp pValue, java.sql.Timestamp pSubstituteValue)
	{
		return pValue != null ? pValue : pSubstituteValue;
	}

	public static String trim(Object pvValue)
	{
		return CoreUtils.trim(pvValue, CoreUtils.STRING);
	}

	public static String trim(String pvValue)
	{
		return CoreUtils.trim(pvValue, CoreUtils.STRING);
	}

	public static String trim(Object pvValue, int pvType)
	{
		return CoreUtils.trim(pvValue == null ? null : pvValue.toString(), pvType);
	}
	
	public static String trim(String pvValue, int pvType)
	{
		if (CoreUtils.isNullStr(pvValue))
		{
			if (pvType == CoreUtils.NUMERIC)
				return "0";
			else if (pvType == CoreUtils.STRING)
				return "";
		}
		return pvValue.trim();
	}

	public static String trim(String pData, char pFind)
	{
		return (CoreUtils.trim(pData, pFind, ' '));
	}

	public static String trim(String pData, char pFind, char pReplace)
	{
		boolean lvFront = false;
		boolean lvBack = false;
		int lvDocPos = pData.indexOf(".");
		char[] lvData = pData.toCharArray();
		int lvLen = lvData.length;
		if (lvDocPos == -1)
		{
			lvBack = true;
		}
		for (int i = 0; i <= lvLen - 1; i++)
		{
			if (lvFront && lvBack)
			{
				break;
			}
			if (lvData[i] == pFind && !lvFront)
			{
				lvData[i] = pReplace;
			}
			else
			{
				lvFront = true;
			}

			if ( (lvData[lvLen - i - 1] == pFind || lvData[lvLen - i - 1] == '.') && !lvBack)
			{
				if (lvData[lvLen - i - 1] == '.')
				{
					lvBack = true;

				}
				lvData[lvLen - i - 1] = pReplace;
			}
			else
			{
				lvBack = true;
			}
		}
		return (new String(lvData).trim());
	}

	public static String formatPrice(String pPrice, DecimalFormat pFormatter)
	{
		String pCheckAOPrice = "AO";
		return formatPrice(pPrice, pFormatter, pCheckAOPrice);
	}

	public static String formatPrice(String pPrice, DecimalFormat pFormatter, String pCheckAOPrice)
	{
		if (pPrice != null && pPrice.trim().equals("AO"))
			return "AO";

		BigDecimal lvdPrice = new BigDecimal(pPrice.trim());
		return pFormatter.format(lvdPrice);
	}

	public static String formatQty(double pQty, DecimalFormat pFormatter)
	{
		String lvValue = "";
		if (pQty >= 1000000000)
		{
			pQty /= 1000000000;
			lvValue = "B";
		}
		else if (pQty >= 1000000)
		{
			pQty /= 1000000;
			lvValue = "M";
		}
		else if (pQty >= 10000)
		{
			pQty /= 1000;
			lvValue = "K";
		}

		DecimalFormat mvFormatter1 = new DecimalFormat("#0.0");
		DecimalFormat mvFormatter2 = new DecimalFormat("#0");

		if (pFormatter.equals(mvFormatter2))
			return mvFormatter2.format(pQty) + lvValue;

		if (isNullStr(lvValue))
			return mvFormatter2.format(pQty) + lvValue;
		else
			return mvFormatter1.format(pQty) + lvValue;
	}

	public static Timestamp getOffSetedDate(int pOffsetDays, java.sql.Timestamp pvCurrentDate)
	{

		Timestamp lvNextDate = (Timestamp) pvCurrentDate.clone();
		if (pOffsetDays != 0)
		{
			long lvSeconds = lvNextDate.getTime();
			lvSeconds += (pOffsetDays * 24 * 60 * 60 * 1000);
			lvNextDate.setTime(lvSeconds);
		}
		return lvNextDate;
	}

	public static String formatDate(String pDate)
	{
		if (!CoreUtils.isNullStr(pDate))
		{
			if ( (pDate.indexOf(" ") != -1) && (pDate.startsWith("2")))
				pDate = pDate.substring(0, pDate.indexOf(" "));
		}
		return pDate;
	}

	public static String formatTime(String pTime)
	{
		if (!CoreUtils.isNullStr(pTime))
		{
			if ( (pTime.indexOf(" ") != -1) && (pTime.lastIndexOf(".") != -1))
				pTime = pTime.substring(pTime.indexOf(" ") + 1, pTime.lastIndexOf("."));
		}
		return pTime;
	}
	
	public static HashSet<String> splitString(String pValue, String pRegex)
	{
		return splitString(pValue, pRegex, false);
	}
	
	public static HashSet<String> splitString(String pValue, String pRegex, boolean pIgnoreEmptyValue)
	{
		HashSet<String> lvSet = new HashSet<>();
		if (pValue != null)
		{
			String[] lvValues = pValue.split(pRegex);
			for (int i = 0; i < lvValues.length; i++)
			{
				if (pIgnoreEmptyValue && isNullStr(lvValues[i]))
					continue;
				lvSet.add(lvValues[i]);
			}
		}
		return lvSet.isEmpty() ? null : lvSet;
	}
	
	public static boolean isNumeric(char pChar)
	{
		return pChar >= '0' && pChar <= '9';
	}
	
	public static int countEnglishChar(String pValue)
	{
		int lvCount = 0;
		char[] lvChars =  pValue.toCharArray();
		for (int i=0;i<lvChars.length;i++)
		{
			if (lvChars[i] >= 0 && lvChars[i]<=255)
			{
				lvCount++;
			}
		}
		return lvCount; 
	}	
	
	public static String rightTrim(String value)
	{
		return ("'" + value).trim().substring(1);
	}
	public static String formatChineseDate(Date pDate) throws IllegalArgumentException
	{
		Calendar lvCalendar = Calendar.getInstance();
		lvCalendar.setTime(pDate);

		StringBuffer lvReturn = new StringBuffer();
		int lvYear = lvCalendar.get(Calendar.YEAR);
		String lvYearString = String.valueOf(lvYear);
		for (int i = 0; i < lvYearString.length(); i++)
		{
			int lvYearDigit = lvYearString.charAt(i) - '0';
			lvReturn.append(svChineseNumber[lvYearDigit]);
		}
		lvReturn.append("年");
		lvReturn.append(svChineseNumber[lvCalendar.get(Calendar.MONTH) + 1] + "月" + svChineseNumber[lvCalendar.get(Calendar.DAY_OF_MONTH)] + "日");

		return lvReturn.toString();
	}
	
	public static Object convertBase64EncodeStringToObject(String pBase64EncodeString) 
	{
		byte[] lvByteArray = Base64.base64ToByteArray(pBase64EncodeString);
		ObjectInputStream lvObjectInputStream;
		Object lvReturnObject = null;
		try
		{
			lvObjectInputStream = new ObjectInputStream(new ByteArrayInputStream(lvByteArray));
			lvReturnObject =  lvObjectInputStream.readObject();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		return lvReturnObject;
	}
	
	public static String convertObjectToBase64EncodeString (Object pObject) throws IOException{
		String lvReturnString = null;
		ByteArrayOutputStream lvByteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream lvObjectOutputStream = new ObjectOutputStream(lvByteArrayOutputStream);
		lvObjectOutputStream.writeObject(pObject);
		lvObjectOutputStream.close();
		lvReturnString = Base64Utils.base64Encode(lvByteArrayOutputStream.toByteArray());
		return lvReturnString;
	}
	
	public static StringBuffer replaceString(String source, String from, String to)
	{
		StringBuffer	newContent = new StringBuffer();
		int				startSearchIndex = 0;
		int				patternStartIndex = 0;
		while ((patternStartIndex = source.indexOf(from, startSearchIndex)) != -1)
		{
			newContent.append(source.substring(startSearchIndex, patternStartIndex));
			newContent.append(to);
			startSearchIndex = patternStartIndex + from.length();
		}
		newContent.append(source.substring(startSearchIndex, source.length()));
		return newContent;
	}
	
	public static long calculateDays(Date dateEarly, Date dateLater) {  
		return (dateLater.getTime() - dateEarly.getTime()) / (24 * 60 * 60 * 1000);  
	}
	
	public static BigDecimal calculateYears(String dateEarlyStr, String dateLaterStr)
	{
		 SimpleDateFormat lvFormat = new SimpleDateFormat(SERVERDATEFORMAT);
		 Date dateEarly;
		 Date dateLater;

		 try 
		 {
			 dateEarly = lvFormat.parse(dateEarlyStr);
			 dateLater = lvFormat.parse(dateLaterStr);
		 }
		 catch (ParseException e) 
		 {
			 e.printStackTrace();
			 return BigDecimal.ZERO;
		 }

		 return calculateYears(dateEarly, dateLater);
	}
	
	public static BigDecimal calculateYears(Date dateEarly, Date dateLater)
	{
		 double lvTimeDiffInYear = (dateLater.getTime() - dateEarly.getTime()) / (1000.0 * 60.0 * 60.0 * 24.0 * 365.0);
		 return new BigDecimal(lvTimeDiffInYear).setScale(1, BigDecimal.ROUND_HALF_UP);
	}
	
	public static boolean removeDirectory(File directory)
	{
		if (directory == null)
			return false;
		if (!directory.exists())
			return true;
		if (!directory.isDirectory())
			return false;
		String[] list = directory.list();
		if (list != null)
		{
			for (int i = 0; i < list.length; i++)
			{
				File entry = new File(directory, list[i]);
				
				if (entry.isDirectory())
				{
					if (!removeDirectory(entry))
						return false;
				}
				else
				{
					if (!entry.delete())
						return false;
				}
			}
		}
		return directory.delete();
	}

 	public static int countDigit(String pString)
    {	
    	int lvCount = 0;
    	if (pString != null && pString.length()>0)
    	{
    		char[] lvChars = pString.toCharArray();
    		for (int i=0;i<lvChars.length;i++)
    		{
    			if (lvChars [i]>='0' && lvChars [i]<='9')
    			{
    				++lvCount;
    			}
    		}
    	}
    	return lvCount;
    }

    public static int countAlphaChar(String pString)
    {	
    	int lvCount = 0;
    	if (pString != null && pString.length()>0)
    	{
    		char[] lvChars = pString.toCharArray();
    		for (int i=0;i<lvChars.length;i++)
    		{
    			if ((lvChars [i]>='A' && lvChars [i]<='Z' ) ||
    					(lvChars [i]>='a' && lvChars [i]<='z'))
    			{
    				++lvCount;
    			}
    		}
    	}
    	return lvCount;
    }
    
    public static int countUpperChar(String pString)
    {
        int lvCount = 0;
        if (pString != null)
        {
            for (int i = 0; i < pString.length(); i++)
            {
                char lvChar = pString.charAt(i);
                if (lvChar >= 'A' && lvChar <= 'Z')
                {
                    lvCount++;
                }
            }
        }
        return lvCount;
    }
    
    public static int countLowerChar(String pString)
    {
        int lvCount = 0;
        if (pString != null)
        {
            for (int i = 0; i < pString.length(); i++)
            {
                char lvChar = pString.charAt(i);
                if (lvChar >= 'a' && lvChar <= 'z')
                {
                    lvCount++;
                }
            }
        }
        return lvCount;
    }
    
	 public static boolean copyFile(File oldFile, String newPath) {
		 
		 boolean success = false;
		 File newFile = new File(newPath);
		 InputStream inStream = null;  
		 FileOutputStream fout = null;
		 try {  
			 int bytesum = 0;  
			 int byteread = 0;
			 if (oldFile.exists())
			 {
				 inStream = new FileInputStream(oldFile);  
				 fout = new FileOutputStream(newPath);  

				 byte[] buffer = new byte[1024];  

				 while ((byteread = inStream.read(buffer)) != -1) {  
					 bytesum += byteread;  
					 fout.write(buffer, 0, byteread);  
				 }  
				 fout.flush();
			 }  
			 success = true;
		 } catch (Exception e) {  
			 e.printStackTrace();  
		 } finally {  
			 try {  
				 if (inStream != null) {  
					 inStream.close();  
				 }    
				 if (fout != null) {  
					 fout.close();  
				 }  
			 } catch (IOException e) {  
				 e.printStackTrace();  
			 }  
		 }
		 return success;
	 }  
	
	 public static Object zipConvertBase64EncodeStringToObject(String pBase64EncodeString) throws IOException
	 {
		 byte[] lvByteArray = Base64.base64ToByteArray(pBase64EncodeString);
		 ObjectInputStream lvObjectInputStream = null;
		 GZIPInputStream lvGZIPInputStream = null;
		 ByteArrayInputStream lvByteArrayInputStream = null;
		 Object lvReturnObject = null;
		 try
		 {
			 lvByteArrayInputStream = new ByteArrayInputStream(lvByteArray);
			 lvGZIPInputStream = new GZIPInputStream(lvByteArrayInputStream);
			 lvObjectInputStream = new ObjectInputStream(lvGZIPInputStream);
			 lvReturnObject = lvObjectInputStream.readObject();
		 }
		 catch (ClassNotFoundException e)
		 {
			 e.printStackTrace();
		 }
		 finally
		 {
			 if (lvByteArrayInputStream != null)
			 {
				 try {
					 lvByteArrayInputStream.close();
				 } catch (IOException e) {
					 e.printStackTrace();
				 }
			 }

			 if (lvGZIPInputStream != null)
			 {
				 try {
					 lvGZIPInputStream.close();
				 } catch (IOException e) {
					 e.printStackTrace();
				 }
			 }

			 if (lvObjectInputStream != null)
			 {
				 try {
					 lvObjectInputStream.close();
				 } catch (IOException e) {
					 e.printStackTrace();
				 }
			 }
		 }
		 return lvReturnObject;
	 }

	 public static String zipConvertObjectToBase64EncodeString(Object pObject) throws IOException
	 {
		 String lvReturnString = null;

		 ByteArrayOutputStream lvByteArrayOutputStream = null;
		 GZIPOutputStream lvZipOutputStream = null;
		 ObjectOutputStream lvObjectOutputStream = null;

		 try
		 {
			 lvByteArrayOutputStream = new ByteArrayOutputStream();
			 lvZipOutputStream = new GZIPOutputStream(lvByteArrayOutputStream);
			 lvObjectOutputStream = new ObjectOutputStream(lvZipOutputStream);
			 lvObjectOutputStream.writeObject(pObject);
			 lvObjectOutputStream.close();

			 lvReturnString = Base64.byteArrayToBase64(lvByteArrayOutputStream.toByteArray());
		 }
		 finally
		 {
			 if (lvObjectOutputStream != null)
			 {
				 try
				 {
					 lvObjectOutputStream.close();
				 }
				 catch (IOException pIOException)
				 {
					 pIOException.printStackTrace();
				 }
			 }

			 if (lvZipOutputStream != null)
			 {
				 try
				 {
					 lvZipOutputStream.close();
				 }
				 catch (IOException pIOException)
				 {
					 pIOException.printStackTrace();
				 }
			 }

			 if (lvByteArrayOutputStream != null)
			 {
				 try
				 {
					 lvByteArrayOutputStream.close();
				 }
				 catch (IOException pIOException)
				 {
					 pIOException.printStackTrace();
				 }
			 }
		 }
		 return lvReturnString;
	 }

	 public static int toJavaRoundingMethod(String pSystemRoundingMethod)
	 {
         int lvRoundingMode = BigDecimal.ROUND_HALF_UP;
         if ("U".equalsIgnoreCase(pSystemRoundingMethod))
        	 lvRoundingMode = BigDecimal.ROUND_UP;
		 else if ("D".equals(pSystemRoundingMethod))
			 lvRoundingMode = BigDecimal.ROUND_DOWN;
         
         return lvRoundingMode;
	 }
	 
	public static void CSVToExcel(InputStream pCSVInput, OutputStream pExcelOutput, String pSheetName) throws IOException
	{
		try (Workbook workBook = new HSSFWorkbook()) {
			CreationHelper createHelper = workBook.getCreationHelper();
			Sheet sheet = workBook.createSheet(pSheetName);

			String currentLine=null;
			int RowNum=0;
			BufferedReader br = new BufferedReader(new InputStreamReader(pCSVInput));
			while ((currentLine = br.readLine()) != null) 
			{
				Vector lvLine = UtlCSVParser.parseCSVLine(currentLine);
				String str[] = (String[])lvLine.toArray(new String [] {});
				RowNum++;
				Row currentRow=sheet.createRow(RowNum);
				for(short i=0;i<str.length;i++)
				{
					currentRow.createCell(i).setCellValue(createHelper.createRichTextString(str[i]));
				}
			}

			workBook.write(pExcelOutput);
		}
	}
//
//	public static void ExcelToCSV(InputStream pExcelInput, OutputStream pCSVOutput, int pColNum) throws IOException, InvalidExcelFileFormatException
//	{
//		try
//		{
//			Workbook my_xls_workbook = WorkbookFactory.create(pExcelInput);
//			Sheet my_worksheet = my_xls_workbook.getSheetAt(0);
//			Iterator<Row> rowIterator = my_worksheet.rowIterator();
//			while(rowIterator.hasNext()) 
//			{
//				Row row = rowIterator.next();
//				String[] csvdata = new String[pColNum];
//				int lastColumn = row.getLastCellNum();
//				for (int i = 0; i < lastColumn; ++i)
//				{
//					Cell cell = row.getCell(i, Row.RETURN_BLANK_AS_NULL);
//
//					if (cell != null)
//					{
//						
//						switch(cell.getCellType()) 
//						{ 
//							case Cell.CELL_TYPE_STRING:
//								csvdata[i]= cell.toString();	                     
//								break;
//			
//							case Cell.CELL_TYPE_NUMERIC:
//								csvdata[i]= cell.toString();
//						}
//					}
//				}
//
//				for (int j=0; j<csvdata.length; j++)
//				{
//					String lvValue = trim(csvdata[j]);
//					lvValue = "\"" + lvValue.replace("\"", "\\\"") + "\"";
//					pCSVOutput.write(lvValue.getBytes("UTF-8"));
//
//					if (j+1 != csvdata.length)
//						pCSVOutput.write(",".getBytes("UTF-8"));
//				}
//
//				pCSVOutput.write("\r\n".getBytes("UTF-8"));
//			}
//		}
//		catch (ArrayIndexOutOfBoundsException e)
//		{
//			throw new InvalidExcelFileFormatException("Invalid Excel file format", e);
//		}
//	}
//	
	public static class InvalidExcelFileFormatException extends Exception
	{
		public InvalidExcelFileFormatException(String pMessage, Throwable pCause)
		{
			super(pMessage, pCause);
		}
	}
	
	public static String getFormattedInstrumentID(String pInstrumentID)
	{
		if (pInstrumentID!=null)
		{
			try
			{
				double lvdInstrumentID = Double.parseDouble(pInstrumentID.trim());
				if (lvdInstrumentID < 100000)
				{
					DecimalFormat lvDF = new DecimalFormat("00000");
					return lvDF.format(lvdInstrumentID);
				}
			}
			catch(Exception ex)
			{
			}
			return pInstrumentID;
		}
		return "";
	}
	
	public static String getFormattedInstrumentID(String pMarketID, String pInstrumentID)
    {
        if (CoreUtils.isNullStr(pInstrumentID))
            return "";
        
        if(CoreUtils.isNullStr(pMarketID))
        	return pInstrumentID;
        
        Integer lvLength = mvInstrumentFormatMap.get(pMarketID.trim());
        if (lvLength == null)
            return pInstrumentID;

        try
        {
            int lvInstrumentID = Integer.parseInt(pInstrumentID.trim());
            int lvDigit = getDigit(lvInstrumentID);
            if (lvDigit < lvLength)
            {
                return new String(new char[lvLength - lvDigit]).replace('\0', '0') + lvInstrumentID;
            }
        }
        catch (Exception ex)
        {
        }
        return pInstrumentID;
    }
	
	private static int getDigit(int n)
    {
        return n<1E5?n>99?n<1E3?3:n<1E4?4:5:n>9?2:1:n<1E7?n<1E6?6:7:n<1E8?8:n<1E9?9:10;
    }
	
	public static String replaceDateInPath(String pvLocation, Date pvDate)
	{
	    int lvIndex1 = pvLocation.indexOf("{DATE,");
	    int lvIndex2 = pvLocation.indexOf("}");

	    if (lvIndex1>=0 && lvIndex2>lvIndex1)
	    {
	    	String lvFormat = pvLocation.substring(lvIndex1 + 6, lvIndex2);
	    	lvFormat = lvFormat.replaceAll("Y", "y");
	    	lvFormat = lvFormat.replaceAll("m", "M");
	    	lvFormat = lvFormat.replaceAll("D", "d");
	        
	    	String lvCurrentDate = null;
	    	try
			{
				if (!lvFormat.equals("yyyy-MM-dd"))
				{
					SimpleDateFormat lvDF2 = new SimpleDateFormat(lvFormat);
					lvCurrentDate = lvDF2.format(pvDate);
				}
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
	    	pvLocation = pvLocation.substring(0, lvIndex1) + lvCurrentDate + pvLocation.substring(lvIndex2 + 1, pvLocation.length());

	    	lvIndex1 = pvLocation.indexOf("{DATE,");
	    	if (lvIndex1>=0)
	    	{
	    		pvLocation = replaceDateInPath(pvLocation, pvDate);
	    	}
	    }
		return pvLocation;
	}

	public static void createPDF(String pFilename, Vector pContent, String pFontName, int pFontSize) throws Exception{
		
		Document document = new Document();
		try {
			PdfWriter writer = PdfWriter.getInstance(
					document,
					new FileOutputStream(pFilename));
			
			document.open();
			BaseFont bf;
			Font font;
			bf = BaseFont.createFont(pFontName,BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			
			font = new Font(bf, pFontSize);

			for (int i = 0 ; i < pContent.size(); i++)
			{
				document.newPage();
				document.add(new Paragraph(pContent.elementAt(i).toString().replaceAll("\r\n ", "\r\n  "),font));
			}

		} catch (FileNotFoundException e) {
			throw new FileNotFoundException();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			
			try {
				document.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
    
    public static boolean checkChanged(String pValue1, String pValue2)
    {
        if(!CoreUtils.trim(pValue1).equals(CoreUtils.trim(pValue2)))
            return true;
        
        return false;
    }    

    public static boolean checkChanged(BigDecimal pValue1, BigDecimal pValue2)
    {
        if(pValue1 == null && pValue2 == null)
              return false;
       
        if(pValue1 == null || pValue2 == null)
              return true;
        
        return pValue1.compareTo(pValue2) != 0;               
    }    
    
    public static boolean checkChanged(double pValue1, double pValue2)
    {
        if (Math.abs(pValue1 - pValue2) > 0.000001d)
            return true;
        
        return false;
    }
    
    public static boolean equals(Object a, Object b) 
    {
        return (a == b) || (a != null && a.equals(b));
    }

    public static boolean isInstance(String pClassName, Object pObject)
    {
    	try
    	{
    		return Class.forName(pClassName).isInstance(pObject);
    	} 
    	catch(ClassNotFoundException e)
    	{
    		return false;
    	}
    }
    
    public static boolean checkEmailFormatValid(String pEmail)
    {
    	if (pEmail != null && !pEmail.equals(""))
    	{
    		int lvIndexOfAt = pEmail.indexOf("@");
    		int lvIndexOfDot = pEmail.lastIndexOf(".");
    		if (lvIndexOfAt == -1 || lvIndexOfDot == -1 || 	
				lvIndexOfAt == 0 || 						
				lvIndexOfDot == pEmail.length() - 1 || 		
				lvIndexOfAt != pEmail.lastIndexOf("@") || 	
				lvIndexOfDot - lvIndexOfAt < 2)
    		{
    			return false; 
    		}
    	}
    	
    	return true;
    }

    public static int valueOfRoundingMethod(String pRoundingMethod) {
	    pRoundingMethod = trim(pRoundingMethod);
    	if (pRoundingMethod.equalsIgnoreCase("U"))
    		return BigDecimal.ROUND_UP;
    	else if (pRoundingMethod.equalsIgnoreCase("D"))
		    return BigDecimal.ROUND_DOWN;
	    else
		    return BigDecimal.ROUND_HALF_UP;
    }

	public static String removeTrailingZero(String pInput) {
    	if (pInput == null || pInput.trim().length() == 0)
    		return pInput;

    	pInput = pInput.replaceFirst("\\.0*$|(\\.\\d*?)0+$", "$1");

    	return pInput;
	}

    public static String replaceValues(String pOrgStr, String pRegex, Map pValues)
    {
    	return replaceValues(pOrgStr, pRegex, pValues, "`");
    }
    
	public static String replaceValues(String pOrgStr, String pRegex, Map pValues, String pDelimiter){
        StringBuffer lvSb = new StringBuffer();
        Pattern lvPattern = Pattern.compile(pRegex);
        Matcher lvMatcher = lvPattern.matcher(pOrgStr);
	    while(lvMatcher.find()){
	        String lvKey = lvMatcher.group(1);
	        String[] lvKeys = lvKey.split(pDelimiter);
	        if (pValues.containsKey(lvKeys[0]))
	        {
	        	String lvReplacement = null;
	        	Object lvValue = pValues.get(lvKeys[0]);
	        	if (lvValue != null)
	        	{
		        	if (lvValue instanceof Date)
		        	{
		        		Format lvFormatter = new SimpleDateFormat("yyyyMMdd"); 
		        		if (lvKeys.length > 1)
		        		{
		        			try
		        			{
		        				lvFormatter = new SimpleDateFormat(lvKeys[1]);
		        			}
		        			catch (Exception ex)
		        			{}
		        		}
		        		lvReplacement = lvFormatter.format(lvValue);
		        	}
		        	else if (lvValue instanceof BigDecimal)
		        	{
		        		if (lvKeys.length > 1)
		        		{
		        			try
		        			{
		        				DecimalFormat lvFormatter = new DecimalFormat(lvKeys[1]);
		        				if (lvKeys.length > 2)
		        				{
		        					switch (lvKeys[2]) {
								        case "CEILING":
								        	lvFormatter.setRoundingMode(RoundingMode.CEILING);
								            break;
								        case "DOWN":
								        	lvFormatter.setRoundingMode(RoundingMode.DOWN);
								            break;
								        case "FLOOR":
								        	lvFormatter.setRoundingMode(RoundingMode.FLOOR);
								        	break;
								        case "HALF_DOWN":
								        	lvFormatter.setRoundingMode(RoundingMode.HALF_DOWN);
								            break;
								        case "HALF_EVEN":
								        	lvFormatter.setRoundingMode(RoundingMode.HALF_EVEN);
								            break;
								        case "HALF_UP":
								        	lvFormatter.setRoundingMode(RoundingMode.HALF_UP);
								            break;
								        case "UNNECESSARY":
								        	lvFormatter.setRoundingMode(RoundingMode.UNNECESSARY);
								            break;
								        case "UP":
								        	lvFormatter.setRoundingMode(RoundingMode.UP);
								            break;
		        					}
		        				}
		        				lvReplacement = lvFormatter.format(lvValue);
					        }
		        			catch (Exception e)
		        			{}
		        		}
		        		else
		        		{
		        			lvReplacement = ((BigDecimal) lvValue).toPlainString();
		        		}
		        	}
		        	
		        	if (lvReplacement == null)
		        	{
		        		try
	        			{
		        			lvReplacement = lvValue.toString();
	        			}
	        			catch (Exception e)
	        			{}
		        	}
		        	
		        	if (lvReplacement != null)
		        		lvMatcher.appendReplacement(lvSb, lvReplacement);
	        	}
	        }
	    }
	    lvMatcher.appendTail(lvSb);
	    return lvSb.toString();
	}

    public static Date parseServerDate(String pDate) throws ParseException
    {
        return new SimpleDateFormat(SERVERDATEFORMAT).parse(pDate);
    }
	
    public static Map<String, Integer> getInstrumentFormatMap()
	{
		return mvInstrumentFormatMap;
	}
    
    public static HashMap<String, ArrayList<String>> mvMarketTypeMap;
	
	public static void initMarketTypeMap(HashMap<String, ArrayList<String>> pMarketTypeMap)
	{
		mvMarketTypeMap = pMarketTypeMap;
	}
	
	public static String mapMarketType(String pMarketID)
	{
		String [] TYPE = new String [] { "STOCK", "BOND", "FUND", "STRU" };
		for(int i=0; i<TYPE.length; i++)
		{
			ArrayList<String> lvList = mvMarketTypeMap.get(TYPE[i]);
			if (lvList != null && lvList.contains(pMarketID))
				return TYPE[i];
		}
		return "";
	}
	
	public static <O> O coalesceObject(O... pValues)
	{
		for(O value : pValues)
		{
			if(value != null)
				return value;
		}
		return null;
	}
	public static String generateSlug(String input) {
        return input.trim()
                    .toLowerCase()
                    .replaceAll("[^a-z0-9]+", "-") // thay space, ký tự đặc biệt bằng "-"
                    .replaceAll("^-|-$", ""); // bỏ dấu - đầu/cuối
    }
}
