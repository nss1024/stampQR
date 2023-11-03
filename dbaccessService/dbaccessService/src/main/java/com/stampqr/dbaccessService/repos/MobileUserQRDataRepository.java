package com.stampqr.dbaccessService.repos;

import com.stampqr.dbaccessService.resources.MobileUserQRData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MobileUserQRDataRepository extends JpaRepository<MobileUserQRData,Long> {

    public List<MobileUserQRData> findByUserName(String userName);

    public List<MobileUserQRData> findByQrDataId(Long id);

    public MobileUserQRData findByUserNameAndQrDataId(String userName, Long id);

    public void deleteByUserNameAndQrDataId(String userName, Long id);

}
