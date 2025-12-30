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

    /* ID指定で1件取得 */
    public MemoEntity findById(Long id) {
        return memoRepository.findById(id)
                .orElseThrow(() ->
                        new MemoNotFoundException(
                                "指定されたメモ（ID:" + id + "）は見つかりません。"));
    }

    /* 一覧取得（ページ・並び順付き） */
    public Page<MemoEntity> findPage(int page, String sort) {
        Sort sortOrder =
                sort.equals("desc")
                        ? Sort.by(Sort.Direction.DESC, "title")
                        : Sort.by(Sort.Direction.ASC, "title");

        Pageable pageable = PageRequest.of(page, 5, sortOrder);
        return memoRepository.findAll(pageable);
    }

    /* 検索一覧取得 */
    public Page<MemoEntity> searchPage(int page, String sort, String keyword) {
        Sort sortOrder =
                sort.equals("desc")
                        ? Sort.by(Sort.Direction.DESC, "title")
                        : Sort.by(Sort.Direction.ASC, "title");

        Pageable pageable = PageRequest.of(page, 5, sortOrder);
        return memoRepository.findByTitleContaining(keyword, pageable);
    }

    /* 新規作成 */
    public void create(MemoDto memoDto) {
        MemoEntity memo =
                new MemoEntity(
                        memoDto.getTitle(),
                        memoDto.getContent());

        memoRepository.save(memo);
    }

    /* 更新 */
    public void update(MemoDto memoDto) {
        MemoEntity memo = findById(memoDto.getId());

        memo.setTitle(memoDto.getTitle());
        memo.setContent(memoDto.getContent());

        memoRepository.save(memo);
    }

    /* 削除 */
    public void deleteById(Long id) {
        if (!memoRepository.existsById(id)) {
            throw new MemoNotFoundException(
                    "削除対象のメモが見つかりません。");
        }
        memoRepository.deleteById(id);
    }
}
