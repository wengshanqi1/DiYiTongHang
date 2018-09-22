package com.example.lenovo.diyitonghang.ui.topic.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.lenovo.diyitonghang.R;
import com.example.lenovo.diyitonghang.data.News;

import java.util.List;

public class TopicMyAdapter extends BaseMultiItemQuickAdapter<News, BaseViewHolder> {

/*public class TopicMyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<News> listBeans;
    private Context context;
    private View view;
    public TopicMyAdapter(List<News> listBeans, Context context) {
        this.listBeans = listBeans;
        this.context = context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==1){
            View inflate = LayoutInflater.from(context).inflate(R.layout.adapter_noimageview, null);
            return new ViewHolder(inflate);
        }else if (viewType==2){
            View inflate = LayoutInflater.from(context).inflate(R.layout. , null);
            return new ViewHolder1(inflate);
        }else if (viewType==3){
            View inflate = LayoutInflater.from(context).inflate(R.layout., null);
            return new ViewHolder2(inflate);
        }else if (viewType==4){
            View inflate = LayoutInflater.from(context).inflate(R.layout., null);
            return new ViewHolder3(inflate);
        }
        return null;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ViewHolder){
            ((ViewHolder) holder).textView6.setText(listBeans.get(position).getTitle());
            ((ViewHolder) holder).textView10.setText(listBeans.get(position).getLayoutType());

        }else if (holder instanceof  ViewHolder1){
            ((ViewHolder1) holder).textView5.setText(listBeans.get(position).getTitle());
            Glide.with(context).load(listBeans.get(position).getImageListThumb().get(0)).into(((ViewHolder1) holder).imageView6);

        }else if (holder instanceof ViewHolder2){
            ((ViewHolder2) holder).textView20.setText(listBeans.get(position).getTitle());
            Glide.with(context).load(listBeans.get(position).getImageListThumb().get(0)).into(((ViewHolder2) holder).imageView13);
        }else if (holder instanceof ViewHolder3){
            ((ViewHolder3) holder).textView16.setText(listBeans.get(position).getTitle());
            Glide.with(context).load(listBeans.get(position).getImageListThumb().get(0)).into(((ViewHolder3) holder).imageView9);
            Glide.with(context).load(listBeans.get(position).getImageListThumb().get(1)).into(((ViewHolder3) holder).imageView10);
            Glide.with(context).load(listBeans.get(position).getImageListThumb().get(2)).into(((ViewHolder3) holder).imageView11);
        }


    }
    @Override
    public int getItemCount() {
        return listBeans.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textView6;
        private TextView textView10;

        public ViewHolder(View itemView) {
            super(itemView);
            textView6=itemView.findViewById(R.id.textView6);
            textView10=itemView.findViewById(R.id.textView10);
        }
    }
    static class ViewHolder1 extends RecyclerView.ViewHolder{
        private TextView textView5;
        private ImageView imageView6;
        public ViewHolder1(View itemView) {
            super(itemView);
            textView5=itemView.findViewById(R.id.textView5);
            imageView6=itemView.findViewById(R.id.imageView6);
        }

    }
    static class ViewHolder2 extends RecyclerView.ViewHolder{
        private TextView textView20;
        private ImageView imageView13;
        public ViewHolder2(View itemView) {
            super(itemView);
            textView20=itemView.findViewById(R.id.textView20);
            imageView13=itemView.findViewById(R.id.imageView13);
        }
    }
    static class ViewHolder3 extends RecyclerView.ViewHolder{
        private TextView textView16;
        private ImageView imageView9;
        private ImageView imageView10;
        private ImageView imageView11;
        public ViewHolder3(View itemView) {
            super(itemView);
            textView16=itemView.findViewById(R.id.textView16);
            imageView9=itemView.findViewById(R.id.imageView9);
            imageView10=itemView.findViewById(R.id.imageView10);
            imageView11=itemView.findViewById(R.id.imageView11);
        }
    }
    @Override
    public int getItemViewType(int position) {
        if (newListBean.getLayoutType().equals("1")){
            return 2;
        }else if (newListBean.getLayoutType().equals("0")){
            return 1;
        }else if (newListBean.getLayoutType().equals("2")){
            return 3;
        }else if(newListBean.getLayoutType().equals("3")){
            return 4;
        }else {
            return 0;
        }
    }*/

    public TopicMyAdapter(List data) {
        super(data);
        addItemType(0, R.layout.adapter_noimageview);
        addItemType(1, R.layout.adapter_right_image);
        addItemType(2, R.layout.adapter_below_bigimage);
        addItemType(3, R.layout.adapter_below_threeimage);
    }
    @Override
    protected void convert(BaseViewHolder helper,News item) {
        switch (helper.getItemViewType()) {
            case 0:
                helper.setText(R.id.adapter_noimage_title, item.getTitle());
                // 加载网络图片
                break;
            case 1:
                helper.setText(R.id.adapter_right_title, item.getTitle());
                Glide.with(mContext).load(item.getImageListThumb().get(0)).into((ImageView) helper.getView(R.id.adapter_right_image));
                break;
            case 2:
                helper.setText(R.id.adapter_below_bigimage_title, item.getTitle());
                // 加载网络图片
                Glide.with(mContext).load(item.getImageListThumb().get(0)).into((ImageView) helper.getView(R.id.adapter_below_bigimage_image));
                break;
            case 3:
                helper.setText(R.id.adapter_below_threeimage_title, item.getTitle());
                // 加载网络图片
                Glide.with(mContext).load(item.getImageListThumb().get(0)).into((ImageView) helper.getView(R.id.adapter_below_threeimage_rightimage));
                Glide.with(mContext).load(item.getImageListThumb().get(0)).into((ImageView) helper.getView(R.id.adapter_below_threeimage_right));
                Glide.with(mContext).load(item.getImageListThumb().get(0)).into((ImageView) helper.getView(R.id.adapter_below_threeimage_leftimage));
                break;
        }
    }
}
