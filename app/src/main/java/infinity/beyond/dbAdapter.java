package infinity.beyond;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 12/9/15.
 */
public class dbAdapter extends BaseAdapter {

    private final List<ItemData> mDocs = new ArrayList<ItemData>();
    private final Context mContext;

    public dbAdapter(Context context) {
        this.mContext = context;
    }

    public void add(ItemData itemData) {
        mDocs.add(itemData);
    }

    public void clear(){
        mDocs.clear();
    }
    @Override
    public int getCount() {
        return mDocs.size();
    }

    @Override
    public ItemData getItem(int i) {
        return mDocs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            view = View.inflate(mContext,R.layout.itemshow,null);
        }

        ItemData data = getItem(i);

        ImageView imageView;
        TextView title,description,id,itemType;
        title = (TextView) view.findViewById(R.id.itemTitle);
        itemType = (TextView) view.findViewById(R.id.itemType);
        id = (TextView) view.findViewById(R.id.itemId);
        description = (TextView) view.findViewById(R.id.itemDes);

        title.setText(data.getItemTitle());
        description.setText(data.getItemDes());

        return view;
    }
}
