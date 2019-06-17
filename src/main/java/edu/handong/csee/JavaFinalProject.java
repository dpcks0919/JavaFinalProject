package edu.handong.csee;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;

public class JavaFinalProject {

	private String inputPath;
	private String output;
	private static ArrayList<String> line1 = new ArrayList<String>();
	private static ArrayList<String> line2 = new ArrayList<String>();
	
	public static void main(String[] args) {
		JavaFinalProject javaFinal = new JavaFinalProject();
		javaFinal.run(args);
	}

	public void run(String[] args) {

		Options options = createOptions();

		if (parseOptions(options, args)) {

			setHeadLine1(line1);
			setHeadLine2(line2);

			File[] fileList = getFileName(inputPath);

			MyList<Thread> threadList = new MyList<>();

			for (File tempFile : fileList) {
				Thread thread = new Thread(new myThread(tempFile));
				thread.start();
				threadList.add(thread);
			}
			for (int i = 0; i < threadList.size(); i++) {
				try {
					threadList.get(i).join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Utils.writeAFile(line1, output.substring(0, 6) + "1.csv");
			Utils.writeAFile(line2, output.substring(0, 6) + "2.csv");
			Utils.writeAFile(ExcelReader.getErrorList(), "error.csv");
		}

	}

	public void setHeadLine1(ArrayList<String> line) {
		line.add("\"" + "학생 이름" + "\"" + "," + "\"" + "제목" + "\"" + "," + "\"" + "요약문 (300자 내외)" + "\"" + "," + "\""
				+ "핵심어\r\n" + "(keyword,쉽표로 구분)" + "\"" + "," + "\"" + "조회날짜" + "\"" + "," + "\"" + "실제자료조회\r\n"
				+ "출처 (웹자료링크)" + "\"" + "," + "\"" + "원출처 (기관명 등)" + "\"" + "," + "\"" + "제작자\r\n" + "(Copyright 소유처)"
				+ "\"" + ",");
	}

	public void setHeadLine2(ArrayList<String> line) {
		line.add(" 1. 찾은 자료 내에 있는 그림이나 표의 자료내 위치(쪽번호)와 표와 그림을 설명하는 캡션(주석)을 적습니다.\r\n"
				+ "2. 표와 그림의 캡션이 없는 경우, 본문의 내용을 보고 간단히 설명을 적오주세요.");
		line.add("\"" + "학생 이름" + "\"" + "," + "\"" + "제목(반드시 요약문 양식에 입력한 제목과 같아야 함.)" + "\"" + "," + "\"" + "표/그림 일련번호"
				+ "\"" + "," + "\"" + "자료유형(표,그림,…)" + "\"" + "," + "\"" + "자료에 나온 표나 그림 설명(캡션)" + "\"" + "," + "\""
				+ "자료가 나온 쪽번호" + "\"" + ",");
	}

	public File[] getFileName(String path) {
		File dirFile = new File(path);
		File[] fileList = dirFile.listFiles();
		return fileList;
	}

	public static void readFileInZip(File file) {
		ZipFile zipFile;
		ArrayList<String> temp1 = new ArrayList<String>();
		ArrayList<String> temp2 = new ArrayList<String>();
		try {
			String tempPath = file.getParent();
			String tempFileName = file.getName();
			String fileName = tempFileName.substring(0, 4);

			zipFile = new ZipFile(tempPath + "\\" + tempFileName);
			Enumeration<? extends ZipArchiveEntry> entries = zipFile.getEntries();

			ZipArchiveEntry entry = entries.nextElement();
			InputStream stream = zipFile.getInputStream(entry);
			ExcelReader myReader = new ExcelReader();

			temp1 = myReader.getData10(stream, tempFileName);
			for (int i = 0; i < temp1.size(); i++) {
				synchronized (line1) {
					line1.add(fileName + temp1.get(i));
				}
			}

			entry = entries.nextElement();
			stream = zipFile.getInputStream(entry);

			temp2 = myReader.getData20(stream, tempFileName);

			for (int i = 0; i < temp2.size(); i++) {
				synchronized (line2) {
					line2.add(fileName + temp2.get(i));
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();

		try {
			CommandLine cmd = parser.parse(options, args);

			inputPath = cmd.getOptionValue("i");
			output = cmd.getOptionValue("o");
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private Options createOptions() {
		Options options = new Options();

		options.addOption(Option.builder("i").longOpt("input").desc("Set an input file path").hasArg()
				.argName("Input path").required().build());

		options.addOption(
				Option.builder("o").longOpt("output").desc("Set an output file path").hasArg().argName("Output path")
						// .required()
						.build());

		return options;
	}

}
