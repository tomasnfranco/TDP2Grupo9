package com.tdp2grupo9.listview;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.tdp2grupo9.R;
import com.tdp2grupo9.adapter.GalleryAdapter;
import com.tdp2grupo9.adapter.MensajeAdapter;
import com.tdp2grupo9.maps.MapsActivity;
import com.tdp2grupo9.modelo.Imagen;
import com.tdp2grupo9.modelo.Mensaje;
import com.tdp2grupo9.modelo.Publicacion;
import com.tdp2grupo9.modelo.PublicacionAtributos;
import com.tdp2grupo9.modelo.Usuario;
import com.tdp2grupo9.utils.TiposEnum;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class PublicacionesAdapter extends BaseExpandableListAdapter {

    private List<Publicacion> publicaciones;
    private Context context;
    private ObtenerAtributosTask obtenerAtributosTask;
    private GuardarPostulacionTask guardarPostulacionTask;
    private EnviarConsultaTask enviarConsultaTask;

    protected PublicacionAtributos publicacionAtributos;
    private SliderLayout photo_slider;
    private SliderLayout video_slider;
    private ListView listView;
    private List<Mensaje> mensajes;
    private ImageView imagenSeleccionada;
    private Gallery gallery;
    private List<Imagen> imagenes;
    private String especieView;
    private ImageView imagenPosicion;
    private Double latitud;
    private Double longitud;
    private String nombreMascota;
    private TiposEnum tipos;
    private Button btnPostularme;
    private TextView consultaParaEnviar;
    private ImageButton btnEnviarConsulta;

    private ImageButton btnRespuesta;
    private EditText editTextRespuesta;


    public PublicacionesAdapter(Context context, List<Publicacion> publicaciones, TiposEnum tipos) {
        this.publicaciones = publicaciones;
        this.context = context;
        this.mensajes = new ArrayList<>();
        this.imagenes = new ArrayList<>();
        this.tipos = tipos;
        obtenerAtributos();
    }

    protected void obtenerAtributos() {
        publicacionAtributos = new PublicacionAtributos();
        obtenerAtributosTask = new ObtenerAtributosTask(this.publicacionAtributos);
        obtenerAtributosTask.execute((Void) null);
    }


    private View getInflatedViewIfNecessary(View view, ViewGroup viewGroup) {
        View publicacionView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            publicacionView = inflater.inflate(R.layout.publicacion_view, viewGroup, false);
        } else {
            publicacionView = view;
            limpiarPublicacionView(publicacionView);
        }
        return publicacionView;
    }

    private View getInflatedViewItemIfNecessary(View view, ViewGroup viewGroup) {
        View publicacionItemView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            publicacionItemView = inflater.inflate(R.layout.activity_perfil_mascota, viewGroup, false);
        } else {
            publicacionItemView = view;
        }
        return publicacionItemView;
    }

    private void limpiarPublicacionView(View publicacionView) {
        ((TextView) publicacionView.findViewById(R.id.publicacion_name)).setText("");
        ((ImageView) publicacionView.findViewById(R.id.publicacion_image)).setImageBitmap(null);
        ((ImageView) publicacionView.findViewById(R.id.publicacion_icon_one)).setImageBitmap(null);
        ((ImageView) publicacionView.findViewById(R.id.publicacion_icon_dos)).setImageBitmap(null);
        ((ImageView) publicacionView.findViewById(R.id.publicacion_icon_tres)).setImageBitmap(null);
    }

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
        return cargarIconoSiEsNecesario(publicacion, view, !publicacion.getCondiciones().isEmpty(), view.getResources().getDrawable(R.drawable.ic_condiciones), loadedIcons);
    }

    private String parserDateText(Date fecha){
        int dia = fecha.getDate();
        int mes = fecha.getMonth() + 1;
        int anio = fecha.getYear() + 1900;

        String date = dia + "/" + mes + "/" + anio;

        return date;
    }

    @Override
    public int getGroupCount() {
        return publicaciones.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int i) {
        return publicaciones.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        View publicacionView = getInflatedViewIfNecessary(view, viewGroup);
        ((TextView) publicacionView.findViewById(R.id.publicacion_name)).setText(publicaciones.get(i).getNombreMascota());
        ((ImageView) publicacionView.findViewById(R.id.ivLocalizacion)).setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_localizacion));
        if (publicaciones.get(i).getImagenes().size() > 0)
            ((ImageView) publicacionView.findViewById(R.id.publicacion_image)).setImageBitmap(publicaciones.get(i).getImagenes().get(0).getBitmap());
        ((TextView) publicacionView.findViewById(R.id.tv_fecha_publicacion)).setText(parserDateText(publicaciones.get(i).getFechaPublicacion()));
        String distancia = publicaciones.get(i).getDistancia().toString() + "km";
        ((TextView) publicacionView.findViewById(R.id.tvLocalizacion)).setText(distancia);
        int loadedIcons = 0;
        if (cargarIconoHogarTransito(publicaciones.get(i), publicacionView, loadedIcons)) { ++loadedIcons; }
        if (cargarIconoCuidadosEspeciales(publicaciones.get(i), publicacionView, loadedIcons)) { ++loadedIcons; }
        if (cargarIconoCondicionesAdopcion(publicaciones.get(i), publicacionView, loadedIcons)) { ++loadedIcons; }
        return publicacionView;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        View itemView = getInflatedViewItemIfNecessary(view, viewGroup);
        final int id = publicaciones.get(i).getId();
        final int position = i;
        btnPostularme = (Button) itemView.findViewById(R.id.buttonPublicacion);
        if (tipos == TiposEnum.MIS_PUBLICACIONES || tipos == TiposEnum.MIS_POSTULACIONES)
            btnPostularme.setVisibility(View.GONE);

        btnPostularme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarPostulacionTask = new GuardarPostulacionTask(position, id);
                guardarPostulacionTask.execute((Void)null);

            }
        });

        cargarInformacionBasica(i, itemView);
        cargarLocalizacionMascota(i, itemView);
        cargarFotos(i, itemView);
        cargarVideos(i, itemView);
        cargarInformacionAdicional(i, itemView);
        cargarListViewMensajes(i, itemView);

        return itemView;
    }

    private void cargarInformacionBasica(int i, View v){
        ((TextView) v.findViewById(R.id.infSexo)).setText(publicacionAtributos.getSexos().get(publicacionAtributos.getSexos().indexOf(publicaciones.get(i).getSexo())).toString());
        ((TextView) v.findViewById(R.id.infEdad)).setText(publicacionAtributos.getEdades().get(publicacionAtributos.getEdades().indexOf(publicaciones.get(i).getEdad())).toString());
        ((TextView) v.findViewById(R.id.infColor)).setText(publicacionAtributos.getColores().get(publicacionAtributos.getColores().indexOf(publicaciones.get(i).getColor())).toString());
        ((TextView) v.findViewById(R.id.infRaza)).setText(publicacionAtributos.getRazas().get(publicacionAtributos.getRazas().indexOf(publicaciones.get(i).getRaza())).toString());
        ((TextView) v.findViewById(R.id.infTamanio)).setText(publicacionAtributos.getTamanios().get(publicacionAtributos.getTamanios().indexOf(publicaciones.get(i).getTamanio())).toString());
    }

    private void cargarInformacionAdicional(int i, View v){
        String condiciones = publicaciones.get(i).getCondiciones();
        String vacunas = publicacionAtributos.getVacunasAlDia().get(publicacionAtributos.getVacunasAlDia().indexOf(publicaciones.get(i).getVacunasAlDia())).toString();
        String castrado = publicacionAtributos.getCastrados().get(publicacionAtributos.getCastrados().indexOf(publicaciones.get(i).getCastrado())).toString();
        String proteccion = publicacionAtributos.getProtecciones().get(publicacionAtributos.getProtecciones().indexOf(publicaciones.get(i).getProteccion())).toString();
        String energia = publicacionAtributos.getEnergias().get(publicacionAtributos.getEnergias().indexOf(publicaciones.get(i).getEnergia())).toString();
        String papeles = publicacionAtributos.getPapelesAlDia().get(publicacionAtributos.getPapelesAlDia().indexOf(publicaciones.get(i).getPapelesAlDia())).toString();
        String compatibleCon = publicacionAtributos.getCompatibilidades().get(publicacionAtributos.getCompatibilidades().indexOf(publicaciones.get(i).getCompatibleCon())).toString();

        if (!condiciones.isEmpty())
            ((TextView) v.findViewById(R.id.infCondicionesAdopcion)).setText(condiciones);

        String mensaje="";

        if (vacunas.equals("Si"))
            mensaje+= v.getContext().getString(R.string.tiene_vacunas)+". ";
        else if (vacunas.equals("No"))
            mensaje+= "No " + v.getContext().getString(R.string.tiene_vacunas).toLowerCase() + ". ";

        if (papeles.equals("Si"))
            mensaje+= v.getContext().getString(R.string.tiene_papeles)+". ";
        else if (papeles.equals("No"))
            mensaje+= "No " + v.getContext().getString(R.string.tiene_papeles).toLowerCase() + ". ";

        if (castrado.equals("Si"))
            mensaje+= v.getContext().getString(R.string.esta_castrado)+". ";
        else if (papeles.equals("No"))
            mensaje+= "No " + v.getContext().getString(R.string.esta_castrado).toLowerCase() + ". ";

        if(!compatibleCon.equals("No aplica"))
            mensaje+= v.getContext().getString(R.string.es_compatible) +" " + compatibleCon.toLowerCase() + ". ";

        if(!energia.equals("No aplica"))
            mensaje+= "Es " + energia.toLowerCase() + ". ";

        if(!proteccion.equals("No aplica"))
            mensaje+= "Es " + proteccion.toLowerCase() + ". ";

        ((TextView) v.findViewById(R.id.infAdicional)).setText(mensaje);
    }

    private void cargarFotos(int i, View view) {
        imagenes = publicaciones.get(i).getImagenes();

        if (imagenes.isEmpty()) {
            CardView cardview_photos = (CardView) view.findViewById(R.id.cardview_fotos_pm);
            cardview_photos.setVisibility(View.GONE);
        } else {
            CardView cardview_photos = (CardView) view.findViewById(R.id.cardview_fotos_pm);
            cardview_photos.setVisibility(View.VISIBLE);

            if(imagenes.size() > 1){
                imagenSeleccionada = (ImageView) view.findViewById(R.id.image_seleccionada);
                gallery = (Gallery) view.findViewById(R.id.gallery_photos);

                gallery.setAdapter(new GalleryAdapter(view.getContext(), imagenes));
                gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    public void onItemClick(AdapterView parent, View v, int position, long id) {
                        imagenSeleccionada.setImageBitmap(imagenes.get(position).getBitmap());
                    }

                });
            } else {
                imagenSeleccionada = (ImageView) view.findViewById(R.id.image_seleccionada);
                imagenSeleccionada.setImageBitmap(imagenes.get(0).getBitmap());
            }
        }

    }

    private void cargarVideos(int i, View view) {

        String video = publicaciones.get(i).getVideoLink();
        video_slider = (SliderLayout) view.findViewById(R.id.video_slider);
        final HashMap<String, String> videos = new HashMap<>();

        if (!video.isEmpty()) {
            video = video.substring(32);
            int aux = 0;
            videos.put("Video " + aux, video);
        }

        if (videos.isEmpty()) {
            CardView cardview_video = (CardView) view.findViewById(R.id.cardview_videos_pm);
            cardview_video.setVisibility(View.GONE);
        } else {
            CardView cardview_video = (CardView) view.findViewById(R.id.cardview_videos_pm);
            cardview_video.setVisibility(View.VISIBLE);
            for (final String name : videos.keySet()) {
                TextSliderView slide = new TextSliderView(view.getContext());
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
                            context.startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + videos.get(name)));
                            context.startActivity(intent);
                        }
                    }
                });
                video_slider.addSlider(slide);
            }
            video_slider.setPresetTransformer(SliderLayout.Transformer.RotateDown);
            video_slider.setCustomAnimation(new DescriptionAnimation());
            video_slider.setCustomIndicator((PagerIndicator) view.findViewById(R.id.custom_indicator_video));
        }
    }
    
    private void cargarLocalizacionMascota(int i, View v){

        especieView = publicacionAtributos.getEspecies().get(publicacionAtributos.getEspecies().indexOf(publicaciones.get(i).getEspecie())).toString();
        imagenPosicion = (ImageView) v.findViewById(R.id.iv_localizacion_mascota);
        imagenPosicion.setImageBitmap(BitmapFactory.decodeResource(v.getResources(),R.drawable.localizacion_perro));

        latitud = publicaciones.get(i).getLatitud();
        longitud = publicaciones.get(i).getLongitud();
        nombreMascota = publicaciones.get(i).getNombreMascota();

        if (especieView.equals("Gato"))
            imagenPosicion.setImageBitmap(BitmapFactory.decodeResource(v.getResources(),R.drawable.localizacion_gato));

        imagenPosicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MapsActivity.class);
                intent.putExtra("latitud", latitud);
                intent.putExtra("longitud", longitud);
                intent.putExtra("nombre", nombreMascota);
                intent.putExtra("especie", especieView);
                context.startActivity(intent);
            }
        });
        
    }

    private void cargarListViewMensajes(int i, View v) {

        //mensajes = publicaciones.get(i).getMensajes();
        mensajes = loadMensajesMock();

        consultaParaEnviar = (TextView) v.findViewById(R.id.consulta_para_enviar);
        btnEnviarConsulta = (ImageButton) v.findViewById(R.id.btn_enviar_consulta);

        if (tipos == TiposEnum.MIS_PUBLICACIONES) {
            consultaParaEnviar.setVisibility(View.GONE);
            btnEnviarConsulta.setVisibility(View.GONE);

            listView = (ListView) v.findViewById(R.id.listView_consultas);
            listView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            });

            //mensajes = publicaciones.get(i).getMensajes();

            if (!mensajes.isEmpty()) {
                listView.setAdapter(new MensajeAdapter(v.getContext(), mensajes, tipos));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View view,
                                            final int position, long arg) {
                        btnRespuesta = (ImageButton) view.findViewById(R.id.btn_responder);
                        editTextRespuesta = (EditText) view.findViewById(R.id.editText_respuesta);


                        btnRespuesta.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //TODO guardar respuesta
                            }
                        });

                    }
                });
            }

        }else {
            consultaParaEnviar.setVisibility(View.VISIBLE);
            btnEnviarConsulta.setVisibility(View.VISIBLE);

            final Mensaje mensaje = new Mensaje();
            mensaje.setPregunta(consultaParaEnviar.getText().toString());

            btnEnviarConsulta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    enviarConsultaTask = new EnviarConsultaTask();
                    enviarConsultaTask.execute((Void) null);
                }
            });

            listView = (ListView) v.findViewById(R.id.listView_consultas);
            listView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            });

            mensajes = publicaciones.get(i).getMensajes();

            if (!mensajes.isEmpty()) {
                listView.setAdapter(new MensajeAdapter(v.getContext(), mensajes, tipos));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View view,
                                            int position, long arg) {
                    }
                });
            }
        }


    }

    private List<Mensaje> loadMensajesMock(){
        List<Mensaje> mensajes = new ArrayList<>();
        Mensaje m1 = new Mensaje();
        m1.setPregunta("hola");
        m1.setRespuesta("chau");
        Mensaje m2 = new Mensaje();
        m2.setPregunta("Tiene pulgas");
        Mensaje m3 = new Mensaje();
        m3.setPregunta("hello");
        m3.setRespuesta("bye");
        mensajes.add(m1);
        mensajes.add(m2);
        mensajes.add(m3);
        return mensajes;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }



    public class ObtenerAtributosTask extends AsyncTask<Void, Void, Boolean> {

        PublicacionAtributos publicacionAtributos;
        ObtenerAtributosTask(PublicacionAtributos publicacionAtributos) {
            this.publicacionAtributos = publicacionAtributos;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                this.publicacionAtributos.cargarAtributos(Usuario.getInstancia().getToken());
                Thread.sleep(500);
            } catch (InterruptedException e) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            obtenerAtributosTask = null;
        }

        @Override
        protected void onCancelled() {
            obtenerAtributosTask = null;
        }
    }

    public class GuardarPostulacionTask extends AsyncTask<Void, Void, Boolean> {

        int id_publicacion;
        int position;

        GuardarPostulacionTask(int position, int id_publicacion) {
            this.id_publicacion = id_publicacion;
            this.position = position;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                publicaciones.get(position).quieroAdoptar(Usuario.getInstancia().getToken(), id_publicacion);
                Thread.sleep(500);
            } catch (InterruptedException e) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            guardarPostulacionTask = null;
        }

        @Override
        protected void onCancelled() {
            guardarPostulacionTask = null;
        }
    }

    public class EnviarConsultaTask extends AsyncTask<Void, Void, Boolean> {

        int id_publicacion;
        int position;

        EnviarConsultaTask() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
           /* try {
                //TODO enviar mensaje
            } catch (InterruptedException e) {
                return false;
            }*/
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            enviarConsultaTask = null;
        }

        @Override
        protected void onCancelled() {
            enviarConsultaTask = null;
        }
    }


}
