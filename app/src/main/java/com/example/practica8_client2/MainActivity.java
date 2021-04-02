package com.example.practica8_client2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{

    private Button upBtn,downBtn,leftBtn,rightBtn;
    private Button fireBtn;
    private BufferedWriter bwriter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        upBtn = findViewById(R.id.upBtn);
        downBtn = findViewById(R.id.downBtn);
        leftBtn = findViewById(R.id.leftBtn);
        rightBtn = findViewById(R.id.rightBtn);
        fireBtn = findViewById(R.id.fireBtn);

        //Metodo de suscripcion
        upBtn.setOnTouchListener(this);
        downBtn.setOnTouchListener(this);
        leftBtn.setOnTouchListener(this);
        rightBtn.setOnTouchListener(this);
        fireBtn.setOnTouchListener(this);


        new Thread(

                () -> {

                    try {
                        //Direccion del computador

                        //Server -> 192.168.18.4
                        Socket socket = new Socket("192.168.18.4", 6000);

                        OutputStream os = socket.getOutputStream();
                        OutputStreamWriter osw = new OutputStreamWriter(os);
                        bwriter = new BufferedWriter(osw);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

        ).start();


    }

    //metodo de notificacion

    public void onClickEvolucionado(View v) {
        Gson gson = new Gson();


        switch (v.getId()){
            case R.id.upBtn:
                Coordenada up = new Coordenada("UP");
                String jsonup = gson.toJson(up);
                enviar(jsonup);
                break;
            case R.id.downBtn:
                Coordenada down = new Coordenada("DOWN");
                String jsondown = gson.toJson(down);
                enviar(jsondown);
                break;
            case R.id.leftBtn:
                Coordenada left = new Coordenada("LEFT");
                String jsonleft = gson.toJson(left);
                enviar(jsonleft);
                break;
            case R.id.rightBtn:
                Coordenada right = new Coordenada("RIGHT");
                String jsonright = gson.toJson(right);
                enviar(jsonright);
                break;
        }
    }

    public void enviar(String msg){
        new Thread(() -> {
            try {
                bwriter.write(msg + "\n");
                bwriter.flush();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Gson gson = new Gson();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                switch(v.getId()) {
                    case R.id.upBtn:
                        Coordenada coordenada = new Coordenada("UPSTART");
                        String json = gson.toJson(coordenada);
                        enviar(json);
                        break;
                    case R.id.downBtn:
                        Coordenada downStart = new Coordenada("DOWNSTART");
                        String downStartJson = gson.toJson(downStart);
                        enviar(downStartJson);
                        break;
                    case R.id.rightBtn:
                        Coordenada rightStart = new Coordenada("RIGHTSTART");
                        String rightStartJson = gson.toJson(rightStart);
                        enviar(rightStartJson);
                        break;
                    case R.id.leftBtn:
                        Coordenada leftStart = new Coordenada("LEFTSTART");
                        String leftStartJson = gson.toJson(leftStart);
                        enviar(leftStartJson);
                        break;

                    //PARA DISPARAR
                    case R.id.fireBtn:
                        Coordenada fireStart = new Coordenada("FIRESTART");
                        String fireStartJson = gson.toJson(fireStart);
                        enviar(fireStartJson);
                        break;


                }
                break;


            case MotionEvent.ACTION_UP:
                switch (v.getId()) {
                    case R.id.upBtn:
                        Coordenada upStop = new Coordenada("UPSTOP");
                        String upStopJson = gson.toJson(upStop);
                        enviar(upStopJson);
                        break;
                    case R.id.downBtn:
                        Coordenada downStop = new Coordenada("DOWNSTOP");
                        String downStopJson = gson.toJson(downStop);
                        enviar(downStopJson);
                        break;
                    case R.id.rightBtn:
                        Coordenada rightStop = new Coordenada("RIGHTSTOP");
                        String rightStopJson = gson.toJson(rightStop);
                        enviar(rightStopJson);
                        break;
                    case R.id.leftBtn:
                        Coordenada leftStop = new Coordenada("LEFTSTOP");
                        String leftStopJson = gson.toJson(leftStop);
                        enviar(leftStopJson);
                        break;

                }
                break;
        }



        return true;
    }
}