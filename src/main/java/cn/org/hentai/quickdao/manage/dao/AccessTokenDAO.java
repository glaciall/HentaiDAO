package cn.org.hentai.quickdao.manage.dao;

import cn.org.hentai.db.DBAccess;
import cn.org.hentai.quickdao.manage.model.AccessToken;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by matrixy on 2017/8/27.
 */
@Repository
public class AccessTokenDAO extends DBAccess
{
    public long save(AccessToken token)
    {
        return insertInto().valueWith(token).save();
    }

    public AccessToken getByToken(long uid, String token)
    {
        List<AccessToken> tokens = select().where(clause("token = ?", token).and("user_id = ?", uid)).orderBy("id", "desc").queryForLimit(AccessToken.class, 1);
        if (tokens.size() > 0) return tokens.get(0);
        else return null;
    }

    public String configureTableName()
    {
        return "accesstokens";
    }

    public String[] configureFields()
    {
        return new String[] { "id", "user_id", "token", "rand", "expire_time", "create_time" };
    }

    public String primaryKey()
    {
        return "id";
    }
}
