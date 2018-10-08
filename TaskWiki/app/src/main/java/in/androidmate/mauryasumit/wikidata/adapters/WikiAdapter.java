package in.androidmate.mauryasumit.wikidata.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import in.androidmate.mauryasumit.wikidata.Others.ItemClickListener;
import in.androidmate.mauryasumit.wikidata.R;
import in.androidmate.mauryasumit.wikidata.models.Page;


public class WikiAdapter extends RecyclerView.Adapter<WikiAdapter.WikidataHolder>  {

    List<Page> wikiList;
    Context context;
    private ItemClickListener itemClickListener;


    public WikiAdapter(List<Page> wikiList, Context context) {
        this.wikiList = wikiList;
        this.context = context;
    }

    @Override
    public WikidataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_wikiitem,parent,false);
        WikidataHolder mh = new WikidataHolder(view);
        return mh;
    }

    @Override
    public void onBindViewHolder(final WikidataHolder holder, int position) {

        holder.tvTitle.setText(wikiList.get(position).getTitle());

        if(wikiList.get(position).getTerms()!=null){

            holder.tvOverview.setText(wikiList.get(position).getTerms().getDescription().get(0));
        }else {

            holder.tvOverview.setText("Description Not Available");
        }


        holder.tvotherinfo.setText(wikiList.get(position).getTitle());

        if(wikiList.get(position).getThumbnail()!=null){
        Glide.with(context).load(wikiList.get(position).getThumbnail().getSource()).into(holder.iwikiprofile);

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListener != null)
                    itemClickListener.onClick(view,holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return wikiList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setClickListener(ItemClickListener itemClickListener) {    //  Method for setting clicklistner interface
        this.itemClickListener = itemClickListener;

    }


    public class WikidataHolder extends RecyclerView.ViewHolder {

        TextView tvTitle,tvOverview,tvotherinfo;
        ImageView iwikiprofile;

        public WikidataHolder(View v) {
            super(v);
            tvTitle = (TextView) v.findViewById(R.id.tvTitle);
            tvOverview = (TextView) v.findViewById(R.id.tvOverView);
            tvotherinfo = (TextView) v.findViewById(R.id.tvotherinfo);
            iwikiprofile = (ImageView) v.findViewById(R.id.iwikiprofile);
        }


    }
}
