/*
 * Copyright 2005-2017 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.admin;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.shopxx.Filter;
import net.shopxx.Message;
import net.shopxx.Pageable;
import net.shopxx.entity.AdPosition;
import net.shopxx.entity.Country;
import net.shopxx.service.AdPositionService;
import net.shopxx.service.CountryService;
import net.shopxx.util.StringUtil;

/**
 * Controller - 广告位
 * 
 * @author SHOP++ Team
 * @version 5.0.3
 */
@Controller("adminAdPositionController")
@RequestMapping("/admin/ad_position")
public class AdPositionController extends BaseController {

	@Inject
	private AdPositionService adPositionService;
	@Inject
	private CountryService countryService;
	
	/**
	 * 添加
	 */
	@GetMapping("/add")
	public String add(ModelMap model) {
		return "admin/ad_position/add";
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public String save(AdPosition adPosition, String countryName, RedirectAttributes redirectAttributes) {
		if (!isValid(adPosition)) {
			return ERROR_VIEW;
		}
		adPosition.setAds(null);
		adPosition.setCountry(countryService.findByName(countryName));
		adPositionService.save(adPosition);
		addFlashMessage(redirectAttributes, Message.success(SUCCESS_MESSAGE));
		return "redirect:list";
	}

	/**
	 * 编辑
	 */
	@GetMapping("/edit")
	public String edit(Long id, ModelMap model) {
		model.addAttribute("adPosition", adPositionService.find(id));
		return "admin/ad_position/edit";
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public String update(AdPosition adPosition, String countryName, RedirectAttributes redirectAttributes) {
		if (!isValid(adPosition)) {
			return ERROR_VIEW;
		}
		adPosition.setCountry(countryService.findByName(countryName));
		adPositionService.update(adPosition, "ads");
		addFlashMessage(redirectAttributes, Message.success(SUCCESS_MESSAGE));
		return "redirect:list";
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(String countryName, Pageable pageable, ModelMap model) {
		Country country = null;
		if (StringUtil.isNotEmpty(countryName)) {
			country = countryService.findByName(countryName);
		}
		model.addAttribute("countryName", countryName);
		if (null != country) {
			Filter filter = new Filter();
			filter.setProperty("country");
			filter.setValue(country);
			filter.setOperator(Filter.Operator.eq);
			pageable.getFilters().add(filter);
		}
		model.addAttribute("page", adPositionService.findPage(pageable));
		return "admin/ad_position/list";
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public @ResponseBody Message delete(Long[] ids) {
		adPositionService.delete(ids);
		return Message.success(SUCCESS_MESSAGE);
	}

}