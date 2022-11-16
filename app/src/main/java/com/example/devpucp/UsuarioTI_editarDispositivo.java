package com.example.devpucp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

public class UsuarioTI_editarDispositivo extends AppCompatActivity {

    ImageButton beforeButtonEditar, nextButtonEditar;
    ImageSwitcher imageSwitcherDisp;

    int index=0;
    int imagenesid[]={R.drawable.ic_profile_foreground, R.drawable.ic_logo_pucp_celeste_foreground, R.drawable.ic_devices_foreground};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_ti_editar_dispositivo);

        beforeButtonEditar=findViewById(R.id.btn_UsuarioTI_before);
        nextButtonEditar=findViewById(R.id.btn_UsuarioTI_next);
        imageSwitcherDisp= findViewById(R.id.UsuarioTI_imageSwitcherDispEditar);

        beforeButtonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                --index;
                if (index<0){
                    index=imagenesid.length-1;
                }
                imageSwitcherDisp.setImageResource(imagenesid[index]);
            }
        });

        nextButtonEditar.setOnClickListener(new View.OnClickListener() {
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
}