package com.naah.admin.utils;

import com.naah.po.Answer;
import com.naah.po.User;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * excel表格工具类
 * @author dazhi
 *
 */
public class ExcelUtil {
	private static final Logger logger = Logger.getLogger(ExcelUtil.class);

	public static void savaUserData(String path) {
		savaUserData(new File(path));
	}

	public static void savaUserDataForRoot(String path) {
		savaUserData(new File(Thread.currentThread().getContextClassLoader().getResource("").getPath()+"/"+path));
	}

	public static void printdata(OutputStream outputStream){
		Collection<User> values = HeapVariable.usersMap.values();
		Workbook workbook = new XSSFWorkbook();
		try {
			analyticData(0, workbook.createSheet(), values);
			workbook.write(outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if(workbook != null){
					workbook.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public static void printUserData(String filePath){
		Map<String, User> usersMap = HeapVariable.usersMap;
		Collection<User> values = usersMap.values();
		Workbook workbook = null;

		try {
			//实例化文件
			logger.info("excel文件创建成功");
			workbook = new XSSFWorkbook(new FileInputStream(new File(filePath)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		Sheet sheet = workbook.createSheet("user");
		Class clazz = User.class;
		//生成sheet
		try {
			createSheet(workbook,sheet,clazz,values);
			workbook.write(new FileOutputStream(new File(filePath)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void printUserData(OutputStream outputStream){
		Map<String, User> usersMap = HeapVariable.usersMap;
		Collection<User> values = usersMap.values();
		Workbook workbook = null;

		try {
			//实例化文件
			logger.info("excel文件创建成功");
			workbook = new XSSFWorkbook();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Sheet sheet = workbook.createSheet("user");
		Class clazz = User.class;
		//生成sheet
		try {
			createSheet(workbook,sheet,clazz,values);
			workbook.write(outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void createSheet(Workbook workbook,Sheet sheet,Class clazz,Collection<User> values) throws Exception {
		Field[] declaredFields = clazz.getDeclaredFields();
		if("answers".equals(sheet.getSheetName())){
			createRow(-1, sheet);
		}else{
			createRow(-1, sheet);
		}
		Row createRow = sheet.createRow(1);
		for (int i = 0; i < declaredFields.length; i++) {
			if(declaredFields[i].getName().equals("answers")){
				Sheet answers = workbook.createSheet(declaredFields[i].getName());
				createSheet(workbook, answers,Answer.class,values);
			}else{
				Cell createCell = createRow.createCell(i);
				createCell.setCellValue(declaredFields[i].getName());
			}
		}
		writeDataExecl(sheet, createRow, clazz, declaredFields, 2, values);
	}

	private static void createRow(int count, Sheet createSheet) {
		Row time = createSheet.createRow(count + 1);
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String format = dateFormat.format(date);
		CellRangeAddress cra = new CellRangeAddress(count + 1, count + 1, 0, 7);
		Cell createCell2 = time.createCell(0);
		createCell2.setCellValue(format);
		createSheet.addMergedRegion(cra);
	}
	private static void createRow(int count, Sheet createSheet,String name) {
		Row time = createSheet.createRow(count + 1);
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String format = dateFormat.format(date);
		CellRangeAddress cra = new CellRangeAddress(count + 1, count + 1, 0, 7);
		Cell createCell2 = time.createCell(0);
		createCell2.setCellValue(format+"用户:"+name);
		createSheet.addMergedRegion(cra);
	}


	public static void savaUserData(File file) {
		int count = 0;
		Map<String, User> usersMap = HeapVariable.usersMap;
		Collection<User> values = usersMap.values();
		Workbook workbook = null;

		// 文件没有改变
		if (HeapVariable.MD5DataChange.equals(MD5Util.md5(values.toString()))) {
			return;
		} else {
			String md5 = MD5Util.md5(values.toString());
			HeapVariable.MD5DataChange = md5;
			logger.info("数据修改，执行写入操作！");
		}
		try {
			workbook = new XSSFWorkbook();

			if (!file.exists()) {
				workbook.createSheet();
			} else {
				workbook = new XSSFWorkbook(new FileInputStream(file));
			}
			// 读写原来的数据
			Sheet sheetAt = workbook.getSheetAt(0);
			count = sheetAt.getLastRowNum() + 1;
			// 解析数据
			analyticData(count, sheetAt, values);

			workbook.write(new FileOutputStream(file));
			logger.info("写入操作完成");
		} catch (Exception e) {
			logger.error(Arrays.toString(e.getStackTrace()));
			e.printStackTrace();
		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 把数据写到新增数据写到excel表中
	 *
	 * @param count
	 * @return
	 * @throws Exception
	 */
	private static void analyticData(int count, Sheet createSheet, Collection<User> values) throws Exception {
		// 记录时间
		createRow(count, createSheet);

		// 生成表格标题
		Row createRow = createSheet.createRow(count + 3);
		Class clazz = User.class;

		Field[] declaredFields = clazz.getDeclaredFields();

		for (int i = 0; i < declaredFields.length; i++) {
			Cell createCell = createRow.createCell(i);
			createCell.setCellValue(declaredFields[i].getName());
		}

		// 迭代插入所有user数据
		writeDataExecl(createSheet, createRow, clazz, declaredFields, count + 4, values);
	}



	/**
	 * 遍历数据
	 *
	 * @param createSheet
	 * @param createRow
	 * @param clazz
	 * @param declaredFields
	 * @param countNum
	 * @param values
	 * @throws NoSuchMethodException
	 */
	private static void writeDataExecl(Sheet createSheet, Row createRow, Class clazz, Field[] declaredFields,
			int countNum, Collection<User> values) throws Exception {
		Iterator<User> iterator = values.iterator();
		int count = countNum;
		if(createSheet.getSheetName().equals("user")){
			count = getData(createSheet, createRow, clazz, declaredFields, iterator, count);
		}else{
			for (User user : values) {
				Iterator<Answer> iterator2 = user.getAnswers().iterator();
				count = getAnwer(createSheet, createRow, clazz, declaredFields, iterator2, count,user.getUsername());
			}
		}
	}

	private static int getAnwer(Sheet createSheet, Row createRow, Class clazz, Field[] declaredFields,
			Iterator<Answer> iterator, int count,String name)
					throws Exception {
		while (iterator.hasNext()) {
			Answer user = iterator.next();
			short lastCellNum = createRow.getLastCellNum();
			Row createRow2 = createSheet.createRow(count++);
			// 遍历插入标题对应的数据
			for (int i = 0; i < lastCellNum; i++) {
				Cell cell = createRow.getCell(i);
				for (int j = 0; j < declaredFields.length; j++) {
					if (declaredFields[j].getName().equals(cell.toString())) {
						Cell createCell = createRow2.createCell(i);
						// 获得user对象中的值
						Method method = clazz.getMethod("get" + StringUtil.initialsUpper(declaredFields[j].getName()));
						Object invoke = method.invoke(user);
						if (invoke != null) {
							createCell.setCellValue(invoke.toString());
						}
						break;
					}
					Cell createCell = createRow2.createCell(declaredFields.length+1);
					createCell.setCellValue(name);
				}
			}
		}
		return count;
	}

	private static int getData(Sheet createSheet, Row createRow, Class clazz, Field[] declaredFields,
			Iterator<User> iterator, int count)
					throws Exception {
		while (iterator.hasNext()) {
			User user = iterator.next();
			short lastCellNum = createRow.getLastCellNum();
			Row createRow2 = createSheet.createRow(count++);
			// 遍历插入标题对应的数据
			for (int i = 0; i < lastCellNum; i++) {
				Cell cell = createRow.getCell(i);
				for (int j = 0; j < declaredFields.length; j++) {
					if (declaredFields[j].getName().equals(cell.toString())) {
						Cell createCell = createRow2.createCell(i);
						// 获得user对象中的值
						Method method = clazz.getMethod("get" + StringUtil.initialsUpper(declaredFields[j].getName()));
						Object invoke = method.invoke(user);
						if (invoke != null) {
							createCell.setCellValue(invoke.toString());
						}
						break;
					}
				}
			}
		}
		return count;
	}



}
