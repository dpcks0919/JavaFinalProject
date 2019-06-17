package edu.handong.csee;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Utils {

	public static void writeAFile(ArrayList<String> lines, String targetFileName) {

		try {
			File realFile = null;

			if (targetFileName.charAt(0) == 'C') {
				String[] array = targetFileName.split("\\\\");
				String name = array[0];
				for (int i = 1; i < array.length - 1; i++) {
					name += "\\\\" + array[i];
				}

				File file = new File(name);

				if (!file.exists()) {
					file.getParentFile().mkdirs();
					file.mkdirs();
				}
				realFile = new File(targetFileName);
			}

			if (targetFileName.charAt(1) == '/') {
				String[] array = targetFileName.split("/");
				String name = "C:\\\\Users\\\\dpcks\\\\git\\\\HGUCourseCounter";
				for (int i = 1; i < array.length - 1; i++) {
					name += "\\\\" + array[i];
				}

				File file = new File(name);

				if (!file.exists()) {
					file.getParentFile().mkdirs();
					file.mkdirs();
				}

				realFile = new File(name + "\\\\" + array[array.length - 1]);

			} else {
				realFile = new File(targetFileName);
			}

			FileWriter fw = new FileWriter(realFile);

			for (int i = 0; i < lines.size(); i++) {
				fw.write(lines.get(i) + "\r\n");
			}
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
