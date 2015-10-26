package com.tdp2grupo9.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tdp2grupo9.R;
import com.tdp2grupo9.modelo.Alerta;
import com.tdp2grupo9.modelo.Fecha;
import com.tdp2grupo9.modelo.PublicacionAtributos;
import com.tdp2grupo9.modelo.TipoPublicacion;
import com.tdp2grupo9.modelo.Usuario;
import com.tdp2grupo9.modelo.publicacion.AtributoPublicacion;
import com.tdp2grupo9.tabbed.TabbedFragment;

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

        infFechaAlerta.setText(Fecha.parseDate(fechaCreacion));
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

        String infoBasica = "";

        String especie = PublicacionAtributos.getInstancia().getEspecie(alertasList.get(i).getEspecie()).getValor() + "s ";
        infoBasica += especie + " ";
        switch (alertasList.get(i).getTipoPublicacion()) {
            case ADOPCION:
                infoBasica += alertaView.getContext().getString(R.string.en_adopcion).toLowerCase() + ".\n";
                break;
            case ENCONTRADA:
                infoBasica += "encontrados.\n";
                break;
            case PERDIDA:
                infoBasica += "perdidos.\n";
                break;
        }
        if (alertasList.get(i).getRaza().getId() > 0)
            infoBasica += PublicacionAtributos.getInstancia().getRaza(alertasList.get(i).getRaza()).getValor() + ". ";
        if (alertasList.get(i).getSexo().getId() > 0)
            infoBasica += PublicacionAtributos.getInstancia().getSexo(alertasList.get(i).getSexo()).getValor() + ". ";
        if (alertasList.get(i).getEdad().getId() > 0)
            infoBasica += PublicacionAtributos.getInstancia().getSexo(alertasList.get(i).getEdad()).getValor() + ". ";
        if (alertasList.get(i).getTamanio().getId() > 0)
            infoBasica += alertaView.getContext().getString(R.string.tamanio) + " " +PublicacionAtributos.getInstancia().getSexo(alertasList.get(i).getTamanio()).getValor() + ". ";
        if (alertasList.get(i).getColor().getId() > 0)
            infoBasica += alertaView.getContext().getString(R.string.color)+ " " + PublicacionAtributos.getInstancia().getSexo(alertasList.get(i).getColor()).getValor() + ". ";

        return infoBasica;
    }

    private String getInformacionAdicional(int i, View alertaView){

        String infoAdicional="";

        AtributoPublicacion vacunasEntity = alertasList.get(i).getVacunasAlDia();
        if (vacunasEntity.getId() > 0) {
            String valor = PublicacionAtributos.getInstancia().getVacunasAlDia(vacunasEntity).getValor();
            if (valor.equals("Si"))
                infoAdicional += "Que " + alertaView.getContext().getString(R.string.tenga_vacunas) + ". ";
            else if (valor.equals("No"))
                infoAdicional += "Que no " + alertaView.getContext().getString(R.string.tenga_vacunas).toLowerCase() + ". ";
        }

        AtributoPublicacion papelesEntity = alertasList.get(i).getPapelesAlDia();
        if (papelesEntity.getId() > 0) {
            String valor = PublicacionAtributos.getInstancia().getPapelesAlDia(papelesEntity).getValor();
            if (valor.equals("Si"))
                infoAdicional += "Que " + alertaView.getContext().getString(R.string.tenga_papeles) + ". ";
            else if (valor.equals("No"))
                infoAdicional += "Que no " + alertaView.getContext().getString(R.string.tenga_papeles).toLowerCase() + ". ";
        }

        AtributoPublicacion castradoEntity = alertasList.get(i).getCastrado();
        if (castradoEntity.getId() > 0) {
            String valor = PublicacionAtributos.getInstancia().getCastrado(castradoEntity).getValor();
            if (valor.equals("Si"))
                infoAdicional+= "Que " + alertaView.getContext().getString(R.string.este_castrado)+". ";
            else if (valor.equals("No"))
                infoAdicional+= "Que no " + alertaView.getContext().getString(R.string.este_castrado).toLowerCase() + ". ";
        }

        AtributoPublicacion compatibleEntity = alertasList.get(i).getCompatibleCon();
        if (compatibleEntity.getId() > 0) {
            String valor = PublicacionAtributos.getInstancia().getCompatibleCon(compatibleEntity).getValor();
            if (!valor.equals("No aplica") && !valor.isEmpty())
                infoAdicional += "Que " + alertaView.getContext().getString(R.string.sea_compatible) + " " + valor.toLowerCase() + ". ";
        }

        AtributoPublicacion energiaEntity = alertasList.get(i).getEnergia();
        if (energiaEntity.getId() > 0) {
            String valor = PublicacionAtributos.getInstancia().getEnergia(energiaEntity).getValor();
            if (!valor.equals("No aplica") && !valor.isEmpty())
                infoAdicional += "Sea " + valor.toLowerCase() + ". ";
        }

        AtributoPublicacion proteccionEntity = alertasList.get(i).getProteccion();
        if (proteccionEntity.getId() > 0) {
            String valor = PublicacionAtributos.getInstancia().getProteccion(proteccionEntity).getValor();
            if (!valor.equals("No aplica") && !valor.isEmpty())
                infoAdicional += "Sea " + valor.toLowerCase() + ". ";
        }

        infoAdicional+= alertaView.getContext().getString(R.string.a_distancia) + " " + alertasList.get(i).getDistancia() + "km.";

        return infoAdicional;
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
