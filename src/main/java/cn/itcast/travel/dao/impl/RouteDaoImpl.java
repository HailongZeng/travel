package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HailongZeng
 * @date 1/3/20 5:30 下午
 */
public class RouteDaoImpl implements RouteDao {

    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    /**
     * 查询总记录数
     * @param cid
     * @return
     */
    @Override
    public int findTotalCount(int cid, String rname) {
//        String sql = "select count(*) from tab_route where cid=?";
        //1.定义sql模板
        String sql = "select count(*) from tab_route where 1=1";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList(); //条件们
        //2.判断参数是否有值
        if (cid != 0){
            sb.append(" and cid=?");
            params.add(cid); //添加?对应的值
        }
        if (rname != null && rname.length() > 0){
            sb.append(" and rname like ?");
            params.add("%"+rname+"%"); //添加?对应的值
        }
        sql = sb.toString();
        return template.queryForObject(sql, Integer.class, params.toArray());
    }

    /**
     * 查询每页的记录
     * @param cid
     * @param start
     * @param pageSize
     * @return
     */
    @Override
    public List<Route> findByPage(int cid, int start, int pageSize, String rname) {
//        String sql = "select * from tab_route where cid=? limit ?, ?";
        //1.定义sql模板
        String sql = "select * from tab_route where 1 = 1";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList(); //条件们
        //2.判断参数是否有值
        if (cid != 0){
            sb.append(" and cid=?");
            params.add(cid); //添加?对应的值
        }
        if (rname != null && rname.length() > 0){
            sb.append(" and rname like ?");
            params.add("%"+rname+"%"); //添加?对应的值
        }
        sb.append(" limit ?, ?");  //分页条件
        params.add(start);
        params.add(pageSize);
        sql = sb.toString();
        return template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), params.toArray());
    }

    /**
     * 根据rid查询
     * @param rid
     * @return
     */
    @Override
    public Route findOne(int rid) {
        String sql = "select * from tab_route where rid=?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rid);
    }
}
