package com.fc.memoapp.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fc.memoapp.dto.MemoDto;
import com.fc.memoapp.entity.MemoEntity;
import com.fc.memoapp.exception.MemoNotFoundException;
import com.fc.memoapp.repository.MemoRepository;

@Service
public class MemoService {
	private static final Logger logger =
	        LoggerFactory.getLogger(MemoService.class);

    private final MemoRepository memoRepository;
    public MemoService(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }
    public List<MemoEntity> findAll() {
        return memoRepository.findAll();
    }
    public MemoEntity findById(Long id) {
        return memoRepository.findById(id)
            .orElseThrow(() -> 
            new MemoNotFoundException("指定されたメモ（ID:" + id + "）は見つかりません。"));
    }
    public void save(MemoDto memoDto) {
        MemoEntity memo = new MemoEntity();
        memo.setId(memoDto.getId());
        memo.setTitle(memoDto.getTitle());
        memo.setContent(memoDto.getContent());
        memoRepository.save(memo);
    }
    public void deleteById(Long id) {
        memoRepository.deleteById(id);
    }
    public List<MemoEntity> findAllOrderByTitleAsc() {
        return memoRepository.findAll(Sort.by(Sort.Direction.ASC, "title"));
    }
    public Page<MemoEntity> findPage(int page, String sort) {
        Sort sortOrder = sort.equals("desc")
                ? Sort.by(Sort.Direction.DESC, "title")
                : Sort.by(Sort.Direction.ASC, "title");
        Pageable pageable = PageRequest.of(page, 5, sortOrder);
        return memoRepository.findAll(pageable);
    }
    
    public Page<MemoEntity> searchPage(int page, String sort, String keyword) {
        Sort sortOrder = sort.equals("desc")
                ? Sort.by(Sort.Direction.DESC, "title")
                : Sort.by(Sort.Direction.ASC, "title");
        Pageable pageable = PageRequest.of(page, 5, sortOrder);
        return memoRepository.findByTitleContaining(keyword, pageable);
    }


    

}
