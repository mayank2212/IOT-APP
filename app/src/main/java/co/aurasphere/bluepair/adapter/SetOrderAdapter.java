package co.aurasphere.bluepair.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.List;

import co.aurasphere.bluepair.R;
import co.aurasphere.bluepair.model.SetOrderModel;

public class SetOrderAdapter extends RecyclerView.Adapter<SetOrderAdapter.ViewHolder> {
    private Context context;
    private List<SetOrderModel> list;

    public SetOrderAdapter(Context context, List<SetOrderModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_set_order,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        viewHolder.tvSetOrderName.setText(list.get(i).getSetOrderName());
        viewHolder.tvTimer.setText(list.get(i).getSetOrderTimer());
        final int pos = i;
        viewHolder.seekSetOrder.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                viewHolder.tvTimer.setText(String.valueOf(i));
                list.get(pos).setSetOrderTimer(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvSetOrderName;
        private TextView tvTimer;
        private SeekBar seekSetOrder;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSetOrderName=itemView.findViewById(R.id.custom_set_order_tv);
            tvTimer=itemView.findViewById(R.id.custom_set_order_timer);
            seekSetOrder=itemView.findViewById(R.id.custom_set_order_seek_bar);
        }
    }
}
