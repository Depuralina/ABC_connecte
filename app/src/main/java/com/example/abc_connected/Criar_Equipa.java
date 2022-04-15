package com.example.abc_connected;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.load.model.Model;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.internal.cache.DiskLruCache;

public class Criar_Equipa extends AppCompatActivity {
    private ArrayList adpt;
    private HashMap hash;
    private ListView listviewData;
    private ArrayAdapter adapter;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference().child("Atletas");
    protected void onCreate(Bundle savedinstance){
        super.onCreate(savedinstance);
        setContentView(R.layout.activity_criar_equipa);
        listviewData = findViewById(R.id.window_list);
        adpt = new ArrayList<>();
        hash = new HashMap();
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    hash = (HashMap) dataSnapshot.getValue();
                    adpt.add("Nome: "+hash.get("Nome")+"\n"+"Escalao: "+hash.get("Escalao"));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice,adpt);
        listviewData.setAdapter(adapter);
    }
    public boolean onCreateOptionsMenu(Menu menu){
         getMenuInflater().inflate(R.menu.main_menu, (android.view.Menu) menu);
        return true;
        //return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int id = item.getItemId();
        if(id == R.id.item_done){
            String itemSelected = "Selected item: \n";
            for(int i=0;i<listviewData.getCount();i++){
                if(listviewData.isItemChecked(i)){
                    itemSelected += listviewData.getItemAtPosition(i)+ "\n";
                }
            }
            Toast.makeText(this,itemSelected, Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
