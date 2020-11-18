package com;

import java.util.ArrayList;
import java.util.List;

public class Classroom extends Thread{
	static List<Student> students = new ArrayList();
//	Professor professor = new Professor();
//	List<Thread> threads = new ArrayList<>();
	static int count;
	static Student[] s;
	public static void init(){
		count = Repository.count;
		s = new Student[count];
		for(int i=0;i<count;i++) {
			 students.add(new Student(i));
		}
		for(Student s:students) {
			s.start();
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
		new Professor();
	}
}
