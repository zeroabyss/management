package zero.management;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;

/**
 * Created by Aiy on 2016/12/5.
 */

public class Pager_Fragment extends Fragment{
    private static final String EXTAR_PAGER_NUM="num";
    private static final String EXTAR_PAGER_STU_OR_AD="stu_or_ad";

    private Person person;
    private EditText name;
    private EditText sex;
    private EditText num;
    private EditText password;
    private EditText from;
    private EditText subject;
    private EditText score;
    private int isOK;
    private int saved_num;
    public static Pager_Fragment newInstance(int num,int stu_or_ad){
        Bundle bundle=new Bundle();
        bundle.putSerializable(EXTAR_PAGER_NUM,num);
        bundle.putSerializable(EXTAR_PAGER_STU_OR_AD,stu_or_ad);
        Pager_Fragment fragment=new Pager_Fragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private class Watcher implements TextWatcher{
        private EditText type;
        public Watcher(EditText type){
            this.type=type;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (type==name){
                person.setName(name.getText().toString());
            }
            if (type==sex){
                if(s.toString().equals("男")||s.toString().equals("女")||s.toString().equals("")){
                    person.setSex(sex.getText().toString());
                }else{
                    Toast.makeText(getActivity(),"输入错误",Toast.LENGTH_SHORT).show();
                }
            }
            if (type==password){
                person.setPassword(password.getText().toString());
            }
            if (type==from){
                person.setFrom(from.getText().toString());
            }
            if(type==subject){
                person.setSubject(subject.getText().toString());
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int num=(int)(getArguments().getSerializable(EXTAR_PAGER_NUM));
        person=Person_Lib.getPersonLib(getActivity()).getPerson(num);
        isOK=(int)getArguments().getSerializable(EXTAR_PAGER_STU_OR_AD);
    }

    private void fullText(EditText et,String s){
        if(! (s==null)){
            et.setText(s);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.pager_ex, container, false);

        //设置name选项
        name = (EditText) v.findViewById(R.id.pager_ex_name);
        sex = (EditText) v.findViewById(R.id.pager_ex_sex);
        num = (EditText) v.findViewById(R.id.pager_ex_num);
        password = (EditText) v.findViewById(R.id.pager_ex_password);
        from = (EditText) v.findViewById(R.id.pager_ex_from);
        subject = (EditText) v.findViewById(R.id.pager_ex_subject);
        score = (EditText) v.findViewById(R.id.pager_ex_score);

        if (isOK == 0) {
            name.setEnabled(false);
            sex.setEnabled(false);
            from.setEnabled(false);
            num.setEnabled(false);
            subject.setEnabled(false);
            score.setEnabled(false);
        }

        fullText(name, person.getName());
        fullText(sex, person.getSex());
        fullText(password, person.getPassword());
        fullText(from, person.getFrom());
        fullText(subject, person.getSubject());
        num.setText(person.getNum()+"");
        score.setText(person.getScore()+"");
        saved_num=Integer.parseInt(num.getText().toString());

        name.addTextChangedListener(new Watcher(name));
        sex.addTextChangedListener(new Watcher(sex));
        password.addTextChangedListener(new Watcher(password));
        from.addTextChangedListener(new Watcher(from));
        subject.addTextChangedListener(new Watcher(subject));
        num.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(num.hasFocus()==false){
                    int num1=Integer.parseInt(num.getText().toString());
                    if (num1==0){
                        Toast.makeText(getActivity(),"学号不为0",Toast.LENGTH_SHORT).show();
                    }
                    Person person1=Person_Lib.getPersonLib(getActivity())
                            .getPerson(Integer.parseInt(num.getText().toString()));
                    if(person1!=null&&saved_num != num1){
                        Toast.makeText(getActivity(),"学号已存在请更换",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        return  v;
    }


    @Override
    public void onPause() {
        super.onPause();
        person.setNum(Integer.parseInt(num.getText().toString()));
        person.setScore(Integer.parseInt(score.getText().toString()));
        Person_Lib.getPersonLib(getActivity()).updatePerson(person);
    }

}
