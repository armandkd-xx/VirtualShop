package org.blockface.virtualshop.persistance;

import java.sql.ResultSet;

public interface Database
{
    public void Load() throws Exception;

    public ResultSet SelectQuery(String query);

    public void DeleteQuery(String query);

    public void UpdateQuery(String query);

    public void InsertQuery(String query);
}
