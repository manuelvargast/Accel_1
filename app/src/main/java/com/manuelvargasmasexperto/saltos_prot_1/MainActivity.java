package com.manuelvargasmasexperto.saltos_prot_1;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    //1) DECLARACION VARIABLES
    SensorManager sensorManager; //Gestiona sensores
    Sensor sensor; //Almacena sensor particular
    SensorEventListener sensorEventListener; //Avisa de eventos del sensor
    int latigo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //2) INICIALIZACIÓN VARIABLES
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);//Llamado a gestor
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);//Especificacion
        if(sensor == null)
            finish();//Fin o aviso de "no tienes acelerometro"

        // 3) INICIALIZACION INTERFAZ Y SOBREESCRITURA
        sensorEventListener = new SensorEventListener() {

            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {

                //4) CAPTURA VALORES ACELEROMETRO EN ARREGLO: 0 = X, 1 = Y, 2 = Z
                    float x = sensorEvent.values[0];
                    if(x < -5 && latigo == 0){ //= MOVIMIENTO A LA DERECHA
                        latigo++;
                        getWindow().getDecorView().setBackgroundColor(Color.BLUE);
                    }else if(x > 5 && latigo == 1) { //= YA ESTABAMOS EN DER Y LO MOV A IZQ
                        latigo++;
                        getWindow().getDecorView().setBackgroundColor(Color.RED);
                    }

                    if (latigo == 2){
                        sonido();
                        latigo = 0;
                    }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }

        }; //WTF?!

        comienzo();

    }

    private void sonido(){
        //5) SONIDO DE LATIGO
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.latigo);
        mediaPlayer.start();
    }

    private void comienzo(){
        //6) INICIO DEL EVENTO
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void fin(){
        //7) DETENCION DEL EVENTO
        sensorManager.unregisterListener(sensorEventListener);
    }

    @Override
    protected void onPause() {
        fin();
        super.onPause();
    }

    @Override
    protected void onResume() {
        comienzo();
        super.onResume();
    }
}
