package edu.handong.csee;

import java.io.File;

public class myThread implements Runnable {

	private File fileName;

	public myThread(File fileName) {
		this.fileName = fileName;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		JavaFinalProject myJava = new JavaFinalProject();
		myJava.readFileInZip(fileName);
	}

}
