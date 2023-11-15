package com.stampqr.dbaccessService.repos;

import com.stampqr.dbaccessService.resources.StampConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StampConfigRepository extends JpaRepository<StampConfig,Long> {
}
