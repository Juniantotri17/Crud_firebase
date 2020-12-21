package com.example.frbase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.frbase.paketku.AdapterDataku;
import com.example.frbase.paketku.Dataku;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
   //Deklarasi fungsi dari floating dan recycle
    FloatingActionButton tblData;
    RecyclerView recycleView;
    //Deklarasi untuk firebase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");

    List<Dataku> list = new ArrayList<>();
    AdapterDataku adapterDataku;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //panggil id fungsi dari layout
        tblData = findViewById(R.id.tombol_data);
        recycleView = findViewById(R.id.recycle_view);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        //setting tombol floating
        tblData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogTambahData();
            }
        });
        bacaData();
    }

    private void bacaData() {
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                //menampilkan data dari list
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Dataku value = snapshot.getValue(Dataku.class);
                    list.add(value);
                }
                adapterDataku = new AdapterDataku(MainActivity.this,list);
                recycleView.setAdapter(adapterDataku);

                setClick();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }

    private void setClick() {
    adapterDataku.setOnCallBack(new AdapterDataku.OnCallBack() {
        @Override
        public void onTblHapus(Dataku dataku) {
            hapusData(dataku);
        }

        @Override
        public void onTblEdit(Dataku dataku) {
        showDialogEditData(dataku);
        }
    });
    }

    private void showDialogEditData(final Dataku dataku) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.tambah_data_layout);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //untuk menutup kembali layot yang sudah dipanggil
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
        //// Sampai sini adalah fungsi agar ketika clik floating diarahkan ke tambah data layout

        //fungsi untuk ketika tombol x ditekan akan kembali ke halaman awal
        ImageButton tblKeluar = dialog.findViewById(R.id.tbl_keluar);
        tblKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // Deklarasikan fungsi tambah data dan tombol tambah
        final EditText Tambah = dialog.findViewById(R.id.tambah_data);
        final Button tblTambah = dialog.findViewById(R.id.tbl_tambah);
        Tambah.setText(dataku.getIsi());

        TextView tvTambah = dialog.findViewById(R.id.tv_tambah);

        Tambah.setText(dataku.getIsi());
        tblTambah.setText("Update");
        tvTambah.setText("Edit Data");

        //fungsi untuk isi tambah data dan tombol data
        tblTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(Tambah.getText())){
                    tblTambah.setError("Silahkan Isi Data");
                }else {
                    editData(dataku, Tambah.getText().toString());
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    private void editData(Dataku dataku, String baru) {
        myRef.child(dataku.getKunci()).child("isi").setValue(baru).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Update Berhasil",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void hapusData(final Dataku dataku) {
        myRef.child(dataku.getKunci()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(getApplicationContext(),dataku.getIsi()+ "Berhasil menambahkan data",Toast.LENGTH_SHORT).show();
            }
        });
    }

    //fungsi agar ketika clik floating diarahkan ke tambah data layout
    private void showDialogTambahData() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.tambah_data_layout);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //untuk menutup kembali layot yang sudah dipanggil
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
        //// Sampai sini adalah fungsi agar ketika clik floating diarahkan ke tambah data layout

        //fungsi untuk ketika tombol x ditekan akan kembali ke halaman awal
        ImageButton tblKeluar = dialog.findViewById(R.id.tbl_keluar);
        tblKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // Deklarasikan fungsi tambah data dan tombol tambah
        final EditText Tambah = dialog.findViewById(R.id.tambah_data);
        final Button tblTambah = dialog.findViewById(R.id.tbl_tambah);


        //fungsi untuk isi tambah data dan tombol data
        tblTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(Tambah.getText())){
                    tblTambah.setError("Silahkan Isi Data");
                }else {
                    simpanData(Tambah.getText().toString());
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }
    //fungsi untuk kirim data ke database
    private void simpanData(String s) {
        String kunci = myRef.push().getKey();
        Dataku dataku = new Dataku(kunci,s);

        myRef.child(kunci).setValue(dataku).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Berhasil menambahkan data",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
