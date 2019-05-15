package com.jsp.studyShare.Service;import com.jsp.studyShare.utils.RedisKeyUtils;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Service;import java.util.HashMap;import java.util.Map;@Servicepublic class LikeService {    @Autowired    JedisAdapter jedisAdapter;    /**     * 点赞：用户点赞后，被点赞的like集合中就会加上一个该点赞的用户信息     * @param openid     * @param entityId     * @return     */    public Map<String, Long> like(String openid, int entityId) {        Map<String, Long> map = new HashMap<>();        //在当前shares上点赞后获取key： LIKE:ENTITY_SHARE:2        String likeKey = RedisKeyUtils.getLikeKey(entityId);        //在喜欢的集合中添加当前操作用户的id        jedisAdapter.sadd(likeKey, openid);        String disLikeKey = RedisKeyUtils.getDisLikeKey(entityId);        jedisAdapter.srem(disLikeKey, openid);        //返回点赞数量        map.put("like", jedisAdapter.scard(likeKey));        map.put("disLike", jedisAdapter.scard(disLikeKey));        return map;    }    /**     * 反对     * @param openid     * @param entityId     * @return     */    public Map<String, Long> disLike(String openid, int entityId) {        Map<String, Long> map = new HashMap<>();        //反对集合加openid        String disLikeKey = RedisKeyUtils.getDisLikeKey(entityId);        jedisAdapter.sadd(disLikeKey, openid);        //从点赞集合删除openid        String likeKey = RedisKeyUtils.getLikeKey(entityId);        jedisAdapter.srem(likeKey, openid);        //返回点赞数量        map.put("like", jedisAdapter.scard(likeKey));        map.put("disLike", jedisAdapter.scard(disLikeKey));        return map;    }}