package com.jsp.studyShare.Service;import com.jsp.studyShare.Dao.DataDao;import com.jsp.studyShare.Dao.UserDao;import com.jsp.studyShare.model.*;import com.jsp.studyShare.utils.JsonUtil;import com.jsp.studyShare.utils.studyShareUtil;import org.bouncycastle.ocsp.RespData;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.boot.autoconfigure.session.RedisSessionProperties;import org.springframework.stereotype.Service;import org.springframework.util.StringUtils;import org.springframework.web.multipart.MultipartFile;import java.io.File;import java.io.IOException;import java.nio.file.Files;import java.nio.file.StandardCopyOption;import java.util.*;@Servicepublic class DataService {    @Autowired    private DataDao dataDAO;    @Autowired    private UserDao userDAO;    @Autowired    private HostHolder hostHolder;    public Map<String, String> addData(String jsonStr) {        Map<String, String> map = JsonUtil.strToMap(jsonStr);        Map<String, String> MsgMap = new HashMap<>();        Data data = new Data();        String cid = map.get("cid");        if (StringUtils.isEmpty(cid)) {            MsgMap.put("msg", "cid不能为空");            return MsgMap;        }        data.setCid(Integer.parseInt(cid));        data.setDataDesc(map.get("dataDesc"));        data.setDataName(map.get("dataName"));        data.setUploadTime(new Date());        data.setOpenid(hostHolder.getUser().getOpenid());        data.setDataFile(map.get("dataFile"));        data.setStatus(1);        data.setUrl(map.get("url"));        dataDAO.insertData(data);        //根据openid查询出上传的资源,添加到数据库        List<Data> dataByUser = dataDAO.findDataByOpenId(hostHolder.getUser().getOpenid(), 1);        String uploaded = "";        for (Data data1 : dataByUser) {            uploaded += data1.getDid() + "-";        }        //根据open修改uploaded        userDAO.updateUpload(hostHolder.getUser().getOpenid(), uploaded);        map.put("msg", "新增成功");        return map;    }    public List<ResponseData> getDataByCid(int cid) {        List<Data> datas = dataDAO.findDataByCid(cid, 1);        List<ResponseData> respDatas = new ArrayList<>();        for (Data data : datas) {            ResponseData respData = new ResponseData();            respData.setCid(cid);            respData.setDataDesc(data.getDataDesc());            respData.setDataName(data.getDataName());            respData.setDataFile(data.getDataFile());            respData.setUrl(data.getUrl());            //获取用户昵称            User user = userDAO.getUserById(data.getOpenid());            respData.setNickname(user.getNickname());            respData.setUploadTime(data.getUploadTime());            respData.setDid(data.getDid());            respDatas.add(respData);        }        return respDatas;    }    public List<ResponseData> getAllData() {        List<Data> datas = dataDAO.findAllData(1);        List<ResponseData> respDatas = new ArrayList<>();        for (Data data : datas) {            ResponseData respData = new ResponseData();            respData.setCid(data.getCid());            respData.setDataDesc(data.getDataDesc());            respData.setDataName(data.getDataName());            respData.setDataFile(data.getDataFile());            respData.setUrl(data.getUrl());            //获取用户昵称            User user = userDAO.getUserById(data.getOpenid());            respData.setNickname(user.getNickname());            respData.setUploadTime(data.getUploadTime());            respData.setDid(data.getDid());            respDatas.add(respData);        }        return respDatas;    }    public String saveFile(MultipartFile file) throws IOException {        // xxx._ = adfa.jpg        //找到 . 的位置        int dopos = file.getOriginalFilename().lastIndexOf(".");        if (dopos < 0) {            return null;        }        //找到后缀        String fileExt = file.getOriginalFilename().substring(dopos + 1).toLowerCase();        if (!studyShareUtil.isFileAllow(fileExt)) {            return null;        }        //格式符合        String filename = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;        Files.copy(file.getInputStream(), new File(studyShareUtil.FILE_DIR + filename).toPath()                , StandardCopyOption.REPLACE_EXISTING);        //return studyShareUtil.studyShare_DOMAIN + "file?filename=" + filename;        return studyShareUtil.studyShare_DOMAIN + filename;    }    public List<ResponseData> queryData(String keywords){        keywords = "%" + keywords + "%";        List<Data> datas = dataDAO.queryData(keywords, 1);        List<ResponseData> respDatas = new ArrayList<>();        for (Data data : datas) {            ResponseData respData = new ResponseData();            respData.setCid(data.getCid());            respData.setDataDesc(data.getDataDesc());            respData.setDataName(data.getDataName());            respData.setDataFile(data.getDataFile());            respData.setUrl(data.getUrl());            //获取用户昵称            User user = userDAO.getUserById(data.getOpenid());            respData.setNickname(user.getNickname());            respData.setUploadTime(data.getUploadTime());            respData.setDid(data.getDid());            respDatas.add(respData);        }        return respDatas;    }    /**     * 用户已下载     * @param did     */    public void download(int did){        User user = userDAO.getUserById(hostHolder.getUser().getOpenid());        String beforeDownload = user.getDownloaded();        String downloaded = null;        if (StringUtils.isEmpty(beforeDownload)) {            downloaded = did + "-";        } else {            downloaded = user.getDownloaded() + did + "-";        }        //保存用户已下载资源        userDAO.updateDownload(hostHolder.getUser().getOpenid(),downloaded);    }    public void saveData(int did) {        User user = userDAO.getUserById(hostHolder.getUser().getOpenid());        String beforeCollection = user.getCollection();        String collection = null;        if (StringUtils.isEmpty(beforeCollection)) {             collection = did + "-";        } else {            collection = beforeCollection + did + "-";        }        //保存用户已收藏资源        userDAO.updateCollection(hostHolder.getUser().getOpenid(), collection);    }    public List<ResponseData> recommend() {        List<Data> datas = dataDAO.recommend(1);        List<ResponseData> respDatas = new ArrayList<>();        for (Data data : datas) {            ResponseData respData = new ResponseData();            respData.setCid(data.getCid());            respData.setDataDesc(data.getDataDesc());            respData.setDataName(data.getDataName());            respData.setDataFile(data.getDataFile());            respData.setUrl(data.getUrl());            //获取用户昵称            User user = userDAO.getUserById(data.getOpenid());            respData.setNickname(user.getNickname());            respData.setUploadTime(data.getUploadTime());            respData.setDid(data.getDid());            respDatas.add(respData);        }        return respDatas;    }    public ResponseData getDataByDid(int cid) {        Data data = dataDAO.findDataByDid(cid, 1);        ResponseData respData = new ResponseData();        respData.setCid(data.getCid());        respData.setDataDesc(data.getDataDesc());        respData.setDataName(data.getDataName());        respData.setDataFile(data.getDataFile());        respData.setUrl(data.getUrl());        //获取用户昵称        User user = userDAO.getUserById(data.getOpenid());        respData.setNickname(user.getUsername());        respData.setUploadTime(data.getUploadTime());        respData.setDid(data.getDid());        return respData;    }}