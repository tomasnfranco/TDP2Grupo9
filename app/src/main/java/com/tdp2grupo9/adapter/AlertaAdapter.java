package com.tdp2grupo9.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tdp2grupo9.R;
import com.tdp2grupo9.fragment.MisNotificacionesFragment;
import com.tdp2grupo9.modelo.Alerta;
import com.tdp2grupo9.modelo.PublicacionAtributos;
import com.tdp2grupo9.modelo.TipoPublicacion;
import com.tdp2grupo9.modelo.Usuario;
import com.tdp2grupo9.modelo.publicacion.Castrado;
import com.tdp2grupo9.modelo.publicacion.Color;
import com.tdp2grupo9.modelo.publicacion.CompatibleCon;
import com.tdp2grupo9.modelo.publicacion.Edad;
import com.tdp2grupo9.modelo.publicacion.Energia;
import com.tdp2grupo9.modelo.publicacion.Especie;
import com.tdp2grupo9.modelo.publicacion.PapelesAlDia;
import com.tdp2grupo9.modelo.publicacion.Proteccion;
import com.tdp2grupo9.modelo.publicacion.Raza;
import com.tdp2grupo9.modelo.publicacion.Sexo;
import com.tdp2grupo9.modelo.publicacion.Tamanio;
import com.tdp2grupo9.modelo.publicacion.VacunasAlDia;
import com.tdp2grupo9.tabbed.TabbedFragment;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;


public class AlertaAdapter extends BaseAdapter{

    Context context;
    List<Alerta> alertasList;
    TextView infAdicional;
    TextView infBasica;
    TextView infFechaAlerta;
    private TextView titleAdicional;
    private ImageButton btnBuscar;
    private ImageButton btnEliminar;
    private ImageButton btnEditar;
    private EliminarAlertaTask eliminarAlertaTask;
    private View alertaView;

    public AlertaAdapter(Context context, List<Alerta> alertas){
        this.alertasList = alertas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return alertasList.size();
    }

    public void removeItem(int i) {
        alertasList.remove(i);
    }

    @Override
    public Object getItem(int i) {
        return alertasList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        alertaView = getInflatedViewIfNecessary(view, viewGroup);
        infAdicional = (TextView) alertaView.findViewById(R.id.infAdicional);
        titleAdicional = (TextView) alertaView.findViewById(R.id.titleInfoAdicional);

        infBasica = (TextView) alertaView.findViewById(R.id.infBasica);
        infFechaAlerta = (TextView) alertaView.findViewById(R.id.fecha_alerta);
        btnBuscar = (ImageButton) alertaView.findViewById(R.id.btn_Buscar);
        btnEliminar= (ImageButton) alertaView.findViewById(R.id.btn_Delete);
        btnEditar = (ImageButton) alertaView.findViewById(R.id.btn_Editar);


        String infoBasica = getInformacionBasica(i, alertaView);
        String infoAdicional= getInformacionAdicional(i,alertaView);

        Date fechaCreacion = alertasList.get(i).getFechaCreacion();

        infFechaAlerta.setText(parserDateText(fechaCreacion));
        infBasica.setText(infoBasica);
        if (infoAdicional.isEmpty()){
            titleAdicional.setVisibility(View.GONE);
            infAdicional.setVisibility(View.GONE);
        }else {
            infAdicional.setText(infoAdicional);
        }

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("Se presiona boton buscar en posición " + i);
                Bundle bundle = new Bundle();
                bundle.putString("tipopublicacion", TipoPublicacion.getTipoPublicacionToString(alertasList.get(i).getTipoPublicacion().getValue()));
                bundle.putInt("especie", alertasList.get(i).getEspecie().getId());
                bundle.putInt("raza", alertasList.get(i).getRaza().getId());
                bundle.putInt("sexo", alertasList.get(i).getSexo().getId());
                bundle.putInt("tamanio", alertasList.get(i).getTamanio().getId());
                bundle.putInt("edad", alertasList.get(i).getEdad().getId());
                bundle.putInt("color", alertasList.get(i).getColor().getId());
                bundle.putInt("proteccion", alertasList.get(i).getProteccion().getId());
                bundle.putInt("energia", alertasList.get(i).getEnergia().getId());
                bundle.putInt("castrado", alertasList.get(i).getCastrado().getId());
                bundle.putInt("compatiblecon", alertasList.get(i).getCompatibleCon().getId());
                bundle.putInt("vacunas", alertasList.get(i).getVacunasAlDia().getId());
                bundle.putInt("papeles", alertasList.get(i).getPapelesAlDia().getId());
                Integer distancia = alertasList.get(i).getDistancia();
                if (distancia == null)
                    bundle.putInt("distancia", -1);
                else
                    bundle.putInt("distancia", distancia);
                bundle.putDouble("latitud", alertasList.get(i).getLatitud());
                bundle.putDouble("longitud", alertasList.get(i).getLongitud());

                TabbedFragment.newInstance().showBuscarMascotaResults(bundle);

            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO implementar
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarAlertaTask = new EliminarAlertaTask(alertasList.get(i), i);
                eliminarAlertaTask.execute((Void) null);
            }
        });

        return alertaView;
    }

    private String getInformacionBasica(int i, View alertaView){
        String especie = PublicacionAtributos.getInstancia().getEspecies().get(PublicacionAtributos.getInstancia().getEspecies().indexOf(alertasList.get(i).getEspecie())).toString();
        String sexo = PublicacionAtributos.getInstancia().getSexos().get(PublicacionAtributos.getInstancia().getSexos().indexOf(alertasList.get(i).getSexo())).toString();
        String edad = PublicacionAtributos.getInstancia().getEdades().get(PublicacionAtributos.getInstancia().getEdades().indexOf(alertasList.get(i).getEdad())).toString();
        String color = PublicacionAtributos.getInstancia().getColores().get(PublicacionAtributos.getInstancia().getColores().indexOf(alertasList.get(i).getColor())).toString();
        String raza = PublicacionAtributos.getInstancia().getRazas().get(PublicacionAtributos.getInstancia().getRazas().indexOf(alertasList.get(i).getRaza())).toString();
        String tamanio = PublicacionAtributos.getInstancia().getTamanios().get(PublicacionAtributos.getInstancia().getTamanios().indexOf(alertasList.get(i).getTamanio())).toString();

        String infoBasica ="";
        infoBasica+= especie + " ";
        infoBasica+= raza + ". ";
        infoBasica+= sexo + ". ";
        infoBasica+= edad + ". ";
        infoBasica+= alertaView.getContext().getString(R.string.tamanio)+ " " + tamanio + ". ";
        infoBasica+= alertaView.getContext().getString(R.string.color)+ " " + color + ".";

        return infoBasica;
    }

    private String getInformacionAdicional(int i, View alertaView){

        VacunasAlDia vacunasEntity = alertasList.get(i).getVacunasAlDia();
        Castrado castradoEntity = alertasList.get(i).getCastrado();
        Proteccion proteccionEntity = alertasList.get(i).getProteccion();
        Energia energiaEntity = alertasList.get(i).getEnergia();
        PapelesAlDia papelesEntity = alertasList.get(i).getPapelesAlDia();
        CompatibleCon compatibleEntity = alertasList.get(i).getCompatibleCon();

        String vacunas = "";
        String castrado = "";
        String energia = "";
        String papeles = "";
        String proteccion = "";
        String compatibleCon = "";

        if (PublicacionAtributos.getInstancia().getVacunasAlDia().indexOf(vacunasEntity) > -1){
            vacunas= PublicacionAtributos.getInstancia().getVacunasAlDia().get(PublicacionAtributos.getInstancia().getVacunasAlDia().indexOf(vacunasEntity)).toString();
        }
        if (PublicacionAtributos.getInstancia().getCastrados().indexOf(castradoEntity) > -1)
            castrado = PublicacionAtributos.getInstancia().getCastrados().get(PublicacionAtributos.getInstancia().getCastrados().indexOf(castradoEntity)).toString();
        if (PublicacionAtributos.getInstancia().getProtecciones().indexOf(proteccionEntity) > -1)
            proteccion = PublicacionAtributos.getInstancia().getProtecciones().get(PublicacionAtributos.getInstancia().getProtecciones().indexOf(proteccionEntity)).toString();
        if (PublicacionAtributos.getInstancia().getEnergias().indexOf(energiaEntity) > -1)
            energia = PublicacionAtributos.getInstancia().getEnergias().get(PublicacionAtributos.getInstancia().getEnergias().indexOf(energiaEntity)).toString();
        if (PublicacionAtributos.getInstancia().getPapelesAlDia().indexOf(papelesEntity) > -1)
            papeles = PublicacionAtributos.getInstancia().getPapelesAlDia().get(PublicacionAtributos.getInstancia().getPapelesAlDia().indexOf(papelesEntity)).toString();
        if (PublicacionAtributos.getInstancia().getCompatibilidades().indexOf(compatibleEntity) > -1)
            compatibleCon = PublicacionAtributos.getInstancia().getCompatibilidades().get(PublicacionAtributos.getInstancia().getCompatibilidades().indexOf(compatibleEntity)).toString();

        String infoAdicional="";

        if (vacunas.equals("Si"))
            infoAdicional+= "Que " + alertaView.getContext().getString(R.string.tenga_vacunas)+". ";
        else if (vacunas.equals("No"))
            infoAdicional+= "Que no " + alertaView.getContext().getString(R.string.tenga_vacunas).toLowerCase() + ". ";
        if (papeles.equals("Si"))
            infoAdicional+= "Que " +  alertaView.getContext().getString(R.string.tenga_papeles)+". ";
        else if (papeles.equals("No"))
            infoAdicional+= "Que no " + alertaView.getContext().getString(R.string.tenga_papeles).toLowerCase() + ". ";
        if (castrado.equals("Si"))
            infoAdicional+= "Que " + alertaView.getContext().getString(R.string.este_castrado)+". ";
        else if (castrado.equals("No"))
            infoAdicional+= "Que no " + alertaView.getContext().getString(R.string.este_castrado).toLowerCase() + ". ";
        if(!compatibleCon.equals("No aplica") &&!compatibleCon.isEmpty() )
            infoAdicional+= "Que " + alertaView.getContext().getString(R.string.sea_compatible) +" " + compatibleCon.toLowerCase() + ". ";
        if(!energia.equals("No aplica") && !energia.isEmpty() )
            infoAdicional+= "Sea " + energia.toLowerCase() + ". ";
        if(!proteccion.equals("No aplica") && !proteccion.isEmpty())
            infoAdicional+= "Sea " + proteccion.toLowerCase() + ". ";

        infoAdicional+= alertaView.getContext().getString(R.string.a_distancia) + " " + alertasList.get(i).getDistancia() + "km.";

        return infoAdicional;
    }

    private String parserDateText(Date fecha){
        int dia = fecha.getDate();
        int mes = fecha.getMonth() + 1;
        int anio = fecha.getYear() + 1900;

        String date = dia + "/" + mes + "/" + anio;

        return date;
    }

    private View getInflatedViewIfNecessary(View view, ViewGroup viewGroup) {
        View alertaView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            alertaView = inflater.inflate(R.layout.cardview_alertas, viewGroup, false);
        } else {
            alertaView = view;
        }
        return alertaView;
    }

    public class EliminarAlertaTask extends AsyncTask<Void, Void, Boolean> {

        private Alerta alerta;
        private int position;

        EliminarAlertaTask(Alerta alerta, int i) {
            this.alerta = alerta;
            position = i;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                this.alerta.borrarAlerta(Usuario.getInstancia().getToken());
                Thread.sleep(200);
            } catch (InterruptedException e) {
                return false;
            }
            return this.alerta.getId() > 0;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            eliminarAlertaTask = null;
            if (success) {
                Toast.makeText(context, "Alerta Eliminada",
                        Toast.LENGTH_LONG).show();
                removeItem(position);
                notifyDataSetChanged();
            }
            else {
                Toast.makeText(context, "Error: No se pudo eliminar.",
                        Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            eliminarAlertaTask = null;
        }
    }


}
