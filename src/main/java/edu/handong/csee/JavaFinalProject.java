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
		line.add("\"" + "�л� �̸�" + "\"" + "," + "\"" + "����" + "\"" + "," + "\"" + "��๮ (300�� ����)" + "\"" + "," + "\""
				+ "�ٽɾ�\r\n" + "(keyword,��ǥ�� ����)" + "\"" + "," + "\"" + "��ȸ��¥" + "\"" + "," + "\"" + "�����ڷ���ȸ\r\n"
				+ "��ó (���ڷḵũ)" + "\"" + "," + "\"" + "����ó (����� ��)" + "\"" + "," + "\"" + "������\r\n" + "(Copyright ����ó)"
				+ "\"" + ",");
	}

	public void setHeadLine2(ArrayList<String> line) {
		line.add(" 1. ã�� �ڷ� ���� �ִ� �׸��̳� ǥ�� �ڷ᳻ ��ġ(�ʹ�ȣ)�� ǥ�� �׸��� �����ϴ� ĸ��(�ּ�)�� �����ϴ�.\r\n"
				+ "2. ǥ�� �׸��� ĸ���� ���� ���, ������ ������ ���� ������ ������ �����ּ���.");
		line.add("\"" + "�л� �̸�" + "\"" + "," + "\"" + "����(�ݵ�� ��๮ ��Ŀ� �Է��� ����� ���ƾ� ��.)" + "\"" + "," + "\"" + "ǥ/�׸� �Ϸù�ȣ"
				+ "\"" + "," + "\"" + "�ڷ�����(ǥ,�׸�,��)" + "\"" + "," + "\"" + "�ڷῡ ���� ǥ�� �׸� ����(ĸ��)" + "\"" + "," + "\""
				+ "�ڷᰡ ���� �ʹ�ȣ" + "\"" + ",");
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
