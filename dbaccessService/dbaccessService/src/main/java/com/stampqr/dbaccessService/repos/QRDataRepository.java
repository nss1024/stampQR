package com.stampqr.dbaccessService.repos;

import com.stampqr.dbaccessService.resources.QRData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QRDataRepository extends JpaRepository<QRData,Long> {

    public QRData findByQrDataTag(String tag);

    public List<QRData> findAllByUserIdOrderByCreateDate(Long userId);

    public List<QRData> findAllByUserIdAndActive(Long userId, Boolean active);

    public List<QRData> findAllByUserIdAndActiveOrderByCreateDateDesc(Long userId, Boolean active);

}
