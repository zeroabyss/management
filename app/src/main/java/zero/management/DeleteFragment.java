package zero.management;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Aiy on 2016/12/11.
 */

public class DeleteFragment extends DialogFragment {
    private EditText et;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v= LayoutInflater.from(getActivity()).inflate(R.layout.delete,null);
        et=(EditText)v.findViewById(R.id.delete_edit_text);
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("请输入要删除的学号")
                .setPositiveButton(android.R.string.ok,new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String s=et.getText().toString();
                        Person_Lib.getPersonLib(getActivity()).Delete(Integer.parseInt(s));
                        sendResult(Activity.RESULT_OK);
                    }
                })
                .create();
    }
    private void sendResult(int resultCode){
        if (getTargetFragment()==null){
            return;
        }
        Intent i=new Intent();
        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,i);
    }

}
