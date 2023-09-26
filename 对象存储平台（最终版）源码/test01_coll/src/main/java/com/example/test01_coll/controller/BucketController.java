package com.example.test01_coll.controller;

import com.example.test01_coll.service.impl.BucketService;
import com.example.test01_coll.service.impl.GroupService;
import com.example.test01_coll.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/bucket")
public class BucketController {
    @Autowired
    BucketService bucketService;

    @Autowired
    GroupService groupService;

    /**
     * @param name      桶的名称
     * @param authority 对外权限
     */
    @RequestMapping("/add")
    public ResultUtil createBucket(String name, int authority) {

        boolean result = bucketService.addBucket(name, authority);

        if (result) {
            return new ResultUtil(200, "创建成功");
        } else {
            return new ResultUtil(500, "此bucket已存在");
        }
    }


    /**
     * 更新桶的对外权限
     *
     * @param bucketId  桶的id
     * @param authority 对外权限
     */
    @RequestMapping("/update/authority")
    public ResultUtil updateAuthority(int bucketId, int authority) {

        bucketService.updateAuthority(bucketId, authority);

        return new ResultUtil(200, "修改成功");
    }

    /**
     * 更改bucket的名字
     *
     * @param bucketId 桶的id
     * @param newName  桶的新名字
     */
    @RequestMapping("/update/name")
    public ResultUtil updateName(int bucketId, String newName) {

        bucketService.updateName(bucketId, newName);

        return new ResultUtil(200, "修改成功");
    }

    /**
     * 伪删除桶
     *
     * @param bucketId 要删除的桶的id
     */
    @RequestMapping("/delete")
    public ResultUtil deleteBucket(int bucketId) {

        bucketService.deleteBucket(bucketId);

        return new ResultUtil(200, "删除成功");
    }

    /**
     * 增加桶的用户
     *
     * @param bucketId 桶的id
     * @param email    用户邮箱
     * @param type     权限类别
     */
    @RequestMapping("/user/add")
    public ResultUtil addUser(int bucketId, String email, int type) {

        System.out.println(bucketId);

        System.out.println(type);

        groupService.addUserAuthority(bucketId, email, type);

        return new ResultUtil(200, "添加成功");
    }

    /**
     * 更改桶用户的权限
     *
     * @param bucketId 桶的id
     * @param email    用户邮箱
     * @param type     权限类别
     */
    @RequestMapping("/user/setAuth")
    public ResultUtil setUserAuthority(int bucketId, String email, int type) {

        groupService.setUserAuthority(bucketId, email, type);

        return new ResultUtil(200, "设置成功");
    }

    /**
     * 得到该用户对此bucket的权限
     *
     * @param bucketId 桶的id
     */
    @RequestMapping("/user/getAuth")
    public ResultUtil getUserAuthority(int bucketId) {

        ResultUtil resultUtil = new ResultUtil(200, "获取成功");

        Map<String, Object> map = new HashMap<>();

        map.put("authority", groupService.getUserAuthority(bucketId));

        resultUtil.setData(map);

        return resultUtil;
    }

    /**
     * 获得设置权限的用户列表
     *
     * @param bucketId 桶的id
     */
    @RequestMapping("/user/list")
    public ResultUtil getUserList(int bucketId) {
        ResultUtil resultUtil = new ResultUtil(200, "获取成功");

        Map<String, Object> map = new HashMap<>();

        map.put("userList", groupService.getUserList(bucketId));

        resultUtil.setData(map);

        return resultUtil;
    }

    /**
     * 我的bucket列表
     */
    @RequestMapping("/myBuckets")
    public ResultUtil getBuckets() {
        ResultUtil resultUtil = new ResultUtil(200, "获取成功");

        Map<String, Object> map = new HashMap<>();

        map.put("bucketList", bucketService.getBuckets());

        resultUtil.setData(map);

        return resultUtil;
    }

    /**
     * 删除的bucket列表
     */
    @RequestMapping("/delBuckets")
    public ResultUtil getDeletedBuckets() {
        ResultUtil resultUtil = new ResultUtil(200, "获取成功");

        Map<String, Object> map = new HashMap<>();

        map.put("bucketList", bucketService.getDeletedBuckets());

        resultUtil.setData(map);

        return resultUtil;
    }

    /**
     * 恢复桶
     *
     * @param bucketId 桶的id
     */
    @RequestMapping("/recovery")
    public ResultUtil recoveryBucket(int bucketId) {
        String msg = "恢复成功";

        int code = 200;

        boolean result = bucketService.recoveryBucket(bucketId);

        if (!result) {
            code = 500;

            msg = "恢复失败";
        }
        return new ResultUtil(code, msg);

    }

}
