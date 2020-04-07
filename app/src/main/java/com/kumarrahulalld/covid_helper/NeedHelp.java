package com.kumarrahulalld.covid_helper;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

import java.util.ArrayList;
import java.util.List;

import io.opencensus.internal.Utils;

public class NeedHelp extends AppCompatActivity {
String ph="";
String vu="";
    List<String> v;
    List<String> vv;
    double lat,lon;
    FirebaseAuth mauth;
    FirebaseUser us;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_help);
        ph=getIntent().getStringExtra("phone");
        lat=Double.parseDouble(getIntent().getStringExtra("Lat"));
        lon=Double.parseDouble(getIntent().getStringExtra("Lon"));
mauth=FirebaseAuth.getInstance();
us=mauth.getCurrentUser();
        DatabaseReference u = FirebaseDatabase.getInstance().getReference();
        u.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                getdata(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void request(View view){
        EditText nm=(EditText)findViewById(R.id.Name);
        EditText ds=(EditText)findViewById(R.id.desc);
        if(nm.getText().toString().equals(""))
        {
            Toast.makeText(this,"Name Field Is Empty.",Toast.LENGTH_LONG).show();
        }
        else if(ds.getText().toString().equals(""))
        {
            Toast.makeText(this,"Description Field Is Empty.",Toast.LENGTH_LONG).show();

        }
        else if(ds.getText().length()<100)
        {
            Toast.makeText(this,"Description Is Very Short.",Toast.LENGTH_LONG).show();

        }
        else if(!(nm.getText().toString().matches("^[a-zA-Z\\s]*$")))
        {
            Toast.makeText(this,"Name Is Invalid Only Spaces And Letters Are Allowed.",Toast.LENGTH_LONG).show();

        }
        else if(v.size()>1)
        {
            String help="";
            String helpid="";
            DatabaseReference f= FirebaseDatabase.getInstance().getReference(ph+"/Need");
            DatabaseReference f1=f.push();
            f1.child("Name").setValue(nm.getText().toString());
            f1.child("Description").setValue(ds.getText().toString());
            f1.child("Email").setValue(vu+"");
            f1.child("Phone").setValue(ph+"");
            f1.child("Latitude").setValue(lat+"");
            f1.child("Longitude").setValue(lon+"");
            f1.child("Status").setValue("0");
            for(int i=0;i<v.size();i++)
            {
                DatabaseReference f2=FirebaseDatabase.getInstance().getReference(v.get(i)+"/Will");
                DatabaseReference f3=f2.push();
                help+=v.get(i);
                help+="$";
                helpid+=f3.getKey();
                helpid+="$";
                f3.child("Name").setValue(nm.getText().toString());
                f3.child("Description").setValue(ds.getText().toString());
                f3.child("Email").setValue(vu+"");
                f3.child("Phone").setValue(ph+"");
                f3.child("Latitude").setValue(lat+"");
                f3.child("Longitude").setValue(lon+"");
                f3.child("Status").setValue("0");
                BackgroundMail.newBuilder(this).withUsername("kumarrahul.allduniv@gmail.com")
                        .withPassword("Rm@1749001").withMailto(vv.get(i))
                        .withType(BackgroundMail.TYPE_PLAIN)
                        .withSubject("IMPORTANT COVID-HELPER NOTIFICATION :- Someone Needs Your Help Near You.")
                .withBody("Hi User, Greetings From COVID-19 Hepler. \n"+nm.getText().toString()+"  Needs Your Help In Your Area , Have A Look On Problem.\n Login Into COVID-19 Hepler Application And Open Willing To Help Section.\n You Will Find All The Details There.\n Thank You ,\n COVID-19 Helper Team.")
                  .withProcessVisibility(false)
                        .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                @Override
                public void onSuccess() {
                    //do some magic
                }
            })
                    .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                        @Override
                        public void onFail() {
                            Toast.makeText(NeedHelp.this,"Sorry ! Some Of User's In Your Area Can't Be Contacted Right Now , But Contacting Others Don't Worry.",Toast.LENGTH_LONG).show();
                        }
                    })
                    .send();
            }
            help+=ph+"$";
            helpid+=f1.getKey()+"$";
            f1.child("Helpers").setValue(help+"");
            f1.child("HelpersId").setValue(helpid+"");
            Toast.makeText(NeedHelp.this,"Request Submitted Successfully.",Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this,"No Helper Available Near You. Try Later .",Toast.LENGTH_LONG).show();
        }

    }
    public void getdata(DataSnapshot d) {
        v = new ArrayList<>();
        vv = new ArrayList<>();
        double lat2,lon2;
        for (DataSnapshot dt : d.getChildren()) {
            if(dt!=null && dt.child("Latitude").getValue()!=null && dt.child("Longitude").getValue()!=null){
                if(dt.getKey().equals(ph))
                {
                vu=dt.child("Email").getValue().toString();
                }
                else {
                    lat2 = Double.parseDouble(dt.child("Latitude").getValue().toString());
                    lon2 = Double.parseDouble(dt.child("Longitude").getValue().toString());
                    if (distance(lat, lat2, lon, lon2)) {
                        v.add(dt.getKey());
                        vv.add(dt.child("Email").getValue().toString());
                    }
                }
            }
            }
        }

    public boolean distance(double lat1,double lat2, double lon1,double lon2) {
        lon1 = Math.toRadians(lon1);
        lon2 = Math.toRadians(lon2);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2), 2);

        double c = 2 * Math.asin(Math.sqrt(a));
        double r = 6371;

        // calculate the result
        return (c * r)<20;
    }
    }
