package com.example.sarapavas.lab6;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText eNombre, eCantidad, ePrecio;
    private TextView tMostrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Peluchitos peluches = new Peluchitos(this);

        eNombre = (EditText) findViewById(R.id.eNombre);
        eCantidad = (EditText) findViewById(R.id.eCantidad);
        ePrecio = (EditText) findViewById(R.id.ePrecio);
        tMostrar = (TextView) findViewById(R.id.tVista);
        /**/

        //SQLiteDatabase db = peluches.getWritableDatabase();


        //VerTabla();
        //db.close();
    }
    public void agregar (View view) {
        Peluchitos peluches = new Peluchitos(this);
        SQLiteDatabase bd = peluches.getWritableDatabase();

        String nombre = eNombre.getText().toString();
        String cantidad = eCantidad.getText().toString();
        String precio = ePrecio.getText().toString();
        if(nombre.isEmpty()){
            Toast.makeText(this, "Indrozca un Nombre" ,
                    Toast.LENGTH_SHORT).show();}

        else {
            Cursor c = bd.rawQuery("SELECT ident FROM Peluches", null);
            int Ident;

            ContentValues registro = new ContentValues();//Es para guardar los datos ingresados
            registro.put("nombre", nombre);//tag debe aparecer igual que en la clase BaseDeDatos
            if (c.moveToLast()) {
                int pIdent = c.getInt(0) + 1;
                Ident = pIdent;
                registro.put("ident", Ident);
            }

            registro.put("cantidad", cantidad);
            registro.put("precio", precio);
            registro.put("venta", 0);
            bd.insert("Peluches", null, registro);
            bd.close();// cerrar para que guarde

            eNombre.setText("");
            eCantidad.setText("");
            ePrecio.setText("");
            Toast.makeText(this, "Se guardaron los datos del peluche",
                    Toast.LENGTH_SHORT).show();
        }

    }
    public void eliminar (View v) {
        Peluchitos peluches = new Peluchitos(this);
        SQLiteDatabase bd = peluches.getWritableDatabase();
        String[] nombre = new String[1];
        nombre[0]=eNombre.getText().toString();
        if(nombre[0].isEmpty()){
            Toast.makeText(this, "Indrozca un Nombre" ,
                    Toast.LENGTH_SHORT).show();}

        else {
            bd.execSQL("DELETE FROM Peluches WHERE nombre=?", nombre);
            //bd.delete("Peluches", "nombre=?" + nombre, null);
            bd.close();
            eNombre.setText("");
            eCantidad.setText("");
            ePrecio.setText("");
        }

    }
    public void buscar (View view){
        Peluchitos peluches = new Peluchitos(this);
        SQLiteDatabase bd = peluches.getWritableDatabase();

        String inombre = eNombre.getText().toString();
        String[] campos = new String[]{"nombre","ident","cantidad","precio"};
        String[] args = new String[]{inombre};
        Cursor c= bd.query("Peluches", campos, "nombre=?", args, null, null, null);
        if(inombre.isEmpty()){
            Toast.makeText(this, "Indrozca un Nombre" ,
                    Toast.LENGTH_SHORT).show();}

        else{

            if(c.moveToFirst()==true) {
                tMostrar.setText("Id-Nombre-Cantidad-Precio\n");
                do {

                    String nombre = c.getString(0);
                    int ident = c.getInt(1);
                    int cantidad = c.getInt(2);
                    int precio = c.getInt(3);

                    tMostrar.append("" + ident + "--" + nombre + "--" + cantidad + "--" + precio + "\n");
                }while (c.moveToNext());
            }
        // se obtuvo un cursor con 0 filas entrantes
            else {
                Toast.makeText(this, "No existe estudiante con dicha identificaion" ,
                    Toast.LENGTH_SHORT).show();}
        }



        bd.close();
    }
    public void actualizar(View v) {
        Peluchitos peluches = new Peluchitos(this);
        SQLiteDatabase bd = peluches.getWritableDatabase();

        String cantidad = eCantidad.getText().toString();
        String nombre = eNombre.getText().toString();

        String[] args = new String[]{nombre};
        if(nombre.isEmpty()){
            Toast.makeText(this, "Indrozca un Nombre" ,
                    Toast.LENGTH_SHORT).show();}

        else {
            ContentValues registro = new ContentValues();
            registro.put("cantidad", cantidad);
            int cant = bd.update("Peluches", registro, "nombre=?", args);
            bd.close();
            if (cant == 1)
                Toast.makeText(this, "se modificaron los datos", Toast.LENGTH_SHORT)
                        .show();
            else
                Toast.makeText(this, "no existe estudiante con dicho documento",
                        Toast.LENGTH_SHORT).show();
        }
    }
    public void ganancias(View view){
        Peluchitos peluches = new Peluchitos(this);
        SQLiteDatabase db = peluches.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT precio,venta FROM Peluches", null);

        int ganancias=0;
        tMostrar.setText("");
        if(c.moveToFirst()){
            do{
                int precio = c.getInt(0);
                int venta =c.getInt(1);

                ganancias+=precio*venta;

            }while(c.moveToNext());
        }
        tMostrar.setText("Las Ganancias Totales Son: $"+ganancias);
    }
    public void ventas(View view){
        Peluchitos peluches = new Peluchitos(this);
        SQLiteDatabase db = peluches.getWritableDatabase();
        String inombre = eNombre.getText().toString();
        String[] campos = new String[]{"nombre","cantidad","venta"};
        String[] args = new String[]{inombre};
        Cursor c= db.query("Peluches", campos, "nombre=?", args, null, null, null);
        if(inombre.isEmpty()){
            Toast.makeText(this, "Indrozca un Nombre" ,
                    Toast.LENGTH_SHORT).show();}

        else {
            if (c.moveToFirst()) {
                do {
                    String nombre = c.getString(0);
                    int cantidad = c.getInt(1) - 1;
                    int venta = c.getInt(2) + 1;
                    if (cantidad >= 0) {
                        if (cantidad <= 5) {
                            String title = "Quedan Menos de 5 unidades", content = nombre, ticker = ":P";
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());


                            builder.setContentTitle(title).setContentText(content).setTicker(ticker).setSmallIcon(R.mipmap.ic_launcher).setContentInfo("Peluchitos");

                            Intent notIntent = new Intent(MainActivity.this, MainActivity.class);

                            PendingIntent contIntent = PendingIntent.getActivity(MainActivity.this, 0, notIntent, 0);

                            builder.setContentIntent(contIntent);

                            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                            nm.notify(1, builder.build());
                        }
                        ContentValues registro = new ContentValues();
                        registro.put("cantidad", cantidad);
                        registro.put("venta", venta);
                        int cant = db.update("Peluches", registro, "nombre=?", args);
                        if (cant == 1)
                            Toast.makeText(this, "Venta exitosa", Toast.LENGTH_SHORT)
                                    .show();
                        else
                            Toast.makeText(this, "No se pudo realizar venta, no se encuentra",
                                    Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(this, "Peluche Agotado",
                                Toast.LENGTH_SHORT).show();


                } while (c.moveToNext());
            }
        }


    }
    public void mostrar(View view){
        Peluchitos peluches = new Peluchitos(this);
        SQLiteDatabase db = peluches.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT nombre,ident,cantidad,precio,venta FROM Peluches", null);

        tMostrar.setText("");
        tMostrar.setText("Id-Nombre-Cantidad-Precio-Vendidos\n");
        if(c.moveToFirst()){
            do{
                String nombre = c.getString(0);
                int ident = c.getInt(1);
                int cantidad = c.getInt(2);
                int precio = c.getInt(3);
                int venta =c.getInt(4);

                tMostrar.append(""+ident+"--"+nombre+"--"+cantidad+"--"+precio+"--"+venta+"\n");

            }while(c.moveToNext());

        }
        db.close();

    }
}
