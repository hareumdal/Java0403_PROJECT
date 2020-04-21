package test;

import java.util.Random;

import javax.swing.JFrame;

public class Test extends JFrame {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Test();

	}

	Test() {
		Random r = new Random();
		String[] a = {"3","2"};
		char[] rAlphabet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'o', 'p', 'q', 'r', 's',
				't', 'u', 'v', 'w', 'x', 'y', 'z' };

		String setRoomName = ""+ rAlphabet[1] + + r.nextInt(9999);
	}

}
