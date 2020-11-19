package com;

import java.util.ArrayList;
import java.util.List;

public class Classroom extends Thread {
	static List<Student> students = new ArrayList();

	static int count;
	static Student[] s;

	public static void init() throws InterruptedException {
		Thread profThread = new Thread(new Professor(), "prof");
		count = Repository.getInstance().getCount();
		s = new Student[count];
		for (int i = 0; i < 10; i++) {
			Thread t = new Thread(new Student(i), String.valueOf(i));
			t.start();
		}
		
		Thread.sleep(10000);
		profThread.start();
		
	}

	public static void kill() {
		for (Student s : students) {
			s.stop();
		}
		Repository.getInstance().clearAll();
	}

	public static void freeze() {
		for (Student s : students) {
			s.freeze();
		}
	}
}
