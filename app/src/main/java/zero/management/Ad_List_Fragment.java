package zero.management;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Aiy on 2016/12/4.
 */

public class Ad_List_Fragment extends Fragment {
    private RecyclerView recyclerView;
    private PersonAdapter adapter;
    private static final int REQUEST=0;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.list_recycler,container,false);
        recyclerView=(RecyclerView)view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        update();
        return view;
    }

    public void update(){
        Person_Lib person_lib=Person_Lib.getPersonLib(getActivity());
        List<Person> list=person_lib.getList();
        if (adapter==null){
            adapter=new PersonAdapter(list);
            recyclerView.setAdapter(adapter);
        }else {
            adapter.setList(list);
            adapter.notifyDataSetChanged();
    }
    }

    private class PersonHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView num;
        private Person person;
        public PersonHolder(View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.list_ex_name);
            num=(TextView)itemView.findViewById(R.id.list_ex_num);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent=Pager.newInstance(getActivity(),person.getNum(),1);
                    startActivityForResult(intent,1);
                }
            });
        }
        public void bingPerson(Person person){
            this.person=person;
            name.setText("姓名："+person.getName());
            num.setText("学号："+person.getNum());
        }
    }

    private class PersonAdapter extends RecyclerView.Adapter<PersonHolder>{
        private List<Person> list;

        public PersonAdapter(List<Person> persons){
            this.list=persons;
        }

        public void setList(List<Person> persons){
            this.list=persons;
        }
        @Override
        public PersonHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater=LayoutInflater.from(getActivity());
            View view=layoutInflater.inflate(R.layout.list_ex,parent,false);
            return new PersonHolder(view);
        }

        @Override
        public void onBindViewHolder(PersonHolder holder, int position) {
            Person person=list.get(position);
            holder.bingPerson(person);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_list_ex,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_list_ex_new_person:
                Person person=new Person();
                Person_Lib.getPersonLib(getActivity()).addPerson(person);
                Intent i=Pager.newInstance(getActivity(),person.getNum(),1);
                startActivity(i);
                return true;
            case R.id.menu_list_ex_delete_person:
                FragmentManager fm=getFragmentManager();
                DeleteFragment delete=new DeleteFragment();
                delete.setTargetFragment(Ad_List_Fragment.this,REQUEST);
                delete.show(fm,"tag");
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        update();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode!= Activity.RESULT_OK){
            return;
        }
        if (requestCode == REQUEST){
            update();
        }
    }
}
