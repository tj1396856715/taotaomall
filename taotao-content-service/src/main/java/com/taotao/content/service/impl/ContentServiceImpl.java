package com.taotao.content.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentService;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper mapper;

	@Override
	public EasyUIDataGridResult getContentList(Long categoryId, Integer page, Integer rows) {
		if (page == null || page == 0) {
			page = 1;
		}
		if (rows == null || rows == 0) {
			rows = 20;
		}
		PageHelper.startPage(page, rows);
		TbContentExample example = new TbContentExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> list = mapper.selectByExample(example);
		PageInfo<TbContent> info = new PageInfo<>(list);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal((int) info.getTotal());
		result.setRows(info.getList());
		return result;
	}

	@Override
	public TaotaoResult saveContent(TbContent tbContent) {
		tbContent.setCreated(new Date());
		tbContent.setUpdated(tbContent.getCreated());
		mapper.insertSelective(tbContent);
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult deleteContent(Long id) {
		mapper.deleteByPrimaryKey(id);
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult updateContent(TbContent tbContent) {
		mapper.updateByPrimaryKeySelective(tbContent);
		return TaotaoResult.ok();
	}

}
