package com;

import java.util.ArrayList;
import java.util.List;

public class Classroom extends Thread{
	List<Student> students = new ArrayList();
	Professor professor = new Professor();
	List<Thread> threads = new ArrayList<>();
}
