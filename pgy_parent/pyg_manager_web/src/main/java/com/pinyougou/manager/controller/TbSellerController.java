package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbSeller;
import com.pinyougou.sellergoods.service.TbSellerService;
import domaincommon.PageResult;
import domaincommon.Result;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.util.List;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/7/7 19:38
 */
@RestController
@RequestMapping("/seller")
public class TbSellerController {

    @Reference
    private TbSellerService tbSellerService;

    /**
     * 查询所有审核
     * @return
     */
    @RequestMapping("/findAll")
    public List<TbSeller> findAll(){
        return tbSellerService.findAll();
    }


    // 导出exportXls 表
    @RequestMapping("/exportXls")
    public void getExportXls(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 1.首先去获取模板
        String templatePath = request.getSession().getServletContext().getRealPath("") + "/template/seller.xls";

        // 2.获取一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(templatePath));

        // 3.获取需要导入的数据
        List<TbSeller> sellersAll = tbSellerService.findAll();

        // 4.获取已有的工作表
        HSSFSheet sheet = workbook.getSheetAt(0);

        // 自己设置样式
        //HSSFCellStyle cellStyle = workbook.getSheetAt(0).getRow(0).getCell(0).getCellStyle();

        int rowIndex = 3;
        for (TbSeller tbSeller : sellersAll) {

            HSSFRow row = sheet.createRow(rowIndex);
            HSSFCell SellerIdCell = row.createCell(0);
            SellerIdCell.setCellValue(tbSeller.getSellerId());
          //  SellerIdCell.setCellStyle(cellStyle);   在存放里面

            HSSFCell NameCell = row.createCell(1);
            NameCell.setCellValue(tbSeller.getName());

            HSSFCell NickNameCell = row.createCell(2);
            NickNameCell.setCellValue(tbSeller.getNickName());

            HSSFCell LinkmanNameCell = row.createCell(3);
            LinkmanNameCell.setCellValue(tbSeller.getLinkmanName());

            HSSFCell TelephoneCell = row.createCell(4);
            TelephoneCell.setCellValue(tbSeller.getTelephone());

            HSSFCell StatusCell = row.createCell(5);
            StatusCell.setCellValue(tbSeller.getStatus());

            rowIndex++;
        }

        // 在浏览器下载
        ServletOutputStream outputStream = response.getOutputStream();

        // 一个流  两个头  1. 文件打开方式 in - line    attachment  2. 文件的mime类型（常见类型的可以省略）
        response.setHeader("Content-Disposition","attachment; filename=seller.xls");


        workbook.write(outputStream);

    }

    @RequestMapping("/importXls")
    public void getimportXls(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
    }

    /**
     * 查询审核分页的结果集
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/sellerPage/{pageNum}/{pageSize}")
    public PageResult findSellerPage(@PathVariable("pageNum") int pageNum,@PathVariable("pageSize") int pageSize){
        return tbSellerService.findSellerPage(pageNum,pageSize);
    }

    /**
     * 添加单个审核信息
     * @param tbSeller
     * @return
     */
    @RequestMapping("/sellerSave")
    public Result saveSeller(@RequestBody TbSeller tbSeller){
        try {
            tbSellerService.saveSeller(tbSeller);
            return new Result(true,"添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"添加失败");
        }
    }

    /**
     * 查询单个审核
     * @param id
     * @return
     */
    @RequestMapping("/sellerInitFind/{id}")
    public TbSeller sellerInitFind(@PathVariable("id") String id) {
        return tbSellerService.sellerInitFind(id);
    }

    /**
     * 修改单个审核
     * @param
     * @return
     */
    @RequestMapping("/sellerUpdate/{status}/{sellerId}")
    public Result sellerUpdate(@PathVariable("status") String status,@PathVariable("sellerId") String sellerId) {
        try {
            tbSellerService.sellerUpdate(status,sellerId);
            return new Result(true,"");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"状态未改变！");
        }

    }

    /**
     * 搜索审核功能
     * @param pageNum
     * @param pageSize
     * @param tbSeller
     * @return
     */
    @RequestMapping("/searchSellers/{pageNum}/{pageSize}")
    public PageResult searchSellers(@PathVariable("pageNum") int pageNum,@PathVariable("pageSize") int pageSize,@RequestBody TbSeller tbSeller) {
        return tbSellerService.searchSeller(pageNum,pageSize,tbSeller);
    }

}
