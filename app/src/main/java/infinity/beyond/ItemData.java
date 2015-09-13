package infinity.beyond;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created on 12/9/15.
 */
public class ItemData {

    private Integer itemId;
    private Integer itemCount;
    private String itemType;
    private String itemTitle;
    private String itemDes;
    private JSONObject json;

    public ItemData(Integer dataId,Integer itemCount, JSONObject attr) throws JSONException {
        this.itemId = dataId;
        this.itemCount = itemCount;
        this.json = attr;

        if(itemId == 56) {
            setItemType("Home - Office Furniture");
            setItemTitle(json.getString("attribute_Furniture_Type"));
            setItemDes("No description");
        } else if(itemId == 71) {
            setItemType("Cars");
            setItemTitle(json.getString("attribute_Brand_name"));
            setItemDes(json.getString("attribute_Model"));
        } else if(itemId == 72) {
            setItemType("Bikes & Scooters");
            setItemTitle(json.getString("attribute_Brand_name"));
            setItemDes(json.getString("attribute_Model"));
        } else if(itemId == 149) {
            setItemType("Mobile Phones");
            setItemTitle(json.getString("attribute_Brand_name"));
            setItemDes(json.getString("attribute_Model"));
        }
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemDes() {
        return itemDes;
    }

    public void setItemDes(String itemDes) {
        this.itemDes = itemDes;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public JSONObject getJson() {
        return json;
    }

    public void setJson(JSONObject json) {
        this.json = json;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
}
