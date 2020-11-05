package com.example.colortilescanvas;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TilesActivity extends AppCompatActivity {

    Bundle extras;
    short tilesX, tilesY, numberOfColors, interval; //используется short, а не int для большинства переменных, т.к. их значения не могут быть больше диапазона short
    //    String motionEvent;
    Boolean epilepsy, square;
    int duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //скрывает панель уведомлений на активности с плитками

        Intent intent = getIntent();
        extras = intent.getExtras();
        /*tilesX = (short) extras.getInt("tilesX", 4);
        tilesY = (short) extras.getInt("tilesY", 4);*/
        tilesY = tilesX = (short) extras.getInt("tiles", 4);
        numberOfColors = (short) extras.getInt("colors", 2);
//        motionEvent = extras.getString("motionEvent");
        square = extras.getBoolean("square", false);
        epilepsy = extras.getBoolean("epilepsy", false);
        duration = extras.getInt("duration", 1000);
        interval = (short) extras.getInt("interval", 1);

        setContentView(new TilesView(this));
    }

    public class TilesView extends View {

        //        ArrayList<ArrayList<Tile>> tiles = new ArrayList<ArrayList<Tile>>();
        Tile[][] tiles = new Tile[tilesX][tilesY]; //массив объектов Tile

        List<Integer> colors = new ArrayList<>(); //список цветов

        short tileWidth, tileHeight, modW, modH, i, j, temp;
        short[] pointFirst, pointSecond; //точки для того, чтобы наметить каждый прямоугольник

        Random random = new Random();

        Stopwatch stopwatch = null;

        public TilesView(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs, 0);
        }

        public TilesView(Context context) {
            super(context);
            generateColors((short) 300);
            generateTiles();
        }

        @Override
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            drawTiles(canvas);
        }

        void generateTiles() {
            for (i = 0; i < tilesX; i++)
                for (j = 0; j < tilesY; j++)
                    tiles[i][j] = new Tile(colors.get(random.nextInt(numberOfColors)), new short[]{0, 0}, new short[]{0, 0});
        }

        void blendColors() {
            for (i = 0; i < tilesX; i++)
                for (j = 0; j < tilesY; j++)
                    changeColor(tiles[i][j].getColor(), i, j);
        }

        void drawTiles(Canvas canvas) {
            modW = (short) (canvas.getWidth() % tilesX);
            modH = (short) (canvas.getHeight() % tilesY);
            tileWidth = (short) (canvas.getWidth() / tilesX);
            tileHeight = square ? (short) (canvas.getWidth() / tilesX) : (short) (canvas.getHeight() / tilesY); //тернарный оператор включает "квадратный режим", если square = true

            Paint paint = new Paint();

            tiles[0][0].setPoints(new short[]{0, 0}, new short[]{tileWidth, tileHeight});

            //вычисление точек для каждой плитки
            for (i = 0; i < tilesX; i++) { //на каждой итерации происходит переход на одну "строку" вниз
                temp = modW;
                for (j = 0; j < tilesY; j++) { //на каждой итерации совершается переход на одну плитку вправо
                    paint.setColor(tiles[i][j].getColor());
                    canvas.drawRect(new Rect(tiles[i][j].getPointFirstX(), tiles[i][j].getPointFirstY(), tiles[i][j].getPointSecondX(), tiles[i][j].getPointSecondY()), paint);
                    if (j < tilesY - 1)
                        tiles[i][j + 1].setXes(tiles[i][j].getPointSecondX(), (short) (tiles[i][j].getPointSecondX() + (temp > 0 ? tileWidth + 1 : tileWidth)));
                    if (j > 0 && j < tilesY - 1)
                        tiles[i][j + 1].setYs(tiles[i][j - 1].getPointFirstY(), tiles[i][j - 1].getPointSecondY());
                    if (j == 1)
                        tiles[i][j].setYs(tiles[i][j - 1].getPointFirstY(), tiles[i][j - 1].getPointSecondY());
                    temp--; /*операции с переменными modW, modH, tileWidth, tileHeight, temp нужны, чтобы остатки от деления ширины и высоты экрана на количество плиток
                            равномерно распределялись между плитками, иначе этот остаток будет отображаться белыми линиями справа и снизу*/
                }
                j = 0;
                if (i < tilesX - 1)
                    tiles[i + 1][j].setPoints((short) 0, tiles[i][j].getPointSecondY(), tileWidth, (short) (tiles[i][j].getPointSecondY() + (modH > 0 ? tileHeight + 1 : tileHeight)));
                modH--;
            }
        }

        void generateColors(short difference) { /*алгоритм рандомно генерирует цвета в зависимости от переменной difference,
                                                  при большом количестве плиток можно получить разные красивые цветовые гаммы*/
            colors.add(Color.parseColor("#00FFFFFF"));
            int currentColor = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
            for (i = 1; i < numberOfColors; i++) {
                while ((Math.abs(Color.red(colors.get(i - 1)) - Color.red(currentColor)) +
                        Math.abs(Color.green(colors.get(i - 1)) - Color.green(currentColor)) +
                        Math.abs(Color.blue(colors.get(i - 1)) - Color.blue(currentColor))) < difference)
                    currentColor = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
                colors.add(currentColor);
            }
        }

        void changeColor(int color, short i, short j) {
            tiles[i][j].setColor(colors.get((colors.indexOf(color) + 1) % numberOfColors));
        }

        void epilepsy() { //меняет цвета каждой плитки и перерисовывает экран в течение заданного количества времени и через заданный интервал (в миллисекундах)
            new CountDownTimer(duration, interval) {
                @Override
                public void onTick(long millisUntilFinished) {
                    blendColors();
                    invalidate();
                }

                @Override
                public void onFinish() {
                    Log.d("mytag", "done!");
                }
            }.start();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {

            short x = (short) event.getX();
            short y = (short) event.getY();

            if (event.getAction() == MotionEvent.ACTION_DOWN) {

                if (stopwatch == null && !epilepsy) //запускает таймер при касании экрана, если он ещё не запущен
                    stopwatch = new Stopwatch();

                pointFirst = new short[]{0, 0};
                pointSecond = new short[]{tileWidth, tileHeight};

                for (i = 0; i < tilesX; i++) { //проверка, какой плитки коснулись, при помощи координат касания
                    for (j = 0; j < tilesY; j++) {
                        if (x > pointFirst[0] & y > pointFirst[1] & x < pointSecond[0] & y < pointSecond[1]) {
                            Log.d("mytag", "1. Color of the tile = " + colors.indexOf(tiles[i][j].getColor()) + " " + colors.size());
                            changeColor(tiles[i][j].getColor(), i, j);
                            Log.d("mytag", "Color of the tile = " + i + ", " + j + " changed");
                            Log.d("mytag", "2. Color of the tile = " + colors.indexOf(tiles[i][j].getColor()));
                            x = i;
                            y = j;
                            for (int i = 0; i < tilesX; i++) {
                                changeColor(tiles[x][i].getColor(), x, (short) i);
                                changeColor(tiles[i][y].getColor(), (short) i, y);
                            }
                            break;
                        }
                        pointFirst[0] += tileWidth; //на каждой итерации совершается переход на одну плитку вправо
                        pointSecond[0] += tileWidth;
                    }
                    pointFirst[0] = 0; //на каждой итерации происходит переход на одну "строку" вниз
                    pointSecond[0] = tileWidth;
                    pointFirst[1] += tileHeight;
                    pointSecond[1] += tileHeight;
                }

                if (epilepsy)
                    epilepsy();
                else
                    check();

                invalidate(); //заставляет экран перерисоваться
            }
            return true;
        }

        void check() { //проверяет, все ли плитки одинакового цвета и выводит время решения головоломки
            int counter = 0;
            for (i = 0; i < tilesX; i++)
                for (j = 0; j < tilesY; j++)
                    if (tiles[i][j].getColor() == colors.get(1))
                        counter++;

            if (counter == tilesX * tilesY || counter == 0) {
                double time = stopwatch.elapsedTime();
                stopwatch = null; //обнуление таймера
                Toast.makeText(TilesView.super.getContext(), "Congratulations! " + time + " sec.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class Stopwatch { //класс простого таймера

        private long start;

        public Stopwatch() {
            start = System.currentTimeMillis();
        }

        public double elapsedTime() {
            long now = System.currentTimeMillis();
            return (now - start) / 1000.0;
        }
    }
}