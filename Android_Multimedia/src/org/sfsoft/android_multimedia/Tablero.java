package org.sfsoft.android_multimedia;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import android.view.View.OnTouchListener;

public class Tablero extends View implements OnTouchListener {
	
	private Context contexto;
	private float selX;
	private float selY;
	private Casilla[][] tablero;
	private boolean[][] ocupadas;
	
	private float altura;
	private float anchura;
	
	private float ALTO_PANTALLA;
	private float ANCHO_PANTALLA;
	
	private boolean turnoOrdenador = false;
	
	private final boolean debug = true;

	public Tablero(Context contexto) {
		super(contexto);

		this.contexto = contexto;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		
		Paint fondo = new Paint();
		fondo.setColor(getResources().getColor(android.R.color.background_light));
		
		canvas.drawRect(0, 0, getWidth(), getHeight(), fondo);
		setOnTouchListener(this);
		
		pintarLineasTablero(canvas);
		
		pintarMovimientos(canvas);
	}
	
	private void pintarLineasTablero(Canvas canvas) {
		
		Paint pintor = new Paint();
		pintor.setColor(getResources().getColor(android.R.color.black));
		
		canvas.drawLine(anchura, 0, anchura, ALTO_PANTALLA, pintor);
		canvas.drawLine(anchura * 2, 0, anchura * 2, ALTO_PANTALLA, pintor);
		
		canvas.drawLine(0, altura, ANCHO_PANTALLA, altura, pintor);
		canvas.drawLine(0, altura * 2, ANCHO_PANTALLA, altura * 2, pintor);
	}
	
	private void inicializarTablero() {
		
		this.tablero = new Casilla[3][3];
		this.ocupadas = new boolean[3][3];
		
		for (int i = 0; i < this.tablero.length; i++) {
			for (int j = 0; j < this.tablero[0].length; j++) {
				tablero[i][j] = new Casilla(anchura * j, altura * i, anchura * (j + 1), altura * (i + 1), ' ');
				ocupadas[i][j] = false;
			}	
		}
	}
	
	private void pintarMovimientos(Canvas canvas) {
		
		Paint pintor = new Paint();
		pintor.setColor(Color.RED);
		pintor.setStrokeWidth(10);
		
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[0].length; j++) {
				
				if (tablero[i][j].getValor() == 'X') {
					
					canvas.drawLine(tablero[i][j].getX0(), tablero[i][j].getY0(), tablero[i][j].getX1(), 
							tablero[i][j].getY1(), pintor);
					canvas.drawLine(tablero[i][j].getX0() + anchura, 
							tablero[i][j].getY0(), tablero[i][j].getX0(), tablero[i][j].getY1(), pintor);
				}
				else if (tablero[i][j].getValor() == 'O') {
					
					canvas.drawCircle(
							tablero[i][j].getX0() + anchura / 2,
							tablero[i][j].getY0() + altura / 2,
							60, pintor);
				}
				else {
				}
			}
		}
	}
	
	private void anotarMovimiento(float x, float y) { 
		
		Zona zona = getZona(x, y);
		
		if (!ocupadas[zona.i][zona.j]) {
			tablero[zona.i][zona.j].setValor('X');
			ocupadas[zona.i][zona.j] = true;
		}
		
		turnoOrdenador = true;
	}
	
	private void calcularMovimientoOponente() {
		
		Toast.makeText(this.contexto, "ordenador", Toast.LENGTH_SHORT).show();
		
		Random generador = new Random();
		
		if (!turnoOrdenador) {
			return;
		}
		
		int i = generador.nextInt(2);
		int j = generador.nextInt(2);
		
		while (ocupadas[i][j]) {
			i = generador.nextInt(2);
			j = generador.nextInt(2);
		}
		
		tablero[i][j].setValor('O');
		ocupadas[i][j] = true;
		
		turnoOrdenador = false;
	}
	
	private boolean finTablero() {
		
		for (int i = 0; i < ocupadas.length; i++) {
			for (int j = 0; j < ocupadas[0].length; j++) {
				if (!ocupadas[i][j])
					return false;
			}	
		}
		
		return true;
	}
	
	private Zona getZona(float x, float y) {
		
		Zona zona = new Zona();
		
		if (y < altura)
			zona.i = 0;		
		else if ((altura < y) && (y < altura * 2))
			zona.i = 1;
		else if (y > (altura * 2))
			zona.i = 2;
		
		if (x < anchura)
			zona.j = 0; 				
		else if ((anchura < x) && (x < (anchura * 2)))
			zona.j = 1;
		else if (((anchura * 2) < x) && (x < ANCHO_PANTALLA))
			zona.j = 2;
		
		return zona;
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		
		super.onSizeChanged(w, h, oldw, oldh);
		
		this.anchura = w / 3;
		this.altura = h / 3;
		
		ANCHO_PANTALLA = w;
		ALTO_PANTALLA = h;
		
		inicializarTablero();
	}
	
	public boolean onTouch(View v, MotionEvent evento) {
		
		if (finTablero()) {
			Toast.makeText(this.contexto, "Fin Partida", Toast.LENGTH_SHORT).show();
			return false;
		}
		
		anotarMovimiento(evento.getX(), evento.getY());	
		//calcularMovimientoOponente();
		v.invalidate();
		
		return true;
	}
	
	static class Zona {
		
		public int i;
		public int j;
	}
}
