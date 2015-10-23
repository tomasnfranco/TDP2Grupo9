package com.tdp2grupo9.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tdp2grupo9.R;
import com.tdp2grupo9.modelo.Alerta;
import com.tdp2grupo9.modelo.PublicacionAtributos;
import com.tdp2grupo9.modelo.Usuario;
import com.tdp2grupo9.modelo.publicacion.Castrado;
import com.tdp2grupo9.modelo.publicacion.CompatibleCon;
import com.tdp2grupo9.modelo.publicacion.Energia;
import com.tdp2grupo9.modelo.publicacion.PapelesAlDia;
import com.tdp2grupo9.modelo.publicacion.Proteccion;
import com.tdp2grupo9.modelo.publicacion.VacunasAlDia;

import java.util.Date;
import java.util.List;


public class AlertaAdapter extends BaseAdapter {

    Context context;
    List<Alerta> alertasList;
    TextView infAdicional;
    TextView infBasica;
    TextView infFechaAlerta;
    private TextView titleAdicional;

    public AlertaAdapter(Context context, List<Alerta> alertas){
        this.alertasList = alertas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return alertasList.size();
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        View alertaView = getInflatedViewIfNecessary(view,viewGroup);

        infAdicional = (TextView) alertaView.findViewById(R.id.infAdicional);
        titleAdicional = (TextView) alertaView.findViewById(R.id.titleInfoAdicional);

        infBasica = (TextView) alertaView.findViewById(R.id.infBasica);
        infFechaAlerta = (TextView) alertaView.findViewById(R.id.fecha_alerta);

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

}
