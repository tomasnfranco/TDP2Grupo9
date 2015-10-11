package com.tdp2grupo9.adopcion;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.tdp2grupo9.R;
import com.tdp2grupo9.modelo.Mensaje;
import com.tdp2grupo9.modelo.Publicacion;
import com.tdp2grupo9.view.MensajeAdapter;

import java.util.List;


public class PerfilMascotaActivity extends Activity{

    private Publicacion publicacion;
    private List<Mensaje> mensajes;
    private ListView listView;
    //private SliderLayout video_slider;
    //private SliderLayout photo_slider;
    private double currentLat = -34.5976786;
    private double currentLon = -58.4430195;
    private GoogleMap map;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_mascota);
        cargarPortada();

    }

    public void cargarPortada() {
        ((TextView) findViewById(R.id.publicacion_name)).setText(publicacion.getNombreMascota());
        ((ImageView) findViewById(R.id.publicacion_image)).setImageBitmap(publicacion.getImagenes().get(0).getBitmap());
        int loadedIcons = 0;
        //if (cargarIconoHogarTransito(publicacion, view, loadedIcons)) { ++loadedIcons; }
        //if (cargarIconoCuidadosEspeciales(publicacion, view, loadedIcons)) { ++loadedIcons; }
        //if (cargarIconoCondicionesAdopcion(publicacion, view, loadedIcons)) { ++loadedIcons; }

    }

    private void cargarInformacionBasica(){
        ((TextView) findViewById(R.id.infSexo)).setText(publicacion.getSexo().toString());
        ((TextView) findViewById(R.id.infEdad)).setText(publicacion.getEdad().toString());
        ((TextView) findViewById(R.id.infColor)).setText(publicacion.getColor().toString());
        ((TextView) findViewById(R.id.infRaza)).setText(publicacion.getRaza().toString());
        ((TextView) findViewById(R.id.infTamanio)).setText(publicacion.getTamanio().toString());
    }


    private void cargarListViewConsultas(){
        listView = (ListView) findViewById(R.id.listView_consultas);
        listView.setAdapter(new MensajeAdapter(this, mensajes));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view,
                                    int position, long arg) {
            }
        });
    }

    /*
    private void cargarVideos(ArrayList<String> urlVideos) {
        video_slider = (SliderLayout) view.findViewById(R.id.video_slider);
        final HashMap<String, String> videos = new HashMap<>();
        int aux = 0;
        for (String urlVideo : urlVideos) {
            videos.put("Video " + aux, urlVideo);
            aux++;
        }

        if (videos.isEmpty()) {
            CardView cardview_video = (CardView) view.findViewById(R.id.cardview_videos_pm);
            cardview_video.setVisibility(View.GONE);
        } else {
            for (final String name : videos.keySet()) {
                TextSliderView slide = new TextSliderView(getActivity());
                slide.image("http://img.youtube.com/vi/" + videos.get(name) + "/mqdefault.jpg");
                slide.setScaleType(BaseSliderView.ScaleType.CenterCrop);
                slide.description("Reproducir");
                slide.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                    @Override
                    public void onSliderClick(BaseSliderView baseSliderView) {
                        Toast.makeText(baseSliderView.getContext(), "Click", Toast.LENGTH_SHORT).show();
                        try {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videos.get(name)));
                            intent.putExtra("force_fullscreen", true);
                            startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + videos.get(name)));
                            startActivity(intent);
                        }
                    }
                });
                video_slider.addSlider(slide);
            }
            video_slider.setPresetTransformer(SliderLayout.Transformer.RotateDown);
            video_slider.setCustomAnimation(new DescriptionAnimation());
            video_slider.setCustomIndicator((PagerIndicator) view.findViewById(R.id.custom_indicator_video));
        }
    }*/

    private ImageView getNextIconView(View view, int loadedIcons) {
        switch (loadedIcons) {
            case 0: return (ImageView) view.findViewById(R.id.publicacion_icon_one);
            case 1: return (ImageView) view.findViewById(R.id.publicacion_icon_dos);
            default: return (ImageView) view.findViewById(R.id.publicacion_icon_tres);
        }
    }

    private boolean cargarIconoSiEsNecesario(Publicacion publicacion, View view, boolean necesario, Drawable drawable, int loadedIcons) {
        if (necesario) {
            getNextIconView(view, loadedIcons).setImageDrawable(drawable);
        }
        return necesario;
    }

    private boolean cargarIconoHogarTransito(Publicacion publicacion, View view, int loadedIcons) {
        return cargarIconoSiEsNecesario(publicacion, view, publicacion.getNecesitaTransito(), view.getResources().getDrawable(R.drawable.home_transit), loadedIcons);
    }

    private boolean cargarIconoCuidadosEspeciales(Publicacion publicacion, View view, int loadedIcons) {
        return cargarIconoSiEsNecesario(publicacion, view, publicacion.getRequiereCuidadosEspeciales(), view.getResources().getDrawable(R.drawable.cuidados_especiales), loadedIcons);
    }


    private boolean cargarIconoCondicionesAdopcion(Publicacion publicacion, View view, int loadedIcons) {
        return cargarIconoSiEsNecesario(publicacion, view, publicacion.getCondiciones().isEmpty(), view.getResources().getDrawable(R.drawable.ic_condiciones), loadedIcons);
    }




}
