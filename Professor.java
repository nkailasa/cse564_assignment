package com;

public class Professor {
	static int count;

	public static void start() {
		count = Blackboard.count;
		for(int i=0;i<count;i++) {
			Student s = new Student(i);
			s.start();
		}
	}

}
