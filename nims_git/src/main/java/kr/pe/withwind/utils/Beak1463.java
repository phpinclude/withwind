package kr.pe.withwind.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Beak1463 {
	
	private int param;
	
	public Beak1463(int param) {
		this.param = param;
	}
	
	public int start() {
		int reValue = 0;
		
		while (param != 1) {
			
			if (param % 3 == 0) {
				param = param / 3;
			}else if (param % 2 == 0) {
				param = param / 2;
			}else{
				param = param - 1;
			}
			
			reValue++;
		}
		
		return reValue;
	}
	
	public int S321(int param) {
		int reValue = 0;
		
		while (param != 1) {
			
			if (param % 3 == 0) {
				param = param / 3;
			}else if (param % 2 == 0) {
				param = param / 2;
			}else{
				param = param - 1;
			}
			
			reValue++;
		}
		
		return reValue;
	}
	
	public int S312(int param) {
		int reValue = 0;
		
		while (param != 1) {
			
			if (param % 3 == 0) {
				param = param / 3;
			}else if (param % 2 != 0) {
				param = param - 1;
			}else{
				param = param / 2;
			}
			
			reValue++;
		}
		
		return reValue;
	}
	
	public int S231(int param) {
		int reValue = 0;
		
		while (param != 1) {
			
			if (param % 2 == 0) {
				param = param / 2;
			}else if (param % 3 == 0) {
				param = param / 3;
			}else{
				param = param - 1;
			}
			
			reValue++;
		}
		
		return reValue;
	}
	
	public int S213(int param) {
		int reValue = 0;
		
		while (param != 1) {
			
			if (param % 2 == 0) {
				param = param / 2;
			}else if (param % 3 != 0) {
				param = param - 1;
			}else{
				param = param / 3;
			}
			
			reValue++;
		}
		
		return reValue;
	}
	
	public int S123(int param) {
		int reValue = 0;
		
		while (param != 1) {
			
			if (param % 2 == 0) {
				param = param / 2;
			}else if (param % 3 != 0) {
				param = param - 1;
			}else{
				param = param / 3;
			}
			
			reValue++;
		}
		
		return reValue;
	}
	
	public static void main(String[] arg) throws NumberFormatException, IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		int param = Integer.parseInt(reader.readLine());
		
		Beak1463 mainClass = new Beak1463(param);
		
		System.out.println(mainClass.start());
	}
}
