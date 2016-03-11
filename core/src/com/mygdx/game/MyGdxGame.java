package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture numeros[],background,start,movimientos,gano, play, mezclar;
	int puzzle[][];
	int clicks=0;
	BitmapFont font;
	boolean inicio = true, ganar = false;
	Sound click;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		numeros = new Texture[9];
		background = new Texture("background.png");
		start = new Texture("start.png");
		movimientos = new Texture("movimientos.png");
		play = new Texture("play.png");
		gano = new Texture("gano.png");
		mezclar = new Texture("mezclar.png");
		puzzle = new int [3][3];
		font = new BitmapFont();
		click = Gdx.audio.newSound(Gdx.files.internal("click.mp3"));

		for (int i=0; i<9;i++){
			numeros [i] = new Texture(i+".png");
		}
		mezclar();
	}

	@Override
	public void render () {

		Gdx.gl.glClearColor(1, 0, 0, 1);

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		if (inicio){
			batch.draw(background,0,0);
			batch.draw(start,275,100);
			if (Gdx.input.justTouched()) {
				click.play();
				int click_x = Gdx.input.getX();
				int click_y = Gdx.graphics.getHeight() - Gdx.input.getY();
				if (click_x > 225 && click_x < 425 && click_y > 100 && click_y < 150) {
					inicio = false;
					ganar = false;
				}
			}
		}
		if (inicio==false&&ganar==false){
			batch.draw(background, 0, 0);
			batch.draw(movimientos,450,400);
			font.draw(batch, Integer.toString(clicks), 650, 427);
			font.getData().setScale(1f);
			font.setColor(255, 255, 255, 255);
			batch.draw(mezclar, 500,200);

			for (int x = 0; x < 3; x++)
				for (int y = 0; y < 3; y++)
					batch.draw(numeros[puzzle[y][x]], 150 * x, 150 * y);
		}

		if (ganar==false){
			if (Gdx.input.justTouched()){
				click.play();
				int click_x = Gdx.input.getX();
				int click_y = Gdx.graphics.getHeight()-Gdx.input.getY();
				if (click_x > 500 && click_x < 700 && click_y > 200 && click_y < 250) {
					mezclar();
					clicks =0;
				}
				for (int x=0; x<3; x++){
					for (int y=0; y<3; y++){
						if (click_x>x*150 && click_x<x*150+150 && click_y>y*150 && click_y<y*150+150){
							if (y+1<3 && puzzle[y+1][x]==0) {//0 a la derecha
								puzzle[y + 1][x] = puzzle[y][x];
								puzzle[y][x] = 0;
								clicks++;
								//font.draw(batch, Integer.toString(clicks), 500, 300);
							}
							if (y-1>=0 && puzzle[y-1][x]==0) {//0 a la izquiera
								puzzle[y - 1][x] = puzzle[y][x];
								puzzle[y][x] = 0;
								clicks++;
								//font.draw(batch, Integer.toString(clicks), 500, 300);
							}
							if (x+1<3 && puzzle[y][x+1]==0) {//0 arriba
								puzzle[y][x+1] = puzzle[y][x];
								puzzle[y][x] = 0;
								clicks++;
								//font.draw(batch, Integer.toString(clicks), 500, 300);
							}
							if (x-1>=0 && puzzle[y][x-1]==0) {//0 abajo
								puzzle[y][x-1] = puzzle[y][x];
								puzzle[y][x] = 0;
								clicks++;
								//font.draw(batch, Integer.toString(clicks),500,300);
							}
						}
					}
				}
				if (puzzle[2][0]==1&&puzzle[2][1]==2&&puzzle[2][2]==3&&puzzle[1][0]==4&&puzzle[1][1]==5&&puzzle[1][2]==6
						&&puzzle[0][0]==7&&puzzle[0][1]==8&&puzzle[0][2]==0) {
					System.out.println("Gano");
					ganar = true;
				}

			}
		}
		if (ganar){
			batch.draw(gano,0,0);
			batch.draw(play,275,100);
			if (Gdx.input.justTouched()) {
				click.play();
				int click_x = Gdx.input.getX();
				int click_y = Gdx.graphics.getHeight() - Gdx.input.getY();
				if (click_x > 225 && click_x < 425 && click_y > 100 && click_y < 150) {
					inicio = true;
					ganar = false;
					clicks = 0;
					mezclar();
				}
			}
		}
		batch.end();
	}

	public void mezclar(){
		for (int x=0; x<3;x++){
			for (int y=0; y<3;y++){
				puzzle[x][y]=-1;
			}
		}
		for (int i=0; i<9;i++){
			int x_rand=(int)(Math.random()*10%3);
			int y_rand=(int)(Math.random()*10%3);
			while (puzzle[y_rand][x_rand]!=-1){
				x_rand=(int)(Math.random()*10%3);
				y_rand=(int)(Math.random()*10%3);
			}
			puzzle[y_rand][x_rand]=i;
		}
	}
}

