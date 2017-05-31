package zero.management;

import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Client extends AppCompatActivity {

    private Button denglu;
    private EditText editText_id;
    private EditText getEditText_password;
    private Person person;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client);

        denglu=(Button)findViewById(R.id.Main_Button);
        editText_id=(EditText)findViewById(R.id.Main_Edit_ID);
        getEditText_password=(EditText)findViewById(R.id.Main_Edit_Password);

        denglu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=editText_id.getText().toString();
                String password=getEditText_password.getText().toString();
                if(id.equals("")){
                    Toast toast = Toast.makeText(Client.this, "输入的帐号有误", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, 100);
                    toast.show();
                } else if((id.equals("88888888"))&&(password.equals("password"))){
                    Intent i=new Intent(Client.this,Ad_List.class);
                    startActivity(i);
                }else {
                    person = Person_Lib.getPersonLib(Client.this).getPerson(Integer.parseInt(id));
                    if (person == null) {
                        Toast toast = Toast.makeText(Client.this, "输入的帐号有误", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.BOTTOM, 0, 100);
                        toast.show();
                    } else {
                        if (password.equals(person.getPassword())) {
                            Intent i=Pager.newInstance(Client.this,Integer.parseInt(id),0);
                            startActivity(i);
                        }else {
                            Toast toast = Toast.makeText(Client.this, "输入的密码有误", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.BOTTOM, 0, 100);
                            toast.show();
                        }
                    }
                }
            }
        });

    }
}
