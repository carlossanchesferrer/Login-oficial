package com.carlossanchez.loging;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SesionFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener{

    RequestQueue rq;
    JsonRequest jrq;
    EditText cajaUser, cajaPwd;
    Button btnConsultar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_sesion, container, false);
        View vista = inflater.inflate(R.layout.fragment_sesion, container, false);



        cajaUser = vista.findViewById(R.id.usu1);
        cajaPwd = vista.findViewById(R.id.pass);
        btnConsultar=vista.findViewById(R.id.bt1);
        rq = Volley.newRequestQueue(getContext());

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciarSesion();
            }
        });

        return vista;
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(),"no encontrado" +error.toString(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        User usuario =new User();
        Toast.makeText(getContext(),"usuario encontrado" +cajaUser.getText().toString(),Toast.LENGTH_LONG).show();



        JSONArray jsonArray = response.optJSONArray("datos");
        JSONObject jsonObject = null;

        try {
            jsonObject = jsonArray.getJSONObject(0);
            usuario.setUser(jsonObject.optString("user"));
            usuario.setPwd(jsonObject.optString("pwd"));
            usuario.setNames(jsonObject.optString("names"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void iniciarSesion(){
        String url ="http://192.168.1.34/login/sesion.php?user="+cajaUser.getText().toString()+
                "&pwd="+cajaPwd.getText().toString();

        jrq = new JsonObjectRequest(Request.Method.GET, url, (String) null,this,this);
        rq.add(jrq);



    }
}
