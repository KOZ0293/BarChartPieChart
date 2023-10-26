package com.vsb.kru13.barcodetemplate;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class BarcodeView extends View {

    //UPC-A code

    //http://en.wikipedia.org/wiki/EAN_code
    //http://www.terryburton.co.uk/barcodewriter/generator/


    static final int[] L = {0x0D,  //000 1101
            0x19,  //001 1001
            0x13,  //001 0011
            0x3D,  //011 1101
            0x23,  //010 0011
            0x31,  //011 0001
            0x2F,  //010 1111
            0x3B,  //011 1011
            0x37,  //011 0111
            0x0B   //000 1011
    };

    static final int[] R = {0x72, //111 0010
            0x66, //110 0110
            0x6C, //110 1100
            0x42, //100 0010
            0x5C, //101 1100
            0x5E, //100 1110
            0x50, //101 0000
            0x44, //100 0100
            0x48, //100 1000
            0x74  //111 0100
    };

    final static int BARCODE_WIDTH =  600;
    final static int BARCODE_HEIGHT = 200;
    final static int BARCODE_LINE_WIDTH = 5;

    // čísla čárového kódu
    int code[] = new int[12];

    public BarcodeView(Context context) {
        super(context);
        setDefaults();
    }

    public BarcodeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setDefaults();
    }

    public BarcodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setDefaults();
    }

    public BarcodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setDefaults();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // při změně velikosti view,  w a h obsahují novou velikost
    }




    // nastaví výchozí hodnoty
    void setDefaults() {
        int copyFrom[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2};
        System.arraycopy(copyFrom, 0, code, 0, copyFrom.length);
    }
    private void drawNumbers(Canvas canvas, Paint black){
        int myX=20;
        for (int i=0; i<code.length; i++){
            canvas.drawText(String.valueOf(code[i]), myX, 230, black);
            myX+=40;
        }

    }

    private int drawStartEnd(Canvas canvas, int x,Paint red, Paint white){
        canvas.drawLine(x,0,x,BARCODE_HEIGHT,red);
        canvas.drawLine(x+BARCODE_LINE_WIDTH,0,x+BARCODE_LINE_WIDTH,BARCODE_HEIGHT,white);
        canvas.drawLine(x+2*BARCODE_LINE_WIDTH,0,x+2*BARCODE_LINE_WIDTH,BARCODE_HEIGHT,red);
        return x + 3*BARCODE_LINE_WIDTH;
    }

    private int drawMiddle(Canvas canvas, int x, Paint red, Paint white){
        canvas.drawLine(x,0,x,BARCODE_HEIGHT,white);
        canvas.drawLine(x+BARCODE_LINE_WIDTH,0,x+BARCODE_LINE_WIDTH,BARCODE_HEIGHT,red);
        canvas.drawLine(x+2*BARCODE_LINE_WIDTH,0,x+2*BARCODE_LINE_WIDTH,BARCODE_HEIGHT,white);
        canvas.drawLine(x+3*BARCODE_LINE_WIDTH,0,x+3*BARCODE_LINE_WIDTH,BARCODE_HEIGHT,red);
        canvas.drawLine(x+4*BARCODE_LINE_WIDTH,0,x+4*BARCODE_LINE_WIDTH,BARCODE_HEIGHT,white);

        return x + 5*BARCODE_LINE_WIDTH;
    }


    private int drawLineFromNumber(Canvas canvas, int x, int num, Paint black, Paint white){
        int currentPos = x;

        for(int i = 7; i > 0; i--){
            currentPos += BARCODE_LINE_WIDTH;
            int bit = (num >> (i-1)) & 1; // Získání i-tého bitu z čísla num

            if (bit == 0){
                canvas.drawLine(currentPos, 0, currentPos, BARCODE_HEIGHT, white);
            } else {
                canvas.drawLine(currentPos, 0, currentPos, BARCODE_HEIGHT, black);
            }
        }

        return currentPos + BARCODE_LINE_WIDTH;
    }

    private void drawBarcode(Canvas canvas,int[]code, Paint black, Paint white, Paint red){
        int posX = 0;
        posX = drawStartEnd(canvas,posX,red,white);
        for(int i = 0; i<code.length/2;i++){
            posX = drawLineFromNumber(canvas,posX,L[code[i]],black,white);
        }
        posX = drawMiddle(canvas,posX,red,white);
        for(int i = 6; i<code.length; i++){
            posX = drawLineFromNumber(canvas,posX,R[code[i]],black,white);
        }
        drawStartEnd(canvas,posX,red,white);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint twPaint = new Paint();
        twPaint.setColor(Color.WHITE);
        twPaint.setStrokeWidth(BARCODE_LINE_WIDTH);

        Paint tbPaint = new Paint();
        tbPaint.setColor(Color.BLACK);
        tbPaint.setStrokeWidth(BARCODE_LINE_WIDTH);

        Paint trPaint = new Paint();
        trPaint.setColor(Color.RED);
        trPaint.setStrokeWidth(BARCODE_LINE_WIDTH);

        // vykreslí bílý obdelník do kterého se bude kreslit čárový kód
        canvas.drawRect(new Rect(0, 0, BARCODE_WIDTH, BARCODE_HEIGHT), twPaint);

        // tloušťka čáry


        // velikost písma, antialiasing
        trPaint.setTextSize(30);
        trPaint.setAntiAlias(true);
        tbPaint.setTextSize(30);
        tbPaint.setAntiAlias(true);

        //canvas.drawLine(0, 0, BARCODE_WIDTH, BARCODE_HEIGHT, tbPaint);
        //canvas.drawLine(0, BARCODE_HEIGHT, BARCODE_WIDTH, 0, tbPaint);
        drawBarcode(canvas,code,tbPaint,twPaint,trPaint);
        drawNumbers(canvas,tbPaint);

        //canvas.drawText("Zde bude čárový kód", (int)(BARCODE_WIDTH * 0.3), (int)(BARCODE_HEIGHT * 0.95), trPaint);

    }


    public void getNum(int[] num) {
        System.arraycopy(num, 0, code, 0, num.length);
    }
}
