package com.taotao.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;

/**
 * 内容分类
 * 
 * @author 糖
 *
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
	// 1注入mapper
	@Autowired
	private TbContentCategoryMapper mapper;

	@Override
	public List<EasyUITreeNode> getContentCategoryList(Long parentId) {

		// 2创建example
		TbContentCategoryExample example = new TbContentCategoryExample();
		// 3设置执行条件
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		// 4执行查询
		List<TbContentCategory> list = mapper.selectByExample(example);
		// 5转成EasyUITreeNode列表
		List<EasyUITreeNode> nodes = new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tbContentCategory.getId());
			node.setState(tbContentCategory.getIsParent() ? "closed" : "open");
			node.setText(tbContentCategory.getName());// 分类名称
			nodes.add(node);
		}
		// 6返回
		return nodes;
	}

	@Override
	public TaotaoResult createContentCategory(Long parentId, String name) {
		// 1构建对象 补全其他属性
		// 2插入contentcategory表数据
		// 3返回taotaoresult包含内容的id 需要主键返回
		TbContentCategory category = new TbContentCategory();
		category.setParentId(parentId);
		category.setName(name);
		category.setIsParent(false);// 新增的节点都是叶子节点
		category.setCreated(new Date());
		category.setSortOrder(1);
		category.setStatus(1);
		category.setUpdated(category.getCreated());
		mapper.insertSelective(category);
		// 判断如果要添加的节点的父节点本身是叶子节点，需要将其更新为父节点
		TbContentCategory parent = mapper.selectByPrimaryKey(parentId);
		if (!parent.getIsParent()) {
			parent.setIsParent(true);
			mapper.updateByPrimaryKeySelective(parent);
		}
		return TaotaoResult.ok(category);
	}

	@Override
	public TaotaoResult updateContentCategory(Long id, String name) {
		TbContentCategory selectByPrimaryKey = mapper.selectByPrimaryKey(id);
		selectByPrimaryKey.setName(name);
		mapper.updateByPrimaryKeySelective(selectByPrimaryKey);
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult deleteContentCategory(Long id) {
		TbContentCategory selectByPrimaryKey = mapper.selectByPrimaryKey(id);
		if (selectByPrimaryKey.getIsParent()) {
			return TaotaoResult.build(500, "该节点为父节点，无法删除");
		}
		mapper.deleteByPrimaryKey(id);
		//查询该节点的父节点是否还有其他子节点
		Integer selectId = mapper.selectByParentId(selectByPrimaryKey.getParentId());
		if (selectId == 0) {
			//没有其他子节点的话就将该父节点修改为子节点
			TbContentCategory category = new TbContentCategory();
			category.setId(selectByPrimaryKey.getParentId());
			category.setIsParent(false);
			mapper.updateByPrimaryKeySelective(category);
		}
		return TaotaoResult.ok();
	}

}
