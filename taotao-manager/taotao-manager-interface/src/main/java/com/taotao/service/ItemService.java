package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;

/**
 * 商品相关的处理的service
 * 
 * @title ItemService.java
 *
 * 
 * @version 1.0
 */
public interface ItemService {

	/**
	 * 根据当前的页码和每页 的行数进行分页查询
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	public EasyUIDataGridResult getItemList(Integer page, Integer rows);

	/**
	 * 添加商品基本数据和描述数据
	 * 
	 * @param item
	 * @param desc
	 * @return
	 */
	public TaotaoResult saveItem(TbItem item, String desc);

	/**
	 * 删除商品
	 * 
	 * @param id
	 * @return
	 */
	public TaotaoResult deleteItem(Long id);

	/**
	 * 获取参数
	 * 
	 */
	public EasyUIDataGridResult getItemParamList(Integer page, Integer rows);

	/**
	 * 删除规格参数
	 * 
	 * @param id
	 * @return
	 */
	public TaotaoResult deleteItemParam(Long id);

	/**
	 * 根据itemcatid查询规格参数
	 * 
	 * @param itemcatId
	 * @return
	 */
	public EasyUIDataGridResult queryItemCatId(Integer itemcatId);

	/**
	 * 保存商品规格
	 * 
	 * @param itemcatId
	 * @param paramData
	 * @return
	 */
	public TaotaoResult saveItemParam(Long itemcatId, String paramData);

	/**
	 * 根据商品id查看商品描述
	 * 
	 * @param itemId
	 * @return
	 */
	public EasyUIDataGridResult queryItemDesc(Long itemId);

	/**
	 * 根据商品id查看商品规格
	 * 
	 * @param itemId
	 * @return
	 */
	public EasyUIDataGridResult queryItemParam(Long itemId);
}
