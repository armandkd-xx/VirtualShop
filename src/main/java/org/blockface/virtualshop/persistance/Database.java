package org.blockface.virtualshop.persistance;

import java.sql.ResultSet;

public interface Database
{
    public void Load() throws Exception;

    public ResultSet Query(String query);

    public void Unload();
}
