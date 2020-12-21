package com.example.frbase.paketku;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.frbase.R;

import java.util.List;

//buat methode Adapterdataku,klik kanan pada Adapterdataku lalu generate,implements method.
// buat methode ViewHooldeer
//klik kanan pada AdapterDataku lali generate.konstruktor
public class AdapterDataku extends RecyclerView.Adapter<AdapterDataku.ViewHolder> {
    Context context;
    List<Dataku> list;

    OnCallBack onCallBack;
    //buat setter untuk Oncallback


    public void setOnCallBack(OnCallBack onCallBack) {
        this.onCallBack = onCallBack;
    }

    public AdapterDataku(Context context, List<Dataku> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_data_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.listView.setText(list.get(position).getIsi());

        holder.tblHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCallBack.onTblHapus(list.get(position));
            }
        });

        holder.tblEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCallBack.onTblEdit(list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    //Deklarasikan id yang ada di list_data_layout

    //KLIK KANAN pada viewholder lalu generate,constructor
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView listView;
        ImageButton tblHapus, tblEdit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            listView = itemView.findViewById(R.id.list_view);
            tblHapus = itemView.findViewById(R.id.tbl_hapus);
            tblEdit = itemView.findViewById(R.id.tbl_edit);
        }
    }
    public interface OnCallBack{
        void onTblHapus(Dataku dataku);
        void onTblEdit(Dataku dataku);
    }
}


