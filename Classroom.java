package com;

import java.util.ArrayList;
import java.util.List;

public class Classroom extends Thread{
	static List<Student> students = new ArrayList();
	 
	
	static int count;
	static Student[] s;
	public static void init() throws InterruptedException{
		Professor runnable = new Professor(); 
		Thread profThread = new Thread(runnable);
		count = Repository.count;
		s = new Student[count];
		for(int i=0;i<count;i++) {
			 students.add(new Student(i));
		}
		for(Student s:students) {
			s.start();
			profThread.start();
		}
		
	}
	
	public static void kill() {
		for(Student s:students) {
			s.stop();
		}
	}


	public static void freeze() {
		for(Student s:students) {
			s.freeze();
		}
	}
}
