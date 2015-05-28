package sasidhar.azuqua.com.azuquaapitest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.azuqua.androidAPI.model.Flo;

import java.util.Collections;
import java.util.List;

/**
 * Created by BALASASiDHAR on 27-May-15.
 */
public class FlosAdapter extends RecyclerView.Adapter<FlosAdapter.MyFlosHolder> {

    private LayoutInflater layoutInflater;
    private Context mContext;
    private RecyclerClickListener clickListener;
    List<Flo> data = Collections.emptyList();

    public FlosAdapter(Context context, List<Flo> flos){
        this.mContext = context;
        this.data = flos;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyFlosHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.flo_row, viewGroup, false);
        MyFlosHolder viewholder = new MyFlosHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(MyFlosHolder viewHolder, int i) {
        Flo flo = data.get(i);
        String floName = flo.getName() == null ? "No Name " : flo.getName();
        String floDescription = flo.getDescription() == null ? "No description" : flo.getDescription();
        viewHolder.title.setText(floName.toUpperCase());
        viewHolder.description.setText(floDescription);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyFlosHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title,description;

        public MyFlosHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.title = (TextView) itemView.findViewById(R.id.floName);
            this.description = (TextView) itemView.findViewById(R.id.floDescription);
        }

        @Override
        public void onClick(View v) {
            clickListener.itemClicked(v, getPosition());
        }
    }

    public void setClickListener(RecyclerClickListener clickListener){
        this.clickListener = clickListener;
    }

    public interface RecyclerClickListener{
        void itemClicked(View view, int position);
    }
}
