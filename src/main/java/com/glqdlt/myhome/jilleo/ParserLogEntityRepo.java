package com.glqdlt.myhome.jilleo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParserLogEntityRepo extends JpaRepository<ParserLogEntity,Long> {
    Page<ParserLogEntity> findAllByUrlEntity_IdAndSeqLessThanOrderBySeqDesc(Integer uriId, Long seq, Pageable pageable);
}
