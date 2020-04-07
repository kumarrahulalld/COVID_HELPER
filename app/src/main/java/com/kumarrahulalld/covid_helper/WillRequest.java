package com.kumarrahulalld.covid_helper;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.TextLinks;
import android.widget.Button;
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

public class WillRequest extends AppCompatActivity {
    RecyclerView rv;
    String ph="";
    DatabaseReference mdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_will_request);
        ph=getIntent().getStringExtra("Phone");
        rv=(RecyclerView) findViewById(R.id.recyle1);
        mdb=FirebaseDatabase.getInstance().getReference(ph.toString()+"/Will");
        mdb.keepSynced(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }
    @Override
    protected void onStart(){
        super.onStart();
        FirebaseRecyclerOptions<Requests> options=new FirebaseRecyclerOptions.Builder<Requests>().setQuery(mdb,Requests.class).build();
        FirebaseRecyclerAdapter<Requests,RequestHolder>firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Requests,RequestHolder>(options) {
            @NonNull
            @Override
            public RequestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.willcard, parent, false);
                RequestHolder r=new RequestHolder(view);
                return r;
            }

            @Override
            protected void onBindViewHolder(@NonNull RequestHolder holder, int position, @NonNull final Requests model) {
               if(model!=null && model.getStatus()!=null) {
                   if (model.getStatus().equals("0")) {
                       holder.nm.setText(model.getName());
                       holder.des.setText(model.getDescription());
                       holder.loc.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               String strUri = "http://maps.google.com/maps?q=loc:" + model.getLatitude() + "," + model.getLongitude() + " (" + "Needs Your Help Here." + ")";

                               Intent l = new Intent(Intent.ACTION_VIEW, Uri.parse(strUri));
                               l.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                               startActivity(l);
                           }
                       });
//                holder.m.getMapAs;
                       holder.emwill.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + model.getEmail().toString()));
                               startActivity(emailIntent);
                           }
                       });

                       holder.phwill.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + model.getPhone().toString()));
                               startActivity(intent);
                           }
                       });

                   }
                   else
                   {
                       holder.itemView.setVisibility(View.GONE);
                       holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));

                       // holder.phwill.setVisibility(View.GONE);
                      // holder.emwill.setVisibility(View.GONE);
                      // holder.loc.setVisibility(View.GONE);
                      // holder.nm.setVisibility(View.GONE);
                      // holder.des.setVisibility(View.GONE);
                   }
               }
            }

        };
        rv.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }
    public static class RequestHolder extends RecyclerView.ViewHolder{
        TextView nm,des;
        Button loc,emwill,phwill;
        public RequestHolder(View itemView){
            super(itemView);
            nm=itemView.findViewById(R.id.willname);
            des=itemView.findViewById(R.id.willdesc1);
            loc=itemView.findViewById(R.id.locbtn);
            emwill=itemView.findViewById(R.id.embtn);
            phwill=itemView.findViewById(R.id.phbtn);

        }
    }
}
