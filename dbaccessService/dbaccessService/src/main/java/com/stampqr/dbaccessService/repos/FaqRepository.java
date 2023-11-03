package com.stampqr.dbaccessService.repos;

import com.stampqr.dbaccessService.resources.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FaqRepository extends JpaRepository<FAQ,Long> {

    public List<FAQ> findByActive(Boolean active);


}
