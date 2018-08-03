package com.pinyougou.cart.controller;
import java.util.List;

import com.pinyougou.order.service.OrderService;
import domaincommon.PageResult;
import domaincommon.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbOrder;

/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/order")
public class OrderController {

	@Reference
	private OrderService orderService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbOrder> findAll(){			
		return orderService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage/{pageNum}/{pageSize}")
	public PageResult findPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize){
		return orderService.findPage(pageNum, pageSize);
	}
	
	/**
	 * 增加
	 * @param order
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody TbOrder order){

		try {
			order.setUserId(SecurityContextHolder.getContext().getAuthentication().getName());
			order.setSourceType("2");
			orderService.add(order);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param order
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody TbOrder order){
		try {
			orderService.update(order);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "修改失败");
		}
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne/{id}")
	public TbOrder findOne(@PathVariable("id") Long id){
		return orderService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete/{ids}")
	public Result delete(@PathVariable("ids") Long[] ids){
		try {
			orderService.delete(ids);
			return new Result(true, "删除成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}
	
		/**
	 * 查询+分页
	 * @param 
	 * @param 
	 * @param 
	 * @return
	 */
	  
	@RequestMapping("/search/{pageNum}/{pageSize}")
	public PageResult search(@RequestBody TbOrder order, @PathVariable("pageNum") int pageNum,@PathVariable("pageSize") int pageSize ){
		return orderService.findPage(order, pageNum, pageSize);		
	}
	
}
