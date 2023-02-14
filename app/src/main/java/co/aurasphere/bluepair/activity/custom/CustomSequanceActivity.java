package co.aurasphere.bluepair.activity.custom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.aurasphere.bluepair.R;
import co.aurasphere.bluepair.adapter.SetOrderAdapter;
import co.aurasphere.bluepair.model.SetOrderModel;
import co.aurasphere.bluepair.operation.BluetoothOperation;

public class CustomSequanceActivity extends AppCompatActivity {
    private RecyclerView rvSetorder;
    private TextView tvCustomStart;
    private List<SetOrderModel> setOrderModels;
    private SetOrderAdapter setOrderAdapter;
    private ImageView imgBackBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_sequance);
        getSupportActionBar().hide();
        init();
    }
    private void init(){
        tvCustomStart=findViewById(R.id.custom_sequance_start);
        rvSetorder=findViewById(R.id.custom_set_order_rv);
        imgBackBtn=findViewById(R.id.custom_sequence_back_btn);
        imgBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        rvSetorder.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvSetorder.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView
        setOrderModels=new ArrayList<>();
        setOrderModels.add(new SetOrderModel("Sequence 1","10 Min"));
        setOrderModels.add(new SetOrderModel("Sequence 2","10 Min"));
//        setOrderModels.add(new SetOrderModel("Sequence 3","10 Min"));
//        setOrderModels.add(new SetOrderModel("Sequence 4","10 Min"));

        setOrderAdapter=new SetOrderAdapter(CustomSequanceActivity.this,setOrderModels);
        rvSetorder.setAdapter(setOrderAdapter);

        tvCustomStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BluetoothOperation.sendCommand(getCommand());
            }
        });
    }

    String getCommand(){
        StringBuilder command = new StringBuilder("#$HYDROSEQ");
        int pos = 1;
        for (SetOrderModel setOrderModel : setOrderModels){
            command.append("H").append(pos).append(setOrderModel.getSetOrderTimer());
        }
        command.append("$#");
        return command.toString();
    }
}