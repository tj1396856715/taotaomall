package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.IDUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemDescExample;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.pojo.TbItemParamExample.Criteria;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private TbItemMapper mapper;
	@Autowired
	private TbItemDescMapper descmapper;

	@Autowired
	private TbItemParamMapper paramMapper;

	@Autowired
	private TbItemDescMapper descMapper;

	@Autowired
	private TbItemParamItemMapper paramItemMapper;

	@Override
	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		// 1.设置分页的信息 使用pagehelper
		if (page == null)
			page = 1;
		if (rows == null)
			rows = 30;
		PageHelper.startPage(page, rows);
		// 2.注入mapper
		// 3.创建example 对象 不需要设置查询条件
		TbItemExample example = new TbItemExample();
		// 4.根据mapper调用查询所有数据的方法
		List<TbItem> list = mapper.selectByExample(example);
		// 5.获取分页的信息
		PageInfo<TbItem> info = new PageInfo<>(list);
		// 6.封装到EasyUIDataGridResult
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal((int) info.getTotal());
		result.setRows(info.getList());
		// 7.返回
		return result;
	}

	@Override
	public TaotaoResult saveItem(TbItem item, String desc) {
		// 生成商品的id
		long itemId = IDUtils.genItemId();
		// 1.补全item 的其他属性
		item.setId(itemId);
		item.setCreated(new Date());
		// 1-正常，2-下架，3-删除',
		item.setStatus((byte) 1);
		item.setUpdated(item.getCreated());
		// 2.插入到item表 商品的基本信息表
		int insertSelective = mapper.insertSelective(item);
		if (insertSelective == 0) {
			return TaotaoResult.build(500, "增加失败");
		}
		// 3.补全商品描述中的属性
		TbItemDesc desc2 = new TbItemDesc();
		desc2.setItemDesc(desc);
		desc2.setItemId(itemId);
		desc2.setCreated(item.getCreated());
		desc2.setUpdated(item.getCreated());
		// 4.插入商品描述数据
		// 注入tbitemdesc的mapper
		int insertSelective2 = descmapper.insertSelective(desc2);
		if (insertSelective2 == 0) {
			return TaotaoResult.build(500, "增加失败");
		}
		// 5.返回taotaoresult
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult deleteItem(Long id) {
		int Key = mapper.deleteByPrimaryKey(id);
		if (Key == 0) {
			return TaotaoResult.build(500, "删除失败");
		}
		return TaotaoResult.ok();
	}

	@Override
	public EasyUIDataGridResult getItemParamList(Integer page, Integer rows) {
		if (page == null)
			page = 1;
		if (rows == null)
			rows = 30;
		PageHelper.startPage(page, rows);
		TbItemParamExample example = new TbItemParamExample();
		List<TbItemParam> list = paramMapper.selectByExample(example);
		PageInfo<TbItemParam> info = new PageInfo<>(list);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal((int) info.getTotal());
		result.setRows(info.getList());
		return result;
	}

	@Override
	public TaotaoResult deleteItemParam(Long id) {
		int key = paramMapper.deleteByPrimaryKey(id);
		if (key == 0) {
			return TaotaoResult.build(500, "删除失败");
		}
		return TaotaoResult.ok();
	}

	@Override
	public EasyUIDataGridResult queryItemCatId(Integer itemcatId) {
		PageHelper.startPage(1, 30);
		TbItemParamExample example = new TbItemParamExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andItemCatIdEqualTo((long) itemcatId);
		List<TbItemParam> list = paramMapper.selectByExample(example);
		PageInfo<TbItemParam> info = new PageInfo<>(list);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal((int) info.getTotal());
		result.setRows(info.getList());
		return result;
	}

	@Override
	public TaotaoResult saveItemParam(Long itemcatId, String paramData) {
		TbItemParam record = new TbItemParam();
		record.setItemCatId(itemcatId);
		record.setParamData(paramData);
		record.setCreated(new Date());
		record.setUpdated(new Date());
		int insertSelective = paramMapper.insertSelective(record);
		if (insertSelective == 0) {
			return TaotaoResult.build(500, "添加失败");
		}
		return TaotaoResult.ok();
	}

	@Override
	public EasyUIDataGridResult queryItemDesc(Long itemId) {
		PageHelper.startPage(1, 30);
		TbItemDescExample exampleDesc = new TbItemDescExample();
		com.taotao.pojo.TbItemDescExample.Criteria createCriteria = exampleDesc.createCriteria();
		createCriteria.andItemIdEqualTo(itemId);
		List<TbItemDesc> list = descMapper.selectByExample(exampleDesc);
		PageInfo<TbItemDesc> info = new PageInfo<>(list);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal((int) info.getTotal());
		result.setRows(info.getList());
		return result;
	}

	@Override
	public EasyUIDataGridResult queryItemParam(Long itemId) {
		PageHelper.startPage(1, 30);
		TbItemParamItemExample example = new TbItemParamItemExample();
		com.taotao.pojo.TbItemParamItemExample.Criteria createCriteria = example.createCriteria();
		createCriteria.andItemIdEqualTo(itemId);
		List<TbItemParamItem> list = paramItemMapper.selectByExample(example);
		PageInfo<TbItemParamItem> info = new PageInfo<>(list);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal((int) info.getTotal());
		result.setRows(info.getList());
		return result;
	}
}
