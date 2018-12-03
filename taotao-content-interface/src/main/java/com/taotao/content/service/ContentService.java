package com.taotao.content.service;
/**
 * 内容处理的接口
 * @author 糖
 *
 */

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

public interface ContentService {

	/**
	 * 获取节点内容列表
	 * 
	 * @return
	 */
	public EasyUIDataGridResult getContentList(Long categoryId, Integer page, Integer rows);

	/**
	 * 插入内容一条记录
	 * 
	 * @param tbContent
	 * @return
	 */
	public TaotaoResult saveContent(TbContent tbContent);

	/**
	 * 删除内容
	 * 
	 * @param id
	 * @return
	 */
	public TaotaoResult deleteContent(Long id);

	/**
	 * 更新内容
	 * 
	 */
	public TaotaoResult updateContent(TbContent tbContent);
}
