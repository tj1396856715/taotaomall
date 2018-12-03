package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;

/**
 * 处理内容表相关的
 * 
 * @author 糖
 *
 */
@Controller
public class ContentController {

	@Autowired
	private ContentService contentService;

	@RequestMapping(value = "/content/query/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyUIDataGridResult getContentList(Long categoryId, Integer page, Integer rows) {
		return contentService.getContentList(categoryId, page, rows);
	}

	@RequestMapping(value = "/content/save", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult saveContent(TbContent tbContent) {
		return contentService.saveContent(tbContent);
	}

	@RequestMapping(value = "/content/delete", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult deleteContent(@RequestParam(value = "ids", defaultValue = "0") Long id) {
		return contentService.deleteContent(id);
	}

	@RequestMapping(value = "/rest/content/edit", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult updateContent(TbContent tbContent) {
		return contentService.updateContent(tbContent);
	}

}
