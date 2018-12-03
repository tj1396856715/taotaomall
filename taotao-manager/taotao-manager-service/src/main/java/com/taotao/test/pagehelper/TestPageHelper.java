package com.taotao.test.pagehelper;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.pojo.TbItemParamItemExample.Criteria;

public class TestPageHelper {

	@Test
	public void testHelper() {
		// 1初始化spring容器
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		// 2获取mapper的代理对象
		TbItemParamItemMapper itemMapper = context.getBean(TbItemParamItemMapper.class);
		// 3设置分页信息
		PageHelper.startPage(1, 3);// 3行紧跟着的第一个查询才会被分页
		// 4调用mapper的方法查询数据
		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andItemIdEqualTo((long) 1188043);
		List<TbItemParamItem> list = itemMapper.selectByExample(example);

		// 5取分页信息
		System.out.println("第一个分页list的长度:" + list.size());
		PageInfo<TbItemParamItem> info = new PageInfo<>(list);
		// 6循环遍历结果集
		System.out.println("总记录：" + info.getTotal());
		for (TbItemParamItem tbItem : info.getList()) {
			System.out.println(tbItem.getId() + " --- " + tbItem.getParamData());
		}
	}
}
