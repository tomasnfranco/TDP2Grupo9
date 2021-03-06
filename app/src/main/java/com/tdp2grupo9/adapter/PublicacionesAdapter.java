package com.tdp2grupo9.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.tdp2grupo9.R;
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

import java.io.InputStream;
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
    private DenunciarPublicacionTask denunciarPublicacionTask;

    private ListView listView;
    private List<Mensaje> mensajes;
    private ImageView imagenSeleccionada;
    private List<Imagen> imagenes;
    private String especieView;
    private ImageView imagenPosicion;
    private Double latitud;
    private Double longitud;
    private String nombreMascota;
    private TiposEnum tipos;
    private Button btnPostularme;
    private ImageButton btnEnviarConsulta;

    CallbackManager callbackManager;
    ShareDialog shareDialog;
    private ShareButton shareButton;
    private View postularmeAdopcionClickable;
    private View postularmeTransitoClickable;
    private View eliminarPublicacionClickable;
    private View editarPublicacionClickable;
    private View eliminarPostulacionAdopcionClickable;
    private View eliminarPostulacionTransitoClickable;
    private View eliminarReclamoClickable;
    private View eliminarAvisoClickable;
    private View reclamarMascotaClickable;
    private View encontreTuMascotaClickable;
    private View denunciarPublicacionClickable;

    private List<Postulante> postulantesAdopcion;
    private List<Postulante> postulantesOfrecenTransito;
    private ExpandableListView listViewAdopciones;
    private ExpandableListView listViewOfrecenTransito;
    private android.support.v7.app.AlertDialog dialogIcon;
    private TextView replicaConsulta;
    private View itemView;
    private String valorSexo;
    private ExpandableListView listViewReclamarMascota;
    private ExpandableListView listViewEncontreTuMascota;
    private View panelPostulantesEnAdopcion;
    private View panelPostulantesEncontraronMascotas;
    private View panelPostulantesReclamanMascotas;
    private View panelPostulantesOfrecenTransito;
    FragmentActivity activity;
    private String motivo_denuncia;
    private String descripcion;

    String[] itemsDenuncia;

    public PublicacionesAdapter(Context context, List<Publicacion> publicaciones, TiposEnum tipos, FragmentActivity activity) {
        this.publicaciones = publicaciones;
        this.context = context;
        this.mensajes = new ArrayList<>();
        this.imagenes = new ArrayList<>();
        this.tipos = tipos;
        this.activity = activity;
        this.motivo_denuncia = "";
        this.descripcion = "";

        itemsDenuncia = new String[]{context.getString(R.string.motivo_denuncia_1),
                context.getString(R.string.motivo_denuncia_3),
                context.getString(R.string.motivo_denuncia_4),
                context.getString(R.string.motivo_denuncia_5)};

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

    private boolean cargarIconoSiEsNecesario(Publicacion publicacion, View view, boolean necesario, Drawable drawable, int loadedIcons, final String tooltip) {
        if (necesario) {
            final ImageView image =  getNextIconView(view, loadedIcons);
            image.setImageDrawable(drawable);

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    displayToastAboveButton(image, tooltip);
                }
            });
        }
        return necesario;
    }

    private void displayToastAboveButton(View v, String tooltip)
    {
        int xOffset = 0;
        int yOffset = 0;
        Rect gvr = new Rect();

        View parent = (View) v.getParent();
        int parentHeight = parent.getHeight();

        if (v.getGlobalVisibleRect(gvr))
        {
            View root = v.getRootView();

            int halfWidth = root.getRight() / 2;
            int halfHeight = root.getBottom() / 2;

            int parentCenterX = ((gvr.right - gvr.left) / 2) + gvr.left;

            int parentCenterY = ((gvr.bottom - gvr.top) / 2) + gvr.top;

            if (parentCenterY <= halfHeight)
            {
                yOffset = -(halfHeight - parentCenterY) - parentHeight;
            }
            else
            {
                yOffset = (parentCenterY - halfHeight) - parentHeight;
            }

            if (parentCenterX < halfWidth)
            {
                xOffset = -(halfWidth - parentCenterX);
            }

            if (parentCenterX >= halfWidth)
            {
                xOffset = parentCenterX - halfWidth;
            }
        }


        if (tipos == TiposEnum.RECIENTES || tipos == TiposEnum.BUSQUEDA){
                xOffset -= 70;
        }

        Toast toast = Toast.makeText(context, tooltip, Toast.LENGTH_SHORT);
        System.out.println("X: " + xOffset + " " + "Y: " + yOffset);
        toast.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL, xOffset, yOffset + 225);
        toast.show();
    }

    private boolean cargarIconoHogarTransito(Publicacion publicacion, View view, int loadedIcons) {
        return cargarIconoSiEsNecesario(publicacion, view, publicacion.getNecesitaTransito(), view.getResources().getDrawable(R.drawable.home_transit), loadedIcons,
                context.getResources().getString(R.string.hogar_transito));
    }

    private boolean cargarIconoCuidadosEspeciales(Publicacion publicacion, View view, int loadedIcons) {
        return cargarIconoSiEsNecesario(publicacion, view, publicacion.getRequiereCuidadosEspeciales(), view.getResources().getDrawable(R.drawable.cuidados_especiales), loadedIcons,
                context.getResources().getString(R.string.cuidados_especiales));
    }

    private boolean cargarIconoCondicionesAdopcion(Publicacion publicacion, View view, int loadedIcons) {
        return cargarIconoSiEsNecesario(publicacion, view, !publicacion.getCondiciones().isEmpty(), view.getResources().getDrawable(R.drawable.ic_condiciones), loadedIcons,
                context.getResources().getString(R.string.condiciones_adopcion));
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
                if (publicaciones.get(i).getConcreatada())
                    tipo_publicacion.setText(publicacionView.getContext().getString(R.string.adoptada));
                else tipo_publicacion.setText(publicacionView.getContext().getString(R.string.en_adopcion));
                break;
            case PERDIDA:
                tipo_publicacion.setText(publicacionView.getContext().getString(R.string.perdida));
                break;
            case ENCONTRADA:
                if (publicaciones.get(i).getConcreatada())
                    tipo_publicacion.setText(publicacionView.getContext().getString(R.string.reencontrada));
                else tipo_publicacion.setText(publicacionView.getContext().getString(R.string.encontrada));
                break;
        }

        ImageView publicacionSexoMascota = (ImageView) publicacionView.findViewById(R.id.publicacion_icon_sexo);

        Especie especieEntity = publicaciones.get(i).getEspecie();
        String valorEspecie = PublicacionAtributos.getInstancia().getEspecie(especieEntity).getValor();

        Sexo sexoEntity = publicaciones.get(i).getSexo();
        if (sexoEntity.getId() > 0) {
            valorSexo = PublicacionAtributos.getInstancia().getSexo(sexoEntity).getValor();
            if (valorEspecie.equals("Perro")) {
                switch (valorSexo) {
                    case "Hembra":
                        publicacionSexoMascota.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_perro_hembra));
                        break;
                    case "Macho":
                        publicacionSexoMascota.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_perro_macho));
                        break;
                    default:
                        publicacionSexoMascota.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_perro_desconocido));
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
                        publicacionSexoMascota.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_gato_desconocido));
                        break;
                }

            }
        }

        ((TextView) publicacionView.findViewById(R.id.publicacion_name)).setText(publicaciones.get(i).getNombreMascota());
        ((ImageView) publicacionView.findViewById(R.id.ivLocalizacion)).setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_localizacion));
        if (publicaciones.get(i).getImagenes().size() > 0)
            ((ImageView) publicacionView.findViewById(R.id.publicacion_image)).setImageBitmap(publicaciones.get(i).getImagenes().get(0).getBitmap());
        else {
            if (valorEspecie.equals("Gato")){
                ((ImageView) publicacionView.findViewById(R.id.publicacion_image)).setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.sin_foto_gato));
            }else {
                ((ImageView) publicacionView.findViewById(R.id.publicacion_image)).setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.sin_foto_perro));
            }
        }


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

        initialiceClickable(itemView, i);
        activateClickable(i);
        onClickButtonPublicacion(i);

        cargarInformacionBasica(i, itemView);
        cargarLocalizacionMascota(i, itemView);
        cargarFotos(i, itemView);
        cargarVideos(i, itemView);
        cargarInformacionAdicional(i, itemView);
        cargarMensajes(i, itemView);
        cargarPostulaciones(i, itemView);
        cargarMensajesPublicante(i, itemView);

        return itemView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    private void initialiceClickable(View itemView, int i) {
        createShareFacebookButton(i);
        postularmeAdopcionClickable = itemView.findViewById(R.id.postularme_a_adoptar);
        postularmeTransitoClickable = itemView.findViewById(R.id.postular_a_hogar);
        reclamarMascotaClickable = itemView.findViewById(R.id.reclamar_mascota);
        eliminarPublicacionClickable = itemView.findViewById(R.id.eliminar_publicacion);
        editarPublicacionClickable = itemView.findViewById(R.id.editar_publicacion);
        eliminarPostulacionAdopcionClickable = itemView.findViewById(R.id.eliminar_postulacion_adoptar);
        eliminarPostulacionTransitoClickable = itemView.findViewById(R.id.eliminar_postulacion_transito);
        eliminarReclamoClickable = itemView.findViewById(R.id.eliminar_reclamo);
        eliminarAvisoClickable = itemView.findViewById(R.id.eliminar_aviso);
        denunciarPublicacionClickable = itemView.findViewById(R.id.denunciar_publicacion);
        encontreTuMascotaClickable = itemView.findViewById(R.id.encontre_tu_mascota);

        postularmeAdopcionClickable.setFocusable(false);
        postularmeTransitoClickable.setFocusable(false);
        eliminarPublicacionClickable.setFocusable(false);
        editarPublicacionClickable.setFocusable(false);
        eliminarPostulacionAdopcionClickable.setFocusable(false);
        eliminarPostulacionTransitoClickable.setFocusable(false);
        denunciarPublicacionClickable.setFocusable(false);
        encontreTuMascotaClickable.setFocusable(false);
        eliminarAvisoClickable.setFocusable(false);

        postularmeAdopcionClickable.setVisibility(View.GONE);
        postularmeTransitoClickable.setVisibility(View.GONE);
        reclamarMascotaClickable.setVisibility(View.GONE);
        eliminarPublicacionClickable.setVisibility(View.GONE);
        editarPublicacionClickable.setVisibility(View.GONE);
        eliminarPostulacionAdopcionClickable.setVisibility(View.GONE);
        eliminarPostulacionTransitoClickable.setVisibility(View.GONE);
        eliminarReclamoClickable.setVisibility(View.GONE);
        eliminarAvisoClickable.setVisibility(View.GONE);
        denunciarPublicacionClickable.setVisibility(View.GONE);
        encontreTuMascotaClickable.setVisibility(View.GONE);
    }

    private void createShareFacebookButton(int i) {
        FacebookSdk.sdkInitialize(context);
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this.activity);

        shareButton = (ShareButton) itemView.findViewById(R.id.fb_share_button);
        List<SharePhoto> photos = new ArrayList<SharePhoto>();
        for (Imagen imagen : publicaciones.get(i).getImagenes()) {
            photos.add(new SharePhoto.Builder()
                    .setBitmap(imagen.getBitmap())
                    .build());
        }
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhotos(photos)
                .build();
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            Publicacion p = publicaciones.get(i);
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle("Busca sus Huellas")
                    .setContentDescription("La mascota " + p.getNombreMascota() + TipoPublicacion.getTipoPublicacionToStringSingular(
                            p.getTipoPublicacion().getValue()
                    ))
                    .setImageUrl(Uri.parse("https://raw.githubusercontent.com/tomasnfranco/TDP2Grupo9/master/app/src/main/res/drawable/logo_aplicacion.png"))
                    .setContentUrl(Uri.parse("http://www.facebook.com/buscasushuellas"))
                    .build();
            shareButton.setShareContent(linkContent);
        }

    }


    private void activateClickable(final int i){

        if (tipos != TiposEnum.MIS_PUBLICACIONES)
            denunciarPublicacionClickable.setVisibility(View.VISIBLE);

        if (publicaciones.get(i).getTipoPublicacion().equals(TipoPublicacion.ADOPCION) &&
                !isPostulateAdopcion(i) &&
                tipos != TiposEnum.MIS_PUBLICACIONES)
            postularmeAdopcionClickable.setVisibility(View.VISIBLE);

        if (publicaciones.get(i).getTipoPublicacion().equals(TipoPublicacion.ENCONTRADA) &&
                !isPostulateAdopcion(i) &&
                tipos != TiposEnum.MIS_PUBLICACIONES)
            reclamarMascotaClickable.setVisibility(View.VISIBLE);

        if (publicaciones.get(i).getTipoPublicacion().equals(TipoPublicacion.PERDIDA) &&
                !isPostulateAdopcion(i) &&
                tipos != TiposEnum.MIS_PUBLICACIONES)
            encontreTuMascotaClickable.setVisibility(View.VISIBLE);

        if (publicaciones.get(i).getNecesitaTransito() && !isPostulateTransito(i) &&
                tipos != TiposEnum.MIS_PUBLICACIONES){
            postularmeTransitoClickable.setVisibility(View.VISIBLE);
        }

        if (tipos == TiposEnum.MIS_PUBLICACIONES){
            eliminarPublicacionClickable.setVisibility(View.VISIBLE);
            if (!publicaciones.get(i).getConcreatada())
                editarPublicacionClickable.setVisibility(View.VISIBLE);
        }

        if (tipos == TiposEnum.MIS_POSTULACIONES){
            if (publicaciones.get(i).getTipoPublicacion().equals(TipoPublicacion.ADOPCION)  && isPostulateAdopcion(i))  eliminarPostulacionAdopcionClickable.setVisibility(View.VISIBLE);
            if (publicaciones.get(i).getTipoPublicacion().equals(TipoPublicacion.ENCONTRADA)  && isPostulateAdopcion(i))  eliminarReclamoClickable.setVisibility(View.VISIBLE);
            if (publicaciones.get(i).getTipoPublicacion().equals(TipoPublicacion.PERDIDA)  && isPostulateAdopcion(i))  eliminarAvisoClickable.setVisibility(View.VISIBLE);
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


        reclamarMascotaClickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogIcon = getDialogoConfirmacion(TiposClickeableEnum.RECLAMAR_MASCOTA, i).create();
                dialogIcon.show();
            }
        });

        encontreTuMascotaClickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogIcon = getDialogoConfirmacion(TiposClickeableEnum.ENCONTRE_TU_MASCOTA, i).create();
                dialogIcon.show();
            }
        });

        denunciarPublicacionClickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialogoDenunciar(TiposClickeableEnum.DENUNCIAR_PUBLICACION, i);
            }
        });


        editarPublicacionClickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ejecutarAccionClickeable(TiposClickeableEnum.EDITAR_PUBL, i);
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

        eliminarReclamoClickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogIcon = getDialogoConfirmacion(TiposClickeableEnum.ELIMINAR_RECLAMO, i).create();
                dialogIcon.show();
            }
        });

        eliminarAvisoClickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogIcon = getDialogoConfirmacion(TiposClickeableEnum.ELIMINAR_AVISO, i).create();
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

        String title = "\n ";
        title += getMensajeDeConfirmacion(tipo);

        TextView myMsg = new TextView(context);
        TextView myTitle = new TextView(context);


        String mensaje = "\n";

        if (tipo == TiposClickeableEnum.POST_ADOPCION || tipo == TiposClickeableEnum.POST_TRANSITO ){
            mensaje += context.getString(R.string.se_enviara_notificacion);
        }

        if (tipo == TiposClickeableEnum.RECLAMAR_MASCOTA ){
            mensaje += context.getString(R.string.se_enviara_notificacion_reclamo);
        }

        if ( tipo == TiposClickeableEnum.ENCONTRE_TU_MASCOTA) {
            mensaje += context.getString(R.string.se_enviara_notificacion_encontre);
        }

        myMsg.setText(mensaje);
        myMsg.setTextSize(14);
        myMsg.setGravity(Gravity.CENTER_HORIZONTAL);

        myTitle.setText(title);
        myTitle.setTextSize(18);
        myTitle.setGravity(Gravity.CENTER_HORIZONTAL);

        builder.setView(myMsg,50,20,50,20);
        builder.setCustomTitle(myTitle);


        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
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

    private void getDialogoDenunciar(final TiposClickeableEnum tipo,final int position){
        final android.support.v7.app.AlertDialog.Builder builder =
                new android.support.v7.app.AlertDialog.Builder(context);

        String title = "\n ";
        title += getMensajeDeConfirmacion(tipo);
        title += "\n \n";


        TextView myTitle = new TextView(context);

        myTitle.setText(title);
        myTitle.setTextSize(19);
        myTitle.setTextColor(Color.BLACK);
        myTitle.setGravity(Gravity.CENTER_HORIZONTAL);

        EditText editText_descripcion = new EditText(context);
        editText_descripcion.setTextSize(18);
        editText_descripcion.setMaxLines(4);
        editText_descripcion.setHint(R.string.descripcion_denuncia);

        builder.setSingleChoiceItems(itemsDenuncia, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.i("Dialogos", "Opcion elegida: " + itemsDenuncia[i]);
                motivo_denuncia = itemsDenuncia[i];
            }
        });

        builder.setCustomTitle(myTitle);
        builder.setView(editText_descripcion, 28, 50, 28, 50);

        builder.setPositiveButton("Aceptar", null);
        builder.setNegativeButton("Cancelar", null);


        final AlertDialog dialog = builder.create();
        dialog.show();

        Button btn_aceptar = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button btn_cancelar = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Dialogo confirmacion", "Confirmacion Cancelada.");
                dialog.cancel();
            }
        });

        descripcion = editText_descripcion.getText().toString();

        btn_aceptar.setOnClickListener(new CustomListener(dialog, position, motivo_denuncia,
                editText_descripcion));

    }

    public class CustomListener implements View.OnClickListener{
        private final AlertDialog dialog;
        private EditText descripcion;
        private int i;
        private String motivo_denuncia;

        public CustomListener(AlertDialog dialog,int position, String motivo, EditText descripcion){
            this.dialog = dialog;
            this.i = position;
            this.motivo_denuncia = motivo;
            this.descripcion = descripcion;

            Log.i("MOTIVO", motivo_denuncia);
            Log.i("DESCRIPCION", descripcion.getText().toString());
        }

        @Override
        public void onClick(View view) {

            int checkedPosition = dialog.getListView().getCheckedItemPosition();
            dialog.dismiss();

            if (checkedPosition >= 0) {
                String motivoDenuncia = itemsDenuncia[checkedPosition];
                String descripcionDenuncia = descripcion.getText().toString();

                Log.i("MOTIVO", motivoDenuncia);
                Log.i("DESCRIPCION", descripcionDenuncia);

                if (!motivoDenuncia.isEmpty()) {
                    denunciarPublicacionTask = new DenunciarPublicacionTask(i, motivoDenuncia,
                            descripcionDenuncia);
                    denunciarPublicacionTask.execute((Void) null);
                }
            }

            Log.i("Dialogo confirmacion", "Confirmacion Aceptada.");

        }
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
            case RECLAMAR_MASCOTA:
                mensaje = context.getString(R.string.confirmacion_reclamar);
                break;
            case ENCONTRE_TU_MASCOTA:
                mensaje = context.getString(R.string.confirmacion_encontre_tu_mascota);
                break;
            case EDITAR_PUBL:
                mensaje = context.getString(R.string.confirmacion_editar);
                break;
            case DENUNCIAR_PUBLICACION:
                mensaje = context.getString(R.string.confirmacion_denunciar_publicacion);
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
            case ELIMINAR_RECLAMO:
                mensaje = context.getString(R.string.confirmacion_eliminar_reclamo);
                break;
            case ELIMINAR_AVISO:
                mensaje = context.getString(R.string.confirmacion_eliminar_aviso);
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
            case RECLAMAR_MASCOTA:
                guardarPostulacionTask = new GuardarPostulacionTask(i, publicaciones.get(i).getId(), TiposClickeableEnum.RECLAMAR_MASCOTA, itemView);
                guardarPostulacionTask.execute((Void) null);
                break;
            case ENCONTRE_TU_MASCOTA:
                guardarPostulacionTask = new GuardarPostulacionTask(i, publicaciones.get(i).getId(), TiposClickeableEnum.ENCONTRE_TU_MASCOTA, itemView);
                guardarPostulacionTask.execute((Void) null);
                break;
            case EDITAR_PUBL:
                ((DrawerMenuActivity) activity).navigateToEditarPublicacion(publicaciones.get(i));
                break;
            /*case DENUNCIAR_PUBLICACION:
                denunciarPublicacionTask = new DenunciarPublicacionTask(i, this.motivo_denuncia, this.descripcion);
                denunciarPublicacionTask.execute((Void) null);
                break;*/
            case ELIMINAR_PUBL:
                eliminarPublicacionTask = new EliminarPublicacionTask(i);
                eliminarPublicacionTask.execute((Void) null);
                break;
            case ELIMINAR_POST_ADOP:
                eliminarPostulacionTask = new EliminarPostulacionTask(i, publicaciones.get(i).getId(),TiposClickeableEnum.ELIMINAR_POST_ADOP );
                eliminarPostulacionTask.execute((Void) null);
                break;
            case ELIMINAR_RECLAMO:
                eliminarPostulacionTask = new EliminarPostulacionTask(i, publicaciones.get(i).getId(),TiposClickeableEnum.ELIMINAR_RECLAMO );
                eliminarPostulacionTask.execute((Void) null);
                break;
            case ELIMINAR_AVISO:
                eliminarPostulacionTask = new EliminarPostulacionTask(i, publicaciones.get(i).getId(),TiposClickeableEnum.ELIMINAR_AVISO );
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
        if (condiciones == null || condiciones.isEmpty() || condiciones.equals(" "))
            condiciones = "No tiene.";

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

    private void cargarFotos(final int i, final View view) {

        if (publicaciones.get(i).getImagenes().isEmpty()) {
            CardView cardview_photos = (CardView) view.findViewById(R.id.cardview_fotos_pm);
            cardview_photos.setVisibility(View.GONE);
        } else {
            CardView cardview_photos = (CardView) view.findViewById(R.id.cardview_fotos_pm);
            cardview_photos.setVisibility(View.VISIBLE);
            Gallery galleryImagenes = (Gallery) view.findViewById(R.id.gallery_photos);
            galleryImagenes.setVisibility(View.GONE);
            final ImageView imagenSelect = (ImageView) view.findViewById(R.id.image_seleccionada);
            imagenSelect.setImageBitmap(publicaciones.get(i).getImagenes().get(0).getBitmap());

            if(publicaciones.get(i).getImagenes().size() > 1){
                galleryImagenes.setVisibility(View.VISIBLE);
                galleryImagenes.setAdapter(new GalleryAdapter(context, publicaciones.get(i).getImagenes()));
                galleryImagenes.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    public void onItemClick(AdapterView parent, View v, int position, long id) {
                        imagenSelect.setImageBitmap(publicaciones.get(i).getImagenes().get(position).getBitmap());
                    }

                });
            }
        }

    }

    private void cargarVideos(int i, View view) {

        String video = "";

        final HashMap<String, String> videos = new HashMap<>();

        if (publicaciones.get(i).getVideoLink() != null && !publicaciones.get(i).getVideoLink().isEmpty() &&
                publicaciones.get(i).getVideoLink().length() > 32) {
            video = publicaciones.get(i).getVideoLink().substring(32);
            videos.put("Video", video);
        }

        if (videos.isEmpty()) {
            CardView cardview_video = (CardView) view.findViewById(R.id.cardview_videos_pm);
            cardview_video.setVisibility(View.GONE);
        } else {
            CardView cardview_video = (CardView) view.findViewById(R.id.cardview_videos_pm);
            cardview_video.setVisibility(View.VISIBLE);

            View view_reproducir = view.findViewById(R.id.view_reproducir_video);
            ImageView image_video = (ImageView) view.findViewById(R.id.image_video);

            new DownloadImageTask(image_video)
                    .execute("http://img.youtube.com/vi/" + video + "/mqdefault.jpg");

            view_reproducir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videos.get("Video")));
                        intent.putExtra("force_fullscreen", true);
                        context.startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + videos.get("Video")));
                        context.startActivity(intent);
                    }
                }
            });



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
                intent.putExtra("sexo", valorSexo);
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
                listView.setAdapter(new MensajeAdapter(v.getContext(), mensajes, tipos, publicaciones.get(i).getConcreatada(),activity));
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
                listView.setAdapter(new MensajeAdapter(v.getContext(), mensajes, tipos, publicaciones.get(i).getConcreatada(), activity));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View view,
                                            final int position, long arg) {

                    }
                });
            }

            btnEnviarConsulta.setVisibility(View.VISIBLE);
            replicaConsulta.setVisibility(View.VISIBLE);
            onClickButtonMensaje(i, v);
        }

    }

    private void cargarMensajesPublicante(final int i, final View v) {
        List<Mensaje> mensajesPrivados = null;

        ListView listViewConsultaPrivada = (ListView) v.findViewById(R.id.listView_consultas_publicante);
        listViewConsultaPrivada.setVisibility(View.GONE);

        listViewConsultaPrivada.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        if (isPostulateAdopcion(i)) {
            for (Postulante p :publicaciones.get(i).getQuierenAdoptar()){
                if (p.getId() == Usuario.getInstancia().getId()){
                    List<Mensaje> mp = p.getMensajesPrivados();
                    if (!mp.isEmpty())
                        mensajesPrivados = mp;
                }
            }
        }

        if (isPostulateTransito(i)) {
            for (Postulante p :publicaciones.get(i).getOfrecenTransito()){
                if (p.getId() == Usuario.getInstancia().getId()){
                    List<Mensaje> mp = p.getMensajesPrivados();
                    if (!mp.isEmpty()) {
                        if (mensajesPrivados != null)
                            mensajesPrivados.addAll(mp);
                        else mensajesPrivados = mp;
                    }
                }
            }
        }

        if (tipos == TiposEnum.MIS_POSTULACIONES) {

            if (mensajesPrivados == null) {
                CardView cardViewMensajesPrivados = (CardView) v.findViewById(R.id.cardview_mensajes_privados);
                cardViewMensajesPrivados.setVisibility(View.GONE);
            } else {
                listViewConsultaPrivada.setVisibility(View.VISIBLE);
                listViewConsultaPrivada.setAdapter(new MensajePrivadoAdapter(v.getContext(), mensajesPrivados));
                listViewConsultaPrivada.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View view,
                                            final int position, long arg) {

                    }
                });
            }
        }else {
            CardView cardViewMensajesPrivados = (CardView) v.findViewById(R.id.cardview_mensajes_privados);
            cardViewMensajesPrivados.setVisibility(View.GONE);
        }

    }

    private void updateConsultaEnviada(View v, int i, Mensaje consulta){
        mensajes.add(consulta);
        listView.setVisibility(View.VISIBLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view,
                                    final int position, long arg) {

            }
        });
        listView.setAdapter(new MensajeAdapter(v.getContext(), mensajes, tipos, publicaciones.get(i).getConcreatada(), activity));
    }

    private void updateCancelacionDePostulacion(){
        ((DrawerMenuActivity) activity).showUpdateCancelPostulaciones();
    }

    private void updateEnviarPostulacion(){
        ((DrawerMenuActivity) activity).showUpdatePostulaciones();
    }

    private void initialiceElementosMensajes(View v){
        replicaConsulta = (TextView) v.findViewById(R.id.replica_consulta);
        btnEnviarConsulta = (ImageButton) v.findViewById(R.id.btn_enviar_consulta);
        listView = (ListView) v.findViewById(R.id.listView_consultas);

        btnEnviarConsulta.setFocusable(false);
        replicaConsulta.setVisibility(View.GONE);
        listView.setVisibility(View.GONE);
        btnEnviarConsulta.setVisibility(View.GONE);

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
                getDialogoEnviarMensaje(i,v);
            }
        });

        replicaConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialogoEnviarMensaje(i,v);
            }
        });

    }

    private void getDialogoEnviarMensaje(final int i, final View v){

        final android.support.v7.app.AlertDialog.Builder builder =
                new android.support.v7.app.AlertDialog.Builder(context);

        final EditText input = new EditText(context);
        input.setTextSize(16);
        input.setMaxLines(4);
        input.setHint(R.string.escriba_su_consulta);
        builder.setView(input,15,50,15,50);

        builder.setPositiveButton("Aceptar", null);
        builder.setNegativeButton("Cancelar", null);

        final AlertDialog dialog = builder.create();
        dialog.show();

        Button btn_aceptar = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button btn_cancelar = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Dialogo confirmacion", "Confirmacion Cancelada.");
                dialog.cancel();
            }
        });

        btn_aceptar.setOnClickListener(new CustomListenerEnviar(dialog,input, i, v));

    }

    public class CustomListenerEnviar implements View.OnClickListener{
        private final AlertDialog dialog;
        private EditText pregunta;
        private String consulta;
        private int i;
        private View v;

        public CustomListenerEnviar(AlertDialog dialog, EditText respuesta, int position, View v){
            this.dialog = dialog;
            this.pregunta = respuesta;
            this.i = position;
            this.v = v;
        }

        @Override
        public void onClick(View view) {
            consulta = pregunta.getText().toString();
            if (!validator(consulta)){
                System.out.println("CLICK ENVIAR CONSULTA: " + consulta);
                Mensaje mensaje = new Mensaje();
                mensaje.setPregunta(consulta);
                mensaje.setPublicacionId(publicaciones.get(i).getId());
                mensaje.setUsuarioPreguntaId(Usuario.getInstancia().getId());
                mensaje.setUsuarioRespuestaId(publicaciones.get(i).getPublicadorId());
                enviarConsultaTask = new EnviarConsultaTask(mensaje, i, v);
                enviarConsultaTask.execute((Void) null);
                dialog.dismiss();
            } else {
                System.out.println("CLICK DEBE COMPLETAR CONSULTA");
                pregunta.setError("Debe escribir una consulta");
            }

            Log.i("Dialogo confirmacion", "Confirmacion Aceptada.");

        }
    }

    private boolean validator(String mensaje){
        return mensaje.toString().isEmpty();
    }

    private void executeActionButtonMensaje(int i, String pregunta, View v){
        if (pregunta.isEmpty()){
        }else{
            Mensaje mensaje = new Mensaje();
            mensaje.setPregunta(pregunta);
            mensaje.setPublicacionId(publicaciones.get(i).getId());
            mensaje.setUsuarioPreguntaId(Usuario.getInstancia().getId());
            mensaje.setUsuarioRespuestaId(publicaciones.get(i).getPublicadorId());
            enviarConsultaTask = new EnviarConsultaTask(mensaje, i, v);
            enviarConsultaTask.execute((Void) null);
        }
    }

    private void cargarPostulaciones(final int i, final View v) {

        postulantesAdopcion = publicaciones.get(i).getQuierenAdoptar();
        postulantesOfrecenTransito = publicaciones.get(i).getOfrecenTransito();

        panelPostulantesEnAdopcion = (View) v.findViewById(R.id.view_adoptantes);
        panelPostulantesOfrecenTransito = (View) v.findViewById(R.id.view_ofrecen_transito);
        panelPostulantesReclamanMascotas = (View) v.findViewById(R.id.view_reclaman_su_mascota);
        panelPostulantesEncontraronMascotas = (View) v.findViewById(R.id.view_encontraron_tu_mascota);

        panelPostulantesEnAdopcion.setVisibility(View.GONE);
        panelPostulantesOfrecenTransito.setVisibility(View.GONE);
        panelPostulantesReclamanMascotas.setVisibility(View.GONE);
        panelPostulantesEncontraronMascotas.setVisibility(View.GONE);


        if (tipos == TiposEnum.MIS_PUBLICACIONES){

            listViewAdopciones = (ExpandableListView) v.findViewById(R.id.listView_quieren_adoptar);
            listViewOfrecenTransito = (ExpandableListView) v.findViewById(R.id.listView_ofrecen_transito);
            listViewReclamarMascota = (ExpandableListView) v.findViewById(R.id.listView_reclaman_mascota);
            listViewEncontreTuMascota = (ExpandableListView) v.findViewById(R.id.listView_encontraron_mascota);

            listViewAdopciones.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            });

            listViewOfrecenTransito.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            });


            listViewReclamarMascota.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            });

            listViewEncontreTuMascota.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            });


            TipoPublicacion tipo = publicaciones.get(i).getTipoPublicacion();

            if (postulantesAdopcion.isEmpty() && postulantesOfrecenTransito.isEmpty()){
                CardView cardview_postulaciones = (CardView) v.findViewById(R.id.cardview_postulaciones);
                cardview_postulaciones.setVisibility(View.GONE);
            }else if (publicaciones.get(i).getConcreatada() && publicaciones.get(i).getEnTransito()){
                CardView cardview_postulaciones = (CardView) v.findViewById(R.id.cardview_postulaciones);
                cardview_postulaciones.setVisibility(View.GONE);
            }else if (publicaciones.get(i).getConcreatada()){
                CardView cardview_postulaciones = (CardView) v.findViewById(R.id.cardview_postulaciones);
                cardview_postulaciones.setVisibility(View.GONE);
            } else if (publicaciones.get(i).getEnTransito() && postulantesAdopcion.isEmpty()){
                CardView cardview_postulaciones = (CardView) v.findViewById(R.id.cardview_postulaciones);
                cardview_postulaciones.setVisibility(View.GONE);
            }else if (!postulantesAdopcion.isEmpty() && tipo == TipoPublicacion.ADOPCION && (postulantesOfrecenTransito.isEmpty()||publicaciones.get(i).getEnTransito()) ){
                panelPostulantesEnAdopcion.setVisibility(View.VISIBLE);
                setAdapterAdopciones(v, "Adopcion", postulantesAdopcion, publicaciones.get(i).getId());
            } else if (!postulantesAdopcion.isEmpty() && tipo == TipoPublicacion.ENCONTRADA && (postulantesOfrecenTransito.isEmpty()||publicaciones.get(i).getEnTransito()) ){
                panelPostulantesReclamanMascotas.setVisibility(View.VISIBLE);
                setAdapterReclamanMascota(v, "Encontrada", postulantesAdopcion, publicaciones.get(i).getId());
            }else if (!postulantesAdopcion.isEmpty() && tipo == TipoPublicacion.PERDIDA && (postulantesOfrecenTransito.isEmpty()||publicaciones.get(i).getEnTransito()) ){
                panelPostulantesEncontraronMascotas.setVisibility(View.VISIBLE);
                setAdapterEncontreTuMascota(v, "Perdida", postulantesAdopcion, publicaciones.get(i).getId());
            } else if ((postulantesAdopcion.isEmpty() || publicaciones.get(i).getConcreatada()) && !postulantesOfrecenTransito.isEmpty()){
                panelPostulantesOfrecenTransito.setVisibility(View.VISIBLE);
                setAdapterTransito(v, "Transito", postulantesOfrecenTransito,publicaciones.get(i).getId());
            } else if (tipo == TipoPublicacion.ADOPCION) {
                panelPostulantesEnAdopcion.setVisibility(View.VISIBLE);
                panelPostulantesOfrecenTransito.setVisibility(View.VISIBLE);
                setAdapterAdopciones(v, "Adopcion", postulantesAdopcion, publicaciones.get(i).getId());
                setAdapterTransito(v,"Transito", postulantesOfrecenTransito,publicaciones.get(i).getId());
            } else if (tipo == TipoPublicacion.ENCONTRADA) {
                panelPostulantesReclamanMascotas.setVisibility(View.VISIBLE);
                panelPostulantesOfrecenTransito.setVisibility(View.VISIBLE);
                setAdapterReclamanMascota(v, "Encontrada", postulantesAdopcion, publicaciones.get(i).getId());
                setAdapterTransito(v,"Transito", postulantesOfrecenTransito,publicaciones.get(i).getId());
            } else if (tipo == TipoPublicacion.PERDIDA) {
                panelPostulantesEncontraronMascotas.setVisibility(View.VISIBLE);
                panelPostulantesOfrecenTransito.setVisibility(View.VISIBLE);
                setAdapterEncontreTuMascota(v, "Perdida", postulantesAdopcion, publicaciones.get(i).getId());
                setAdapterTransito(v,"Transito", postulantesOfrecenTransito,publicaciones.get(i).getId());
            }
        }else{
            CardView cardview_postulaciones = (CardView) v.findViewById(R.id.cardview_postulaciones);
            cardview_postulaciones.setVisibility(View.GONE);
        }

    }

    private void setAdapterReclamanMascota(View v, String tipoAdapter, List<Postulante> postulantes, int id_publicacion){
        List<Mensaje> mensajesPrivados = new ArrayList<>();
        listViewReclamarMascota.setAdapter(new PostulacionesAdapter(v.getContext(), tipoAdapter, postulantes, id_publicacion,
                mensajesPrivados, activity));
        listViewReclamarMascota.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view,
                                    int position, long arg) {
            }
        });
    }

    private void setAdapterEncontreTuMascota(View v, String tipoAdapter, List<Postulante> postulantes, int id_publicacion){
        List<Mensaje> mensajesPrivados = new ArrayList<>();
        listViewEncontreTuMascota.setAdapter(new PostulacionesAdapter(v.getContext(), tipoAdapter, postulantes, id_publicacion,
                mensajesPrivados, activity));
        listViewEncontreTuMascota.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view,
                                    int position, long arg) {
            }
        });
    }

    private void setAdapterAdopciones(View v, String tipoAdapter, List<Postulante> postulantes, int id_publicacion){
        List<Mensaje> mensajesPrivadas = getListMensajesPrivadosMock();
        listViewAdopciones.setAdapter(new PostulacionesAdapter(v.getContext(), tipoAdapter, postulantes, id_publicacion,
                mensajesPrivadas, activity));

        listViewAdopciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view,
                                    int position, long arg) {
            }
        });
    }

    private void setAdapterTransito(View v, String tipoAdapter, List<Postulante> postulantes, int id_publicacion){
        List<Mensaje> mensajesPrivados = new ArrayList<>();
        listViewOfrecenTransito.setAdapter(new PostulacionesAdapter(v.getContext(), tipoAdapter, postulantes, id_publicacion,
                mensajesPrivados, activity));
        listViewOfrecenTransito.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view,
                                    int position, long arg) {
            }
        });
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
                if (tipo == TiposClickeableEnum.POST_ADOPCION ||
                        tipo == TiposClickeableEnum.RECLAMAR_MASCOTA || tipo == TiposClickeableEnum.ENCONTRE_TU_MASCOTA)
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
                updateEnviarPostulacion();
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
                    Toast.makeText(context, "Mensaje enviado", Toast.LENGTH_SHORT).show();
                    updateConsultaEnviada(v, i, consulta);
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
                Toast.makeText(context, context.getResources().getString(R.string.publicacion_cancelada),
                        Toast.LENGTH_LONG).show();
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

    public class DenunciarPublicacionTask extends AsyncTask<Void, Void, Boolean> {

        private int position;
        private String motivo_denuncia;
        private String descripcion;

        DenunciarPublicacionTask(int i, String motivo_denuncia, String descripcion) {
            this.position = i;
            this.motivo_denuncia = motivo_denuncia;
            this.descripcion = descripcion;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                publicaciones.get(position).denunciarPublicacion(Usuario.getInstancia().getToken(), this.motivo_denuncia, this.descripcion);
                Thread.sleep(200);
            } catch (InterruptedException e) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success){
                notifyDataSetChanged();
                Toast.makeText(context, context.getResources().getString(R.string.publicacion_denunciada),
                        Toast.LENGTH_LONG).show();
//                denunciarPublicacionClickable.setVisibility(View.GONE);
            }
            denunciarPublicacionTask = null;
        }

        @Override
        protected void onCancelled() {
            denunciarPublicacionTask = null;
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
                if (tipo == TiposClickeableEnum.ELIMINAR_POST_ADOP || tipo == TiposClickeableEnum.ELIMINAR_RECLAMO
                        || tipo == TiposClickeableEnum.ELIMINAR_AVISO){
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
                Toast.makeText(context, context.getResources().getString(R.string.postulacion_cancelada),
                        Toast.LENGTH_LONG).show();
                //removeItem(position);
                updateCancelacionDePostulacion();
                notifyDataSetChanged();
            }
            eliminarPostulacionTask = null;
        }

        @Override
        protected void onCancelled() {
            eliminarPostulacionTask = null;
        }
    }

    private List<Mensaje> getListMensajesPrivadosMock(){
        List<Mensaje> mensajes = new ArrayList<>();
        Mensaje m1 = new Mensaje();
        m1.setPregunta("Te gustan los animales");
        m1.setRespuesta("Si, me encantan.");
        m1.setFechaPregunta(new Date(2015, 10, 12));
        m1.setFechaRespuesta(new Date(2015,11,12));
        mensajes.add(m1);
        return mensajes;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
                //URL newurl = new URL(urldisplay);
                //mIcon11 = BitmapFactory.decodeStream(newurl.openConnection() .getInputStream());
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}
