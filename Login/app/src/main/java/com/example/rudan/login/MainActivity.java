package com.example.rudan.login;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity{

    private EditText email;
    private EditText password;
    String emailString;
    String passwordString;
    private Button btnLogin;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Toast.makeText(context, "Mensagem recebida", Toast.LENGTH_SHORT).show();
            btnLogin.setEnabled(true);
            btnLogin.setText("Acessar");

            Integer code = intent.getIntExtra("code", 403);
            if(code == 200){
                Toast.makeText(context, "Logado com Sucesso", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(context, "Falha no Login", Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(context, "code: "+code, Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = findViewById(R.id.textEmail);
        password = findViewById(R.id.textPassword);
        btnLogin = findViewById(R.id.btnLogin);

        registerReceiver(receiver, new IntentFilter("LOGAR"));
    }

    public void logar(View view){
        btnLogin.setText("aguarde");
        btnLogin.setEnabled(false);
        emailString  =  email.getText().toString();
        passwordString  =  password.getText().toString();

        Intent intent = new Intent(this, HelloService.class);
        intent.putExtra("email", emailString);
        intent.putExtra("password", passwordString);
        startService(intent);
        Toast.makeText(this, "intent enviada", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

}
