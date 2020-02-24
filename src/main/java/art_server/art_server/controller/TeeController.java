package art_server.art_server.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@RestController
public class TeeController {

    @Value("${web.upload-path}")
    private String localFileUrl;

    @GetMapping("/hell")
    public String hh(String kk){
        System.out.println(kk);
        return "eeeee";
    }

    @ResponseBody
    @PostMapping(path = "/img")
    public String getImg(@RequestParam(value = "image",required = false)MultipartFile image,@RequestParam(value = "style",required = false)MultipartFile style,HttpServletRequest request) throws IOException {

        JSONObject json=new JSONObject();
        if(image==null||style==null){
            json.put("state","error");
            return json.toJSONString();
        }
        String imageName=image.getOriginalFilename();
        String styleName=style.getOriginalFilename();
        String imagePath=null,stylepath=null;
        String imageType=null,styleType=null;
        imageType = imageName.indexOf(".") != -1 ? imageName.substring(imageName.lastIndexOf(".") + 1, imageName.length()) : null;
        styleType=styleName.indexOf(".") != -1 ? styleName.substring(styleName.lastIndexOf(".") + 1, styleName.length()) : null;
        if(imageType==null||styleType==null){
            json.put("state","error");
            return json.toJSONString();
        }
        if ("GIF".equals(imageType.toUpperCase())||"PNG".equals(imageType.toUpperCase())||"JPG".equals(imageType.toUpperCase())){
            String trueFileName = String.valueOf(System.currentTimeMillis()) + imageName;
            imagePath=localFileUrl+"/"+trueFileName;
            image.transferTo(new File(imagePath));
        }else{
            json.put("state","error");
            return json.toJSONString();
        }
        if ("GIF".equals(styleType.toUpperCase())||"PNG".equals(styleType.toUpperCase())||"JPG".equals(styleType.toUpperCase())){
            String trueFileName = String.valueOf(System.currentTimeMillis()) + styleName;
            stylepath=localFileUrl+"/"+trueFileName;
            style.transferTo(new File(stylepath));
        }else{
            json.put("state","error");
            return json.toJSONString();
        }
        json.put("state","success");
        return json.toJSONString();
    }











}
