package com.fc.memoapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fc.memoapp.dto.MemoDto;
import com.fc.memoapp.entity.MemoEntity;
import com.fc.memoapp.exception.MemoNotFoundException;
import com.fc.memoapp.service.MemoService;

@Controller
public class MemoController {
	private static final Logger logger = LoggerFactory.getLogger(MemoController.class);

	private final MemoService memoService;

	public MemoController(MemoService memoService) {
		this.memoService = memoService;
	}

	@GetMapping("/")
	public String index(
			MemoDto memoDto,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "asc") String sort,
			Model model) {
		Page<MemoEntity> memoPage = memoService.findPage(page, sort);
		model.addAttribute("memos", memoPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", memoPage.getTotalPages());
		model.addAttribute("sort", sort);
		return "index";
	}

	@PostMapping("/add")
	public String add(@Validated MemoDto memoDto, BindingResult result, Model model) {
		if (result.hasErrors()) {
			int defaultPage = 0;
			String defaultSort = "asc";
			Page<MemoEntity> memoPage = memoService.findPage(defaultPage, defaultSort);
			model.addAttribute("memos", memoPage.getContent());
			model.addAttribute("currentPage", defaultPage);
			model.addAttribute("totalPages", memoPage.getTotalPages());
			model.addAttribute("sort", defaultSort);
			return "index";
		}
		memoService.create(memoDto);
		logger.info(
				"メモを追加しました title={}, contentLength={}",
				memoDto.getTitle(),
				memoDto.getContent().length());
		return "redirect:/";
	}

	@PostMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
	    memoService.deleteById(id);
	    return "redirect:/";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Long id, Model model) {
	    MemoEntity entity = memoService.findById(id);
	    model.addAttribute("memoDto", entity);
	    model.addAttribute("memoEntity", entity);
	    return "edit";
	}


	@PostMapping("/edit/{id}")
	public String editSubmit(
	        @PathVariable Long id,
	        @Validated MemoDto memoDto,
	        BindingResult result,
	        Model model) {

	    if (result.hasErrors()) {
	        MemoEntity entity = memoService.findById(id);
	        model.addAttribute("memoEntity", entity);
	        return "edit";
	    }

	    memoDto.setId(id);
	    memoService.update(memoDto);
	    return "redirect:/";
	}


	@GetMapping("/search")
	public String search(
			MemoDto memoDto,
			@RequestParam String keyword,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "asc") String sort,
			Model model) {
		Page<MemoEntity> memoPage = memoService.searchPage(page, sort, keyword);
		model.addAttribute("memos", memoPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", memoPage.getTotalPages());
		model.addAttribute("sort", sort);
		model.addAttribute("keyword", keyword);
		return "index";
	}

	@GetMapping("/sort/title")
	public String sortByTitle(Model model) {
		model.addAttribute("memos", memoService.findAllOrderByTitleAsc());
		return "index";
	}

	@GetMapping("/detail/{id}")
	public String detail(@PathVariable Long id, Model model) {
		MemoEntity memo = memoService.findById(id);
		model.addAttribute("memo", memo);
		return "detail";
	}

	@ExceptionHandler(MemoNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleNotFoundException(MemoNotFoundException ex, Model model) {
		logger.error("メモが見つかりません message={}", ex.getMessage());
		model.addAttribute("errorMessage", ex.getMessage());
		return "error/404";
	}
}