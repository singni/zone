package com.zone.bos.action;

import com.zone.bos.domain.Region;
import com.zone.bos.service.RegionService;
import com.zone.bos.utils.PinYin4jUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Rian on 2019/1/24.
 * Copyright © 2018 Rian. All rights reserved
 */
@Controller
@Scope("prototype")
public class RegionAction extends BaseAction<Region> {

    private File myFile;

    public void setMyFile(File myFile) {
        this.myFile = myFile;
    }
    /**
     * 批量导入数据
     * @return
     */
    public String importXls() throws IOException {
        String flag="1";
        try {
            HSSFWorkbook hssfWorkbook=new HSSFWorkbook(new FileInputStream(myFile));
            HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
            List<Region> regions=new ArrayList<Region>();
            for(Row row:sheet){
                int rowNum = row.getRowNum();
                if(rowNum==0){
                    continue;
                }
                String id = row.getCell(0).getStringCellValue();
                String province = row.getCell(1).getStringCellValue();
                String city = row.getCell(2).getStringCellValue();
                String district = row.getCell(3).getStringCellValue();
                String postcode = row.getCell(4).getStringCellValue();
                Region region=new Region(id,province,city,district,postcode,null,null,null);

                city  = city.substring(0, city.length() - 1);
                String[] stringToPinyin = PinYin4jUtils.stringToPinyin(city);
                String citycode = StringUtils.join(stringToPinyin, "");

                province  = province.substring(0, province.length() - 1);
                district  = district.substring(0, district.length() - 1);
                String info = province + city + district;
                String[] headByString = PinYin4jUtils.getHeadByString(info);
                String shortcode = StringUtils.join(headByString, "");

                region.setCitycode(citycode);
                region.setShortcode(shortcode);
                regions.add(region);
            }
            regionService.saveBatch(regions);
        }catch (Exception e){
            flag="0";
            e.printStackTrace();
        }

        ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
        ServletActionContext.getResponse().getWriter().print(flag);
        return "NONE";
    }
    /**
     * 分页查询
     * @throws IOException
     */
    public String pageQuery() throws IOException{
        regionService.pageQuery(pageBean);
        this.writePageBean2Json(pageBean);
        return NONE;
    }

    //模糊查询条件
    private String q;
    /**
     * 查询所有的区域数据，返回json
     * @throws IOException
     */
    public String listAjax() throws IOException{
        List<Region> list = null;
        if(StringUtils.isNotBlank(q)){
            list = regionService.findByQ(q);
        }else{
            list = regionService.findAll();
        }

        this.writeList2Json(list);
        return NONE;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }
}
