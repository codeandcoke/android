package org.sfsoft.android_multimedia;

public class Casilla {
	
	private float x0;
	private float y0;
	private float x1;
	private float y1;
	private char valor;
	
	public Casilla() {
		
		this.x0 = 0;
		this.y0 = 0;
		this.x1 = 0;
		this.y1 = 0;
		this.valor = ' ';
	}
	
	public Casilla(float x0, float y0, float x1, float y1, char valor) {
		this.x0 = x0;
		this.y0 = y0;
		this.x1 = x1;
		this.y1 = y1;
		this.valor = valor;
	}

	public float getX0() {
		return x0;
	}

	public void setX0(float x0) {
		this.x0 = x0;
	}

	public float getY0() {
		return y0;
	}

	public void setY0(float y0) {
		this.y0 = y0;
	}

	public float getX1() {
		return x1;
	}

	public void setX1(float x1) {
		this.x1 = x1;
	}

	public float getY1() {
		return y1;
	}

	public void setY1(float y1) {
		this.y1 = y1;
	}

	public char getValor() {
		return valor;
	}

	public void setValor(char valor) {
		this.valor = valor;
	}
	
	

}
