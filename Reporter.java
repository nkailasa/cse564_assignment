package com;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class Reporter implements MouseListener {
	@Override
public void mousePressed(MouseEvent e) {
	// TODO Auto-generated method stub
	
}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Repository.getInstance().setPoints(e.getX(), e.getY());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}