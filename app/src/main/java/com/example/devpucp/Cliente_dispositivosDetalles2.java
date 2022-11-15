package com.example.devpucp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

public class Cliente_dispositivosDetalles2 extends AppCompatActivity {
    ImageButton beforeButton, nextButton;
    ImageSwitcher imageSwitcherDisp;

    int index=0;
    int imagenesid[]={R.drawable.ic_profile_foreground, R.drawable.ic_logo_pucp_celeste_foreground, R.drawable.ic_devices_foreground};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_dispositivos_detalles2);


        beforeButton=findViewById(R.id.btn_before);
        nextButton=findViewById(R.id.btn_next);
        imageSwitcherDisp= findViewById(R.id.Cliente_imageSwitcherDispDetalle);

        beforeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                --index;
                if (index<0){
                    index=imagenesid.length-1;
                }
                imageSwitcherDisp.setImageResource(imagenesid[index]);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index++;
                if (index==imagenesid.length){
                    index=0;
                }
                imageSwitcherDisp.setImageResource(imagenesid[index]);
            }
        });

        imageSwitcherDisp.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {

                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setMaxWidth(250);
                imageView.setMaxHeight(250);

                return imageView;
            }
        });

        imageSwitcherDisp.setImageResource(imagenesid[index]);


    }

    public void irReservarDetalle(View view){
        Intent intent = new Intent(Cliente_dispositivosDetalles2.this, Cliente_Reservar.class);
        startActivity(intent);
    }
}