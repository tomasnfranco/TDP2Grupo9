package com.tdp2grupo9.listview;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.util.Log;
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
import com.tdp2grupo9.adapter.PostulacionesAdapter;
import com.tdp2grupo9.maps.MapsActivity;
import com.tdp2grupo9.modelo.Imagen;
import com.tdp2grupo9.modelo.Mensaje;
import com.tdp2grupo9.modelo.Publicacion;
import com.tdp2grupo9.modelo.PublicacionAtributos;
import com.tdp2grupo9.modelo.TipoPublicacion;
import com.tdp2grupo9.modelo.Usuario;
import com.tdp2grupo9.modelo.publicacion.Postulante;
import com.tdp2grupo9.utils.TiposEnum;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class PublicacionesAdapter extends BaseExpandableListAdapter {

    private List<Publicacion> publicaciones;
    private Context context;
    private GuardarPostulacionTask guardarPostulacionTask;
    private EnviarConsultaTask enviarConsultaTask;

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
    private EditText consultaParaEnviar;
    private ImageButton btnEnviarConsulta;

    private String sexo;
    private View postularmeAdopcionClickable;
    private View postularmeTransitoClickable;
    private List<Postulante> postulantesAdopcion;
    private List<Postulante> postulantesOfrecenTransito;
    private ListView listViewAdopciones;
    private ListView listViewOfrecenTransito;


    public PublicacionesAdapter(Context context, List<Publicacion> publicaciones, TiposEnum tipos) {
        this.publicaciones = publicaciones;
        this.context = context;
        this.mensajes = new ArrayList<>();
        this.imagenes = new ArrayList<>();
        this.tipos = tipos;
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
        TextView tipo_publicacion = (TextView) publicacionView.findViewById(R.id.tv_tipo_publicacion);

        //TODO completar textview con el tipo de la publicacion. Obtener desde publicacion obtenida
        if (publicaciones.get(i).getTipoPublicacion() == TipoPublicacion.ADOPCION) tipo_publicacion.setText(publicacionView.getContext().getString(R.string.en_adopcion));
        if (publicaciones.get(i).getTipoPublicacion() == TipoPublicacion.PERDIDA) tipo_publicacion.setText(publicacionView.getContext().getString(R.string.perdida));
        if (publicaciones.get(i).getTipoPublicacion() == TipoPublicacion.ENCONTRADA)tipo_publicacion.setText(publicacionView.getContext().getString(R.string.encontrada));

        String especie = PublicacionAtributos.getInstancia().getEspecies().get(PublicacionAtributos.getInstancia().getEspecies().indexOf(publicaciones.get(i).getEspecie())).toString();
        sexo = PublicacionAtributos.getInstancia().getSexos().get(PublicacionAtributos.getInstancia().getSexos().indexOf(publicaciones.get(i).getSexo())).toString();
        ImageView publicacionSexoMascota = (ImageView) publicacionView.findViewById(R.id.publicacion_icon_sexo);

        if (sexo.equals("Hembra")){
            if (especie.equals("Gato"))
                publicacionSexoMascota.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_gato_hembra));
            else publicacionSexoMascota.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_perro_hembra));
        }else {
            if (especie.equals("Gato"))
                publicacionSexoMascota.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_gato_macho));
            else publicacionSexoMascota.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_perro_macho));
        }

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
    public View getChildView(final int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        View itemView = getInflatedViewItemIfNecessary(view, viewGroup);
        postularmeAdopcionClickable = itemView.findViewById(R.id.postularme_a_adoptar);
        postularmeTransitoClickable = itemView.findViewById(R.id.postular_a_hogar);
        postularmeAdopcionClickable.setFocusable(false);
        postularmeTransitoClickable.setFocusable(false);

        List<Integer> postulantes = publicaciones.get(i).getQuierenAdoptarIds();
        Boolean postulado = false;

        for (Integer p: postulantes){
            if (p == Usuario.getInstancia().getId()){
                postulado = true;
            }
        }

        if (!postulado)
            postularmeAdopcionClickable.setVisibility(View.VISIBLE);
        else postularmeAdopcionClickable.setVisibility(View.GONE);

        if (tipos == TiposEnum.MIS_PUBLICACIONES || tipos == TiposEnum.MIS_POSTULACIONES){
            postularmeAdopcionClickable.setVisibility(View.GONE);
            postularmeTransitoClickable.setVisibility(View.GONE);
        }

        if (!publicaciones.get(i).getTipoPublicacion().equals(TipoPublicacion.ADOPCION)){
            postularmeAdopcionClickable.setVisibility(View.GONE);
        }

        if (!publicaciones.get(i).getNecesitaTransito()){
            postularmeTransitoClickable.setVisibility(View.GONE);
        }

        postularmeAdopcionClickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarPostulacionTask = new GuardarPostulacionTask(i, publicaciones.get(i).getId());
                guardarPostulacionTask.execute((Void) null);
            }
        });

        postularmeTransitoClickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Hogar transito: ", "Presione el boton");
            }
        });

        cargarInformacionBasica(i, itemView);
        cargarLocalizacionMascota(i, itemView);
        cargarFotos(i, itemView);
        cargarVideos(i, itemView);
        cargarInformacionAdicional(i, itemView);
        cargarListViewMensajes(i, itemView);
        cargarPostulaciones(i, itemView);

        return itemView;
    }

    private void cargarInformacionBasica(int i, View v){
        ((TextView) v.findViewById(R.id.infEspecie)).setText(PublicacionAtributos.getInstancia().getEspecies().get(PublicacionAtributos.getInstancia().getEspecies().indexOf(publicaciones.get(i).getEspecie())).toString());
        ((TextView) v.findViewById(R.id.infSexo)).setText(PublicacionAtributos.getInstancia().getSexos().get(PublicacionAtributos.getInstancia().getSexos().indexOf(publicaciones.get(i).getSexo())).toString());
        ((TextView) v.findViewById(R.id.infEdad)).setText(PublicacionAtributos.getInstancia().getEdades().get(PublicacionAtributos.getInstancia().getEdades().indexOf(publicaciones.get(i).getEdad())).toString());
        ((TextView) v.findViewById(R.id.infColor)).setText(PublicacionAtributos.getInstancia().getColores().get(PublicacionAtributos.getInstancia().getColores().indexOf(publicaciones.get(i).getColor())).toString());
        ((TextView) v.findViewById(R.id.infRaza)).setText(PublicacionAtributos.getInstancia().getRazas().get(PublicacionAtributos.getInstancia().getRazas().indexOf(publicaciones.get(i).getRaza())).toString());
        ((TextView) v.findViewById(R.id.infTamanio)).setText(PublicacionAtributos.getInstancia().getTamanios().get(PublicacionAtributos.getInstancia().getTamanios().indexOf(publicaciones.get(i).getTamanio())).toString());
    }

    private void cargarInformacionAdicional(int i, View v){
        String condiciones = publicaciones.get(i).getCondiciones();
        String vacunas = PublicacionAtributos.getInstancia().getVacunasAlDia().get(PublicacionAtributos.getInstancia().getVacunasAlDia().indexOf(publicaciones.get(i).getVacunasAlDia())).toString();
        String castrado = PublicacionAtributos.getInstancia().getCastrados().get(PublicacionAtributos.getInstancia().getCastrados().indexOf(publicaciones.get(i).getCastrado())).toString();
        String proteccion = PublicacionAtributos.getInstancia().getProtecciones().get(PublicacionAtributos.getInstancia().getProtecciones().indexOf(publicaciones.get(i).getProteccion())).toString();
        String energia = PublicacionAtributos.getInstancia().getEnergias().get(PublicacionAtributos.getInstancia().getEnergias().indexOf(publicaciones.get(i).getEnergia())).toString();
        String papeles = PublicacionAtributos.getInstancia().getPapelesAlDia().get(PublicacionAtributos.getInstancia().getPapelesAlDia().indexOf(publicaciones.get(i).getPapelesAlDia())).toString();
        String compatibleCon = PublicacionAtributos.getInstancia().getCompatibilidades().get(PublicacionAtributos.getInstancia().getCompatibilidades().indexOf(publicaciones.get(i).getCompatibleCon())).toString();

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

        if (mensaje.isEmpty()) mensaje =  "No tiene.";

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

        if (video != null && !video.isEmpty() && video.length() > 32) {
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

        TextView localizacion_mascota = (TextView) v.findViewById(R.id.tv_direccion_mascota);
        localizacion_mascota.setText(publicaciones.get(i).getDireccion());

        especieView = PublicacionAtributos.getInstancia().getEspecies().get(PublicacionAtributos.getInstancia().getEspecies().indexOf(publicaciones.get(i).getEspecie())).toString();
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
                intent.putExtra("sexo", sexo);
                context.startActivity(intent);
            }
        });
        
    }

    private void cargarListViewMensajes(final int i, View v) {

        mensajes = publicaciones.get(i).getMensajes();

        consultaParaEnviar = (EditText) v.findViewById(R.id.consulta_para_enviar);
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

            if (!mensajes.isEmpty()) {
                listView.setAdapter(new MensajeAdapter(v.getContext(), mensajes, tipos));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View view,
                                            final int position, long arg) {

                    }
                });
            }

        }else {
            consultaParaEnviar.setVisibility(View.VISIBLE);
            btnEnviarConsulta.setFocusable(false);
            btnEnviarConsulta.setVisibility(View.VISIBLE);

            btnEnviarConsulta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickButton(i);
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

    private void onClickButton(int i){
        String pregunta =consultaParaEnviar.getText().toString();
        if (pregunta.isEmpty()){
            consultaParaEnviar.setError("Error: Debe escribir una consulta.");
        }else{
            Mensaje mensaje = new Mensaje();
            mensaje.setPregunta(pregunta);
            mensaje.setPublicacionId(publicaciones.get(i).getId());
            mensaje.setUsuarioPreguntaId(Usuario.getInstancia().getId());

            enviarConsultaTask = new EnviarConsultaTask(mensaje);
            enviarConsultaTask.execute((Void) null);
        }
    }

    private void cargarPostulaciones(final int i, final View v) {

        mensajes = publicaciones.get(i).getMensajes();

        postulantesAdopcion = publicaciones.get(i).getQuierenAdoptar();
        postulantesOfrecenTransito = publicaciones.get(i).getOfrecenTransito();
        View panelPostulantesEnAdopcion = (View) v.findViewById(R.id.view_adoptantes);
        View panelPostulantesOfrecenTransito = (View) v.findViewById(R.id.view_ofrecen_transito);

        if (tipos == TiposEnum.MIS_PUBLICACIONES){

            listViewAdopciones = (ListView) v.findViewById(R.id.listView_quieren_adoptar);
            listViewOfrecenTransito = (ListView) v.findViewById(R.id.listView_ofrecen_transito);

            listViewAdopciones.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            });

            listViewAdopciones.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            });

            if (postulantesAdopcion.isEmpty() && postulantesOfrecenTransito.isEmpty()){
                CardView cardview_postulaciones = (CardView) v.findViewById(R.id.cardview_postulaciones);
                cardview_postulaciones.setVisibility(View.GONE);
            }else if (!postulantesAdopcion.isEmpty() && postulantesOfrecenTransito.isEmpty()){
                panelPostulantesOfrecenTransito.setVisibility(View.GONE);
                setAdapterAdopciones(v,"Adopcion", postulantesAdopcion, publicaciones.get(i).getId());
            }else if (postulantesAdopcion.isEmpty() && !postulantesOfrecenTransito.isEmpty()){
                panelPostulantesEnAdopcion.setVisibility(View.GONE);
                setAdapterTransito(v,"Transito", postulantesOfrecenTransito,publicaciones.get(i).getId());
            }else {
                setAdapterAdopciones(v, "Adopcion", postulantesAdopcion,publicaciones.get(i).getId());
                setAdapterTransito(v,"Transito", postulantesOfrecenTransito,publicaciones.get(i).getId());
            }
        }else{
            CardView cardview_postulaciones = (CardView) v.findViewById(R.id.cardview_postulaciones);
            cardview_postulaciones.setVisibility(View.GONE);
        }

    }

    private void setAdapterAdopciones(View v, String tipoAdapter, List<Postulante> postulantes, int id_publicacion){
        listViewAdopciones.setAdapter(new PostulacionesAdapter(v.getContext(), tipoAdapter, postulantes, id_publicacion));
        listViewAdopciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view,
                                    int position, long arg) {
            }
        });
    }

    private void setAdapterTransito(View v, String tipoAdapter, List<Postulante> postulantes, int id_publicacion){
        listViewOfrecenTransito.setAdapter(new PostulacionesAdapter(v.getContext(), tipoAdapter, postulantes, id_publicacion));
        listViewOfrecenTransito.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view,
                                    int position, long arg) {
            }
        });
    }


    private List<Mensaje> loadMensajesMock(){
        List<Mensaje> mensajes = new ArrayList<>();
        Mensaje m1 = new Mensaje();
        m1.setPregunta("hola");
        m1.setRespuesta("chau");
        m1.setFechaPregunta(new Date(2015, 5, 10));
        m1.setFechaRespuesta(new Date(2015, 6, 10));
        Mensaje m2 = new Mensaje();
        m2.setPregunta("Tiene pulgas");
        m2.setFechaPregunta(new Date(2015, 5, 10));
        Mensaje m3 = new Mensaje();
        m3.setPregunta("hello");
        m3.setRespuesta("bye");
        m3.setFechaPregunta(new Date(2015, 8, 10));
        m3.setFechaRespuesta(new Date(2015, 8, 20));
        mensajes.add(m1);
        mensajes.add(m2);
        mensajes.add(m3);
        return mensajes;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
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
                Thread.sleep(200);
            } catch (InterruptedException e) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success){
                postularmeAdopcionClickable.setVisibility(View.GONE);
                notifyDataSetChanged();
            }
            guardarPostulacionTask = null;
        }

        @Override
        protected void onCancelled() {
            guardarPostulacionTask = null;
        }
    }

    public class EnviarConsultaTask extends AsyncTask<Void, Void, Boolean> {

        Mensaje consulta;

        EnviarConsultaTask(Mensaje mensaje) {
            consulta = mensaje;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                consulta.guardarPregunta(Usuario.getInstancia().getToken());
                Thread.sleep(200);
            } catch (InterruptedException e) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success){
                if (consulta.getId() > 0){
                    System.out.println("VIEJA YA LO GUARDE");
                }else System.out.println("VIEJA HUBO UN ERROR");
            }
            enviarConsultaTask = null;
        }

        @Override
        protected void onCancelled() {
            enviarConsultaTask = null;
        }
    }


}
