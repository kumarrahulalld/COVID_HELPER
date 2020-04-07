package com.kumarrahulalld.covid_helper;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.TextLinks;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.maps.MapView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.regex.Pattern;

public class Updates extends AppCompatActivity {
    RecyclerView rv;
    DatabaseReference mdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updates);
        rv=(RecyclerView) findViewById(R.id.recyleup);
        mdb=FirebaseDatabase.getInstance().getReference().child("Updates");
        mdb.keepSynced(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }
    @Override
    protected void onStart(){
        super.onStart();
        FirebaseRecyclerOptions<Update> options=new FirebaseRecyclerOptions.Builder<Update>().setQuery(mdb,Update.class).build();
        FirebaseRecyclerAdapter<Update,RequestHolder>firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Update,RequestHolder>(options) {
            @NonNull
            @Override
            public RequestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.updt, parent, false);
                RequestHolder r=new RequestHolder(view);
                return r;
            }

            @Override
            protected void onBindViewHolder(@NonNull RequestHolder holder, int position, @NonNull Update model) {

                holder.nm.setText(model.getTitle().toString());
                holder.des.setText(model.getDescription().toString());
                Pattern pattern = Pattern.compile(model.getLink().toString());
                Linkify.addLinks(holder.link, pattern, "http://");
                holder.link.setMovementMethod(LinkMovementMethod.getInstance());
                holder.link.setText(Html.fromHtml("<a href='http://"+model.getLink().toString()+"'>Click Here. To Read More ."+"</a>"));
                Picasso.get().load(model.getImage().toString()).into(holder.im);
//                holder.m.getMapAs;


            }

        };
        rv.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }
    public static class RequestHolder extends RecyclerView.ViewHolder{
        TextView nm,des,link;
        ImageView im;
        public RequestHolder(View itemView){
            super(itemView);
            nm=itemView.findViewById(R.id.nameup);
            des=itemView.findViewById(R.id.desc1up);
            link=itemView.findViewById(R.id.linkurl);
            im=itemView.findViewById(R.id.imgup);
        }
    }
}
