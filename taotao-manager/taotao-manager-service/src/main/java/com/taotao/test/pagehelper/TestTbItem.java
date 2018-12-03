package com.taotao.test.pagehelper;

import java.util.Date;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.IDUtils;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItemDesc;

public class TestTbItem {
	@Test
	public void test() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-dao.xml");
		TbItemMapper itemMapper = applicationContext.getBean(TbItemMapper.class);
		// 生成商品的id
//		long itemId = IDUtils.genItemId();
//		// 1.补全item 的其他属性
//		item.setId(itemId);
//		item.setCreated(new Date());
//		// 1-正常，2-下架，3-删除',
//		item.setStatus((byte) 1);
//		item.setUpdated(item.getCreated());
//		// 2.插入到item表 商品的基本信息表
//		int insertSelective = mapper.insertSelective(item);
//		if (insertSelective == 0) {
//			return TaotaoResult.build(500, "增加失败");
//		}
//		// 3.补全商品描述中的属性
//		TbItemDesc desc2 = new TbItemDesc();
//		desc2.setItemDesc(desc);
//		desc2.setItemId(itemId);
//		desc2.setCreated(item.getCreated());
//		desc2.setUpdated(item.getCreated());
//		// 4.插入商品描述数据
//		// 注入tbitemdesc的mapper
//		int insertSelective2 = descmapper.insertSelective(desc2);
//		if (insertSelective2 == 0) {
//			return TaotaoResult.build(500, "增加失败");
//		}
	}
}
