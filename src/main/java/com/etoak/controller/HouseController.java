package com.etoak.controller;

import com.etoak.bean.House;
import com.etoak.bean.HouseVo;
import com.etoak.bean.Page;
import com.etoak.exception.ParamException;
import com.etoak.service.HouseService;
import com.etoak.utils.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/house")
@Slf4j
public class HouseController {
    @Autowired
    HouseService houseService;
    @Value("${upload.dir}")
    //文件上传目录
    private String uploadDirectory;
    @Value("${upload.savePathPrefix}")
    private String savePathPrefix;
    @RequestMapping("/toAdd")
    public String toAdd(){
        return "house/add";
    }
    @PostMapping("/add")
    public String add(@RequestParam("file")MultipartFile file, House house)
            throws IOException, IllegalStateException {

        // 检验参数
        ValidationUtil.validate(house);

        // 上传文件
        String originalFilename = file.getOriginalFilename();
        String prefix = UUID.randomUUID().toString().replaceAll("-", "");
        // 新文件名称：原始文件名_uuid.文件后缀
        String newFilename =  prefix + "_" + originalFilename;

        File destFile = new File(this.uploadDirectory, newFilename);
        file.transferTo(destFile);

        // 给House设置访问地址
        house.setPic(this.savePathPrefix + newFilename);
        houseService.addHouse(house);
        return "redirect:/house/toAdd";
    }

    //上传文件
    @PostMapping("/add2")
    public String add2(@RequestParam("file")MultipartFile file, @Valid House house, BindingResult bindingResult) throws IOException,IllegalStateException {
        //对参数进行校验
        List<ObjectError> allErrors=bindingResult.getAllErrors();
        if(!CollectionUtils.isEmpty(allErrors)){
            StringBuffer msgBuffer=new StringBuffer();
            for(ObjectError objectError:allErrors){
                String message=objectError.getDefaultMessage();
                msgBuffer.append(message).append(";");
            }
            throw  new ParamException("参数校验失败:" + msgBuffer.toString());
        }
        //文件后缀名
        String ofName= file.getOriginalFilename();
        String prefix= UUID.randomUUID().toString().replaceAll("","");
        String newFileName=prefix+"_"+ofName;
        //上传目录,访问方式
        File destFile=new File(this.uploadDirectory,newFileName);
        file.transferTo(destFile);
        house.setPic(this.savePathPrefix + newFileName);
        houseService.addHouse(house);
        return "redirect:/house/toAdd";
    }
    //房源列表查询
    @GetMapping(value = "/list",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Page<HouseVo> queryList(@RequestParam(required = false,defaultValue = "1") int pageNum,@RequestParam(required = false,defaultValue = "10")int pageSize,HouseVo houseVo){
        log.info("pageNum - {},pageSize -{},houseVo-{}",pageNum,pageSize,houseVo);
        return houseService.queryList(pageNum,pageSize,houseVo);
    }
}
