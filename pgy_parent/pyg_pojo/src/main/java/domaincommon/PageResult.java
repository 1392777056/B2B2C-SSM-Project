package domaincommon;

import java.io.Serializable;
import java.util.List;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/7/8 16:28
 */
public class PageResult implements Serializable {

    // 页面的总条数
    private Long total;

    // 返回的对象集
    private List rows;

    public PageResult(){}

    public PageResult(Long total, List rows) {
        this.total = total;
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}
