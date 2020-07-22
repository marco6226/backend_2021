/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.QueryParam;

/**
 *
 * @author fmoreno
 */
public class FilterQuery {

    @QueryParam("offset")
    private Integer offset;

    @QueryParam("rows")
    private Integer rows;

    @QueryParam("sortField")
    private String sortField;

    @QueryParam("sortOrder")
    private Integer sortOrder;

    @QueryParam("filterList")
    private String strFilter;

    @QueryParam("fieldList")
    private String strFields;
    
    @QueryParam("groupBy")
    private String groupBy;
    
    @QueryParam("count")
    private boolean count;

    private List<Filter> filterList;

    private String[] fieldList;

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public boolean isCount() {
        return count;
    }

    public void setCount(boolean count) {
        this.count = count;
    }

    public List<Filter> getFilterList() throws IOException {
        if (filterList == null && strFilter != null) {
            TypeReference<List<Filter>> type = new TypeReference<List<Filter>>() {
            };
            filterList = new ObjectMapper().readValue(this.strFilter, type);
        }
        if (filterList == null) {
            filterList = new ArrayList<>();
        }
        return filterList;

    }

    public void setFilterList(List<Filter> filterList) {
        this.filterList = filterList;
    }

    public String[] getFieldList() throws IOException {
        if (strFields != null) {
            return strFields.split(",");
        }
        return null;
    }

    public void setFieldList(String[] fieldList) {
        this.fieldList = fieldList;
    }

    public void setStrFilter(String strFilter) {
        this.strFilter = strFilter;
    }

    public void setStrFields(String strFields) {
        this.strFields = strFields;
    }

    /**
     * @return the groupBy
     */
    public String getGroupBy() {
        return groupBy;
    }

    /**
     * @param groupBy the groupBy to set
     */
    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
    }

}
