package domainGroup;

import com.pinyougou.pojo.TbOrderItem;

import java.io.Serializable;
import java.util.List;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/7/30 9:38
 */
public class Carts implements Serializable {

    // 商家id
    private String sellerId;

    // 商家名称
    private String sellerName;

    // 购物车POJO
    private List<TbOrderItem> tbOrderItemList;

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public List<TbOrderItem> getTbOrderItemList() {
        return tbOrderItemList;
    }

    public void setTbOrderItemList(List<TbOrderItem> tbOrderItemList) {
        this.tbOrderItemList = tbOrderItemList;
    }
}
