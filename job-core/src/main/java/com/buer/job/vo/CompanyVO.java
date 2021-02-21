package com.buer.job.vo;

import com.buer.job.model.entity.Company;
import lombok.Data;

/**
 * Created by jiewu on 2021/2/20
 */
@Data
public class CompanyVO {
  private Long id;
  private String companyName;
  private String logoKey;
  private String profile;
  private String address;

  public static CompanyVO from(Company company) {
    CompanyVO companyVO = new CompanyVO();
    companyVO.setId(company.getId());
    companyVO.setCompanyName(company.getName());
    companyVO.setLogoKey(company.getLogoKey());
    companyVO.setProfile(company.getProfile());
    companyVO.setAddress(company.getAdress());
    return companyVO;
  }
}
