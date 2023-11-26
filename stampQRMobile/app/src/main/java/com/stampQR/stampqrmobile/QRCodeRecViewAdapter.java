package com.stampQR.stampqrmobile;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.stampQR.stampqrmobile.model.QRData;
import com.stampQR.stampqrmobile.wrappers.QRDataList;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class QRCodeRecViewAdapter extends RecyclerView.Adapter<QRCodeRecViewAdapter.ViewHolder> {


    String imageUrl = "http://18.133.86.241:8000/images/";

    private static final String TAG = "QRCodeRecViewAdapter";
    List<QRData> qrDataList = new ArrayList<>();
    private Context mContext;


    public QRCodeRecViewAdapter(Context mContext) {

        this.mContext = mContext;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_qrcode, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,
                                 int position) {
        Log.d(TAG, "Called");
        holder.qrTag.setText("Name : " + qrDataList.get(position).getQrDataTag());
        holder.qrEcc.setText("ECC : " + qrDataList.get(position).getErrorCorrectionLevel());
        holder.qrVersion.setText("Version : " + qrDataList.get(position).getVer());//+qrDataList.get(position).getImageURL()
        Glide.with(mContext).asBitmap().load(imageUrl+qrDataList.get(position).getImageURL()).into(holder.qrImg);//"https://static.vecteezy.com/system/resources/thumbnails/007/224/606/small/qr-code-black-color-isolated-on-background-vector.jpg"


        holder.contentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, qrDataList.get(position).getPlainText(), Toast.LENGTH_LONG).show();
            }
        });

        holder.createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, ProcessData.class);
                intent.putExtra("encodedText", qrDataList.get(position).getEncodedText());
                intent.putExtra("plainText", qrDataList.get(position).getPlainText());
                intent.putExtra("imageName", qrDataList.get(position).getImageURL());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return qrDataList.size();
    }

    public void setQrDataList(List<QRData> qrDataList) {
        this.qrDataList = qrDataList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView parent;
        private ImageView qrImg;
        private TextView qrTag;
        private TextView qrEcc;
        private TextView qrVersion;
        private Button createBtn;
        private Button contentBtn;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            qrImg = itemView.findViewById(R.id.qrImg);
            qrTag = itemView.findViewById(R.id.qrTag);
            qrEcc = itemView.findViewById(R.id.qrEcc);
            qrVersion = itemView.findViewById(R.id.qrVersion);
            createBtn = itemView.findViewById(R.id.createBtn);
            contentBtn = itemView.findViewById(R.id.contentBtn);


        }
    }

}
