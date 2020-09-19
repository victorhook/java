package ovn1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Function;

import se.lth.cs.p.ovn.turtle.Turtle;
import se.lth.cs.window.SimpleWindow;

public class Ovn1 {
	
	public static void main(String[] args) {
//		SimpleWindow w = new SimpleWindow(500, 500, "SuperWindow");
//		Turtle t = new Turtle(w, 500, 500);
		
		p5();
	}
	
	public static void p5() {
	
		// Generate 100 random numbers
		Random rand = new Random();
		final int MIN = 0, MAX = 1000;
		int numbers[] = new int[100];
		for (int i = 0; i < numbers.length; i++) {
			numbers[i] = rand.nextInt(MAX-MIN);
		}
		System.out.println(String.format("Generating 100 random numbers between %s and %s", MIN, MAX));
		
		// Find max and min
		int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
		
		for (Integer num: numbers) {
			if (num < min) {
				min = num;
			}
			if (num > max) {
				max = num;
			}
		}
		
		System.out.println(String.format("Biggest number: %s\nSmallest number: %s", min, max));
		
	}

	
	
	public static void p4() {
		Scanner scan = new Scanner(System.in);
		int numbers[] = new int[4];
		System.out.println("Ange avångstid: ");
		numbers[0] = scan.nextInt();
		numbers[1] = scan.nextInt();
		
		System.out.println("Ange körtid: ");
		numbers[2] = scan.nextInt();
		numbers[3] = scan.nextInt();
		
		int hour = (numbers[0] + numbers[2]) % 24;
		int minute = numbers[1] + numbers[3];
		hour += minute / 60;
		minute %= 60;
		
		System.out.println(String.format("Ankomsttid: %s:%s", hour, minute));
		
	}
	
	
	public static void triangle(SimpleWindow w, int x, int y) {
		Turtle t = new Turtle(w, x, y);
		t.penDown();
		t.turnNorth();
		t.right(60);
		t.forward(100);
		t.right(120);
		t.forward(100);
		t.right(120);
		t.forward(100);
	}
	
}
