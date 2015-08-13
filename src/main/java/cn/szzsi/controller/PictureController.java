package cn.szzsi.controller;

import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;
import com.jfinal.upload.UploadFile;

/**
 * Created by Yishe on 8/13/2015.
 */
public class PictureController extends Controller{

    public void upload(){
        UploadFile file = getFile("file", PathKit.getWebRootPath() +"/temp");
//        Character.MAX_RADIX

    }


}
