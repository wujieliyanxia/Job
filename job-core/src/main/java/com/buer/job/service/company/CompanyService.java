package com.buer.job.service.company;

import com.buer.job.exception.JobException;
import com.buer.job.model.entity.Company;
import com.buer.job.model.mapper.CompanyMapper;
import com.buer.job.utils.Clock;
import com.buer.job.vo.CompanyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by jiewu on 2021/2/20
 */
@Service
public class CompanyService {
  @Autowired
  private CompanyMapper companyMapper;

  public void insert(String companyName,
                     String profile,
                     String address, String logoKey) {
    Company company = new Company();
    company.setName(companyName);
    company.setAdress(address);
    company.setProfile(profile);
    company.setLogoKey(logoKey);
    Long now = Clock.now();
    company.setTimeCreated(now);
    company.setTimeUpdated(now);
    companyMapper.insert(company);
  }

  public CompanyVO findByIdOrThrow(Long id) {
    Company company = companyMapper.selectById(id);
    return CompanyVO.from(Optional.ofNullable(company).orElseThrow(() -> JobException.error("can not find author by id {}", id)));
  }
}
