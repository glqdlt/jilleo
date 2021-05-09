package com.glqdlt.myhome.jilleo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author glqdlt
 */
public interface LowestPriceQueueEntityRepo extends JpaRepository<LowestPriceQueueEntity,Long> {
    List<LowestPriceQueueEntity> findAllBySubmitFalseOrderBySeqAsc();
}
