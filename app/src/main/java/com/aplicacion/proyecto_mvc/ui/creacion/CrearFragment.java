package com.aplicacion.proyecto_mvc.ui.creacion;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.aplicacion.proyecto_mvc.BD.BDHelper;
import com.aplicacion.proyecto_mvc.BD.Transaccion;
import com.aplicacion.proyecto_mvc.R;
import com.basgeekball.awesomevalidation.AwesomeValidation;

public class CrearFragment extends Fragment {

    Button btncrear;
    EditText etnombre, etapellido, etedad, etdireccion;
    Spinner sppuesto;
    ArrayAdapter adp;
    AwesomeValidation awesomenValitation;
    String Puesto;
    private static final String DEFAULT_POST = "Seleccione";
    String[] sspuestos = {"Seleccione","Director Ejecutivo","Jefe de Ventas","Director de Marketing","Director de Recursos Humanos"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crear,container, false);

        btncrear = (Button)view.findViewById(R.id.btncrear);
        etnombre = (EditText)view.findViewById(R.id.ettnombre);
        etapellido = (EditText)view.findViewById(R.id.ettapellido);
        etedad = (EditText)view.findViewById(R.id.ettedad);
        etdireccion = (EditText)view.findViewById(R.id.ettdireccion);
        sppuesto = (Spinner)view.findViewById(R.id.spppuesto);

        sppuesto.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, sspuestos));

        sppuesto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Puesto = (String) sppuesto.getAdapter().getItem(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btncrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aggempleados();
            }
        });

        return view;
    }


    private void aggempleados() {
        BDHelper conexion = new BDHelper(getActivity(), Transaccion.NameDataBase, null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(Transaccion.nombre, etnombre.getText().toString());
        valores.put(Transaccion.apellido, etapellido.getText().toString());
        valores.put(Transaccion.edad, etedad.getText().toString());
        valores.put(Transaccion.direccion, etdireccion.getText().toString());
        valores.put(Transaccion.puesto, Puesto);

        Long resultado = db.insert(Transaccion.tabla_empleados, Transaccion.id, valores);

        //Toast
        Toast.makeText(getActivity().getApplicationContext(), "REGISTRO INGRESADO: " + resultado.toString(), Toast.LENGTH_LONG).show();


        db.close();

    }



}