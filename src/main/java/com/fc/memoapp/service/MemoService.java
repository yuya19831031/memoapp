package com.fc.memoapp.service;

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

    private final MemoRepository memoRepository;
    public MemoService(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }
    public MemoEntity findById(Long id) {
        return memoRepository.findById(id)
            .orElseThrow(() -> 
            new MemoNotFoundException("指定されたメモ（ID:" + id + "）は見つかりません。"));
    }
    
    public void deleteById(Long id) {
        if (!memoRepository.existsById(id)) {
            throw new MemoNotFoundException("削除対象のメモが見つかりません。");
        }
        memoRepository.deleteById(id);
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
    
    public void update(MemoDto memoDto) {
        MemoEntity memo = findById(memoDto.getId());
        memo.setTitle(memoDto.getTitle());
        memo.setContent(memoDto.getContent());
        memoRepository.save(memo);
    }
    
    public void create(MemoDto memoDto) {
        MemoEntity memo = new MemoEntity(
                memoDto.getTitle(),
                memoDto.getContent()
        );
        memoRepository.save(memo);
    }
}
