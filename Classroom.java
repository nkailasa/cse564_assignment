package com;

public class Classroom extends Thread {
	int count = Repository.getInstance().getCount();
	Professor profObj;
	int incr = count / 10;
	int extra = count % 10;
	Thread[] studentThreads;
	Student[] stuObj;

	public Classroom() {
		profObj = new Professor();
		studentThreads = new Thread[incr + 1];
		stuObj = new Student[incr + 1];
	}

	public void init() {
		profObj.setRun(true);
		Thread p = new Thread(profObj);
		p.start();

		for (int i = 0; i <= incr; i++) {

			if (i == incr) {
				stuObj[i] = new Student(i * 10, extra-1);
				studentThreads[i] = new Thread(stuObj[i]);
			} else {
				stuObj[i] = new Student(i * 10, 0);
				studentThreads[i] = new Thread(new Student(i * 10, 0));
			}
			if(i*10 < count)
			studentThreads[i].start();
		}
	}

	public void freeze() {
		profObj.setRun(false);
		for (int i = 0; i <= incr; i++) {
			if (stuObj[i] != null)
				stuObj[i].setRun(false);
		}
	}

	public void clearAll() {
		profObj.setRun(false);
		for (int i = 0; i <= incr; i++) {
			if (stuObj[i] != null)
				stuObj[i].setRun(false);
		}

		Repository.getInstance().clearAll();
	}

}
