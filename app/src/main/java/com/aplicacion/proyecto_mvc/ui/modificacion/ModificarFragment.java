package com.aplicacion.proyecto_mvc.ui.modificacion;

import android.content.ContentValues;
import android.database.Cursor;
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

public class ModificarFragment extends Fragment {

    Button btnactualizar,btnbuscar, btneliminar;
    EditText id, ettnombre, ettapellido, ettedad, ettdireccion;
    Spinner spppuesto;
    BDHelper conexion;
    String Puesto;
    private static final String DEFAULT_POST = "Seleccione";
    String[] ssppuestos = {"Seleccione","Director Ejecutivo","Jefe de Ventas","Director de Marketing","Director de Recursos Humanos"};


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modificar,container, false);

        spppuesto = (Spinner)view.findViewById(R.id.spppuesto);
        btnactualizar = (Button)view.findViewById(R.id.btnactualizar);
        btnbuscar = (Button)view.findViewById(R.id.btnbuscar);
        btneliminar = (Button)view.findViewById(R.id.btneliminar);
        id = (EditText)view.findViewById(R.id.txtid);
        ettnombre = (EditText)view.findViewById(R.id.ettnombre);
        ettapellido = (EditText)view.findViewById(R.id.ettapellido);
        ettedad = (EditText)view.findViewById(R.id.ettedad);
        ettdireccion = (EditText)view.findViewById(R.id.ettdireccion);

        spppuesto.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, ssppuestos));

        spppuesto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Puesto = (String) spppuesto.getAdapter().getItem(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        conexion= new BDHelper(getActivity(), Transaccion.NameDataBase, null, 1);

        btnbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Buscar();


            }
        });

        btneliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar();

            }
        });

        btnactualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizar();


            }
        });

        return view;
    }


    private void Buscar() {

        SQLiteDatabase db = conexion.getWritableDatabase();

        /* Paramentros de configuracion de la sentencia SELECT */
        String[] params = {id.getText().toString()};//parametro de la busqueda
        String[] fields= {Transaccion.nombre,
                Transaccion.apellido,
                Transaccion.edad,
                Transaccion.direccion,
                Transaccion.puesto};

        String wherecond = Transaccion.id + "=?";

        try {
            Cursor cdata = db.query(Transaccion.tabla_empleados, fields, wherecond, params, null, null, null);
            cdata.moveToFirst();
            ettnombre.setText(cdata.getString(0));
            ettapellido.setText(cdata.getString(1));
            ettedad.setText(cdata.getString(2));
            ettdireccion.setText(cdata.getString(3));
            //sppuesto.setText(cdata.getString(4));

            Toast.makeText(getActivity(), "Consultado con exito", Toast.LENGTH_LONG).show();
        }
        catch (Exception ex){

            ClearScreen();
            Toast.makeText(getActivity(),"Elemento no encontrado",Toast.LENGTH_LONG).show();
        }
    }



    private void eliminar() {
        SQLiteDatabase db = conexion.getWritableDatabase();
        String []  params = {id.getText().toString()};
        String wherecond = Transaccion.id + "=?";
        db.delete(Transaccion.tabla_empleados, wherecond, params);
        Toast.makeText(getActivity(),"Dato eliminado", Toast.LENGTH_LONG).show();
        ClearScreen();

    }

    private void actualizar() {
        SQLiteDatabase db = conexion.getWritableDatabase();
        String []  params = {id.getText().toString()};

        ContentValues valores = new ContentValues();
        valores.put(Transaccion.nombre, ettnombre.getText().toString());
        valores.put(Transaccion.apellido, ettapellido.getText().toString());
        valores.put(Transaccion.edad, ettedad.getText().toString());
        valores.put(Transaccion.direccion, ettdireccion.getText().toString());
        valores.put(Transaccion.puesto, Puesto.toString());

        db.update(Transaccion.tabla_empleados, valores, Transaccion.id +"=?", params);
        Toast.makeText(getActivity(),"Dato actualizados", Toast.LENGTH_LONG).show();
        ClearScreen();

    }


    private void ClearScreen()
    {
        id.setText("");
        ettnombre.setText("");
        ettapellido.setText("");
        ettedad.setText("");
        ettdireccion.setText("");


    }
}