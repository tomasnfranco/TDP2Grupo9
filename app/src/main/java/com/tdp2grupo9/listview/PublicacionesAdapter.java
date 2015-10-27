package com.tdp2grupo9.listview;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.tdp2grupo9.drawer.DrawerMenuActivity;
import com.tdp2grupo9.maps.MapsActivity;
import com.tdp2grupo9.modelo.Imagen;
import com.tdp2grupo9.modelo.Mensaje;
import com.tdp2grupo9.modelo.Publicacion;
import com.tdp2grupo9.modelo.PublicacionAtributos;
import com.tdp2grupo9.modelo.TipoPublicacion;
import com.tdp2grupo9.modelo.Usuario;
import com.tdp2grupo9.modelo.Postulante;
import com.tdp2grupo9.modelo.publicacion.AtributoPublicacion;
import com.tdp2grupo9.modelo.publicacion.Especie;
import com.tdp2grupo9.modelo.publicacion.Sexo;
import com.tdp2grupo9.utils.TiposClickeableEnum;
import com.tdp2grupo9.modelo.TiposEnum;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class PublicacionesAdapter extends BaseExpandableListAdapter {

    private List<Publicacion> publicaciones;
    private Context context;
    private GuardarPostulacionTask guardarPostulacionTask;
    private EnviarConsultaTask enviarConsultaTask;
    private EliminarPublicacionTask eliminarPublicacionTask;
    private EliminarPostulacionTask eliminarPostulacionTask;

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
    private View eliminarPublicacionClickable;
    private View editarPublicacionClickable;
    private View eliminarPostulacionAdopcionClickable;
    private View eliminarPostulacionTransitoClickable;

    private List<Postulante> postulantesAdopcion;
    private List<Postulante> postulantesOfrecenTransito;
    private ListView listViewAdopciones;
    private ListView listViewOfrecenTransito;
    private android.support.v7.app.AlertDialog dialogIcon;
    private TextView replicaConsulta;
    private View itemView;


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

    public void removeItem(int i) {
        publicaciones.remove(i);
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

        switch (publicaciones.get(i).getTipoPublicacion()){
            case ADOPCION:
                tipo_publicacion.setText(publicacionView.getContext().getString(R.string.en_adopcion));
                break;
            case PERDIDA:
                tipo_publicacion.setText(publicacionView.getContext().getString(R.string.perdida));
                break;
            case ENCONTRADA:
                tipo_publicacion.setText(publicacionView.getContext().getString(R.string.encontrada));
                break;
        }

        ImageView publicacionSexoMascota = (ImageView) publicacionView.findViewById(R.id.publicacion_icon_sexo);

        Especie especieEntity = publicaciones.get(i).getEspecie();
        String valorEspecie = PublicacionAtributos.getInstancia().getEspecie(especieEntity).getValor();

        Sexo sexoEntity = publicaciones.get(i).getSexo();
        if (sexoEntity.getId() > 0) {
            String valorSexo = PublicacionAtributos.getInstancia().getSexo(sexoEntity).getValor();
            if (valorEspecie.equals("Perro")) {
                switch (valorSexo) {
                    case "Hembra":
                        publicacionSexoMascota.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_perro_hembra));
                        break;
                    case "Macho":
                        publicacionSexoMascota.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_perro_macho));
                        break;
                    default:
                        publicacionSexoMascota.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_perro_macho)); //TODO: icono perro gris
                        break;
                }
            } else { //es Gato
                switch (valorSexo) {
                    case "Hembra":
                        publicacionSexoMascota.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_gato_hembra));
                        break;
                    case "Macho":
                        publicacionSexoMascota.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_gato_macho));
                        break;
                    default:
                        publicacionSexoMascota.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_gato_macho)); //TODO: icono gato gris
                        break;
                }

            }
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
        itemView = getInflatedViewItemIfNecessary(view, viewGroup);

        initialiceClickable(itemView);
        activateClickable(i);
        onClickButtonPublicacion(i);

        cargarInformacionBasica(i, itemView);
        cargarLocalizacionMascota(i, itemView);
        cargarFotos(i, itemView);
        cargarVideos(i, itemView);
        cargarInformacionAdicional(i, itemView);
        cargarMensajes(i, itemView);
        cargarPostulaciones(i, itemView);

        return itemView;
    }

    private void initialiceClickable(View itemView){
        postularmeAdopcionClickable = itemView.findViewById(R.id.postularme_a_adoptar);
        postularmeTransitoClickable = itemView.findViewById(R.id.postular_a_hogar);
        eliminarPublicacionClickable = itemView.findViewById(R.id.eliminar_publicacion);
        editarPublicacionClickable = itemView.findViewById(R.id.editar_publicacion);
        eliminarPostulacionAdopcionClickable = itemView.findViewById(R.id.eliminar_postulacion_adoptar);
        eliminarPostulacionTransitoClickable = itemView.findViewById(R.id.eliminar_postulacion_transito);

        postularmeAdopcionClickable.setFocusable(false);
        postularmeTransitoClickable.setFocusable(false);
        eliminarPublicacionClickable.setFocusable(false);
        editarPublicacionClickable.setFocusable(false);
        eliminarPostulacionAdopcionClickable.setFocusable(false);
        eliminarPostulacionTransitoClickable.setFocusable(false);

        postularmeAdopcionClickable.setVisibility(View.GONE);
        postularmeTransitoClickable.setVisibility(View.GONE);
        eliminarPublicacionClickable.setVisibility(View.GONE);
        editarPublicacionClickable.setVisibility(View.GONE);
        eliminarPostulacionAdopcionClickable.setVisibility(View.GONE);
        eliminarPostulacionTransitoClickable.setVisibility(View.GONE);
    }

    private void activateClickable(final int i){

        if (publicaciones.get(i).getTipoPublicacion().equals(TipoPublicacion.ADOPCION) &&
                !isPostulateAdopcion(i) &&
                tipos != TiposEnum.MIS_PUBLICACIONES)
            postularmeAdopcionClickable.setVisibility(View.VISIBLE);

        if (publicaciones.get(i).getNecesitaTransito() && !isPostulateTransito(i) &&
                tipos != TiposEnum.MIS_PUBLICACIONES){
            postularmeTransitoClickable.setVisibility(View.VISIBLE);
        }

        if (tipos == TiposEnum.MIS_PUBLICACIONES){
            eliminarPublicacionClickable.setVisibility(View.VISIBLE);
            editarPublicacionClickable.setVisibility(View.VISIBLE);
        }

        if (tipos == TiposEnum.MIS_POSTULACIONES){
            if (isPostulateAdopcion(i))  eliminarPostulacionAdopcionClickable.setVisibility(View.VISIBLE);
            if (isPostulateTransito(i))  eliminarPostulacionTransitoClickable.setVisibility(View.VISIBLE);
        }
    }

    private void onClickButtonPublicacion(final int i){

        postularmeAdopcionClickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogIcon = getDialogoConfirmacion(TiposClickeableEnum.POST_ADOPCION, i).create();
                dialogIcon.show();
            }
        });

        postularmeTransitoClickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogIcon = getDialogoConfirmacion(TiposClickeableEnum.POST_TRANSITO, i).create();
                dialogIcon.show();
            }
        });


        editarPublicacionClickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogIcon = getDialogoConfirmacion(TiposClickeableEnum.EDITAR_PUBL, i).create();
                dialogIcon.show();
            }
        });


        eliminarPublicacionClickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogIcon = getDialogoConfirmacion(TiposClickeableEnum.ELIMINAR_PUBL, i).create();
                dialogIcon.show();
            }
        });

        eliminarPostulacionAdopcionClickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogIcon = getDialogoConfirmacion(TiposClickeableEnum.ELIMINAR_POST_ADOP, i).create();
                dialogIcon.show();
            }
        });


        eliminarPostulacionTransitoClickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogIcon = getDialogoConfirmacion(TiposClickeableEnum.ELIMINAR_POST_TRANS, i).create();
                dialogIcon.show();
            }
        });
    }

    private Boolean isPostulateAdopcion(int i){
        return publicaciones.get(i).isPostuladoAdopcion(Usuario.getInstancia().getId());
    }

    private Boolean isPostulateTransito(int i){
        return publicaciones.get(i).isPostuladoTransito(Usuario.getInstancia().getId());
    }

    private android.support.v7.app.AlertDialog.Builder getDialogoConfirmacion(final TiposClickeableEnum tipo,
                                                                                      final int position){
        final android.support.v7.app.AlertDialog.Builder builder =
                new android.support.v7.app.AlertDialog.Builder(context);
        String mensaje = getMensajeDeConfirmacion(tipo);

        builder.setMessage(mensaje)
                .setTitle(context.getString(R.string.title_dialog_confirmacion))
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.i("Dialogo confirmacion", "Confirmacion Aceptada.");
                        ejecutarAccionClickeable(tipo, position);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.i("Dialogo confirmacion", "Confirmacion Cancelada.");
                        dialog.cancel();
                    }
                });
        return builder;

    }

    private String getMensajeDeConfirmacion(TiposClickeableEnum tipo){
        String mensaje="";
        switch(tipo) {
            case POST_ADOPCION:
                mensaje = context.getString(R.string.confirmacion_postulacion_adopcion);
                break;
            case POST_TRANSITO:
                mensaje = context.getString(R.string.confirmacion_postulacion_transito);
                break;
            case EDITAR_PUBL:
                mensaje = context.getString(R.string.confirmacion_editar);
                break;
            case ELIMINAR_PUBL:
                mensaje = context.getString(R.string.confirmacion_eliminar);
                break;
            case ELIMINAR_POST_ADOP:
                mensaje = context.getString(R.string.confirmacion_eliminar_post_adopcion);
                break;
            case ELIMINAR_POST_TRANS:
                mensaje = context.getString(R.string.confirmacion_eliminar_post_transito);
                break;
            default:
                break;
        }
        return mensaje;
    }

    private void ejecutarAccionClickeable(TiposClickeableEnum tipo, final int i){
        switch(tipo) {
            case POST_ADOPCION:
                guardarPostulacionTask = new GuardarPostulacionTask(i, publicaciones.get(i).getId(), TiposClickeableEnum.POST_ADOPCION, itemView);
                guardarPostulacionTask.execute((Void) null);
                break;
            case POST_TRANSITO:
                guardarPostulacionTask = new GuardarPostulacionTask(i, publicaciones.get(i).getId(), TiposClickeableEnum.POST_TRANSITO, itemView);
                guardarPostulacionTask.execute((Void) null);
                break;
            case EDITAR_PUBL:
                break;
            case ELIMINAR_PUBL:
                eliminarPublicacionTask = new EliminarPublicacionTask(i);
                eliminarPublicacionTask.execute((Void) null);
                break;
            case ELIMINAR_POST_ADOP:
                eliminarPostulacionTask = new EliminarPostulacionTask(i, publicaciones.get(i).getId(),TiposClickeableEnum.ELIMINAR_POST_ADOP );
                eliminarPostulacionTask.execute((Void) null);
                break;
            case ELIMINAR_POST_TRANS:
                eliminarPostulacionTask = new EliminarPostulacionTask(i, publicaciones.get(i).getId(),TiposClickeableEnum.ELIMINAR_POST_TRANS );
                eliminarPostulacionTask.execute((Void) null);
                break;
            default:
                break;
        }
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
        if (condiciones != null && condiciones.isEmpty())
            condiciones = "No tiene.";
        else
            ((TextView) v.findViewById(R.id.infCondicionesAdopcion)).setText(condiciones);

        String infoAdicional="";

        AtributoPublicacion vacunasEntity = publicaciones.get(i).getVacunasAlDia();
        if (vacunasEntity.getId() > 0) {
            String valor = PublicacionAtributos.getInstancia().getVacunasAlDia(vacunasEntity).getValor();
            if (valor.equals("Si"))
                infoAdicional += v.getContext().getString(R.string.tiene_vacunas) + ". ";
            else if (valor.equals("No"))
                infoAdicional += "No " + v.getContext().getString(R.string.tiene_vacunas).toLowerCase() + ". ";
        }

        AtributoPublicacion papelesEntity = publicaciones.get(i).getPapelesAlDia();
        if (papelesEntity.getId() > 0) {
            String valor = PublicacionAtributos.getInstancia().getPapelesAlDia(papelesEntity).getValor();
            if (valor.equals("Si"))
                infoAdicional += v.getContext().getString(R.string.tiene_papeles) + ". ";
            else if (valor.equals("No"))
                infoAdicional += "No " + v.getContext().getString(R.string.tiene_papeles).toLowerCase() + ". ";
        }

        AtributoPublicacion castradoEntity = publicaciones.get(i).getCastrado();
        if (castradoEntity.getId() > 0) {
            String valor = PublicacionAtributos.getInstancia().getCastrado(castradoEntity).getValor();
            if (valor.equals("Si"))
                infoAdicional+= v.getContext().getString(R.string.esta_castrado)+". ";
            else if (valor.equals("No"))
                infoAdicional+= "No " + v.getContext().getString(R.string.esta_castrado).toLowerCase() + ". ";
        }

        AtributoPublicacion compatibleEntity = publicaciones.get(i).getCompatibleCon();
        if (compatibleEntity.getId() > 0) {
            String valor = PublicacionAtributos.getInstancia().getCompatibleCon(compatibleEntity).getValor();
            if (!valor.equals("No aplica") && !valor.isEmpty())
                infoAdicional += v.getContext().getString(R.string.es_compatible) + " " + valor.toLowerCase() + ". ";
        }

        AtributoPublicacion energiaEntity = publicaciones.get(i).getEnergia();
        if (energiaEntity.getId() > 0) {
            String valor = PublicacionAtributos.getInstancia().getEnergia(energiaEntity).getValor();
            if (!valor.equals("No aplica") && !valor.isEmpty())
                infoAdicional += "Es " + valor.toLowerCase() + ". ";
        }

        AtributoPublicacion proteccionEntity = publicaciones.get(i).getProteccion();
        if (proteccionEntity.getId() > 0) {
            String valor = PublicacionAtributos.getInstancia().getProteccion(proteccionEntity).getValor();
            if (!valor.equals("No aplica") && !valor.isEmpty())
                infoAdicional += "Es " + valor.toLowerCase() + ". ";
        }

        if (infoAdicional.isEmpty()) infoAdicional =  "No tiene.";

        ((TextView) v.findViewById(R.id.infAdicional)).setText(infoAdicional);

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

    private void cargarMensajes(final int i, final View v) {
        mensajes = publicaciones.get(i).getMensajes();
        initialiceElementosMensajes(v);

        if (tipos == TiposEnum.MIS_PUBLICACIONES) {

            if (mensajes.isEmpty()) {
                CardView cardViewMensajes = (CardView) v.findViewById(R.id.cardview_mensajes_pm);
                cardViewMensajes.setVisibility(View.GONE);
            } else {
                listView.setVisibility(View.VISIBLE);
                listView.setAdapter(new MensajeAdapter(v.getContext(), mensajes, tipos));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View view,
                                            final int position, long arg) {

                    }
                });
            }
        }else{
            if (!mensajes.isEmpty()) {
                listView.setVisibility(View.VISIBLE);
                listView.setAdapter(new MensajeAdapter(v.getContext(), mensajes, tipos));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View view,
                                            final int position, long arg) {

                    }
                });
            }

            consultaParaEnviar.setVisibility(View.VISIBLE);
            btnEnviarConsulta.setVisibility(View.VISIBLE);
            onClickButtonMensaje(i,v);
        }


    }

    private void updateConsultaEnviada(View v, int i){
        consultaParaEnviar.setText("");
        replicaConsulta.setText("");
        mensajes = publicaciones.get(i).getMensajes();
        listView.setVisibility(View.VISIBLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view,
                                    final int position, long arg) {

            }
        });
        listView.setAdapter(new MensajeAdapter(v.getContext(), mensajes, tipos));
    }

    private void initialiceElementosMensajes(View v){
        consultaParaEnviar = (EditText) v.findViewById(R.id.consulta_para_enviar);
        replicaConsulta = (TextView) v.findViewById(R.id.replica_consulta);
        btnEnviarConsulta = (ImageButton) v.findViewById(R.id.btn_enviar_consulta);
        listView = (ListView) v.findViewById(R.id.listView_consultas);

        btnEnviarConsulta.setFocusable(false);
        replicaConsulta.setVisibility(View.GONE);
        listView.setVisibility(View.GONE);
        btnEnviarConsulta.setVisibility(View.GONE);
        consultaParaEnviar.setVisibility(View.GONE);
        consultaParaEnviar.setText("");

        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }

    private void onClickButtonMensaje(final int i, final View v){

        btnEnviarConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executeActionButtonMensaje(i, replicaConsulta.getText().toString(), v);
            }
        });

        consultaParaEnviar.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {}
            public void afterTextChanged(Editable s) {
                replicaConsulta.setText(s.toString());
            }
        });
    }

    private void executeActionButtonMensaje(int i, String pregunta, View v){
        if (pregunta.isEmpty()){
            consultaParaEnviar.setError("Error: Debe escribir una consulta.");
        }else{
            Mensaje mensaje = new Mensaje();
            mensaje.setPregunta(pregunta);
            mensaje.setPublicacionId(publicaciones.get(i).getId());
            enviarConsultaTask = new EnviarConsultaTask(mensaje, i, v);
            enviarConsultaTask.execute((Void) null);
        }
    }

    private void cargarPostulaciones(final int i, final View v) {

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

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    public class GuardarPostulacionTask extends AsyncTask<Void, Void, Boolean> {

        int id_publicacion;
        int position;
        TiposClickeableEnum tipo;
        View view;

        GuardarPostulacionTask(int position, int id_publicacion, TiposClickeableEnum tipo, View v) {
            this.id_publicacion = id_publicacion;
            this.position = position;
            this.tipo = tipo;
            this.view = v;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                if (tipo == TiposClickeableEnum.POST_ADOPCION)
                    Usuario.getInstancia().quieroAdoptar(id_publicacion);
                else
                    Usuario.getInstancia().ofrezcoTransito(id_publicacion);
                Thread.sleep(200);
            } catch (InterruptedException e) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success){
               /* Intent intent = new Intent(this.view.getContext(), DrawerMenuActivity.class);
                intent.putExtra("tab", 4);
                this.view.getContext().startActivity(intent);*/
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
        View v;
        int i;

        EnviarConsultaTask(Mensaje mensaje,final int position, View itemView) {
            consulta = mensaje;
            this.v = itemView;
            this.i = position;
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
                    updateConsultaEnviada(v, i);
                    notifyDataSetChanged();
                }else {
                }
            }
            enviarConsultaTask = null;
        }

        @Override
        protected void onCancelled() {
            enviarConsultaTask = null;
        }
    }

    public class EliminarPublicacionTask extends AsyncTask<Void, Void, Boolean> {

        private int position;

        EliminarPublicacionTask(int i) {
            this.position = i;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                publicaciones.get(position).cancelarPublicacion(Usuario.getInstancia().getToken());
                Thread.sleep(200);
            } catch (InterruptedException e) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success){
                removeItem(position);
                notifyDataSetChanged();
            }
            eliminarPublicacionTask = null;
        }

        @Override
        protected void onCancelled() {
            eliminarPublicacionTask = null;
        }
    }

    public class EliminarPostulacionTask extends AsyncTask<Void, Void, Boolean> {

        int id_publicacion;
        int position;
        private TiposClickeableEnum tipo;

        EliminarPostulacionTask(int i, int id_publicacion, TiposClickeableEnum tipo) {
            this.position = i;
            this.tipo = tipo;
            this.id_publicacion = id_publicacion;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                if (tipo == TiposClickeableEnum.ELIMINAR_POST_ADOP){
                    Usuario.getInstancia().cancelarQuieroAdoptar(id_publicacion);
                }
                else {
                    Usuario.getInstancia().cancelarTransito(id_publicacion);
                }
                Thread.sleep(200);
            } catch (InterruptedException e) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success){
            }
            eliminarPostulacionTask = null;
        }

        @Override
        protected void onCancelled() {
            eliminarPostulacionTask = null;
        }
    }




}
